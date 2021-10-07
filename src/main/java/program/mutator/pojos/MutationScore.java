package program.mutator.pojos;

public class MutationScore {
	private int deadMutants;
	private int totalMutants;
	private int equivalentMutants;
	private double score;
	
	public MutationScore() {
		this.deadMutants = 0;
		this.totalMutants = 0;
		this.equivalentMutants = 0;
		this.score = 0d;
	}
	
	public MutationScore(int deadMutants, int totalMutants, int equivalentMutants, double score) {
		this.deadMutants = deadMutants;
		this.totalMutants = totalMutants;
		this.equivalentMutants = equivalentMutants;
		this.score = score;
	}
	
	public int getDeadMutants() {
		return deadMutants;
	}
	public void setDeadMutants(int deadMutants) {
		this.deadMutants = deadMutants;
	}
	public int getTotalMutants() {
		return totalMutants;
	}
	public void setTotalMutants(int totalMutants) {
		this.totalMutants = totalMutants;
	}
	public int getEquivalentMutants() {
		return equivalentMutants;
	}
	public void setEquivalentMutants(int equivalentMutants) {
		this.equivalentMutants = equivalentMutants;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
}