package program.mutator.pojos;

public class MutatedLine {
	private String line;
	private int lineNumber;
	
	public MutatedLine(String line, int lineNumber) {
		this.line = line;
		this.lineNumber = lineNumber;
	}
	
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
}
