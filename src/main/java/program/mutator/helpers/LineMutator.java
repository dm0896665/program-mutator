package program.mutator.helpers;

import java.util.ArrayList;

import program.mutator.pojos.InterchangeableItem;
import program.mutator.pojos.InterchangeableItems;

public class LineMutator {
	
	public static boolean isLineMutatable(String line) {
		if(!"".equals(lineContainsWhichChangeableItem(line))) {
			return true;
		}
		return false;
	}
	
	public static String lineContainsWhichChangeableItem(String line) {
		for(InterchangeableItem<?> interchangeableItem : InterchangeableItems.ENUMS) {
			for(String changeableItem : interchangeableItem.getItemsToInterchangeWith()) {
				if(line.contains(changeableItem)) {
					return changeableItem;
				}
			}
		}
		return "";
	}
	
	public static ArrayList<String> mutateLine(String line) {
		if(isLineMutatable(line)) {
			String itemToChange = lineContainsWhichChangeableItem(line);
			ArrayList<String> itemToMutateWith = new ArrayList<String>();
			for(InterchangeableItem<?> interchangeableItem : InterchangeableItems.ENUMS) {
				if(itemToChangeIsInInterchangeableItem(interchangeableItem.getItemsToInterchangeWith(), itemToChange)) {
					for(String changeableItem : interchangeableItem.getItemsToInterchangeWith()) {
						if(!changeableItem.equals(itemToChange)) {
							itemToMutateWith.add(changeableItem);
						}
					}
				}
			}
			
			ArrayList<String> mutatedLines = new ArrayList<String>(itemToMutateWith.size());
			for(String mutatedInequality : itemToMutateWith){
				mutatedLines.add(line.replace(itemToChange, mutatedInequality));
			}
			return mutatedLines;
		} else {
			return null;
		}
	}
	
	private static boolean itemToChangeIsInInterchangeableItem(ArrayList<String> interchangeableItems, String item) {
		return interchangeableItems.contains(item);
	}
}
