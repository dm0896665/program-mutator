package program.mutator.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import program.mutator.controller.ProgramMutatorController;

public class App extends Application
{
	
	//override the start method of the application class
		@Override
		public void start(Stage primaryStage) throws IOException
		{
			Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
			
			//create a new Scene and add the root
			Scene scene = new Scene(root, 900, 700);
			
			//add the scene to the stage
			primaryStage.setScene(scene);
			
			//give the stage a name
			primaryStage.setTitle("Program Mutator");
			
			//display the stage
			primaryStage.show();
		}
		
		//create a main method
		public static void main(String[] args)
		{
			//launch application
			//launch(args);
			
			new ProgramMutatorController();
		}
}
