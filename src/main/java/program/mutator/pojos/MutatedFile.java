package program.mutator.pojos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.io.FileUtils;

import program.mutator.pojos.user.TestCase;
import program.mutator.pojos.user.UserInput;

public class MutatedFile {
	public static String filePath;
	private String fileName;
	private static String originalFilePath;
	private static String originalFileName;
	public static String fileType;
	public static int mutationNumber = 0;
	private int lineMutated;
	private String mutatedLine;
	private static TestCase sampleInput;
	public static ArrayList<String> originalFileLines = new ArrayList<String>();
	public ArrayList<String> mutatedFileLines = new ArrayList<String>();
	public static ArrayList<MutatedLine> mutatedLines = new ArrayList<MutatedLine>();
	
	/*MUST BE CALLED AT START OF PROCESS*/
	public static void initializePaths(String originalPath, String programName, String fileType) {
		MutatedFile.originalFilePath = originalPath;
		MutatedFile.originalFileName = programName;
		MutatedFile.fileType = fileType;
		MutatedFile.filePath = MutatedFile.originalFilePath + "mutatedFiles/";
		new File(MutatedFile.filePath).delete();
		MutatedFile.mutationNumber = 0;
		MutatedFile.mutatedLines = new ArrayList<MutatedLine>();
	}
	
	public MutatedFile(int lineMutated, String mutatedLine, TestCase sampleInput) {
		MutatedFile.mutationNumber++;
		this.fileName = "Mutation" + MutatedFile.mutationNumber;
		this.lineMutated = lineMutated;
		this.mutatedLine = mutatedLine;
		MutatedFile.sampleInput = sampleInput;
		mutate();
	}

	public String getMutatedLine() {
		return mutatedLine;
	}

	public void setMutatedLine(String mutatedLine) {
		this.mutatedLine = mutatedLine;
	}

	public ArrayList<String> getMutatedFileLines() {
		return mutatedFileLines;
	}

	public void setMutatedFileLines(ArrayList<String> mutatedFileLines) {
		this.mutatedFileLines = mutatedFileLines;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		MutatedFile.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static void setFileType(String fileType) {
		MutatedFile.fileType = fileType;
	}

	public static void setOriginalFilePath(String originalFilePath) {
		MutatedFile.originalFilePath = originalFilePath;
	}

	public static void setOriginalFileName(String originalFileName) {
		MutatedFile.originalFileName = originalFileName;
	}

	public int getMutationNumber() {
		return mutationNumber;
	}

	public int getLineMutated() {
		return lineMutated;
	}

	public void setLineMutated(int lineMutated) {
		this.lineMutated = lineMutated;
	}

	public static ArrayList<String> getOriginalFileLines() {
		ArrayList<String> fileLines = new ArrayList<String>();
		for(String fileLine : originalFileLines) {
			fileLines.add(fileLine);
		}
		return fileLines;
	}

	public static void setOriginalFileLines(ArrayList<String> originalFileLines) {
		MutatedFile.originalFileLines = originalFileLines;
	}
	
	public void mutate() {
		try {
			//get content of mutated file
			this.mutatedFileLines = getOriginalFileLines();
			this.mutatedFileLines.set(this.lineMutated - 1, this.mutatedLine);
			MutatedFile.mutatedLines.add(new MutatedLine(this.mutatedLine, this.lineMutated));
			StringBuilder mutatedFileContentStringBuilder = new StringBuilder();
			for(String mutatedLine : this.mutatedFileLines) {
				if(!mutatedLine.startsWith("package")) {
					if(mutatedLine.contains(originalFileName) && mutatedLine.contains("class")) {
						mutatedLine = mutatedLine.replace(originalFileName, this.fileName);
					}
					mutatedFileContentStringBuilder.append(mutatedLine + "\n");
				}
			}
			
			//make sure there is an empty folder for mutated files
			Path path = Paths.get(MutatedFile.filePath + this.fileName + MutatedFile.fileType);
			if(MutatedFile.mutationNumber == 1) {
				if(new File(MutatedFile.filePath).exists())
				{
					FileUtils.deleteDirectory(new File(MutatedFile.filePath));
				}
				new File(MutatedFile.filePath).mkdirs();
			}
			
			//create mutated file and add it to the path
			Files.createFile(path);
			BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(path), true));
	        writer.write(String.valueOf(mutatedFileContentStringBuilder));
	        writer.flush();
	        writer.close();
	        if(!UserInput.willFileRunWell(path.toString(), MutatedFile.sampleInput)) {
	        	path.toFile().delete();
	        	new File(MutatedFile.filePath + this.fileName + ".class").delete();
	        	MutatedFile.mutatedLines.remove(MutatedFile.mutatedLines.size()-1);
	    		MutatedFile.mutationNumber--;
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void condenseFileNumber(int numberOfMutations) {
		new File(MutatedFile.filePath).delete();
		int numberToRemove = MutatedFile.mutationNumber - numberOfMutations;
		MutatedFile.mutationNumber = 0;
		ArrayList<MutatedLine> newLines = new ArrayList<MutatedLine>(MutatedFile.mutatedLines);
		MutatedFile.mutatedLines = new ArrayList<MutatedLine>();
		
		for(int i = 0; i < numberToRemove; i++) {
			newLines.remove(new Random().nextInt(newLines.size()));
		}
		
		for(MutatedLine line : newLines) {
			new MutatedFile(line.getLineNumber(), line.getLine(), sampleInput);
		}
	}
}
