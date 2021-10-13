package program.mutator.pojos.possible.mutation.items;

import java.util.ArrayList;

import program.mutator.pojos.Value;
import program.mutator.pojos.abstracts.MutationItem;
import program.mutator.pojos.interfaces.MutationItemInterface;

public final class Variables extends MutationItem implements MutationItemInterface {
	private static ArrayList<Value> values = new ArrayList<Value>();
	public Variables(ArrayList<Value> values) {
		super(values);
	}
	public Variables() {
		super(values);
	}
}
