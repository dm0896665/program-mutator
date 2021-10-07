package program.mutator.pojos;

import java.util.ArrayList;

public class ScriptOutput {
	private ArrayList<TestCaseOutput> testCaseOutputs = new ArrayList<TestCaseOutput>();
	
	public ScriptOutput(ArrayList<TestCaseOutput> testCaseOutputs) {
		this.testCaseOutputs = testCaseOutputs;
	}

	public ScriptOutput() {
	}

	public ArrayList<TestCaseOutput> getTestCaseOutputs() {
		return testCaseOutputs;
	}

	public void setTestCaseOutputs(ArrayList<TestCaseOutput> testCaseOutputs) {
		this.testCaseOutputs = testCaseOutputs;
	}
	
	@Override
	public String toString() {
		return this.testCaseOutputs.toString();
	}
}
