package program.mutator.pojos;

import java.util.ArrayList;

import program.mutator.pojos.enums.MutationStatus;

public class MutationScore {
	private int deadMutants;
	private int totalMutants;
	private int equivalentMutants;
	private double score;
	private ArrayList<MutationStatus> mutationStatuses = new ArrayList<MutationStatus>();
	
	public MutationScore() {
		this.deadMutants = 0;
		this.totalMutants = 0;
		this.equivalentMutants = 0;
		this.score = 0d;
	}
	
	public MutationScore(int deadMutants, int totalMutants, int equivalentMutants, double score,
			ArrayList<MutationStatus> mutationStatuses) {
		this.deadMutants = deadMutants;
		this.totalMutants = totalMutants;
		this.equivalentMutants = equivalentMutants;
		this.score = score;
		this.mutationStatuses = mutationStatuses;
	}

	public int getDeadMutants() {
		return deadMutants;
	}
	public void setDeadMutants(int deadMutants) {
		this.deadMutants = deadMutants;
	}
	private void addDeadMutants(int count) {
		this.deadMutants+=count;
		this.totalMutants+=count;
	}
	public int getTotalMutants() {
		return totalMutants;
	}
	public void setTotalMutants(int totalMutants) {
		this.totalMutants = totalMutants;
	}
	private void addTotalMutants(int count) {
		this.totalMutants+=count;
	}
	public int getEquivalentMutants() {
		return equivalentMutants;
	}
	public void setEquivalentMutants(int equivalentMutants) {
		this.equivalentMutants = equivalentMutants;
	}
	private void addEquivalentMutants(int count) {
		this.equivalentMutants+=count;
		this.totalMutants+=count;
	}
	public void removeEquivalentMutants(int count) {
		this.equivalentMutants-=count;
	}
	private void resetCounts() {
		this.deadMutants = 0;
		this.equivalentMutants = 0;
		this.totalMutants = 0;
	}
	public void calculateScore() {
		resetCounts();
		mutationStatuses.forEach(mutation -> {
			if(mutation.equals(MutationStatus.DEAD)) {
				addDeadMutants(1);
			} else if(!mutation.equals(MutationStatus.KILLABLE)){
				addEquivalentMutants(1);
			} else {
				addTotalMutants(1);
			}
		});
		this.score = 100 * ((double) this.deadMutants / (double)(this.totalMutants - this.equivalentMutants));
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

	public ArrayList<MutationStatus> getMutationStatuses() {
		return mutationStatuses;
	}

	public void setMutationStatuses(ArrayList<MutationStatus> mutationStatuses) {
		this.mutationStatuses = mutationStatuses;
	}

	@Override
	public String toString() {
		return "MutationScore [deadMutants=" + deadMutants + ", totalMutants=" + totalMutants + ", equivalentMutants="
				+ equivalentMutants + ", score=" + score + ", mutationStatuses=" + mutationStatuses + "]";
	}
	
	
}
