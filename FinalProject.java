/*
- COP 3330 Final Project.
- Logan McBride, Lily Yarbrough
- (optional) Add anything that you would like the TA to be aware of
*/
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FinalProject {
	
	static void mainMenu() { //holds the menu, I decided against doing 7 print lines as he said to avoid redundancy 
		System.out.println("*****************************************\n"
				+ "\t1- Add a new Faculty to the schedule\n"
				+ "\t2- Enroll a Student to a Lecture\n"
				+ "\t3- Print the schedule of a Faculty\n"
				+ "\t4- Print the schedule of an TA\n"
				+ "\t5- Print the schedule of a Student\n"
				+ "\t6- Delete a Lecture\n"
				+ "\t7- Exit\n"
				+ "\t\tEnter your choice:  ");
	}
	
	public static void main(String[] args) {
		String ucfID = "";
		String name = "";
		String rank = "";
		String officeLocation = "";
		String lectures = "";
		String lectureCRN = "";
		
		Scanner scanner = new Scanner(System.in);
		int intInput;
		String stringInput;
		
		System.out.println("Enter the absolute path of the file: ");
		
		while (true) {
			stringInput = scanner.nextLine();
			File file = new File(stringInput);
        	if (file.exists()) {
            	System.out.println("File Found! Let's proceed...");
            	break;
        	} else {
            	System.out.println("Sorry no such file.\nTry again:");
        	}
		}
		mainMenu(); //calls method
		intInput = scanner.nextInt();
		
		if(intInput == 1) {
			
			System.out.println("\tEnter UCF id: ");
			stringInput = scanner.nextLine();
			try{
				if(stringInput.length() != 7) {
					throw new IdException();
				}
			}catch(IdException e){
				e.getStackTrace();
			}
				
		}
			
			System.out.println("\tEnter name: ");
			stringInput = scanner.nextLine();
			
			System.out.println("Enter rank: ");
			stringInput = scanner.nextLine();
			if(stringInput.toLowerCase().contains("professor") || stringInput.toLowerCase().contains("adjunct")){ //come back to this later
				if (rank.equalsIgnoreCase("professor")) {
					rank = stringInput;
				
			}
			
			System.out.println("Enter office location: ");
			officeLocation = scanner.nextLine();
			
			System.out.println("Enter how many lectures: ");
			lectures = scanner.nextLine();
			
			System.out.println("Enter the crns of the lectures: ");
			lectureCRN = scanner.nextLine();
		}
		
		
		
		scanner.close();
	}

}
class IdException extends Exception{
	public IdException() {
		super("Sorry, incorrect format. (IDs are 7 digits)"); //extends exception
	}
}
	