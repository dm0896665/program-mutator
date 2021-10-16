package program.mutator.pojos.user;

import java.util.ArrayList;

public class TestSet {
	private ArrayList<TestCase> testCases = new ArrayList<TestCase>();
	
	public TestSet() {
	}
	
	public TestSet(ArrayList<TestCase> testCases) {
		this.testCases = testCases;
	}

	public void addTestCase(TestCase testCase) {
		this.testCases.add(testCase);
	}

	public ArrayList<TestCase> getTestCases() {
		return testCases;
	}

	public void setTestCases(ArrayList<TestCase> testCases) {
		this.testCases = testCases;
	}

	@Override
	public String toString() {
		return "TestSet [testCases=" + testCases + "]";
	}
}
