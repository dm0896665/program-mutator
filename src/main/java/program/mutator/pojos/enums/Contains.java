package program.mutator.pojos.enums;

import java.util.Arrays;

public enum Contains {
	EQUAL_SIGN("="),
	DECIMAL_POINT("."),
	NOTHING("");
	
	
	String outputString;
	
	Contains(String outputString) {
		this.outputString = outputString;
	}
	
	@Override
	public String toString() {
		return this.outputString;
	}
	
	public static Contains fromString(String outputString) {
		return Arrays.stream(Contains.values()).filter(out -> out.outputString.equals(outputString)).findFirst().orElse(null);
	}
}