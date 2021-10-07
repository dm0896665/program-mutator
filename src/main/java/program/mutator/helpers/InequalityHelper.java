package program.mutator.helpers;

import java.util.ArrayList;
import java.util.Arrays;

public class InequalityHelper {
	private static final ArrayList<String> INEQUALITIES = new ArrayList<String>(Arrays.asList("==","!=",">=","<=",">","<"));
	
	public static boolean lineHasInequality(String line) {
		for(String inequality : INEQUALITIES) {
			if(line.contains(inequality)) {
				return true;
			}
		}
		return false;
	}
	
	public static String lineContainsWhichInequality(String line) {
		for(String inequality : INEQUALITIES) {
			if(line.contains(inequality)) {
				return inequality;
			}
		}
		return "";
	}
	
	public static ArrayList<String> mutateLine(String line) {
		if(lineHasInequality(line)) {
			String inequalityToChange = lineContainsWhichInequality(line);
			ArrayList<String> inequalitiesToMutateWith = new ArrayList<String>();
			for(String inequality : INEQUALITIES) {
				if(!inequality.equals(inequalityToChange)) {
					inequalitiesToMutateWith.add(inequality);
				}
			}
			
			ArrayList<String> mutatedLines = new ArrayList<String>(inequalitiesToMutateWith.size());
			for(String mutatedInequality : inequalitiesToMutateWith){
				mutatedLines.add(line.replace(inequalityToChange, mutatedInequality));
			}
			return mutatedLines;
		} else {
			return null;
		}
	}
}
