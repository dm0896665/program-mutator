package program.mutator.pojos.enums;

public enum AllowedOccurrences {
	ONE(1),
	UNLIMITED(2147483646);
	
	
	int outputInt;
	
	AllowedOccurrences(int outputInt) {
		this.outputInt = outputInt;
	}
	
	@Override
	public String toString() {
		return Integer.toString(this.outputInt);
	}
	
	public int getInt() {
		return this.outputInt;
	}
}
