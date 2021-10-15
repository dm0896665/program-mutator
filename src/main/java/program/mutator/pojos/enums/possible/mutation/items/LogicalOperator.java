package program.mutator.pojos.enums.possible.mutation.items;

import java.util.Arrays;

public enum LogicalOperator {
	NOT("!"),
	CONDITIONAL_AND("&&"),
	CONDITIONAL_OR("||"),
	AND("&"),
	OR("|"),
	XOR("^");
	
	
	String outputString;
	
	LogicalOperator(String outputString) {
		this.outputString = outputString;
	}
	
	@Override
	public String toString() {
		return this.outputString;
	}
	
	public static Inequality fromString(String outputString) {
		return Arrays.stream(Inequality.values()).filter(out -> out.outputString.equals(outputString)).findFirst().orElse(null);
	}
}
