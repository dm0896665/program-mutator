package program.mutator.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import program.mutator.helpers.InequalityHelper;
import program.mutator.pojos.MutatedFile;

public class ProgramMutatorController {
	private String pathOfProgram = "C:\\Path\\to\\file\\";
	private String programName = "PrimeTest";
	private String programEnding = ".java";
	private Path fullProgramPath = Paths.get(pathOfProgram + programName + programEnding);
	private boolean inClass = false;
	
	public ProgramMutatorController() {
		File f = fullProgramPath.toFile();
		
		getFileLinesFromFile(f);
		MutatedFile.initializePaths(pathOfProgram, programName, programEnding);
		
		for(int i = 0; i < MutatedFile.originalFileLines.size(); i++) {
			String line = MutatedFile.originalFileLines.get(i);
			if(line.contains("class")) {
				inClass = true;
			}
			
			if(inClass) {
				int mutationCount = 0;
				if(isLineMutatable(line)) {
					int lineNumber = i + 1;
					if(InequalityHelper.lineHasInequality(line)) {
						for(String mutatedLine : InequalityHelper.mutateLine(line)) {
							mutationCount++;
							MutatedFile mutatedFile = new MutatedFile(mutationCount, lineNumber, mutatedLine);
							mutatedFile.mutate();
						}
					}
				}
			}
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
