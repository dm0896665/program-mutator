package program.mutator.pojos.possible.mutation.items;

import java.util.ArrayList;
import java.util.Arrays;

import program.mutator.pojos.Value;
import program.mutator.pojos.abstracts.MutationItem;
import program.mutator.pojos.enums.AllowedOccurrences;
import program.mutator.pojos.enums.Contains;
import program.mutator.pojos.enums.possible.mutation.items.PrePostFix;
import program.mutator.pojos.interfaces.MutationItemInterface;

public class PrePostFixes extends MutationItem implements MutationItemInterface {
	private static ArrayList<Value> values = new ArrayList<Value>();
	static {
		values.add(new Value(
				PrePostFix.INCREMENT.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				AllowedOccurrences.ONE.getInt())); //occurrences allowed
		
		values.add(new Value(
				PrePostFix.DECREMENT.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				AllowedOccurrences.ONE.getInt())); //occurrences allowed
	}
	public PrePostFixes(ArrayList<Value> values) {
		super(values);
	}
	public PrePostFixes() {
		super(values);
	}
}