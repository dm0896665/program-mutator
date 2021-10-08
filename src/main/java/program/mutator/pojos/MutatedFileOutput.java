package program.mutator.pojos;

import java.util.ArrayList;

public class MutatedFileOutput {
	ArrayList<String> fileOutput = new ArrayList<String>();

	public MutatedFileOutput(ArrayList<String> mutatedFileOutput) {
		this.fileOutput = mutatedFileOutput;
	}
	
	public MutatedFileOutput() {
	}

	public ArrayList<String> getFileOutput() {
		return fileOutput;
	}

	public void setFileOutput(ArrayList<String> mutatedFileOutput) {
		this.fileOutput = mutatedFileOutput;
	}

	@Override
	public String toString() {
		return "MutatedFileOutput [fileOutput=" + fileOutput + "]";
	}

	
}
