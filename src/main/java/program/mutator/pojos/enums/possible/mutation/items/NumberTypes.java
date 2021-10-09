package program.mutator.pojos.enums.possible.mutation.items;

import java.util.Arrays;

public enum NumberTypes {
	INT("int"),
	DOUBLE("double"),
	FLOAT("float"),
	LONG("long"),
	SHORT("short");
	
	
	private String outputString;
	
	NumberTypes(String outputString) {
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
