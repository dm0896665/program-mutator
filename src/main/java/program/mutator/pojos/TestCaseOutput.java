package program.mutator.pojos;

import java.util.ArrayList;

public class TestCaseOutput {
	ArrayList<MutatedFileOutput> mutatedFileOutput = new ArrayList<MutatedFileOutput>();

	public TestCaseOutput(ArrayList<MutatedFileOutput> mutatedFileOutput) {
		this.mutatedFileOutput = mutatedFileOutput;
	}
	
	public TestCaseOutput() {
	}

	public ArrayList<MutatedFileOutput> getMutatedFileOutput() {
		return mutatedFileOutput;
	}

	public void setMutatedFileOutput(ArrayList<MutatedFileOutput> mutatedFileOutput) {
		this.mutatedFileOutput = mutatedFileOutput;
	}

	@Override
	public String toString() {
		return "TestCaseOutput [mutatedFileOutput=" + mutatedFileOutput + "]";
	}

	
}
