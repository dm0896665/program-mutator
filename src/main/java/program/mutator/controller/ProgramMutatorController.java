package program.mutator.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		
		for(int i = 0; i < MutatedFile.originalFileLines.size(); i++) {
			String line = MutatedFile.originalFileLines.get(i);
			if(line.contains("class")) {
				inClass = true;
			}
			
			if(inClass) {
				if(isLineMutatable(line)) {
					int lineNumber = i + 1;
					MutatedFile mutatedFile = new MutatedFile(this.pathOfProgram, lineNumber);
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
	
	private boolean isLineMutatable(String Line) {
		boolean isMutatable = false;
		
		return isMutatable;
	}
}
