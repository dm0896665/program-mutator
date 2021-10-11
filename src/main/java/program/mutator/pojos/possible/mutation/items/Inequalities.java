package program.mutator.pojos.possible.mutation.items;

import java.util.ArrayList;
import java.util.Arrays;

import program.mutator.pojos.Value;
import program.mutator.pojos.abstracts.MutationItem;
import program.mutator.pojos.enums.AllowedOccurrences;
import program.mutator.pojos.enums.Contains;
import program.mutator.pojos.enums.possible.mutation.items.Inequality;
import program.mutator.pojos.interfaces.MutationItemInterface;

public final class Inequalities extends MutationItem implements MutationItemInterface {
	private static ArrayList<Value> values = new ArrayList<Value>();
	static {
		values.add(new Value(
				Inequality.EQUALS.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		
		values.add(new Value(
				Inequality.NOT_EQUALS.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		
		values.add(new Value(
				Inequality.GREATER_THAN_OR_EQUAL_TO.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		
		values.add(new Value(
				Inequality.LESS_THAN_OR_EQUAL_TO.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		
		values.add(new Value(
				Inequality.GREATER_THAN.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		
		values.add(new Value(
				Inequality.LESS_THAN.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
	}
	public Inequalities(ArrayList<Value> values) {
		super(values);
	}
	public Inequalities() {
		super(values);
	}

}
