package program.mutator.helpers;

import java.util.ArrayList;

import program.mutator.pojos.InterchangeableItem;
import program.mutator.pojos.InterchangeableItems;
import program.mutator.pojos.Value;

public class LineMutator {
	
	public static boolean isLineMutatable(String line) {
		if(!"".equals(lineContainsWhichChangeableItem(line))) {
			return true;
		}
		return false;
	}
	
	public static String lineContainsWhichChangeableItem(String line) {
		line = line.toLowerCase();
		for(InterchangeableItem<?> interchangeableItem : InterchangeableItems.MUTATION_ITEMS) {
			for(Value changeableValue : interchangeableItem.getItemsToInterchangeWith()) {
				String changeableItem = changeableValue.getOutputString();
				if(line.contains(changeableItem) && changeableValue.hasShouldContain(line) && changeableValue.occursLessThanAllowedOccurrences(line)) {
					return changeableItem;
				}
			}
		}
		return "";
	}
	
	public static ArrayList<String> mutateLine(String line) {
		if(isLineMutatable(line)) {
			String itemToChange = lineContainsWhichChangeableItem(line);
			ArrayList<Value> itemToMutateWith = new ArrayList<Value>();
			for(InterchangeableItem<?> interchangeableItem : InterchangeableItems.MUTATION_ITEMS) {
				if(itemToChangeIsInInterchangeableItem(interchangeableItem.getItemsToInterchangeWithValues(), itemToChange)) {
					for(Value changeableItem : interchangeableItem.getItemsToInterchangeWith()) {
						if(!changeableItem.getOutputString().equals(itemToChange) && !changeableItem.hasShouldNotContain(line)) {
							 itemToMutateWith.add(changeableItem); 
						}
					}
				}
			}
			
			ArrayList<String> mutatedLines = new ArrayList<String>(itemToMutateWith.size());
			for(Value mutatedInequality : itemToMutateWith){
				if(mutatedInequality.getOccurrencesAllowed() > 1) {
					for(int i = 1; i < InterchangeableItems.getValueFromString(itemToChange).countOccurancesOfValue(line) + 1; i++) {
						mutatedLines.add(changeNthOccurrence(line, itemToChange, i, mutatedInequality.getOutputString()));
					}
				} else {
					mutatedLines.add(line.replace(itemToChange, mutatedInequality.getOutputString()));
				}
			}
			return mutatedLines;
		} else {
			return null;
		}
	}
	
	private static boolean itemToChangeIsInInterchangeableItem(ArrayList<String> interchangeableItems, String item) {
		return interchangeableItems.contains(item);
	}
	
	private static String changeNthOccurrence(String str, String subStr, int n, String changeTo) {
        int start = 0;
        int occurences = 0;
        for(int end = subStr.length(); end < str.length(); end++){
           if(str.substring(start, end).equals(subStr)){
              occurences++;
           };
           if(occurences == n){
              return str.substring(0, start) + changeTo + str.substring(end, str.length());
           };
           start++;
        }
        return "";
    }
	
	
}
