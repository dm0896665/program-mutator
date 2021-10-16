package program.mutator.pojos.abstracts;

import java.util.ArrayList;

import program.mutator.pojos.interfaces.MutationItemInterface;
import program.mutator.pojos.item.swapping.Value;

public abstract class MutationItem implements MutationItemInterface{
	private ArrayList<Value> values;
	
	public MutationItem(ArrayList<Value> values) {
		this.values = values;
	}

	public ArrayList<Value> getValues() {
		return values;
	}

	public void setValues(ArrayList<Value> values) {
		this.values = values;
	}
}
