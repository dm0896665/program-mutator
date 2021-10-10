package program.mutator.pojos.enums.possible.mutation.items;

import java.util.Arrays;

import program.mutator.pojos.interfaces.MutationItemInterface;

public enum NumberType  implements MutationItemInterface {
	INT("int"),
	DOUBLE("double"),
	FLOAT("float"),
	LONG("long"),
	SHORT("short");
	
	
	private String outputString;
	
	NumberType(String outputString) {
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
