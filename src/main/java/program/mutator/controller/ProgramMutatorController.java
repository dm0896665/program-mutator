package program.mutator.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import program.mutator.helpers.InequalityHelper;
import program.mutator.pojos.ExpectedOutput;
import program.mutator.pojos.MutatedFile;
import program.mutator.pojos.MutatedFileOutput;
import program.mutator.pojos.MutationScore;
import program.mutator.pojos.ScriptOutput;
import program.mutator.pojos.TestCaseOutput;

public class ProgramMutatorController {
	public static final String path_bash = "C:/Path/to/Git/bin/bash.exe";
	private String pathOfProgram = "C:/Path/to/file/";
	private String programName = "PrimeTest";
	private String programEnding = ".java";
	private Path fullProgramPath = Paths.get(pathOfProgram + programName + programEnding);
	private ArrayList<List<String>> inputs = new ArrayList<List<String>>();
	private ArrayList<ExpectedOutput> expected = new ArrayList<ExpectedOutput>(); //e.g. Arrays.asList("6 is not a prime number.", "9 is not a prime number.", "5 is a prime number.")
	private boolean inClass = false;
	private boolean debug = true;
	
	public ProgramMutatorController() {
		//initialize inputs (will be done by gui
		this.inputs.add(Arrays.asList("6"));
		this.inputs.add(Arrays.asList("9"));
		this.inputs.add(Arrays.asList("5"));
		
		//get lines from file
		File f = fullProgramPath.toFile();
		getFileLinesFromFile(f);
		MutatedFile.initializePaths(pathOfProgram, programName, programEnding);
		createRunnableOriginalProgram();
		
		//initialize outputs
		ScriptOutput originalScriptOutput = findOutputFromFileRun(true);
		findExpectedOutputFromOriginalScripOutput(originalScriptOutput);
		
		//create mutated files
		createMutatedFiles();
		
		//run mutated files
		ScriptOutput mutatedScriptOutput = findOutputFromFileRun(false);
		
		//calculate mutated score
		calculateMutationScoreFromOutput(mutatedScriptOutput);
		
	}

	private ScriptOutput findOutputFromFileRun(boolean runOriginal) {
		ScriptOutput scriptOutput = new ScriptOutput();
		for(int i = 0; i < this.inputs.size(); i++){
			ArrayList<String> output = runFiles(this.inputs.get(i), runOriginal);
			scriptOutput.getTestCaseOutputs().add(new TestCaseOutput());
			
			//there was a problem in executing mutated files
			if(output == null || "err".equals(output.get(0))) {
				//notify user of problem and restart process
				for(String out : output) {
					if(debug)System.out.println(out);
				}
				return null;
			} 
			int count = 0;
			for(String out : output) {
				if("Running New File".equals(out)) {
					count++;
					scriptOutput.getTestCaseOutputs().get(i).getMutatedFileOutput().add(new MutatedFileOutput());
				} else {
					scriptOutput.getTestCaseOutputs().get(i).getMutatedFileOutput().get(count - 1).getFileOutput().add(out);
				}
			}
		}
		return scriptOutput;
	}
	
