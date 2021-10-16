package program.mutator.helpers;

import java.util.ArrayList;

import program.mutator.pojos.enums.AllowedOccurrences;
import program.mutator.pojos.item.swapping.InterchangeableItem;
import program.mutator.pojos.item.swapping.InterchangeableItems;
import program.mutator.pojos.item.swapping.Value;
import program.mutator.pojos.variable.Scope;

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
		for(InterchangeableItem<?> interchangeableItem : InterchangeableItems.mutationItems) {
			for(Value changeableValue : interchangeableItem.getItemsToInterchangeWith()) {
				String changeableItem = changeableValue.getOutputString();
				if(changeableValue.isInLine(line)) {
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
				for(InterchangeableItem<?> interchangeableItem : InterchangeableItems.mutationItems) {
					//if(itemToChange.equals("num"))System.out.println(interchangeableItem.getItemsToInterchangeWith());
					if(itemToChangeIsInInterchangeableItem(interchangeableItem.getItemsToInterchangeWithValues(), itemToChange)) {
						for(Value changeableItem : interchangeableItem.getItemsToInterchangeWith()) {
							//System.out.println(!changeableItem.getOutputString().equals(itemToChange) + " vs " + !changeableItem.hasShouldNotContain(line) + " for -> " + changeableItem);
							if(!changeableItem.getOutputString().equals(itemToChange) && !changeableItem.hasShouldNotContain(line)) {
								//System.out.println("****************************added " + changeableItem);
								if(changeableItem.getAttachments().size() > 1) {
									if(changeableItem.getAttachments().get(1) instanceof Scope && ((Scope) changeableItem.getAttachments().get(1)).isInScope(VariableHelper.getLineNumberFromLine(line))) { //if it's a variable check to see if it's in scope first))
										itemToMutateWith.add(changeableItem);
									}
								} else {
									itemToMutateWith.add(changeableItem); 
								}
							}
						}
					}
				}
				//System.out.println(" *changing*[" + itemToChange + "]: " + line + " => " + itemsToChange + itemToMutateWith);
				
				for(Value mutatedInequality : itemToMutateWith){
					if(mutatedInequality.getOccurrencesAllowed() > AllowedOccurrences.ONE.getInt()) {
						for(int i = 1; i < InterchangeableItems.getValueFromString(itemToChange).countOccurancesOfValue(line) + 1; i++) {
							//System.out.println("here: '" + changeNthOccurrence(line, itemToChange, i, mutatedInequality.getOutputString()) + "'");
							if(!"".equals(changeNthOccurrence(line, itemToChange, i, mutatedInequality.getOutputString()))) {
								mutatedLines.add(changeNthOccurrence(line, itemToChange, i, mutatedInequality.getOutputString()));
								//System.out.println("Place-------------> " + changeNthOccurrence(line, itemToChange, i, mutatedInequality.getOutputString()));
							}
						}
					} else {
						if(mutatedInequality.isNoSpaceItem()) {
							mutatedLines.add(line.replace(itemToChange, mutatedInequality.getOutputString()));
						} else {
							mutatedLines.add(line.replace(" " + itemToChange + " ", " " + mutatedInequality.getOutputString() + " "));
							//System.out.println(line.replace("Place==============> " + itemToChange, mutatedInequality.getOutputString()) + " " + mutatedInequality.getOutputString());
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
        	if(str.substring(start, end).equals(subStr) && 
        		   (((str.charAt(start-1) == ' ') && (str.charAt(end) == ' ') && !VariableHelper.isAVariableName(subStr)) 
        				   || (VariableHelper.isAVariableName(subStr) && Value.isJustTheItem(str, subStr, n)))){
        	  //System.out.println("'" + str.charAt(start-1) + "' " + str.substring(start, end) + " '" + str.charAt(end) + "' -> " + subStr);
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
