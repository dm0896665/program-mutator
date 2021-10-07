package program.mutator.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import program.mutator.helpers.InequalityHelper;
import program.mutator.pojos.MutatedFile;

public class ProgramMutatorController {
	public static final String path_bash = "C:/Path/to/Git/bin/bash.exe";
	private String pathOfProgram = "C:/Path/to/file/";
	private String programName = "PrimeTest";
	private String programEnding = ".java";
	private Path fullProgramPath = Paths.get(pathOfProgram + programName + programEnding);
	private boolean inClass = false;
	
	public ProgramMutatorController() {
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
						}
					}
				}
			}
		}
		
		//run mutated files
		runMutatedFiles();
		//System.out.println(readFileTxt());
	}
	
	private void runMutatedFiles() {
		try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            String command = "sh " + System.getProperty("user.dir").replace("\\", "/") + "/src/main/resources/calculate-mutation-score.sh " + MutatedFile.filePath.substring(0, MutatedFile.filePath.length() - 1);
            processBuilder.command(path_bash, "-c", command);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            BufferedReader errReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            String errLine;
            StringBuilder errOutput = new StringBuilder();
            while ((errLine = errReader.readLine()) != null) {
            	errOutput.append(errLine + "\n");
            }
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println(" --- Files run successfully");
                System.out.println(output);

            } else {
                System.out.println(" --- Files run unsuccessfully");
                System.out.println(errOutput);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(" --- Interruption in RunCommand: " + e);
            // Restore interrupted state
            Thread.currentThread().interrupt();
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
