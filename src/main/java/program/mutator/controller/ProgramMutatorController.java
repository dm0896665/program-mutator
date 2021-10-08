package program.mutator.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import program.mutator.helpers.InequalityHelper;
import program.mutator.pojos.MutatedFile;
import program.mutator.pojos.MutatedFileOutput;
import program.mutator.pojos.MutationScore;
import program.mutator.pojos.ScriptOutput;
import program.mutator.pojos.TestCaseOutput;

public class ProgramMutatorController {
	public static final String path_bash = "D:/Program Files/Git/bin/bash.exe";
	private String pathOfProgram = "C:/Users/Dylan/OneDrive/y/College/src/mutation/test/";
	private String programName = "PrimeTest";
	private String programEnding = ".java";
	private Path fullProgramPath = Paths.get(pathOfProgram + programName + programEnding);
	private ArrayList<List<String>> inputs = new ArrayList<List<String>>();
	private ArrayList<String> expected = new ArrayList<String>(Arrays.asList("6 is not a prime number.", "9 is not a prime number.", "5 is a prime number."));
	private boolean inClass = false;
	
	public ProgramMutatorController() {
		//initialize inputs (will be done by gui
		this.inputs.add(Arrays.asList("6"));
		this.inputs.add(Arrays.asList("9"));
		this.inputs.add(Arrays.asList("5"));
		
		
		//get file to mutate and initialize process
		File f = fullProgramPath.toFile();
		
		getFileLinesFromFile(f);
		MutatedFile.initializePaths(pathOfProgram, programName, programEnding);
		
		//create mutated files
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
							System.out.println(mutatedLine);
						}
					}
				}
			}
		}
		
		//run mutated files
		ScriptOutput scriptOutput = new ScriptOutput();
		for(int i = 0; i < this.inputs.size(); i++){
			ArrayList<String> output = runMutatedFiles(this.inputs.get(i));
			scriptOutput.getTestCaseOutputs().add(new TestCaseOutput());
			
			//there was a problem in executing mutated files
			if(output == null || "err".equals(output.get(0))) {
				//notify user of problem and restart process
				for(String out : output) {
					System.out.println(out);
				}
				break;
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
		
		//calculate mutated score
		for(int i = 0; i < scriptOutput.getTestCaseOutputs().size(); i++) {
			TestCaseOutput testCaseOutput = scriptOutput.getTestCaseOutputs().get(i);
			MutationScore mutationScore = new MutationScore();
			for(MutatedFileOutput mutatedFileOutput : testCaseOutput.getMutatedFileOutput()) {
				ArrayList<String> fileOutput = mutatedFileOutput.getFileOutput();
				//System.out.println(fileOutput + " vs '" + expected.get(i) + "' ==>" + fileOutput.contains(expected.get(i)));
				if(fileOutput.contains(expected.get(i))) {
					mutationScore.addEquivalentMutants(1);
				} else {
					mutationScore.addDeadMutants(1);
				}
			}
			mutationScore.calculateScore();
			System.out.println(mutationScore);
		}
		
	}
	
	private ArrayList<String> runMutatedFiles(List<String> inputs) {
		try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            StringBuilder arrayToPass = new StringBuilder();
            for(String input : inputs) {
            	arrayToPass.append(input + " ");
            }
            String command = "sh " + System.getProperty("user.dir").replace("\\", "/") + "/src/main/resources/execute-mutants.sh " + MutatedFile.filePath.substring(0, MutatedFile.filePath.length() - 1) + " " + arrayToPass;
            processBuilder.command(path_bash, "-c", command);

            System.out.println("STARTING PROCESS................");
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
                System.out.println(" --- Files run successfully");
                return output;
            } else {
                System.out.println(" --- Files run unsuccessfully");
                return errOutput;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(" --- Interruption in RunCommand: " + e);
            // Restore interrupted state
            Thread.currentThread().interrupt();
            return null;
        }
	}

	private void getFileLinesFromFile(File f) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				MutatedFile.originalFileLines.add(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isLineMutatable(String line) {
		return InequalityHelper.lineHasInequality(line);
	}
}
