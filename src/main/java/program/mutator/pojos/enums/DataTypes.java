package program.mutator.pojos.enums;

import java.util.Arrays;

public enum DataTypes {
	BYTE("byte"),
	SHORT("short"),
	INT("int"),
	LONG("long"),
	FLOAT("float"),
	DOUBLE("double"),
	BOOLEAN("boolean"),
	CHAR("char"),
	STRING("String"),
	BYTE_ARRAY("byte[]"),
	SHORT_ARRAY("short[]"),
	INT_ARRAY("int[]"),
	LONG_ARRAY("long[]"),
	FLOAT_ARRAY("float[]"),
	DOUBLE_ARRAY("double[]"),
	BOOLEAN_ARRAY("boolean[]"),
	CHAR_ARRAY("char[]"),
	STRING_ARRAY("String[]");
	
	
	String outputString;
	
	DataTypes(String outputString) {
		this.outputString = outputString;
	}
	
	@Override
	public String toString() {
		return this.outputString;
	}
	
	public static DataTypes fromString(String outputString) {
		return Arrays.stream(DataTypes.values()).filter(out -> out.outputString.equals(outputString)).findFirst().orElse(null);
	}
	
	public boolean equals(DataTypes type) {
		return this.outputString.equals(type.toString());
	}
}
