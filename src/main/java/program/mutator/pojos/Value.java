package program.mutator.pojos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import program.mutator.pojos.enums.SpaceRules;

public class Value {
	String outputString;
	ArrayList<String> shouldContain;
	ArrayList<String> shouldNotContain;
	SpaceRules shouldHaveSpaceOneEitherSide;

	int occurrencesAllowed;
	
	

	public Value(String outputString, List<String> shouldContain, List<String> shouldNotContain,
			SpaceRules shouldHaveSpaceOneEitherSide, int occurrencesAllowed) {
		this.outputString = outputString;
		this.shouldContain =  new ArrayList<String>(shouldContain);
		this.shouldNotContain = new ArrayList<String>(shouldNotContain);
		this.shouldHaveSpaceOneEitherSide = shouldHaveSpaceOneEitherSide;
		this.occurrencesAllowed = occurrencesAllowed;
	}

	public String getOutputString() {
		return outputString;
	}

	public void setOutputString(String outputString) {
		this.outputString = outputString;
	}

	public ArrayList<String> getShouldContain() {
		return shouldContain;
	}

	public void setShouldContain(ArrayList<String> shouldContain) {
		this.shouldContain = shouldContain;
	}

	public ArrayList<String> getShouldNotContain() {
		return shouldNotContain;
	}

	public void setShouldNotContain(ArrayList<String> shouldNotContain) {
		this.shouldNotContain = shouldNotContain;
	}
	
	public boolean shouldContainSomething() {
		return this.shouldContain.size() > 0 && !"".equals(this.shouldContain.get(0));
	}
	
	public boolean shouldNotContainSomething() {
		return this.shouldNotContain.size() > 0 && !"".equals(this.shouldNotContain.get(0));
	}

	public int getOccurrencesAllowed() {
		return occurrencesAllowed;
	}

	public void setOccurrencesAllowed(int occurrencesAllowed) {
		this.occurrencesAllowed = occurrencesAllowed;
	}
	public SpaceRules isShouldHaveSpaceOneEitherSide() {
		return shouldHaveSpaceOneEitherSide;
	}

	public void setShouldHaveSpaceOneEitherSide(SpaceRules shouldHaveSpaceOneEitherSide) {
		this.shouldHaveSpaceOneEitherSide = shouldHaveSpaceOneEitherSide;
	}

	public boolean hasShouldContain(String line) {
		if(shouldContainSomething()) {
			for(String value : this.shouldContain) {
				if(line.contains(value)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	public boolean hasShouldNotContain(String line) {
		if(shouldNotContainSomething()) {
			for(String value : this.shouldNotContain) {
				if(line.contains(value)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public boolean occursLessThanAllowedOccurrences(String line) {
		return countOccurancesOfValue(line) <= this.occurrencesAllowed;
	}
	
	public int countOccurancesOfValue(String line) {
		int index = 0, count = 0;
        while (true)
        {
            index = line.indexOf(this.outputString, index);
            if (index != -1)
            {
                count ++;
                index += this.outputString.length();
            }
            else {
                break;
            }
        }
        return count;
	}

	public boolean changeableValueHasSpaceOnEitherSide(String line, String changeableItem) {
		return Arrays.asList(line.split(" ")).contains(changeableItem);
	}
	
	public boolean isNoSpaceItem() {
		
		switch(this.shouldHaveSpaceOneEitherSide) {
		case SHOULD_CONTAIN_SPACE_ON_EITHER_SIDE:
			return false;
		case CAN_CONTAIN_SPACE_ON_EITHER_SIDE:
		case SHOULD_NOT_CONTAIN_SPACE_ON_EITHER_SIDE:
		default:
			return true;
		}
	}

	@Override
	public String toString() {
		return "Value [outputString=" + outputString + ", shouldContain=" + shouldContain + ", shouldNotContain="
				+ shouldNotContain + "]";
	}
	
	
}
