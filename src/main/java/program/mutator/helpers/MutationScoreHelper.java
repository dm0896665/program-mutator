package program.mutator.helpers;

import java.util.ArrayList;

import program.mutator.pojos.MutationScore;
import program.mutator.pojos.enums.MutationStatus;

public class MutationScoreHelper {
	public static ArrayList<MutationScore> mutationScores = new ArrayList<MutationScore>();

	public MutationScoreHelper() {
	}
	
	public static void recalculateAllScores() {
		for(int i = 0; i < mutationScores.size(); i++) {
			for(int j = 0; j < mutationScores.size(); j++) {
				if(j != i) {
					for(int k = 0; k < mutationScores.get(j).getMutationStatuses().size(); k++) {
						MutationStatus mutation1 = mutationScores.get(i).getMutationStatuses().get(k);
						MutationStatus mutation2 = mutationScores.get(j).getMutationStatuses().get(k);
						if(mutation1.equals(MutationStatus.EQUIVALENT) && mutation2.equals(MutationStatus.DEAD)) {
							mutationScores.get(i).getMutationStatuses().set(k, MutationStatus.KILLABLE);
						} 
					}
				}
			}
			mutationScores.get(i).calculateScore();
		}
	}

}
