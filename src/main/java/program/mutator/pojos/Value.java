package program.mutator.pojos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import program.mutator.helpers.VariableHelper;
import program.mutator.pojos.enums.Contains;
import program.mutator.pojos.enums.SpaceRules;

public class Value {
	private String outputString;
	private ArrayList<String> shouldContain;
	private ArrayList<String> shouldNotContain;
	private SpaceRules shouldHaveSpaceOneEitherSide;
	private int occurrencesAllowed;
	private ArrayList<Object> attachments;

	public Value(String outputString, List<String> shouldContain, List<String> shouldNotContain,
			SpaceRules shouldHaveSpaceOneEitherSide, int occurrencesAllowed, List<Object> attachments) {
		this.outputString = outputString;
		this.shouldContain = new ArrayList<String>(shouldContain);
		this.shouldNotContain = new ArrayList<String>(shouldNotContain);
		this.shouldHaveSpaceOneEitherSide = shouldHaveSpaceOneEitherSide;
		this.occurrencesAllowed = occurrencesAllowed;
		this.attachments = new ArrayList<Object>(attachments);
	}

	public Value(String outputString, List<String> shouldContain, List<String> shouldNotContain,
			SpaceRules shouldHaveSpaceOneEitherSide, int occurrencesAllowed) {
		this.outputString = outputString;
		this.shouldContain =  new ArrayList<String>(shouldContain);
		this.shouldNotContain = new ArrayList<String>(shouldNotContain);
		this.shouldHaveSpaceOneEitherSide = shouldHaveSpaceOneEitherSide;
		this.occurrencesAllowed = occurrencesAllowed;
		this.attachments = new ArrayList<Object>();
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
				if(value.equals(Contains.EQUAL_SIGN.toString()) && !line.contains(" " + value + " ")) {
					return false;
				}
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
	
	public boolean isInLine(String line) {
		String changeableItem = this.outputString;
		
		switch(this.shouldHaveSpaceOneEitherSide) {
		case SHOULD_CONTAIN_SPACE_ON_EITHER_SIDE:
			return line.contains(" " + changeableItem + " ") && hasShouldContain(line) && occursLessThanAllowedOccurrences(line);
		case CAN_CONTAIN_SPACE_ON_EITHER_SIDE:
			if(VariableHelper.isAVariableName(outputString)) {
				return hasAtLeastOneOccurance(line, changeableItem) && hasShouldContain(line) && occursLessThanAllowedOccurrences(line);
			} else {
				return (line.contains(" " + changeableItem) || line.contains(changeableItem + " ") || line.contains(changeableItem)|| hasAtLeastOneOccurance(line, changeableItem))
						&& hasShouldContain(line) && occursLessThanAllowedOccurrences(line);
			}
		case SHOULD_NOT_CONTAIN_SPACE_ON_EITHER_SIDE:
			return (line.contains(changeableItem) && !(line.contains(" " + changeableItem) || line.contains(changeableItem + " ") || line.contains(" " + changeableItem + " ")))
					&& hasShouldContain(line) && occursLessThanAllowedOccurrences(line);
		default:
			return false;
		}
	}
	
	public static boolean hasAtLeastOneOccurance(String line, String variableName) {
		int start = 0;
        int occurences = 1;
        int oNum = 0;
        for(int end = variableName.length(); end < line.length(); end++){
           if(line.substring(start, end).equals(variableName)){
        	   if(isJustTheItem(line, variableName, occurences)) {
        		   oNum++;
        	   }
        	   occurences++;
           };
           start++;
        }
        return oNum > 0;
	}
	
	public static boolean isJustTheItem(String line, String variableName, int occurrence) {
		int start = 0;
        int occurences = 0;
        //System.out.println("line--------->" + line + " var:" + variableName);
        for(int end = variableName.length(); end < line.length(); end++){
        //if(line.substring(start, end).equals(variableName))System.out.println(((start - 1 >= 0)? !Character.isLetter(line.charAt(start-1)): true) + " '" + ((start - 1 >= 0)?line.charAt(start-1) : "") + "' " + !Character.isLetter(line.charAt(end)) + " '" + line.charAt(end) + "'");
           if(line.substring(start, end).equals(variableName)){
              occurences++;
           };
           if(occurences == occurrence){
               return ((start - 1 >= 0)? !Character.isLetter(line.charAt(start-1)): true) && (!Character.isLetter(line.charAt(end)));
            };
           start++;
        }
        return false;
	}
	
	

	@Override
	public String toString() {
		return "Value [outputString=" + outputString + ", shouldContain=" + shouldContain + ", shouldNotContain="
				+ shouldNotContain + "]";
	}

	public ArrayList<Object> getAttachments() {
		return attachments;
	}

	public void setAttachments(ArrayList<Object> attachments) {
		this.attachments = attachments;
	}
	
	
}
