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
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the absolute path of the file: ");
		String input = scanner.nextLine();
		try{
			File file = new File(input);
		}catch(IOException e) {
			System.out.println("Sorry no such file");
			e.printStackTrace();
		}
		System.out.println("test");
		scanner.close();
	}

}
