package program.mutator.helpers;

import java.util.ArrayList;
import java.util.Arrays;

import program.mutator.pojos.Variable;
import program.mutator.pojos.enums.Contains;
import program.mutator.pojos.enums.DataTypes;

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
					variables.add(new Variable(name, DataTypes.fromString(dataType), value));
					break;
				}
			}
		}
	}
	
	public static void printVariables() {
		if(variables.size() > 0) {
			variables.forEach(variable -> System.out.println("Variable=> " + variable));
		} else {
			System.out.println("***No variables detected***");
		}
	}

}
