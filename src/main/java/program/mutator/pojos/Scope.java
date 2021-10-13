package program.mutator.pojos;

public class Scope {
	private int start;
	private int end;
	
	public Scope(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public Scope(int start) {
		this.start = start;
		this.end = 0;
	}
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	public boolean isInScope(int lineNumber) {
		return lineNumber >= start && lineNumber <= end;
	}

	@Override
	public String toString() {
		return "Scope [start=" + start + ", end=" + end + "]";
	}
}
