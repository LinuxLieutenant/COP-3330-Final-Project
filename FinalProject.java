/*
- COP 3330 Final Project.
- Logan McBride, Lily Yarbrough
- (optional) Add anything that you would like the TA to be aware of
*/
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FinalProject {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the absolute path of the file: ");
		while (true) {
			String input = scanner.nextLine();
			File file = new File(input);
        	if (file.exists()) {
            	System.out.println("File Found! Let's proceed...");
            	break;
        	} else {
            	System.out.println("Sorry no such file.\nTry again:");
        	}
		}
		System.out.println("*******************************************");
		
		
		
		
		
		
		scanner.close();
	}

}
class IdException extends Exception{
	public IdException() {
		super("Sorry, incorrect format. (IDs are 7 digits)");
	}
}
