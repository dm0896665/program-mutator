package program.mutator.pojos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class MutatedFile {
	public static String filePath;
	private String fileName;
	private static String originalFilePath;
	private static String originalFileName;
	public static String fileType;
	private static int mutationNumber = 0;
	private int lineMutated;
	private String mutatedLine;
	public static ArrayList<String> originalFileLines = new ArrayList<String>();
	public ArrayList<String> mutatedFileLines = new ArrayList<String>();
	
	/*MUST BE CALLED AT START OF PROCESS*/
	public static void initializePaths(String originalPath, String programName, String fileType) {
		MutatedFile.originalFilePath = originalPath;
		MutatedFile.originalFileName = programName;
		MutatedFile.fileType = fileType;
		MutatedFile.filePath = MutatedFile.originalFilePath + "mutatedFiles/";
	}
	
	public MutatedFile(int lineMutated, String mutatedLine) {
		MutatedFile.mutationNumber++;
		this.fileName = "Mutation" + MutatedFile.mutationNumber;
		this.lineMutated = lineMutated;
		this.mutatedLine = mutatedLine;
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
		return originalFileLines;
	}

	public static void setOriginalFileLines(ArrayList<String> originalFileLines) {
		MutatedFile.originalFileLines = originalFileLines;
	}
	
	public void mutate() {
		try {
			//get content of mutated file
			this.mutatedFileLines = MutatedFile.originalFileLines;
			this.mutatedFileLines.set(this.lineMutated - 1, this.mutatedLine);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
