package program.mutator.pojos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class InterchangeableItem<T extends Enum<T>> {
	private ArrayList<String> itemsToInterchangeWith;
	
	public InterchangeableItem(T[] aValues) {
		setItemsToInterchangeWith((ArrayList<String>) Arrays.asList(aValues).stream()
				   .map(object -> Objects.toString(object, null))
				   .collect(Collectors.toList()));
	}

	public ArrayList<String> getItemsToInterchangeWith() {
		return itemsToInterchangeWith;
	}

	public void setItemsToInterchangeWith(ArrayList<String> itemsToInterchangeWith) {
		this.itemsToInterchangeWith = itemsToInterchangeWith;
	}
}
