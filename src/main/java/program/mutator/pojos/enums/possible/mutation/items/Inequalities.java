package program.mutator.pojos.enums.possible.mutation.items;

import java.util.Arrays;

public enum Inequalities {
	EQUALS("=="),
	NOT_EQUALS("!="),
	GREATER_THAN_OR_EQUAL_TO(">="),
	LESS_THAN_OR_EQUAL_TO("<="),
	LESS_THAN(">"),
	GREATER_THAN("<");
	
	
	String outputString;
	
	Inequalities(String outputString) {
		this.outputString = outputString;
	}
	
	@Override
	public String toString() {
		return this.outputString;
	}
	
	public static Inequalities fromString(String outputString) {
		return Arrays.stream(Inequalities.values()).filter(out -> out.outputString.equals(outputString)).findFirst().orElse(null);
	}
}
