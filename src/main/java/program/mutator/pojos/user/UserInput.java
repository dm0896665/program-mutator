package program.mutator.pojos.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;

import program.mutator.helpers.LineMutator;
import program.mutator.helpers.VariableHelper;
import program.mutator.pojos.MutatedFile;
import program.mutator.pojos.enums.ScriptRunOutput;

public class UserInput {
	private static final String CONFIG_PATH = (System.getProperty("user.dir").replace("\\", "/") + "/resources/properties/config.properties");
	public static String bashPath;
	private static String pathOfProgram;
	private static String programName;
	private static String programEnding;
	private ArrayList<TestSet> testSets = new ArrayList<TestSet>();
	private int numberOfMutations;
	
	private TestCase sampleInput;
	private static boolean debug = true;
	private Scanner scan = new Scanner(System.in);

	public UserInput() {
		getBashConfigured();
		getAWorkingJavaFile();
		getWorkingTestSets();
		createMutatedFiles();
		getMutationCount();
		removeRandomMutants();
	}
	
	private void removeRandomMutants() {
		try {
			System.out.println("\nRemoving Uneeded Mutated Files...");
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(numberOfMutations < MutatedFile.mutationNumber) {
			MutatedFile.condenseFileNumber(numberOfMutations);
		}
	}

	private void getMutationCount() {
		while(true) {
			if(MutatedFile.mutationNumber > 0) {
				System.out.println("\nWe were able to make " + MutatedFile.mutationNumber + " mutations.");
				System.out.println("How many mutations would you like to have?");
				try {
					int in = scan.nextInt();
					if(in > MutatedFile.mutationNumber) {
						System.out.println("Sorry, but you can only have at most " + MutatedFile.mutationNumber + " mutations.");
					} else if(in < 1) {
						System.out.println("Sorry, but you have to have at least one mutation.");
					} else {
						setNumberOfMutations(in);
						break;
					}
				} catch(Exception e) {
					System.out.println("Must be an integer between 1 and " + MutatedFile.mutationNumber + ".");
				}
			} else {
				System.out.println("We were not able to make any mutations for that program. If you have mutable items, make sure you inputs are done at the top of the class for maximum results.");
				System.out.println("Sorry about that.");
				System.exit(0);
			}
		}
	}

	private void getWorkingTestSets() {
		while(true) {
			testSets = new ArrayList<TestSet>();
			System.out.println("\nEnter your test set(s) where inputs per case are separated by spaces, each case is separated by a comma, and each set is separated by a semicolon");
			System.out.println("ex. '9 12 3,5 11 13,1 6 8;7 25 4,15 19 32,23 17 3'");
			String in = scan.nextLine();
			String[] sets = in.split(";");
			String[] cases;
			for(int i = 0; i < sets.length; i++) {
				ArrayList<TestCase> tc = new ArrayList<TestCase>();
				cases = sets[i].trim().split(",");
				for(int j = 0; j < cases.length; j++) {
					tc.add(new TestCase(new ArrayList<Object>(Arrays.asList(cases[j].trim().split(" ")))));
				}
				testSets.add(new TestSet(tc));
			}
			
			boolean flag = true;
			for(TestSet t : testSets) {
				for(TestCase c : t.getTestCases()) {
					if(!willFileRunWell(pathOfProgram + programName + programEnding, c)) {
						System.out.println("The test case: '" + c.getInputs() + "' did not run well in your program, please swap that out for a case that will work");
						flag = false;
					}
				}
			}
			if(flag) {
				break;
			}
		}
	}

	private void getAWorkingJavaFile() {//Extension
		while(true) {
			String javaFilePath = getValidFilePath("\nPlease enter the path to your .java file that you want mutated. (include name and extension)", ".java");
			File f = new File(javaFilePath);
			setProgramName(f.getName().split("\\.")[0]);
			setProgramEnding("." + f.getName().split("\\.")[1]);
			setPathOfProgram(f.getAbsolutePath().substring(0, (f.getAbsolutePath().length()-f.getName().length())).replace("\\", "/"));
			getFileLinesFromFile(f);
			MutatedFile.initializePaths(pathOfProgram, programName, programEnding);
			cleanFileLocation();
			createRunnableOriginalProgram();
			System.out.println("Enter ONE sample test case for your program (if you have multiple inputs per program run, separate them by space)");
			String in = scan.nextLine().trim();
			if(willFileRunWell(javaFilePath, new TestCase(Arrays.asList(in.split(" "))))) {
				sampleInput = new TestCase(new ArrayList<Object>(Arrays.asList(in)));
				VariableHelper.initializeVariables();
				break;
			} else {
				System.out.println("\nThere was an error when compiling your class. Please make sure your class can compile and run. Also make sure that your test case is able to be used in the program.");
				System.out.println("**Also note, that all input must be through scanners or buffered readers.\n");
			}
		}
	}

	private void getBashConfigured() {
		try {
			FileReader reader=new FileReader(CONFIG_PATH);  
		      
		    Properties p=new Properties();  
			p.load(reader);
		      
		    if(!Paths.get(p.getProperty("bash_path")).toFile().exists()){
		    	System.out.println("Opps! It appears that you don't have git bash, or it's in another place.");
		    	System.out.println("Please download it here: 'https://git-scm.com/downloads'\n");
		    	System.out.println("When you have it installed, or if you have it already installed...");
		    	setBashPath(getValidFilePath("Please enter the path to your git bash.exe file. (Default path: 'C:/Program Files/Git/bin/bash.exe')", "bash.exe"));
		    	System.out.println("Thank you, we will update your bash.exe path, so you will not have to do this again.\n");
		    } else {
		    	setBashPath(p.getProperty("bash_path"));
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	private String getValidFilePath(String promptText, String fileToGet) {
		String filePath = "";
		System.out.println(promptText);
		System.out.println("***Type 'exit' to exit now. You will not be able to later***");
		
		while(true) {
			if(scan.hasNextLine()) {
			
				try {
					filePath = scan.nextLine().trim();
				} catch(NoSuchElementException e) {
					e.printStackTrace();
				}
				
				if("exit".equals(filePath)) {
					System.exit(0);
				}
				
				if(isValidPath(filePath) && (filePath.charAt(filePath.length()-1) != '/' || filePath.charAt(filePath.length()-1) != '\\')) {
					if(!filePath.contains(fileToGet)) {
						System.out.println("That is the wrong file.");
					} else {
						break;
					}
				} else {
					System.out.println("The file: '" + filePath + "' does not exist. Please enter the full path file");
				}
				System.out.println("ex. 'C:/Path/To/File/" + ((fileToGet.charAt(0) == '.')? "SomeFileName" : "") + fileToGet + "'");
				System.out.println(promptText);
			}
		}

		return filePath.replace("\\", "/");
	}
	
	public static boolean isValidPath(String path) {
	    try {
	        Paths.get(path);
	    } catch (InvalidPathException | NullPointerException ex) {
	        return false;
	    }
	    return new File(path).exists();
	}

	public static String getBashPath() {
		return bashPath;
	}

	private static void setBashPath(String bashPath) {
		UserInput.bashPath = bashPath;
		try {
			Properties p=new Properties();  
			p.setProperty("bash_path", bashPath);   
			  
			p.store(new FileWriter(CONFIG_PATH), null);  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public static boolean willFileRunWell(String filePathAndName, TestCase testCase) {
		return !ScriptRunOutput.ERROR.toString().equals(runFile(filePathAndName, testCase).get(0));
	}
	
	private static ArrayList<String> runFile(String filePathAndName, TestCase testCase) {
		try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            
            String command = createCommand(filePathAndName, testCase);
            
            processBuilder.command(bashPath, "-c", command);

            if(debug)System.out.println("STARTING '" + command + "' PROCESS................");
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            BufferedReader errReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String line;
            ArrayList<String> output = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
            	//make sure to not get repeating lines
            	if(output.size() > 0) {
            		if(!(line).equals(output.get(output.size() - 1))) {
	            		output.add(line);
            		}
            	} else {
            		output.add(line);
            	}
            }
            String errLine;
            ArrayList<String> errOutput = new ArrayList<String>(Arrays.asList(ScriptRunOutput.ERROR.toString()));
            while ((errLine = errReader.readLine()) != null) {
            	errOutput.add(errLine);
            }
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                if(debug)System.out.println(" --- '" + command + "' ran successfully");
                return output;
            } else {
                if(debug)System.out.println(" --- '" + command + "' ran unsuccessfully");
                if(debug)System.out.println(errOutput);
                return errOutput;
            }
        } catch (IOException | InterruptedException e) {
            if(debug)System.out.println(" --- Interruption in RunCommand: " + e);
            // Restore interrupted state
            Thread.currentThread().interrupt();
            return null;
        }
	}
	
	private static String createCommand(String file, TestCase testCase) {
		boolean runOriginal = file.contains(UserInput.programName);
		String programName = (new File(file).getName().split("\\.")[0] + ((runOriginal)? "Original" : ""));
		String scriptPath = System.getProperty("user.dir").replace("\\", "/") + "/resources/scripts/";
        String scriptName = "test-file.sh";
        String script = scriptPath + scriptName;
        String pathArg = ((runOriginal)? UserInput.pathOfProgram : MutatedFile.filePath.substring(0, MutatedFile.filePath.length() - 1));
        StringBuilder arrayToPass = new StringBuilder();
        for(Object input : testCase.getInputs()) {
        	arrayToPass.append(input.toString() + " ");
        }
        String scriptArgs = pathArg + " " + programName + " " + arrayToPass.toString();
        
        return "sh " + script + " " + scriptArgs;
	}

	private void getFileLinesFromFile(File f) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			MutatedFile.originalFileLines = new ArrayList<String>();
			VariableHelper.originalFileLines = new ArrayList<String>();
			while (line != null) {
				if(debug)System.out.println(line);
				MutatedFile.originalFileLines.add(line);
				VariableHelper.originalFileLines.add(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void cleanFileLocation() {
		new File(pathOfProgram + programName + "Original" + programEnding).delete();
		new File(pathOfProgram + programName + "Original.class").delete();
	}
	
	public static void createRunnableOriginalProgram() {
		try {
			//get content of original file
			ArrayList<String> lines = MutatedFile.originalFileLines;
			StringBuilder originalFileContentStringBuilder = new StringBuilder();
			String fileName = UserInput.programName + "Original";
			
			for(String line : lines) {
				if(!line.startsWith("package")) {
					if(line.contains(UserInput.programName) && line.contains("class")) {
						line = line.replace(UserInput.programName, fileName);
					}
					originalFileContentStringBuilder.append(line + "\n");
				}
			}
			
			//make sure there is an empty folder for mutated files
			Path path = Paths.get(UserInput.pathOfProgram + fileName + MutatedFile.fileType);
			
			//create original file
			path.toFile().createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(path), true));
	        writer.write(String.valueOf(originalFileContentStringBuilder));
	        writer.flush();
	        writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createMutatedFiles() {
		try {
			System.out.println("\nAbout to create all possible mutations...");
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean inClass = false;
		for(int i = 0; i < MutatedFile.originalFileLines.size(); i++) {
			String line = MutatedFile.originalFileLines.get(i);
			if(line.contains(programName) && line.contains("class")) {
				inClass = true;
			}
			
			if(inClass) {
				if(LineMutator.isLineMutatable(line)) {
					int lineNumber = i + 1;
					for(String mutatedLine : LineMutator.mutateLine(line)) {
						int helper = MutatedFile.mutationNumber;
						new MutatedFile(lineNumber, mutatedLine, sampleInput);
						if(debug && (helper < MutatedFile.mutationNumber))System.out.println("Mutated Line=> " + mutatedLine);
					}
				}
			}
		}
		if(debug)System.out.println("Total Number of Mutations: " + MutatedFile.mutationNumber);
	}

	public String getPathOfProgram() {
		return pathOfProgram;
	}

	private void setPathOfProgram(String pathOfProgram) {
		UserInput.pathOfProgram = pathOfProgram;
	}

	public String getProgramName() {
		return programName;
	}

	private void setProgramName(String programName) {
		UserInput.programName = programName;
	}

	public String getProgramEnding() {
		return programEnding;
	}

	private void setProgramEnding(String programEnding) {
		UserInput.programEnding = programEnding;
	}

	public ArrayList<TestSet> getTestSets() {
		return testSets;
	}

	public int getNumberOfMutations() {
		return numberOfMutations;
	}

	public void setNumberOfMutations(int numberOfMutations) {
		this.numberOfMutations = numberOfMutations;
	}
}
