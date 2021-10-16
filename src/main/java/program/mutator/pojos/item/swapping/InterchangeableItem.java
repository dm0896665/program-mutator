package program.mutator.pojos.item.swapping;

import java.util.ArrayList;

import program.mutator.pojos.abstracts.MutationItem;
import program.mutator.pojos.interfaces.MutationItemInterface;

public class InterchangeableItem<T extends MutationItem & MutationItemInterface> {
	private ArrayList<Value> itemsToInterchangeWith;
	
	public InterchangeableItem(ArrayList<Value> values) {
		setItemsToInterchangeWith(values);
	}

	public ArrayList<Value> getItemsToInterchangeWith() {
		return itemsToInterchangeWith;
	}

	public void setItemsToInterchangeWith(ArrayList<Value> itemsToInterchangeWith) {
		this.itemsToInterchangeWith = itemsToInterchangeWith;
	}

	public ArrayList<String> getItemsToInterchangeWithValues() {
		ArrayList<String> items = new ArrayList<String>();
		itemsToInterchangeWith.forEach(item -> {
			items.add(item.getOutputString());
		});
		return items;
	}
}
