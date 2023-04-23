/*
- COP 3330 Final Project.
- Logan McBride, Lily Yarbrough
- (optional) Add anything that you would like the TA to be aware of
- C:\Users\Logan\eclipse-workspace\Java Final Project\lec.txt
*/
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	            if (parts.length > 2 && parts[0].equalsIgnoreCase(CRN)) {
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
	            if (parts.length > 2 && parts[0].equalsIgnoreCase(CRN)) {
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
	            if (parts.length > 2 && parts[0].equalsIgnoreCase(CRN)) {
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
	            if (parts.length > 2 && parts[0].equalsIgnoreCase(CRN)) {
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
	            if (parts.length > 2 && parts[0].equalsIgnoreCase(CRN)) {
	            	room = parts[1];
	            }
		}
		fileInput.close();
		return room;
	}
	static boolean hasLab(String CRN, String fileName) throws IOException{
		String line;
		BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
		while ((line = fileInput.readLine()) != null) {		
				//System.out.println(line);
	            String[] parts = line.split(",");
		        if (parts.length > 5 && parts[0].equalsIgnoreCase(CRN) && parts[6].equalsIgnoreCase("yes"))
		           return true;
		}
		fileInput.close();
		return false;
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
		        if (parts.length > 5 && parts[0].equalsIgnoreCase(CRN) && parts[6].equalsIgnoreCase("yes"))
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
	static String getLabCRN(String lab, String fileName) throws IOException{
		String labCRN = null;
		String line;
		BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
		while ((line = fileInput.readLine()) != null) {		
	            String[] parts = line.split(",");
		        if (line.equalsIgnoreCase(lab)) {
		           labCRN = parts[0];
		           break;
		        }
		}
		fileInput.close();	
		return labCRN;
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
				List<String> classesTaken = temp.getClassesTaken();
				if (Objects.equals(classesTaken, null))
					return false;
				else {
					for (String a : classesTaken) {
						if (a.equals(lab))
							return true;
					}
				}
			}
	    }
		return false;
	}
	static boolean checkIfTA(ArrayList<Person> people, String id, String lecture) {
		for (Person person : people) {
			if(person instanceof TA && person.getId().equals(id)) {
				TA temp = (TA) person;
				List<String> classesTaken = temp.getClassesTaken();
				if (Objects.equals(classesTaken, null))
					return false;
				else {
					for (String a : classesTaken) {
						if (a.equals(lecture))
							return true;
					}
				}
			}
	    }
		return false;
	}
	static boolean checkIfLectureIsAssigned(ArrayList<Person> people, String CRN) {
		for (Person person : people) {
			if (person instanceof Faculty) {
				Faculty temp = (Faculty) person;
				for (String a : temp.getLecturesTaught()) {
					if (a.equalsIgnoreCase(CRN)){
						return true;
					}
				}
			}
		}
		return false;
	}
	static String findName(ArrayList<Person> people, String ID) {
		for (Person person : people) {
			if (person.getId().equals(ID)) {
				return person.getName();
			}
		}
		return null;
	}
	static void deleteLecture(String CRN, String fileName) throws IOException {
		String line;
	    boolean readingLabs = false;
	    Integer lineIndex = 0;
	    List<Integer> toDelete = new ArrayList<>();
	    BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
	    while ((line = fileInput.readLine()) != null) {
	        lineIndex++;
	        String[] parts = line.split(",");
	        if (parts.length > 0 && parts[0].equalsIgnoreCase(CRN)) {
	            toDelete.add(lineIndex);
	            if (parts.length > 5 && parts[6].equalsIgnoreCase("yes")) {
	                // Delete all two-element entries under this lecture
	            	readingLabs = true;
	                String labLine;
	                Integer labLineIndex = lineIndex;
	                while ((labLine = fileInput.readLine()) != null) {
	                    labLineIndex++;
	                    String[] labParts = labLine.split(",");
	                    if (labParts.length == 2) {
	                        toDelete.add(labLineIndex);
	                    } else {
	                    	readingLabs = false;
	                        break;
	                    }
	                }
	            }
	        }
	    }
	    Path filePath = Paths.get(fileName);
	    List<String> fileContent = new ArrayList<>(Files.readAllLines(filePath, StandardCharsets.UTF_8));
	    for (Integer index : toDelete) {
	        fileContent.set(index - 1, null);
	    }
	    fileContent.removeIf(lineToRemove -> lineToRemove == null);
	    Files.write(filePath, fileContent, StandardCharsets.UTF_8);
	    fileInput.close();
	}
	static String getLectureInfo(String CRN, String fileName) throws IOException{
		String line;
		String prefix;
		String title;
		String modality;
		String type;
		String location;
		String info = null;
		BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
		while ((line = fileInput.readLine()) != null) {						
	            String[] parts = line.split(",");
	            if (parts.length > 2 && parts[0].equalsIgnoreCase(CRN)) {
	            	prefix = parts[1];
	            	title = parts[2];
	            	type = parts[3];
	            	modality = parts[4];
	            	if(parts.length > 5) {
	            		location = parts[5];
	            		info = "[" + CRN + "/" + prefix + "/" + title + "/" + modality + "]";
	            		return info;
	            	} else {
	            		info = "[" + CRN + "/" + prefix + "/" + title + "/" + modality + "]";
	            		return info;
	            	}
	            	
	            } else if(parts.length == 2 && parts[0].equalsIgnoreCase(CRN)){
	            	prefix = parts[1];
	            	info = "[" + CRN + "/" + prefix + "]";
	            	return info;
	            }
		}
		fileInput.close();
		return info;
	}
	static boolean checkIfLabMatches(String labCRN, String lectureCRN, String fileName) throws IOException{
		String line;
		boolean readingLabs = false;
		boolean isLab = false;
		BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
	    while ((line = fileInput.readLine()) != null) {
	        String[] parts = line.split(",");
	        if (parts.length > 5 && parts[0].equalsIgnoreCase(lectureCRN) && parts[6].equalsIgnoreCase("yes")) {
	            readingLabs = true;
	            String labLine;
	            while ((labLine = fileInput.readLine()) != null) {
	               String[] labParts = labLine.split(",");
	               if (labParts.length == 2 && labParts[0].equalsIgnoreCase(labCRN)) {
	            	   isLab = true;
	            	   return isLab;
	               } else {
	            	   readingLabs = false;
	            	   isLab = false;
	                   break;
	               }
	            }
	        } else {
	        	isLab = false;
	        }
	    }
		
		return isLab;
	}
//--------------------------------------Main------------------------------------------------
	public static void main(String[] args) throws IOException{
		ArrayList<Person> people = new ArrayList();
		
		String ucfID = null;
		String name;
		String rank;
		String officeLocation;
		String lectures;
		String lectureCRN;
		String[] lecturesArray;
		String fileName;
		String line;
		List<String> labs = new ArrayList<>();
		
		
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
			printAllPeople(people);
			mainMenu(); //calls method
			stringInput = scanner.nextLine();
//----------------------------------Option 1---------------------------------//
			if(stringInput.equals("1")) {
				
				while(true) {
					System.out.println("Enter UCF id: ");
					stringInput = scanner.nextLine();
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
					lectures = scanner.nextLine();
						
					System.out.println("Enter the crns of the lectures separated by ,: ");
					lectureCRN = scanner.nextLine();
					lecturesArray = lectureCRN.split(",");
					//------------This code will remove any lectures that are already assigned-----------
					//String[] lecturesArrayNew = null;
					for (String a : lecturesArray) {
						if (checkIfLectureIsAssigned(people, a) == true) {
							System.out.println("Sorry, " + a + " is already assigned");
							//elementToRemove = a;
						}

					}
						
					//-------------------------------------------------------------------------------
					for (String a : lecturesArray) {
						labs = getLab(a, fileName);
						if (!labs.isEmpty()) {
							System.out.println("[" + a + "/" + getLecturePrefix(a, fileName) + "/" + getLectureTitle(a, fileName) + "]" + " has these labs:");	
							for (String b : labs) {
								System.out.println(b);
							}
							for (String b : labs) {
								String ucfID2;
								String[] parts = b.split(",");
								while (true) {
									System.out.println("Enter the TA's id for " + parts[0]);
									ucfID2 = scanner.nextLine();
									if (checkIdFormat(ucfID2) == true)
										break;
								}
									if (checkExists(people, ucfID2) == true) {
										if (checkIfStudent(people, ucfID2, b) == false) {
											TA taToUpdate = null;
											for (Person person : people) {
												if(person instanceof TA && person.getId().equals(ucfID2)) {
													taToUpdate = (TA) person;
													taToUpdate.addLabsSupervised(b);
												}
											}
										}else {
											System.out.println("Sorry, The TA that you entered was found to be a student taking that lecture.");
										}
									}else if(checkExists(people, ucfID2) == false){
										if (checkIfStudent(people, ucfID2, b) == false) {
											System.out.print("Name of TA: "); //asking for TA name
											name = scanner.nextLine();
											System.out.print("TA’s supervisor’s name: "); //asks for super. name
											//scanner.nextLine();
											String supervisorName = scanner.nextLine();
											String expectedDegree = "";
											while(true) {
												System.out.print("Degree seeking: ");
												expectedDegree = scanner.nextLine();
												if(!(expectedDegree.equalsIgnoreCase("ms")  || expectedDegree.equalsIgnoreCase("phd") )) { //checks if not ms or phd
													System.out.println("Sorry, this is not a valid degree, please try again.\n"); //lets them try again
												}
												else {
													break; //leaves the while-true
												}
											}
											TA ta = new TA(name,ucfID2,"graduate",null,null,supervisorName,expectedDegree);
											ta.addLabsSupervised(b);
											people.add(ta);
											//System.out.println("[" + a + "/" + getLecturePrefix(a, fileName) + "/" + getLectureTitle(a, fileName) + "]" + " Added!");
										}else {
											System.out.println("Sorry, The TA that you entered was found to be a student taking that lecture.");
										}
									}
							}
							System.out.println("[" + a + "/" + getLecturePrefix(a, fileName) + "/" + getLectureTitle(a, fileName) + "]" + " Added!");
						} else {
							System.out.println("[" + a + "/" + getLecturePrefix(a, fileName) + "/" + getLectureTitle(a, fileName) + "]" + " Added!");
						}
					}
					List<String> lecturesArrayList = new ArrayList<>();
					for (String a : lecturesArray) {
						lecturesArrayList.add(a);
					}
					Faculty faculty = new Faculty(ucfID, name, rank, lecturesArrayList, officeLocation);
					people.add(faculty);

				}else {
					System.out.println("Enter how many lectures: ");
					lectures = scanner.nextLine();
						
					System.out.println("Enter the crns of the lectures separated by ,: ");
					lectureCRN = scanner.nextLine();
					lecturesArray = lectureCRN.split(",");
					//------------This code will remove any lectures that are already assigned-----------
					//String[] lecturesArrayNew = null;
					for (String a : lecturesArray) {
						if (checkIfLectureIsAssigned(people, a) == true) {
							System.out.println("Sorry, " + a + " is already assigned");
							//elementToRemove = a;
						}
					}
					//-------------------------------------------------------------------------------
					for (String a : lecturesArray) {
						labs = getLab(a, fileName);
						if (!labs.isEmpty()) {
							System.out.println("[" + a + "/" + getLecturePrefix(a, fileName) + "/" + getLectureTitle(a, fileName) + "]" + " has these labs:");	
							for (String b : labs) {
								System.out.println(b);
							}
							for (String b : labs) {
								String[] parts = b.split(",");
								String ucfID2;
								while (true) {
									System.out.println("Enter the TA's id for " + parts[0]);
									ucfID2 = scanner.nextLine();
									if (checkIdFormat(ucfID2) == true)
										break;
								}
									if (checkExists(people, ucfID2) == true) {
										if (checkIfStudent(people, ucfID2, b) == false) {
											TA taToUpdate = null;
											for (Person person : people) {
												if(person instanceof TA && person.getId().equals(ucfID2)) {
													taToUpdate = (TA) person;
													taToUpdate.addLabsSupervised(b);
												}
											}
										}else {
											System.out.println("Sorry, The TA that you entered was found to be a student taking that lecture.");
										}
									}else if(checkExists(people, ucfID2) == false){
										if (checkIfStudent(people, ucfID2, b) == false) {
											//Call Lily's method to add a faculty with this ID
											System.out.print("Name of TA: "); //asking for TA name
											name = scanner.nextLine();
											//name = scanner.nextLine();
											System.out.print("TA’s supervisor’s name: "); //asks for super. name
											String supervisorName = scanner.nextLine();
											String expectedDegree = "";
											while(true) {
												System.out.print("Degree seeking: ");
												expectedDegree = scanner.nextLine();
												if(!(expectedDegree.equalsIgnoreCase("ms")  || expectedDegree.equalsIgnoreCase("phd") )) { //checks if not ms or phd
													System.out.println("Sorry, this is not a valid degree, please try again.\n"); //lets them try again
												}
												else {
													break; //leaves the while-true
												}
											}
											TA ta = new TA(name,ucfID2,"graduate",null,null,supervisorName,expectedDegree);
											ta.addLabsSupervised(b);
											people.add(ta);
											System.out.println("[" + a + "/" + getLecturePrefix(a, fileName) + "/" + getLectureTitle(a, fileName) + "]" + " Added!");
										}else {
											System.out.println("Sorry, The TA that you entered was found to be a student taking that lecture.");
										}
									}
							}
						} else {
							System.out.println("[" + a + "/" + getLecturePrefix(a, fileName) + "/" + getLectureTitle(a, fileName) + "]" + " Added!");
						}
						for (Person b : people) {
							Faculty temp = (Faculty) b;
							if (b.getId().equalsIgnoreCase(ucfID)) {
								temp.addLecturesTaught(a);
							}
						}
					}			
					
				}
			}
//------------------------------------Option 2-------------------------------------------
			if(stringInput.equals("2")) {
				while(true) {
					System.out.println("Enter UCF id: ");
					stringInput = scanner.nextLine();
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
				if (checkExists(people, ucfID) == true) {
					System.out.println("Record found/Name: " + findName(people, ucfID));
					System.out.println("Which lecture to enroll [" + findName(people, ucfID) + "] in?");
					lectureCRN = scanner.nextLine();
					//lectureCRN = scanner.nextLine();
					if (checkIfTA(people, ucfID, lectureCRN) == false) {
						if (hasLab(lectureCRN, fileName) == true) {
							System.out.println("[" + getLecturePrefix(lectureCRN, fileName) + "/" + getLectureTitle(lectureCRN, fileName) + "] has these labs:");
							labs = getLab(lectureCRN, fileName);
							String[] labArray = new String[labs.size()];
							int i = 0;
							for (String lab : labs) {
								System.out.println(lab);
								labArray[i] = lab;
								i++;
							}
							Random random = new Random();
							int randomInt = random.nextInt(labs.size()-1);
							String labAssigned = labArray[randomInt];
							System.out.println("[" + findName(people, ucfID) + "] is added to lab : " + labAssigned);
							System.out.println("Student enrolled!");
							Student studentToUpdate;
							for (Person a : people) {
								if (a instanceof Student && a.getId().equals(ucfID)) {
									studentToUpdate = (Student) a;
									studentToUpdate.addClassesTaken(lectureCRN);
									studentToUpdate.addClassesTaken(getLabCRN(labAssigned, fileName));
								}
							}
						} else {
							Student studentToUpdate;
							for (Person a : people) {
								System.out.println("Student enrolled!");
								if (a instanceof Student && a.getId().equals(ucfID)) {
									studentToUpdate = (Student) a;
									studentToUpdate.addClassesTaken(lectureCRN);
								}
							}
						}
					} else {
						System.out.println("Sorry, this person is already a TA for this class");
					}
				} else {
					System.out.print("Record not found. Enter Name: ");
					name = scanner.nextLine();
					//name = scanner.nextLine();
					String type;
					while (true) {
						System.out.print("Enter Student Type (Undergraduate/Graduate): ");
						type = scanner.nextLine();
						if ((type.equalsIgnoreCase("undergraduate") || type.equalsIgnoreCase("graduate"))) {
							break;
						} else {
							System.out.println("Sorry, Please try again");
						}
					}
					Student student = new Student(name, ucfID, type, null);
					people.add(student);
					System.out.print("Which lecture to enroll [" + name + "] in?");
					lectureCRN = scanner.nextLine();
					//lectureCRN = scanner.nextLine();
					if (checkIfTA(people, ucfID, lectureCRN) == false) {
						if (hasLab(lectureCRN, fileName) == true) {
							System.out.println("[" + getLecturePrefix(lectureCRN, fileName) + "/" + getLectureTitle(lectureCRN, fileName) + "] has these labs:");
							labs = getLab(lectureCRN, fileName);
							String[] labArray = new String[labs.size()];
							int i;
							for (String lab : labs) {
								i = 0; 
								System.out.println(lab);
								labArray[i] = lab;
							}
							Random random = new Random();
							int randomInt = random.nextInt(labs.size()-1);
							String labAssigned = labArray[randomInt];
							System.out.println("[" + name + "] is added to lab : " + labAssigned);
							System.out.println("Student enrolled!");
							Student studentToUpdate;
							for (Person a : people) {
								if (a instanceof Student && a.getId().equals(ucfID)) {
									studentToUpdate = (Student) a;
									studentToUpdate.addClassesTaken(lectureCRN);
									studentToUpdate.addClassesTaken(getLabCRN(labAssigned, fileName));
								}
							}
						} else {
							Student studentToUpdate;
							for (Person a : people) {
								System.out.println("Student enrolled!");
								if (a instanceof Student && a.getId().equals(ucfID)) {
									studentToUpdate = (Student) a;
									studentToUpdate.addClassesTaken(lectureCRN);
								}
							}
						}
					} else {
						System.out.println("Sorry, this person is already a TA for this class");
					}
				}
			}
//-------------------------------------Option 3-----------------------------------------
			if(stringInput.equals("3")) {
				while(true) {
					System.out.println("Enter UCF id: ");
					stringInput = scanner.nextLine();
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
				if (checkExists(people, ucfID) == true) {
					System.out.println(findName(people, ucfID) + "is teaching the following lectures:");
					Faculty facultyToPrint = null;
					List<String> lecturesTaught;
					for(Person person : people) {
						if(person instanceof Faculty && person.getId().equals(ucfID)) { 
							facultyToPrint = (Faculty) person;
							lecturesTaught = facultyToPrint.getLecturesTaught();
							for (String lecture : lecturesTaught) {
								if (hasLab(lecture, fileName) == true) {
									System.out.println("[" + getLecturePrefix(lecture, fileName) + "/" + getLectureTitle(lecture, fileName) + "]" + "with Labs:");
									labs = getLab(lecture, fileName);
									for (String lab : labs) {
										System.out.println(lab);
									}
								} else {
									System.out.println("[" + getLecturePrefix(lecture, fileName) + "/" + getLectureTitle(lecture, fileName) + "]" + "[" + getLectureModality(lecture, fileName) + "]");
								}
							}
						}
					}
				} else {
					System.out.println("Sorry no Faculty found.");
				}				
			}
//---------------------------------Option 4---------------------------------------------------
			if(stringInput.equals("5")) {
				while(true) {
					System.out.println("Enter UCF id: ");
					stringInput = scanner.nextLine();
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
				if (checkExists(people, ucfID) == true) {
					System.out.println(findName(people, ucfID) + "is teaching the following labs:");
					TA taToPrint = null;
					List<String> labsSupervised;
					for(Person person : people) {
						if(person instanceof Student && person.getId().equals(ucfID)) { 
							taToPrint = (TA) person;
							labsSupervised = taToPrint.getLabsSupervised();
							
							
							for (String lab : labsSupervised) {
								System.out.println(lab);
							}
						}
					}
				} else {
					System.out.println("Sorry no TA found.");
				}				
			}
//--------------------------------Option 5--------------------------------------------
			if(stringInput.equals("5")) {
				while(true) {
					System.out.println("Enter student UCF id: ");
					stringInput = scanner.nextLine();
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
				if (checkExists(people, ucfID) == true) {
					System.out.println(findName(people, ucfID) + "is enrolled in the following lectures:");
					Student studentToPrint;
					List<String> classesTaken;
					for(Person person : people) {
						if(person instanceof Student && person.getId().equals(ucfID)) { 
							studentToPrint = (Student) person;
							classesTaken = studentToPrint.getClassesTaken();
							for (String classes : classesTaken) {
								if (hasLab(classes, fileName) == true) {
									String labSection = null;
									for (String labsTaken : classesTaken) {
										if (checkIfLabMatches(labsTaken, classes, fileName) == true) {
											labSection = labsTaken;
										}
									}
									System.out.println("[" + getLecturePrefix(classes, fileName) + "/" + getLectureTitle(classes, fileName) + "]/[Lab: " + labSection + "]");
									//labs = getLab(classes, fileName);
									for (String lab : labs) {
										System.out.println(lab);
									}
								} else {
									System.out.println("[" + getLecturePrefix(classes, fileName) + "/" + getLectureTitle(classes, fileName) + "]" + "[" + getLectureModality(classes, fileName) + "]");
								}
							}
						}
					}
				} else {
					System.out.println("Sorry no Student found.");
				}
			}
//-----------------------------------------Option 6------------------------------------------------
			if(stringInput.equals("6")) {
				System.out.print("Enter the crn of the lecture to delete: ");
				lectureCRN = scanner.nextLine();
				//lectureCRN = scanner.nextLine();
				System.out.println("[" + getLecturePrefix(lectureCRN, fileName) + "/" + getLectureTitle(lectureCRN, fileName) + "]" + "[" + getLectureModality(lectureCRN, fileName) + "] Deleted");
				for (Person a : people) {
					if (a instanceof Faculty ) {
						if (((Faculty) a).getLecturesTaught().contains(lectureCRN)) {
							((Faculty) a).removeLecturesTaught(lectureCRN);
						}
					} else if(a instanceof TA) {
						labs = getLab(lectureCRN, fileName);
						for (String lab : labs) {
							if (((TA) a).getLabsSupervised().contains(lab))
								((TA) a).removeLabsSupervised(lab);
						}
					} else if(a instanceof Student){
						if (((Student) a).getClassesTaken().contains(lectureCRN))
							((Student) a).removeClassesTaken(lectureCRN);
					}
				}
				deleteLecture(lectureCRN, fileName);
			}
//--------------------------------------Option 7-------------------------------------
			if(stringInput.equals("7")) {
				System.out.print("You have made a deletion of at least one lecture. Would you like to\r\n"
						+ "print the copy of lec.txt? Enter y/Y for Yes or n/N for No: ");
				stringInput = scanner.nextLine();
				//stringInput = scanner.nextLine();
				while (true) {
					if(!(stringInput.equalsIgnoreCase("y") || stringInput.equalsIgnoreCase("n"))) {
						System.out.print("Is that a yes or no? Enter y/Y for Yes or n/N for No:");
						stringInput = scanner.nextLine();
					}else {
						break;
					}
				}
				if (stringInput.equalsIgnoreCase("y")) {
					Path filePath = Paths.get(fileName);
					try {
						List<String> fileContent = new ArrayList<>(Files.readAllLines(filePath));
						System.out.println(fileContent);
						System.out.println("Bye!");
						break;
					}catch(IOException e) {
						System.err.println("Error reading files " + e.getMessage());
					}
				}else {
					System.out.println("Bye!");
					//add terminating thing here
					break;
				}
			} else {
				System.out.println("Please Try Again");
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
	private List<String> classesTaken = new ArrayList<>();
	public Student(String name, String id, String type, List<String> classesTaken) {
		super(name, id);
		this.type = type;
		if (classesTaken == null) {
		    this.classesTaken = new ArrayList<String>();
		} else {
		    this.classesTaken = classesTaken;
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getClassesTaken() {
		return classesTaken;
	}
	public void setClassesTaken(List<String> classesTaken) {
		this.classesTaken = classesTaken;
	}
	public void addClassesTaken(String classTaken) {
		this.classesTaken.add(classTaken);
	}
	public void removeClassesTaken(String classTaken) {
		this.classesTaken.remove(classTaken);
	}
	@Override
	public String toString() {
		return "Student [type=" + type + ", classesTaken=" + classesTaken.toString() + ", getName()=" + getName()
				+ ", getId()=" + getId() + "]";
	}
	
}

class TA extends Student{
	private List<String> labsSupervised = new ArrayList<String>();
	private String advisor, expectedDegree;
	public TA(String name, String id, String type, List<String> classesTaken, List<String> labsSupervised, String advisor, String expectedDegree) {
		super(name, id, type, classesTaken);
		if (labsSupervised == null) {
		    this.labsSupervised = new ArrayList<String>();
		} else {
		    this.labsSupervised = labsSupervised;
		}
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
	public void removeLabsSupervised(String lab) {
		this.labsSupervised.remove(lab);
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
				+ getClassesTaken().toString() + ", getName()=" + getName() + ", getId()=" + getId() + "]";
	}
	
	
}

class Faculty extends Person{
	private String rank, officeLocation;
	List<String> lecturesTaught = new ArrayList<String>();
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
	public void removeLecturesTaught(String lecture) {
		this.lecturesTaught.remove(lecture);
	}
	@Override
	public String toString() {
		return "Faculty [ID =" + getId() + "name =" + getName() + "rank=" + rank + ", officeLocation=" + officeLocation + ", lecturesTaught="
				+ lecturesTaught.toString() + "]";
	}	
}