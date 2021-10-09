package program.mutator.pojos.enums;

import java.util.Arrays;

public enum ScriptRunOutput {
	RUNNING_NEW_FILE("Running New File"),
	ERROR("err");
	
	
	private String outputString;
	
	ScriptRunOutput(String outputString) {
		this.outputString = outputString;
	}
	
	@Override
	public String toString() {
		return this.outputString;
	}
	
	public static ScriptRunOutput fromString(String outputString) {
		return Arrays.stream(ScriptRunOutput.values()).filter(out -> out.outputString.equals(outputString)).findFirst().orElse(null);
	}
}
