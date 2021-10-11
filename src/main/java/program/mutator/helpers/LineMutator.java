package program.mutator.helpers;

import java.util.ArrayList;

import program.mutator.pojos.InterchangeableItem;
import program.mutator.pojos.InterchangeableItems;
import program.mutator.pojos.Value;
import program.mutator.pojos.enums.AllowedOccurrences;
import program.mutator.pojos.enums.possible.mutation.items.PrePostFix;

public class LineMutator {
	
	public static boolean isLineMutatable(String line) {
		if(lineContainsWhichChangeableItems(line).size() > 0 && !line.trim().startsWith("//")) {
			return true;
		}
		return false;
	}
	
	public static ArrayList<String> lineContainsWhichChangeableItems(String line) {
		line = line.toLowerCase();
		ArrayList<String> changeableItems = new ArrayList<String>();
		for(InterchangeableItem<?> interchangeableItem : InterchangeableItems.MUTATION_ITEMS) {
			for(Value changeableValue : interchangeableItem.getItemsToInterchangeWith()) {
				String changeableItem = changeableValue.getOutputString();
				if((line.contains(" " + changeableItem + " ") && changeableValue.changeableValueHasSpaceOnEitherSide(line, changeableItem) 
						|| (line.contains(changeableItem) && (PrePostFix.INCREMENT.toString().equals(changeableItem) || PrePostFix.DECREMENT.toString().equals(changeableItem)))) 
						&& changeableValue.hasShouldContain(line) && changeableValue.occursLessThanAllowedOccurrences(line)) {
					changeableItems.add(changeableItem);
				}
			}
		}
		return changeableItems;
	}
	
	public static ArrayList<String> mutateLine(String line) {
		if(isLineMutatable(line)) {
			ArrayList<String> itemsToChange = lineContainsWhichChangeableItems(line);
			ArrayList<String> mutatedLines = new ArrayList<String>();
			for(String itemToChange : itemsToChange) {
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
				
				
				for(Value mutatedInequality : itemToMutateWith){
					if(mutatedInequality.getOccurrencesAllowed() > AllowedOccurrences.ONE.getInt()) {
						for(int i = 1; i < InterchangeableItems.getValueFromString(itemToChange).countOccurancesOfValue(line) + 1; i++) {
							if(!"".equals(changeNthOccurrence(line, itemToChange, i, mutatedInequality.getOutputString()))) {
								mutatedLines.add(changeNthOccurrence(line, itemToChange, i, mutatedInequality.getOutputString()));
							}
						}
					} else {
						if(PrePostFix.INCREMENT.toString().equals(itemToChange) || PrePostFix.DECREMENT.toString().equals(itemToChange)) {
							mutatedLines.add(line.replace(itemToChange, mutatedInequality.getOutputString()));
						} else {
							mutatedLines.add(line.replace(" " + itemToChange + " ", " " + mutatedInequality.getOutputString() + " "));
						}
					}
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
           if(str.substring(start, end).equals(subStr) && (str.charAt(start-1) == ' ') && (str.charAt(end) == ' ')){
        	   //System.out.println("'" + str.charAt(start-1) + "' " + str.substring(start, end) + " '" + str.charAt(end) + "'");
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
