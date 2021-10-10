package program.mutator.pojos.enums.possible.mutation.items;

import java.util.Arrays;

public enum Inequality {
	EQUALS("=="),
	NOT_EQUALS("!="),
	GREATER_THAN_OR_EQUAL_TO(">="),
	LESS_THAN_OR_EQUAL_TO("<="),
	LESS_THAN("<"),
	GREATER_THAN(">");
	
	
	String outputString;
	
	Inequality(String outputString) {
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
