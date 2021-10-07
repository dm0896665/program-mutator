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

public class ProgramMutatorController {
	public static final String path_bash = "C:/Path/to/Git/bin/bash.exe";
	private String pathOfProgram = "C:/Path/to/file/";
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
		ArrayList<List<String>> outputs = new ArrayList<List<String>>();
		for(int i = 0; i < this.inputs.size(); i++){
			ArrayList<String> output = runMutatedFiles(this.inputs.get(i));
			
			//there was a problem in executing mutated files
			if(output == null || "err".equals(output.get(0))) {
				//notify user of problem and restart process
				for(String out : output) {
					System.out.println(out);
				}
				break;
			} 
			
			for(String out : output) {
				System.out.println(out);	
			}
		}
		
	}
	
	private ArrayList<String> runMutatedFiles(List<String> inputs) {
		try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            StringBuilder arrayToPass = new StringBuilder();
            for(String input : inputs) {
            	arrayToPass.append(input + " ");
            }
            String command = "sh " + System.getProperty("user.dir").replace("\\", "/") + "/src/main/resources/calculate-mutation-score.sh " + MutatedFile.filePath.substring(0, MutatedFile.filePath.length() - 1) + " " + arrayToPass;
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
                output.add(line + "\n");
            }
            String errLine;
            ArrayList<String> errOutput = new ArrayList<String>(Arrays.asList("err"));
            while ((errLine = errReader.readLine()) != null) {
            	errOutput.add(errLine + "\n");
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
