package program.mutator.main;

import program.mutator.controller.ProgramMutatorController;
import program.mutator.pojos.user.UserInput;

public class App
{
		//create a main method
		public static void main(String[] args)
		{
			while(true) {
				UserInput input = new UserInput();
				new ProgramMutatorController(input);
				try {
					System.out.println("\nRefreshing Process...");
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
}
