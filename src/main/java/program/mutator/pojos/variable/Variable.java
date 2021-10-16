package program.mutator.pojos.variable;

import program.mutator.pojos.enums.DataTypes;

public class Variable {
	private String name;
	private DataTypes dataType;
	private String value;
	private Scope scope;
	
	public Variable(String name, DataTypes dataType, String value, Scope scope) {
		this.name = name;
		this.dataType = dataType;
		this.value = value;
		this.scope = scope;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataTypes getDataType() {
		return dataType;
	}

	public void setDataType(DataTypes dataType) {
		this.dataType = dataType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "Variable [name=" + name + ", dataType=" + dataType + ", value=" + value + ", scope=" + scope + "]";
	}
}
