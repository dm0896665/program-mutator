package program.mutator.pojos;

import java.util.ArrayList;

import program.mutator.pojos.abstracts.MutationItem;
import program.mutator.pojos.interfaces.MutationItemInterface;
import program.mutator.pojos.possible.mutation.items.CompoundOperators;
import program.mutator.pojos.possible.mutation.items.Inequalities;
import program.mutator.pojos.possible.mutation.items.NumberTypes;
import program.mutator.pojos.possible.mutation.items.PrePostFixes;

public class InterchangeableItems {
	public static final ArrayList<InterchangeableItem<?>> MUTATION_ITEMS = new ArrayList<InterchangeableItem<?>>();
	
	static {
		addMutableItem(Inequalities.class);
		addMutableItem(NumberTypes.class);
		addMutableItem(PrePostFixes.class);
		addMutableItem(CompoundOperators.class);
    }
	
	private static <E extends MutationItem & MutationItemInterface> void addMutableItem(Class<E> e) {
		try {
			MUTATION_ITEMS.add(new InterchangeableItem<E>(e.newInstance().getValues()));
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
	
	public static Value getValueFromString(String item) {
		for(InterchangeableItem<?> interchangeableItem : MUTATION_ITEMS) {
			for(Value v : interchangeableItem.getItemsToInterchangeWith()) {
				if(v.getOutputString().equals(item)){
					return v;
				}
			}
		}
		return null;
	}
}
