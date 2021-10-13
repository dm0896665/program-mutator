package program.mutator.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import program.mutator.pojos.InterchangeableItem;
import program.mutator.pojos.InterchangeableItems;
import program.mutator.pojos.Scope;
import program.mutator.pojos.Value;
import program.mutator.pojos.Variable;
import program.mutator.pojos.enums.AllowedOccurrences;
import program.mutator.pojos.enums.Contains;
import program.mutator.pojos.enums.DataTypes;
import program.mutator.pojos.enums.SpaceRules;
import program.mutator.pojos.possible.mutation.items.Variables;

public class VariableHelper {

	public static ArrayList<String> originalFileLines = new ArrayList<String>();
	public static ArrayList<Variable> variables = new ArrayList<Variable>();

	public VariableHelper() {
		
	}

	/*MUST BE CALLED AT START OF PROCESS*/
	public static void initializeVariables() {
		ArrayList<String> dataTypes = new ArrayList<String>();
		Arrays.asList(DataTypes.values()).forEach(dataType -> dataTypes.add(dataType.toString()));
		for(String line : originalFileLines) {
			for(String dataType : dataTypes) {
				//System.out.println(line.trim() + " =>" + (line.trim().contains(dataType + " ") && line.contains(Contains.EQUAL_SIGN.toString())));
				if(line.trim().contains(dataType + " ") && line.contains(Contains.EQUAL_SIGN.toString())){
					int startOfStatement = line.indexOf(dataType);
					int endOfStatement = line.substring(startOfStatement, line.length()).indexOf(";") + startOfStatement;
					String statement = line.substring(startOfStatement, endOfStatement);
					ArrayList<String> leftRight = new ArrayList<String>(Arrays.asList(statement.split("=")));
					ArrayList<String> lefts = new ArrayList<String>(Arrays.asList(leftRight.get(0).split(" ")));
					String name = lefts.get(1);
					String value = leftRight.get(1).trim();
					
					if(dataType.contains(Contains.ARRAY.toString()) && line.contains(Contains.ARRAY.toString())) {
						for(String type : dataTypes) {
							if(type.equals(dataType + Contains.ARRAY.toString())) {
								dataType = type;
								name = lefts.get(2);
								break;
							}
						}
					}
					variables.add(new Variable(name, DataTypes.fromString(dataType), value, new Scope(getLineNumberFromLine(line))));
					break;
				}
			}
		}
		
		findVariablesScopes();
		
		addVariablesToMutateableItems();
	}
	
	private static void findVariablesScopes() {
		for(String line : originalFileLines) {
			variableLoop:
			for(int i = 0; i < variables.size(); i++) {
				Variable variable = variables.get(i);
				if(variable.getScope().getEnd() == 0) {
					Variable scopedVariable = variable;
					if(Value.hasAtLeastOneOccurance(line, variable.getName())) {
						int lineBeforeIndex = getLineNumberFromLine(line) - 2;
						int lineIndex = getLineNumberFromLine(line) - 1;
						int lineAfterIndex = getLineNumberFromLine(line);
						String lineBefore = originalFileLines.get(lineBeforeIndex);
						String lineAfter = originalFileLines.get(lineAfterIndex);
						if((!"".equals(lineBefore.trim()) && lineBefore.trim().charAt(lineBefore.trim().length() - 1) == ')') || (line.contains("if") && (!line.contains("{") && !lineAfter.contains("{")))) {
							scopedVariable.getScope().setEnd(lineIndex + 1);
							variables.set(i, scopedVariable);
							break variableLoop;
						}
						
						int openCount = 1;
						int startAt = lineAfterIndex;
						if(lineAfter.contains("{") && (line.contains("for") || line.contains("if"))) {
							startAt++;
						}
						for(int j = startAt; j < originalFileLines.size(); j++) {
							if(originalFileLines.get(j).contains("{")) {
								openCount++;
							}
							if(originalFileLines.get(j).contains("}")) {
								openCount--;
							}
							if(openCount == 0) {
								scopedVariable.getScope().setEnd(j + 1);
								variables.set(i, scopedVariable);
								break variableLoop;
							}
						}
					}
				}
			}
		}
	}

	private static void addVariablesToMutateableItems() {
		ArrayList<Value> values = new ArrayList<Value>();
		ArrayList<ArrayList<Variable>> variablesByDataType = getVariablesByDataType();
		
		variables.forEach(variable -> {
			List<String> noSwapVariables = new ArrayList<String>();
			variablesByDataType.forEach(variableByDataType -> {
				if(!variableByDataType.contains(variable)) {
					for(int i = 0; i < variableByDataType.size(); i++) {
						noSwapVariables.add(variableByDataType.get(i).getName());
					}
				}
			});
			noSwapVariables.add(Contains.EQUAL_SIGN.toString());
			values.add(new Value(
					variable.getName(), //value
					Arrays.asList(Contains.NOTHING.toString()), //must have
					noSwapVariables, //can't have
					SpaceRules.CAN_CONTAIN_SPACE_ON_EITHER_SIDE, //surrounding space rules
					AllowedOccurrences.UNLIMITED.getInt(), //occurrences allowed
					Arrays.asList(variable.getDataType(), variable.getScope()))); //attachments
		});
		InterchangeableItem<Variables> ici = new InterchangeableItem<Variables>(values);
		InterchangeableItems.MUTATION_ITEMS.add(ici);
	}
	
	public static void printVariables() {
		if(variables.size() > 0) {
			variables.forEach(variable -> System.out.println("Variable=> " + variable));
		} else {
			System.out.println("***No variables detected***");
		}
	}
	
	private static ArrayList<ArrayList<Variable>> getVariablesByDataType(){
		ArrayList<ArrayList<Variable>> variablesByDataType = new ArrayList<ArrayList<Variable>>();
		for(int i = 0; i < variables.size(); i++) {
			boolean added = false;
			for(int j = 0; j < variablesByDataType.size(); j++) {
				ArrayList<Variable> variableByDataType = variablesByDataType.get(j);
				variableLoop:
				for(int k = 0; k < variableByDataType.size(); k++) {
					if(variableByDataType.get(k).getDataType().equals(variables.get(i).getDataType())) {
						variableByDataType.add(variables.get(i));
						added = true;
						break variableLoop;
					}
				}
			}
			if(!added) {
				variablesByDataType.add(new ArrayList<Variable>(Arrays.asList(variables.get(i))));
			}
		}
		return variablesByDataType;
	}
	
	public static Variable getVariableFromValue(Value value) {
		for(Variable variable : variables) {
			if(variable.getName().equals(value.getOutputString()) && variable.getDataType().equals((value.getAttachments().size() > 0)? value.getAttachments().get(0) : "")) {
				return variable;
			}
		}
		return new Variable("", null, "", null);
	}
	
	public static boolean changeableItemIsAVariable(Value changeableItem) {
		for(Variable variable : variables) {
			if(variable.getName().equals(changeableItem.getOutputString()) && variable.getDataType().equals((changeableItem.getAttachments().size() > 0)? changeableItem.getAttachments().get(0) : "")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAVariableName(String variableName) {
		for(Variable v : variables) {
			if(v.getName().equals(variableName)) {
				return true;
			}
		}
		return false;
	}
	
	public static int getLineNumberFromLine(String line) {
		return originalFileLines.indexOf(line) + 1;
	}
}
