package program.mutator.pojos.possible.mutation.items;

import java.util.ArrayList;
import java.util.Arrays;

import program.mutator.pojos.abstracts.MutationItem;
import program.mutator.pojos.enums.AllowedOccurrences;
import program.mutator.pojos.enums.Contains;
import program.mutator.pojos.enums.SpaceRules;
import program.mutator.pojos.enums.possible.mutation.items.NumberType;
import program.mutator.pojos.interfaces.MutationItemInterface;
import program.mutator.pojos.item.swapping.Value;

public final class NumberTypes extends MutationItem implements MutationItemInterface {
	private static ArrayList<Value> values = new ArrayList<Value>();
	static {
		values.add(new Value(
				NumberType.INT.toString(), //value
				Arrays.asList(Contains.EQUAL_SIGN.toString()), //must have
				Arrays.asList(Contains.DECIMAL_POINT.toString()), //can't have
				SpaceRules.SHOULD_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rules
				AllowedOccurrences.ONE.getInt())); //occurrences allowed
		
		values.add(new Value(
				NumberType.DOUBLE.toString(), //value
				Arrays.asList(Contains.EQUAL_SIGN.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				SpaceRules.SHOULD_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rules
				AllowedOccurrences.ONE.getInt())); //occurrences allowed
		
		values.add(new Value(
				NumberType.FLOAT.toString(), //value
				Arrays.asList(Contains.EQUAL_SIGN.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				SpaceRules.SHOULD_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rules
				AllowedOccurrences.ONE.getInt())); //occurrences allowed
		
		values.add(new Value(
				NumberType.LONG.toString(), //value
				Arrays.asList(Contains.EQUAL_SIGN.toString()), //must have
				Arrays.asList(Contains.DECIMAL_POINT.toString()), //can't have
				SpaceRules.SHOULD_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rules
				AllowedOccurrences.ONE.getInt())); //occurrences allowed
		
		values.add(new Value(
				NumberType.SHORT.toString(), //value
				Arrays.asList(Contains.EQUAL_SIGN.toString()), //must have
				Arrays.asList(Contains.DECIMAL_POINT.toString()), //can't have
				SpaceRules.SHOULD_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rules
				AllowedOccurrences.ONE.getInt())); //occurrences allowed
	}
	public NumberTypes(ArrayList<Value> values) {
		super(values);
	}
	public NumberTypes() {
		super(values);
	}

}