	private ArrayList<String> runFiles(List<String> inputs, boolean runOriginal) {
		try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            
            String command = createCommand(inputs, runOriginal);
            
            processBuilder.command(path_bash, "-c", command);

            if(debug)System.out.println("STARTING PROCESS................");
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            BufferedReader errReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String line;
            ArrayList<String> output = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
            	//make sure to not get repeating lines
            	if(output.size() > 0) {
            		if(!(line).equals(output.get(output.size() - 1))) {
	            		output.add(line);
            		}
            	} else {
            		output.add(line);
            	}
            }
            String errLine;
            ArrayList<String> errOutput = new ArrayList<String>(Arrays.asList("err"));
            while ((errLine = errReader.readLine()) != null) {
            	errOutput.add(errLine);
            }
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                if(debug)System.out.println(" --- Files run successfully");
                return output;
            } else {
                if(debug)System.out.println(" --- Files run unsuccessfully");
                return errOutput;
            }
        } catch (IOException | InterruptedException e) {
            if(debug)System.out.println(" --- Interruption in RunCommand: " + e);
            // Restore interrupted state
            Thread.currentThread().interrupt();
            return null;
        }
	}
	
	private String createCommand(List<String> inputs, boolean runOriginal) {
		String scriptPath = System.getProperty("user.dir").replace("\\", "/") + "/src/main/resources/";
        String scriptName = ((runOriginal)? "execute-original" : "execute-mutants") + ".sh";
        String script = scriptPath + scriptName;
        String pathArg = ((runOriginal)? this.pathOfProgram : MutatedFile.filePath.substring(0, MutatedFile.filePath.length() - 1));
        String programName = ((runOriginal)? (this.programName + "Original") : "");
        StringBuilder arrayToPass = new StringBuilder();
        for(String input : inputs) {
        	arrayToPass.append(input + " ");
        }
        String scriptArgs = pathArg + " " + programName + " " + arrayToPass;
        
        return "sh " + script + " " + scriptArgs;
	}

	private void getFileLinesFromFile(File f) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			while (line != null) {
				if(debug)System.out.println(line);
				MutatedFile.originalFileLines.add(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createMutatedFiles() {
		for(int i = 0; i < MutatedFile.originalFileLines.size(); i++) {
			String line = MutatedFile.originalFileLines.get(i);
			if(line.contains("class")) {
				inClass = true;
			}
			
			if(inClass) {
				if(isLineMutatable(line)) {
					int lineNumber = i + 1;
					if(InequalityHelper.lineHasInequality(line)) {
						for(String mutatedLine : InequalityHelper.mutateLine(line)) {
							new MutatedFile(lineNumber, mutatedLine);
							if(debug)System.out.println("Mutated Line=> " + mutatedLine);
						}
					}
				}
			}
		}
	}
	
	private void calculateMutationScoreFromOutput(ScriptOutput mutatedScriptOutput) {
		for(int i = 0; i < mutatedScriptOutput.getTestCaseOutputs().size(); i++) {
			TestCaseOutput testCaseOutput = mutatedScriptOutput.getTestCaseOutputs().get(i);
			MutationScore mutationScore = new MutationScore();
			for(MutatedFileOutput mutatedFileOutput : testCaseOutput.getMutatedFileOutput()) {
				ArrayList<String> fileOutput = mutatedFileOutput.getFileOutput();
				if(debug)System.out.println(expected.get(i).getAllOutput() + " vs '" + fileOutput + "' ==>" + fileOutput.containsAll(expected.get(i).getAllOutput()));
				if(fileOutput.containsAll(expected.get(i).getAllOutput())) {
					mutationScore.addEquivalentMutants(1);
				} else {
					mutationScore.addDeadMutants(1);
				}
			}
			mutationScore.calculateScore();
			if(debug)System.out.println(mutationScore);
		}
	}
	
	private void findExpectedOutputFromOriginalScripOutput(ScriptOutput originalScriptOutput) {
		for(int i = 0; i < originalScriptOutput.getTestCaseOutputs().size(); i++) {
			TestCaseOutput testCaseOutput = originalScriptOutput.getTestCaseOutputs().get(i);
			MutationScore mutationScore = new MutationScore();
			for(MutatedFileOutput mutatedFileOutput : testCaseOutput.getMutatedFileOutput()) {
				ArrayList<String> fileOutput = mutatedFileOutput.getFileOutput();
				this.expected.add(new ExpectedOutput(fileOutput));
			}
		}
	}
	
	public void createRunnableOriginalProgram() {
		try {
			//get content of original file
			ArrayList<String> lines = MutatedFile.originalFileLines;
			StringBuilder originalFileContentStringBuilder = new StringBuilder();
			String fileName = this.programName + "Original";
			
			for(String line : lines) {
				if(!line.startsWith("package")) {
					if(line.contains(this.programName) && line.contains("class")) {
						line = line.replace(this.programName, fileName);
					}
					originalFileContentStringBuilder.append(line + "\n");
				}
			}
			
			//make sure there is an empty folder for mutated files
			Path path = Paths.get(this.pathOfProgram + fileName + MutatedFile.fileType);
			
			//create original file
			path.toFile().createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(path), true));
	        writer.write(String.valueOf(originalFileContentStringBuilder));
	        writer.flush();
	        writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isLineMutatable(String line) {
		return InequalityHelper.lineHasInequality(line);
	}
}
