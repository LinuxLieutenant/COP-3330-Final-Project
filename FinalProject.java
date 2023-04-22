/*
- COP 3330 Final Project.
- Logan McBride, Lily Yarbrough
- (optional) Add anything that you would like the TA to be aware of
*/
import java.io.*;
import java.util.*;

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
	static boolean checkExists(ArrayList<Person> people, String idToCheck) {
		for (Person person : people) {
			if (person.getId().equals(idToCheck))
				return true;
		}
		return false;
	}
	static boolean checkIdFormat(String idToCheck) {
		String input = idToCheck;
		while(true) {
			try{
				if(input.length() != 7 || checkNumeric(input) == false) {
					throw new IdException();
				}else {
					return true;
				}
			}catch(IdException e){
				e.getStackTrace();
				System.out.println(e.getMessage());
				return false;
			}
		}
	}
	static String getLecturePrefix(String CRN, String fileName) throws IOException{
		String prefix = null;
		String line;
		BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
		while ((line = fileInput.readLine()) != null) {						
	            String[] parts = line.split(",");
	            if (line.length() > 2 && parts[0].equalsIgnoreCase(CRN)) {
	            	prefix = parts[1];
	            }
		}
		fileInput.close();
		return prefix;
	}
	static String getLectureTitle(String CRN, String fileName) throws IOException{
		String title = null;
		String line;
		BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
		while ((line = fileInput.readLine()) != null) {						
	            String[] parts = line.split(",");
	            if (line.length() > 2 && parts[0].equalsIgnoreCase(CRN)) {
	            	title = parts[2];
	            }
		}
		fileInput.close();
		return title;
	}
	static String getLectureLevel(String CRN, String fileName) throws IOException{
		String level = null;
		String line;
		BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
		while ((line = fileInput.readLine()) != null) {						
	            String[] parts = line.split(",");
	            if (line.length() > 2 && parts[0].equalsIgnoreCase(CRN)) {
	            	level = parts[3];
	            }
		}
		fileInput.close();
		return level;
	}
	static String getLectureModality(String CRN, String fileName) throws IOException{
		String modality = null;
		String line;
		BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
		while ((line = fileInput.readLine()) != null) {						
	            String[] parts = line.split(",");
	            if (line.length() > 2 && parts[0].equalsIgnoreCase(CRN)) {
	            	modality = parts[4];
	            }
		}
		fileInput.close();
		return modality;
	}
	static String getLectureRoom(String CRN, String fileName) throws IOException{
		String room = null;
		String line;
		BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
		while ((line = fileInput.readLine()) != null) {						
	            String[] parts = line.split(",");
	            if (line.length() > 2 && parts[0].equalsIgnoreCase(CRN)) {
	            	room = parts[1];
	            }
		}
		fileInput.close();
		return room;
	}
	static List<String> getLab(String CRN, String fileName) throws IOException{
		List<String> labCRN = new ArrayList<>();
		List<String> labs = new ArrayList<>();
		String line;
		boolean readingLabs = false;
		//System.out.println(fileName);
		BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
		while ((line = fileInput.readLine()) != null) {		
				//System.out.println(line);
	            String[] parts = line.split(",");
		        if (parts.length > 2 && parts[0].equalsIgnoreCase(CRN) && parts[6].equalsIgnoreCase("yes"))
		           readingLabs = true;
		        else if(parts.length == 2 && readingLabs == true)
		        	labCRN.add(parts[0]);
		        else if(parts.length > 2 && readingLabs == true)
		        	readingLabs = false;
		}
		fileInput.close();
		for (String a : labCRN) {
			BufferedReader fileInput2 = new BufferedReader(new FileReader(fileName));
			while ((line = fileInput2.readLine()) != null) {		
				 String[] parts = line.split(",");
				 if (parts[0].equalsIgnoreCase(a))
					 labs.add(line);
			}
			fileInput2.close();
		}
		
		return labs;
	}
	static boolean checkNumeric(String ID) {
		if (ID == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(ID);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	public static void printAllPeople(ArrayList<Person> people) {
	    for (Person person : people) {
	        System.out.println(person.toString());
	    }
	}
	static boolean checkIfStudent(ArrayList<Person> people, String id, String lab) {
		for (Person person : people) {
			if(person instanceof Student && person.getId().equals(id)) {
				Student temp = (Student) person;
				String[] classesTaken = temp.getClassesTaken();
				for (String a : classesTaken) {
					if (a.equals(lab))
						return true;
				}
			}
	    }
		return false;
	}
	public static void main(String[] args) throws IOException{
		ArrayList<Person> people = new ArrayList();
		
		String ucfID;
		String name;
		String rank;
		String officeLocation;
		String lectures;
		String lectureCRN;
		String[] lecturesArray;
		String fileName;
		String line;
		List<String> labs = new ArrayList<>();
		List<String> lecturesArrayList = new ArrayList<>();
		
		Scanner scanner = new Scanner(System.in);
		int intInput;
		String stringInput;
		System.out.println("Enter the absolute path of the file: ");
		
		while (true) {
			fileName = scanner.nextLine();
			File file = new File(fileName);
        	if (file.exists()) {
            	System.out.println("File Found! Let's proceed...");
            	break;
        	} else {
            	System.out.println("Sorry no such file.\nTry again:");
        	}
		}
		//System.out.println(getLab("69745", fileName));
		while (true){
			//printAllPeople(people);
			mainMenu(); //calls method
			intInput = scanner.nextInt();
			
			if(intInput == 1) {
				
				while(true) {
					System.out.println("Enter UCF id: ");
					stringInput = scanner.next();
					try{
						if(stringInput.length() != 7 || checkNumeric(stringInput) == false) {
							throw new IdException();
						}else {
							ucfID = stringInput;
							break;
						}
					}catch(IdException e){
						e.getStackTrace();
						System.out.println(e.getMessage());
					}
				}
				if (checkExists(people, ucfID) == false) {
					System.out.println("Enter name: ");
					name = scanner.nextLine();
					name = scanner.nextLine();
					
					while(true) {
						System.out.print("Enter rank: ");
						stringInput = scanner.nextLine();
						if(stringInput.toLowerCase().equals ("professor") || stringInput.toLowerCase().equals ("associate professor") || stringInput.toLowerCase().equals ("assistant professor") || stringInput.toLowerCase().equals ("adjunct")){ 
							rank = stringInput;
							break;
						}
						else {
							System.out.println("Rank is not found");
						}
					}
					System.out.println("Enter office location: ");
					officeLocation = scanner.nextLine();
					System.out.println("Enter how many lectures: ");
					lectures = scanner.next();
						
					System.out.println("Enter the crns of the lectures separated by ,: ");
					lectureCRN = scanner.next();
					lecturesArray = lectureCRN.split(",");
					for (String a : lecturesArray) {
						labs = getLab(a, fileName);
						if (!labs.equals(null)) {
							System.out.println(a + "has these labs:");	
							for (String b : labs) {
								System.out.println(b);
							}
							for (String b : labs) {
								String[] parts = b.split(",");
								while (true) {
									System.out.println("Enter the TA's id for " + parts[0]);
									ucfID = scanner.nextLine();
									if (checkIdFormat(ucfID) == true)
										break;
								}
									if (checkExists(people, ucfID) == true) {
										if (checkIfStudent(people, ucfID, b) == false) {
											TA taToUpdate = null;
											for (Person person : people) {
												if(person instanceof TA && person.getId().equals(ucfID)) {
													taToUpdate = (TA) person;
													taToUpdate.addLabsSupervised(b);
												}
											}
										}else {
											System.out.println("Sorry, The TA that you entered was found to be a student taking that lecture.");
										}
									}else if(checkExists(people, ucfID) == false){
										if (checkIfStudent(people, ucfID, b) == false) {
											//Call Lily's method to add a faculty with this ID
										}else {
											System.out.println("Sorry, The TA that you entered was found to be a student taking that lecture.");
										}
									}
							}
						}
					}
					lecturesArrayList = null;
					for (String a : lecturesArray) {
						lecturesArrayList.add(a);
					}
					Faculty faculty = new Faculty(ucfID, name, rank, lecturesArrayList, officeLocation);
					people.add(faculty);

				}else {
					Faculty facultyToUpdate = null;
					for (Person person : people) {
						if(person instanceof Faculty && person.getId().equals(ucfID)) {
							System.out.println("Enter how many lectures: ");
							lectures = scanner.nextLine();
							lectures = scanner.nextLine();
							System.out.println("Enter the crns of the lectures: ");
							lectureCRN = scanner.nextLine();
							lecturesArray = lectureCRN.split(",");
							lecturesArrayList = null;
							for (String a : lecturesArray) {
								facultyToUpdate.addLecturesTaught(a);
							}
							facultyToUpdate = (Faculty) person;
							//facultyToUpdate.setLecturesTaught(lecturesArray);
						}
					}
				}
			}
			if(intInput == 2) {
				System.out.print("Enter UCF id: ");
				stringInput = scanner.nextLine();
				
				System.out.print("Record found/Name: (enter the person's name here)"); //*****needs the name printed******
				
				System.out.print("Which lecture to enroll [] in?"); //*****needs the name printed in the brackets******
				
				//[COP5690/Programming Languages II] has these labs:
					//19005,MSB-103
					//30008,PSY-107
					//20300,HSA1-16
				
				//[Erick Johann] is added to lab : 30008
				
				System.out.println("Student Enrolled!");
			}
			if(intInput == 3) {
				System.out.print("Enter the UCF id: ");
				ucfID = scanner.nextLine();
				Faculty facultyToPrint = null;
				for(Person person : people) {
					if(person instanceof Faculty && person.getId().equals(ucfID)) { 
						facultyToPrint = (Faculty) person;
						System.out.print(facultyToPrint.getId());
						System.out.print("\n[insert name] is teaching the following lectures:"); //come back to later
						System.out.print("\n["  );
						
					}
				}
				
			}
			if(intInput == 4) {
				System.out.println("Enter the TA's UCF id: ");
				ucfID = scanner.nextLine();
			//create if statement in while(true) loop that'll check if it exists, if not, it goes back to the menu	
			}
			if(intInput == 5) {
				System.out.print("Enter the UCF id: ");
				//if/else statement that checks if person exists
				System.out.println("Record found:");
				//prints full name
				System.out.println("Enrolled in the following lectures:\n");
				//prints out the lectures in this format: [COP5690/Programming Languages II]/[Lab: 30008]
			}
			if(intInput == 6) {
				System.out.print("Enter the crn of the lecture to delete: ");
				stringInput = scanner.nextLine();
				//[36637/SOF2058/Introduction to Software] Deleted
			}
			if(intInput == 7) {
				System.out.print("You have made a deletion of at least one lecture. Would you like to\r\n"
						+ "print the copy of lec.txt? Enter y/Y for Yes or n/N for No: ");
				stringInput = scanner.nextLine();
				if(stringInput != "y" || stringInput != "n" || stringInput != "Y" || stringInput != "N") {
					System.out.print("Is that a yes or no? Enter y/Y for Yes or n/N for No:");
	
				}
				else {
					System.out.println("Bye!");
					//add terminating thing here
					break;
				}
				
			}
			
			
			
		}
		scanner.close();
	}
}

class IdException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdException() {
		super(">>>>>>>>>>>>Sorry, incorrect format. (IDs are 7 digits)");
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
	@Override
	public String toString() {
		return "Person [name=" + name + ", id=" + id + "]";
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
	@Override
	public String toString() {
		return "Student [type=" + type + ", classesTaken=" + Arrays.toString(classesTaken) + ", getName()=" + getName()
				+ ", getId()=" + getId() + "]";
	}
	
}

class TA extends Student{
	private List<String> labsSupervised;
	private String advisor, expectedDegree;
	public TA(String name, String id, String type, String[] classesTaken, List<String> labsSupervised, String advisor, String expectedDegree) {
		super(name, id, type, classesTaken);
		this.labsSupervised = labsSupervised;
		this.advisor = advisor;
		this.expectedDegree = expectedDegree;
	}
	public List<String> getLabsSupervised() {
		return labsSupervised;
	}
	public void setLabsSupervised(List<String> labsSupervised) {
		this.labsSupervised = labsSupervised;
	}
	public void addLabsSupervised(String labsSupervised) {
		this.labsSupervised.add(labsSupervised);
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
	@Override
	public String toString() {
		return "TA [labsSupervised=" + (labsSupervised.toString()) + ", advisor=" + advisor + ", expectedDegree="
				+ expectedDegree + ", getType()=" + getType() + ", getClassesTaken()="
				+ Arrays.toString(getClassesTaken()) + ", getName()=" + getName() + ", getId()=" + getId() + "]";
	}
	
	
}

class Faculty extends Person{
	private String rank, officeLocation;
	List<String> lecturesTaught;
	public Faculty(String ID, String name, String rank, List<String> lecturesTaught, String officeLocation) {
		super(name, ID);
		this.rank = rank;
		this.officeLocation = officeLocation;
		this.lecturesTaught = lecturesTaught;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getOfficeLocation() {
		return officeLocation;
	}
	public void setOfficelocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}
	public List<String> getLecturesTaught() {
		return lecturesTaught;
	}
	public void setLecturesTaught(List<String> lecturesTaught) {
		this.lecturesTaught = lecturesTaught;
	}
	public void addLecturesTaught(String lecture) {
		this.lecturesTaught.add(lecture);
	}
	@Override
	public String toString() {
		return "Faculty [ID =" + getId() + "name =" + getName() + "rank=" + rank + ", officeLocation=" + officeLocation + ", lecturesTaught="
				+ lecturesTaught.toString() + "]";
	}
	
	
}