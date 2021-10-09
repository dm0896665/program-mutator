package program.mutator.pojos;

import java.util.ArrayList;

import program.mutator.pojos.enums.possible.mutation.items.Inequalities;

public class InterchangeableItems {
	public static final ArrayList<InterchangeableItem<?>> ENUMS = new ArrayList<InterchangeableItem<?>>();
	
	static {
        ENUMS.add(new InterchangeableItem<Inequalities>(Inequalities.values()));
        //ENUMS.add(new InterchangeableItem<NumberTypes>(NumberTypes.values()));
    }
}
