package program.mutator.pojos.output;

import java.util.ArrayList;

public class ExpectedOutput {
	private ArrayList<String> allOutput = new ArrayList<String>();

	public ExpectedOutput() {
	}

	public ExpectedOutput(ArrayList<String> allOutput) {
		this.allOutput = allOutput;
	}

	public ArrayList<String> getAllOutput() {
		return allOutput;
	}

	public void setAllOutput(ArrayList<String> allOutput) {
		this.allOutput = allOutput;
	}

	@Override
	public String toString() {
		return "ExpectedOutput [allOutput=" + allOutput + "]";
	}
	
	
}
