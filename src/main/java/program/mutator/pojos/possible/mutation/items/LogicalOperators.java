package program.mutator.pojos.possible.mutation.items;

import java.util.ArrayList;
import java.util.Arrays;

import program.mutator.pojos.Value;
import program.mutator.pojos.abstracts.MutationItem;
import program.mutator.pojos.enums.AllowedOccurrences;
import program.mutator.pojos.enums.Contains;
import program.mutator.pojos.enums.SpaceRules;
import program.mutator.pojos.enums.possible.mutation.items.LogicalOperator;
import program.mutator.pojos.enums.possible.mutation.items.PrePostFix;
import program.mutator.pojos.interfaces.MutationItemInterface;

public final class LogicalOperators extends MutationItem implements MutationItemInterface {
	private static ArrayList<Value> values = new ArrayList<Value>();
	static {
		values.add(new Value(
				LogicalOperator.CONDITIONAL_AND.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		
		values.add(new Value(
				LogicalOperator.CONDITIONAL_OR.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		values.add(new Value(
				LogicalOperator.AND.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(LogicalOperator.CONDITIONAL_AND.toString()), //can't have
				SpaceRules.SHOULD_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		
		values.add(new Value(
				LogicalOperator.OR.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(LogicalOperator.CONDITIONAL_OR.toString()), //can't have
				SpaceRules.SHOULD_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		values.add(new Value(
				LogicalOperator.XOR.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
	}
	public LogicalOperators(ArrayList<Value> values) {
		super(values);
	}
	public LogicalOperators() {
		super(values);
	}
}
