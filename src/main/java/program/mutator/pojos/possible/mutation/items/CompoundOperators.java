package program.mutator.pojos.possible.mutation.items;

import java.util.ArrayList;
import java.util.Arrays;

import program.mutator.pojos.abstracts.MutationItem;
import program.mutator.pojos.enums.AllowedOccurrences;
import program.mutator.pojos.enums.Contains;
import program.mutator.pojos.enums.SpaceRules;
import program.mutator.pojos.enums.possible.mutation.items.CompoundOperator;
import program.mutator.pojos.interfaces.MutationItemInterface;
import program.mutator.pojos.item.swapping.Value;

public final class CompoundOperators extends MutationItem implements MutationItemInterface {
	private static ArrayList<Value> values = new ArrayList<Value>();
	static {
		values.add(new Value(
				CompoundOperator.ADD.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.ONE.getInt())); //occurrences allowed
		
		values.add(new Value(
				CompoundOperator.SUBTRACT.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.ONE.getInt())); //occurrences allowed
		
		values.add(new Value(
				CompoundOperator.MULTIPLY.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.ONE.getInt())); //occurrences allowed
		
		values.add(new Value(
				CompoundOperator.DIVIDE.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.ZERO.toString()), //can't have
				SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.ONE.getInt())); //occurrences allowed
	}
	public CompoundOperators(ArrayList<Value> values) {
		super(values);
	}
	public CompoundOperators() {
		super(values);
	}
}
