/*
- COP 3330 Final Project.
- Logan McBride, Lily Yarbrough
- (optional) Add anything that you would like the TA to be aware of
*/
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FinalProject {
	static void MainMenu() { //holds the menu, I decided against doing 7 print lines as he said to avoid redundancy 
		System.out.println("*****************************************\n"
				+ "1- Add a new Faculty to the schedule\n"
				+ "2- Enroll a Student to a Lecture\n"
				+ "3- Print the schedule of a Faculty\n"
				+ "4- Print the schedule of an TA\n"
				+ "5- Print the schedule of a Student\n"
				+ "6- Delete a Lecture\n"
				+ "7- Exit\n");
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the absolute path of the file: ");
		
		while(true) {
			String input = scanner.nextLine();
			File file = new File(input);
			if(file.exists()) {
				System.out.println("File found! Let's proceed...");
			}
			else
				System.out.print("Sorry no such file.\nTry again:");
		}
		

		scanner.close();
	}
	
}