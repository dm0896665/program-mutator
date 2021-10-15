package program.mutator.pojos.possible.mutation.items;

import java.util.ArrayList;
import java.util.Arrays;

import program.mutator.pojos.Value;
import program.mutator.pojos.abstracts.MutationItem;
import program.mutator.pojos.enums.AllowedOccurrences;
import program.mutator.pojos.enums.Contains;
import program.mutator.pojos.enums.SpaceRules;
import program.mutator.pojos.enums.possible.mutation.items.Operator;
import program.mutator.pojos.interfaces.MutationItemInterface;

public final class Operators extends MutationItem implements MutationItemInterface {
	private static ArrayList<Value> values = new ArrayList<Value>();
	static {
		values.add(new Value(
				Operator.ADD.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.NOTHING.toString()), //can't have
				SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		values.add(new Value(
				Operator.SUBTRACT.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.QUOTATION_MARK.toString(), Contains.STRING.toString(), Contains.SYSTEM_DOT_OUT.toString(), Contains.DOT_EQUALS.toString()), //can't have
				SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		values.add(new Value(
				Operator.MULTIPLY.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Contains.QUOTATION_MARK.toString(), Contains.STRING.toString(), Contains.SYSTEM_DOT_OUT.toString(), Contains.DOT_EQUALS.toString()), //can't have
				SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		
		values.add(new Value(
				Operator.DIVIDE.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Operator.SUBTRACT.toString(), Operator.MULTIPLY.toString(), Operator.ADD.toString(), Contains.ZERO.toString()), //can't have
				SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
		values.add(new Value(
				Operator.MODULUS.toString(), //value
				Arrays.asList(Contains.NOTHING.toString()), //must have
				Arrays.asList(Operator.SUBTRACT.toString(), Operator.MULTIPLY.toString(), Operator.ADD.toString(), Contains.ZERO.toString()), //can't have
				SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rule
				AllowedOccurrences.UNLIMITED.getInt())); //occurrences allowed
	}
	public Operators(ArrayList<Value> values) {
		super(values);
	}
	public Operators() {
		super(values);
	}
}
