package program.mutator.helpers;

import java.util.ArrayList;
import java.util.Arrays;

public class NumberTypeHelper {
	private static final ArrayList<String> NUMBER_TYPES = new ArrayList<String>(Arrays.asList("int","double","float","long"));
	
	public static boolean lineHasNumberType(String line) {
		for(String numberType : NUMBER_TYPES) {
			if(line.contains(numberType)) {
				return true;
			}
		}
		return false;
	}
	
	public static String lineContainsWhichNumberType(String line) {
		for(String numberType : NUMBER_TYPES) {
			if(line.contains(numberType)) {
				return numberType;
			}
		}
		return "";
	}
	
	public static ArrayList<String> mutateLine(String line) {
		if(lineHasNumberType(line)) {
			String numberTypeToChange = lineContainsWhichNumberType(line);
			ArrayList<String> numberTypeToMutateWith = new ArrayList<String>();
			for(String numberType : NUMBER_TYPES) {
				if(!numberType.equals(numberTypeToChange)) {
					numberTypeToMutateWith.add(numberType);
				}
			}
			
			ArrayList<String> mutatedLines = new ArrayList<String>(numberTypeToMutateWith.size());
			for(String mutatedInequality : numberTypeToMutateWith){
				mutatedLines.add(line.replace(numberTypeToChange, mutatedInequality));
			}
			return mutatedLines;
		} else {
			return null;
		}
	}
}
