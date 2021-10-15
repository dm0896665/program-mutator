package program.mutator.pojos.enums;

import java.util.Arrays;

public enum Contains {
	EQUAL_SIGN("="),
	DECIMAL_POINT("."),
	NOTHING(""),
	ZERO("0"),
	ARRAY("[]"),
	QUOTATION_MARK("\""),
	STRING("String"),
	SYSTEM_DOT_OUT("System.out"),
	DOT_EQUALS(".equals(");
	
	
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
