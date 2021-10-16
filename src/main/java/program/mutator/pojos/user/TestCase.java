package program.mutator.pojos.user;

import java.util.ArrayList;
import java.util.List;

public class TestCase {
	private ArrayList<Object> inputs = new ArrayList<Object>();
	
	public TestCase() {
	}

	public TestCase(ArrayList<Object> inputs) {
		this.inputs = inputs;
	}

	public TestCase(List<String> inputs) {
		this.inputs = new ArrayList<Object>(inputs);
	}

	public ArrayList<Object> getInputs() {
		return inputs;
	}

	public void setInputs(ArrayList<Object> inputs) {
		this.inputs = inputs;
	}
	
	public void addInput(Object input) {
		this.inputs.add(input);
	}

	@Override
	public String toString() {
		return "TestCase [inputs=" + inputs + "]";
	}
}
