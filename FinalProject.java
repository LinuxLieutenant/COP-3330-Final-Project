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
				+ "1- Add a new Faculty to the schedule\n"
				+ "2- Enroll a Student to a Lecture\n"
				+ "3- Print the schedule of a Faculty\n"
				+ "4- Print the schedule of an TA\n"
				+ "5- Print the schedule of a Student\n"
				+ "6- Delete a Lecture\n"
				+ "7- Exit\n");
	}
	
	public static void main(String[] args) {
		ArrayList<Person> people = new ArrayList();
		
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
		super("Sorry, incorrect format. (IDs are 7 digits)");
	}
}

abstract class Person{
	private String name, id;
	public Person(String name, String id) {
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}

class Student extends Person{
	private String type;
	private String[] classesTaken;
	public Student(String name, String id, String type, String[] classesTaken) {
		super(name, id);
		this.type = type;
		this.classesTaken = classesTaken;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getClassesTaken() {
		return classesTaken;
	}
	public void setClassesTaken(String[] classesTaken) {
		this.classesTaken = classesTaken;
	}
	
}

class TA extends Student{
	private String[] labsSupervised;
	private String advisor, expectedDegree;
	public TA(String name, String id, String type, String[] classesTaken, String[] labsSupervised, String advisor, String expectedDegree) {
		super(name, id, type, classesTaken);
		this.labsSupervised = labsSupervised;
		this.advisor = advisor;
		this.expectedDegree = expectedDegree;
	}
	public String[] getLabsSupervised() {
		return labsSupervised;
	}
	public void setLabsSupervised(String[] labsSupervised) {
		this.labsSupervised = labsSupervised;
	}
	public String getAdvisor() {
		return advisor;
	}
	public void setAdvisor(String advisor) {
		this.advisor = advisor;
	}
	public String getExpectedDegree() {
		return expectedDegree;
	}
	public void setExpectedDegree(String expectedDegree) {
		this.expectedDegree = expectedDegree;
	}
	
}

class Faculty extends Person{
	private String rank;
	String[] lecturesTaught;
	public Faculty(String name, String ID, String rank, String[] lecturesTaught) {
		super(name, ID);
		this.rank = rank;
		this.lecturesTaught = lecturesTaught;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String[] getLecturesTaught() {
		return lecturesTaught;
	}
	public void setLecturesTaught(String[] lecturesTaught) {
		this.lecturesTaught = lecturesTaught;
	}
	
}