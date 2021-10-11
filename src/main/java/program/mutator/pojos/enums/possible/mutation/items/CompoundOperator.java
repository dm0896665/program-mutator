package program.mutator.pojos.enums.possible.mutation.items;

import java.util.Arrays;

public enum CompoundOperator {
	ADD("+="),
	SUBTRACT("-="),
	MULTIPLY("*="),
	DIVIDE("/=");
	
	
	String outputString;
	
	CompoundOperator(String outputString) {
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
