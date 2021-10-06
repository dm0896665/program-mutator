package program.mutator.pojos;

import java.util.ArrayList;

public class MutatedFile {
	private String filePath;
	private int mutationNumber;
	private int lineMutated;
	private String mutatedLine;
	public static ArrayList<String> originalFileLines = new ArrayList<String>();
	public ArrayList<String> mutatedFileLines;
	
	public MutatedFile(String filePath, int lineMutated) {
		this.filePath = filePath;
		this.lineMutated = lineMutated;
	}
	
	public MutatedFile(String filePath, int mutationNumber, int lineMutated, String mutatedLine,
			ArrayList<String> mutatedFileLines) {
		this.filePath = filePath;
		this.mutationNumber = mutationNumber;
		this.lineMutated = lineMutated;
		this.mutatedLine = mutatedLine;
		this.mutatedFileLines = mutatedFileLines;
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
		this.filePath = filePath;
	}

	public int getMutationNumber() {
		return mutationNumber;
	}

	public void setMutationNumber(int mutationNumber) {
		this.mutationNumber = mutationNumber;
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
		this.mutatedFileLines = MutatedFile.originalFileLines;
		this.mutatedFileLines.set(this.lineMutated, this.mutatedLine);
	}
}
