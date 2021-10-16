package program.mutator.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import program.mutator.helpers.MutationScoreHelper;
import program.mutator.helpers.VariableHelper;
import program.mutator.pojos.MutatedFile;
import program.mutator.pojos.MutationScore;
import program.mutator.pojos.enums.MutationStatus;
import program.mutator.pojos.enums.ScriptRunOutput;
import program.mutator.pojos.output.ExpectedOutput;
import program.mutator.pojos.output.MutatedFileOutput;
import program.mutator.pojos.output.ScriptOutput;
import program.mutator.pojos.output.TestCaseOutput;
import program.mutator.pojos.user.TestSet;
import program.mutator.pojos.user.UserInput;

public class ProgramMutatorController {
	private String bashPath;
	private String pathOfProgram;
	private String programName;
	private String programEnding;
	private TestSet inputs = new TestSet();
	private ArrayList<ExpectedOutput> expected = new ArrayList<ExpectedOutput>();
	private boolean debug = true;
	
	public ProgramMutatorController(UserInput input) {
		//initialize inputs
		this.bashPath = UserInput.getBashPath();
		this.pathOfProgram = input.getPathOfProgram();
		this.programName = input.getProgramName();
		this.programEnding = input.getProgramEnding();

		try {
			System.out.println("\nGetting Variables...");
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		VariableHelper.printVariables();

		try {
			System.out.println("\nInitializing Mutation Process...");
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(TestSet set : input.getTestSets()) {
			//initialize test set
			inputs = set;
			
			//initialize outputs
			try {
				System.out.println("\nCalculating Oringinal Outputs...");
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			UserInput.cleanFileLocation();
			UserInput.createRunnableOriginalProgram();
			ScriptOutput originalScriptOutput = findOutputFromFileRun(true);
			findExpectedOutputFromOriginalScripOutput(originalScriptOutput);
			
			//run mutated files
			try {
				System.out.println("\nCalculating Mutated Outputs...");
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ScriptOutput mutatedScriptOutput = findOutputFromFileRun(false);
			
			//calculate mutated score
			calculateMutationScoreFromOutput(mutatedScriptOutput);
		}
		
		//update scores
		MutationScoreHelper.recalculateAllScores();
		
		//print test sets and their respective scores
		try {
			System.out.println("\nUpdating all mutation scores...");
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < MutationScoreHelper.mutationScores.size(); i++) {
			System.out.println("Test Set: '" + input.getTestSets().get(i) + "'  ===> Mutation Score: '" + MutationScoreHelper.mutationScores.get(i) + "'");
		}
		MutationScoreHelper.mutationScores = new ArrayList<MutationScore>();
	}

	private ScriptOutput findOutputFromFileRun(boolean runOriginal) {
		ScriptOutput scriptOutput = new ScriptOutput();
		for(int i = 0; i < this.inputs.getTestCases().size(); i++){
			ArrayList<String> output = runFiles(this.inputs.getTestCases().get(i).getInputs(), runOriginal);
			scriptOutput.getTestCaseOutputs().add(new TestCaseOutput());
			
			//there was a problem in executing mutated files
			if(output == null || ScriptRunOutput.ERROR.toString().equals(output.get(0))) {
				//notify user of problem and restart process
				for(String out : output) {
					if(debug)System.out.println("Script Run Error=> " + out);
				}
				return null;
			} 
			int count = 0;
			for(String out : output) {
				if(ScriptRunOutput.RUNNING_NEW_FILE.toString().equals(out)) {
					count++;
					scriptOutput.getTestCaseOutputs().get(i).getMutatedFileOutput().add(new MutatedFileOutput());
				} else {
					scriptOutput.getTestCaseOutputs().get(i).getMutatedFileOutput().get(count - 1).getFileOutput().add(out);
				}
			}
		}
		return scriptOutput;
	}
	
	private ArrayList<String> runFiles(List<Object> inputs, boolean runOriginal) {
		try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            
            String command = createCommand(inputs, runOriginal);
            
            processBuilder.command(bashPath, "-c", command);

            if(debug)System.out.println("STARTING '" + command + "' PROCESS................");
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
            ArrayList<String> errOutput = new ArrayList<String>(Arrays.asList(ScriptRunOutput.ERROR.toString()));
            while ((errLine = errReader.readLine()) != null) {
            	errOutput.add(errLine);
            }
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                if(debug)System.out.println(" --- '" + command + "' ran successfully");
                return output;
            } else {
                if(debug)System.out.println(" --- '" + command + "' ran unsuccessfully");
                return errOutput;
            }
        } catch (IOException | InterruptedException e) {
            if(debug)System.out.println(" --- Interruption in RunCommand: " + e);
            // Restore interrupted state
            Thread.currentThread().interrupt();
            return null;
        }
	}
	
	private String createCommand(List<Object> inputs, boolean runOriginal) {
		String scriptPath = System.getProperty("user.dir").replace("\\", "/") + "/resources/scripts/";
        String scriptName = ((runOriginal)? "execute-original" : "execute-mutants") + ".sh";
        String script = scriptPath + scriptName;
        String pathArg = ((runOriginal)? this.pathOfProgram : MutatedFile.filePath.substring(0, MutatedFile.filePath.length() - 1));
        String programName = ((runOriginal)? (this.programName + "Original") : "");
        StringBuilder arrayToPass = new StringBuilder();
        for(Object input : inputs) {
        	arrayToPass.append(input.toString() + " ");
        }
        String scriptArgs = pathArg + " " + programName + " " + arrayToPass;
        
        return "sh " + script + " " + scriptArgs;
	}
	
	private void calculateMutationScoreFromOutput(ScriptOutput mutatedScriptOutput) {
		//for each test case output
		MutationScore mutationScore = new MutationScore();
		for(int i = 0; i < mutatedScriptOutput.getTestCaseOutputs().size(); i++) {
			TestCaseOutput testCaseOutput = mutatedScriptOutput.getTestCaseOutputs().get(i);
			//for each mutated file output
			for(int j = 0; j < testCaseOutput.getMutatedFileOutput().size(); j++) {
				MutatedFileOutput mutatedFileOutput = testCaseOutput.getMutatedFileOutput().get(j);
				ArrayList<String> fileOutput = mutatedFileOutput.getFileOutput();
				if(debug)System.out.println(expected.get(i).getAllOutput() + " vs '" + fileOutput + "' ==>" + fileOutput.containsAll(expected.get(i).getAllOutput()));
				//if the mutated file outputs the same as the original
				if(fileOutput.containsAll(expected.get(i).getAllOutput())) {
					//if it's the first run add a new mutation status 
					if(mutationScore.getMutationStatuses().size() <= j) {
						mutationScore.getMutationStatuses().add(MutationStatus.EQUIVALENT);
					}
				//otherwise the mutant is dead
				} else {
					//if it's the first run add a new mutation status
					if(mutationScore.getMutationStatuses().size() <= j) {
						mutationScore.getMutationStatuses().add(MutationStatus.DEAD);
					} else {
						//if the mutant hasn't been killed yet, kill it
						if(!mutationScore.getMutationStatuses().get(j).equals(MutationStatus.DEAD)) {
							mutationScore.getMutationStatuses().set(j, MutationStatus.DEAD);
						}
					}
				}
			}
		}
		mutationScore.calculateScore();
		MutationScoreHelper.mutationScores.add(mutationScore);
		if(debug)System.out.println(mutationScore);
	}
	
	private void findExpectedOutputFromOriginalScripOutput(ScriptOutput originalScriptOutput) {
		expected = new ArrayList<ExpectedOutput>();
		for(int i = 0; i < originalScriptOutput.getTestCaseOutputs().size(); i++) {
			TestCaseOutput testCaseOutput = originalScriptOutput.getTestCaseOutputs().get(i);
			for(MutatedFileOutput mutatedFileOutput : testCaseOutput.getMutatedFileOutput()) {
				ArrayList<String> fileOutput = mutatedFileOutput.getFileOutput();
				this.expected.add(new ExpectedOutput(fileOutput));
			}
		}
	}

	public String getProgramEnding() {
		return programEnding;
	}

	public void setProgramEnding(String programEnding) {
		this.programEnding = programEnding;
	}
}
