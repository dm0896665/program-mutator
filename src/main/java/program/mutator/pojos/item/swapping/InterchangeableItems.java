package program.mutator.pojos.item.swapping;

import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

import program.mutator.pojos.abstracts.MutationItem;
import program.mutator.pojos.interfaces.MutationItemInterface;

public class InterchangeableItems {
	public static ArrayList<InterchangeableItem<?>> mutationItems = new ArrayList<InterchangeableItem<?>>();
	
	static {
		addMutableItems();
    }
	
	public static void addMutableItems() {
		 Reflections reflections = new Reflections("program.mutator.pojos.possible.mutation.items");

		 Set<Class<? extends MutationItem>> allClasses = 
		     reflections.getSubTypesOf(MutationItem.class);
		 allClasses.forEach(clazz -> addMutableItem(clazz));
	}
	
	private static <E extends MutationItem & MutationItemInterface> void addMutableItem(Class<E> e) {
		try {
			if(e.newInstance().getValues().size() > 0) {
				mutationItems.add(new InterchangeableItem<E>(e.newInstance().getValues()));
			}
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
	
	public static Value getValueFromString(String item) {
		for(InterchangeableItem<?> interchangeableItem : mutationItems) {
			for(Value v : interchangeableItem.getItemsToInterchangeWith()) {
				if(v.getOutputString().equals(item)){
					return v;
				}
			}
		}
		return null;
	}
}
