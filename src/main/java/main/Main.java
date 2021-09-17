package main;

import controller.*;
import java.util.*;

public class Main {
	
	public static void main(String args[]) {
		
		//These are for testing purposes! The given values should be given to the Controller by the UI, not main! Main only exists to start the program and set the GUI!		
		Controller controller = new Controller();
		controller.setInputManagement();
		
		System.out.println("End of program!");
		
		
		//System.out.println(controller + " aaa");
		//controller.setInputManagement("Heavy Metal");
		
	}

}
