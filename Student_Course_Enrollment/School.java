package homework5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class School {

	private String name;
	private ArrayList<Course> courses = new ArrayList<Course>();
	private ArrayList<Student> students = new ArrayList<Student>();
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Course> getCourses() {
		return courses;
	}
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	public ArrayList<Student> getStudents() {
		return students;
	}
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}
	
	public void readCourses () throws IOException {
		File inputFile = new File("C:\\Users\\ys_kc\\eclipse-workspace\\Homeworks\\src\\homework5\\COURSES.txt\\");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			String[] arrOfStr = line.split(";", 3);
			
			
		 courses.add(new Course(arrOfStr[0],arrOfStr[1],Integer.parseInt(arrOfStr[2]))) ;
			
		}
		reader.close();
	}
	
	public void readStudents () throws IOException {
		File inputFile = new File("C:\\Users\\ys_kc\\eclipse-workspace\\Homeworks\\src\\homework5\\STUDENTS.txt\\");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			String[] arrOfStr = line.split(";", 3);
			String[] courseCode = arrOfStr[2].split(";");
			ArrayList<Course> studentCourses = new ArrayList<Course>();
			for(int i = 0; i < courseCode.length; i++) {
				for(int j = 0; j < courses.size(); j++) {
					if(courses.get(j).getCode().equals(courseCode[i]) ) {
						studentCourses.add(courses.get(j));
					}
				}
			}
			students.add(new Student(Integer.parseInt(arrOfStr[0]), arrOfStr[1], studentCourses));
		 
			
		}
		reader.close();
	}
	public void calculateTotalCreditFor(int studentID) {
		int totalCredit = 0;
		String nameOfStudent = "";
		for(int i = 0; i < students.size(); i++) {
			if(students.get(i).getId() == studentID) {
				nameOfStudent = students.get(i).getName();
				for(int j = 0; j < students.get(i).getCourses().size(); j++) {
					totalCredit += students.get(i).getCourses().get(j).getCredit();
					
				}
			}
		}
		System.out.println(nameOfStudent +"'s total credits are =" + totalCredit);
	}
	public ArrayList<Course> getCoursesNoOneEnrolled() {
		ArrayList<Course> noOneEnrolled = new ArrayList<Course>();
		for(int i=0; i < courses.size(); i++) {
			boolean status = false;
			for(int j=0; j < students.size(); j++) {
				for(int k = 0; k < students.get(j).getCourses().size(); k++) {
					if(courses.get(i).getCode().equals(students.get(j).getCourses().get(k).getCode())) {
						status = true;
						break;
					}
				}
				if(status) {
					break;
				}
			}
			if(!status) {
				noOneEnrolled.add(courses.get(i));
			}
		}
		return noOneEnrolled;		
	}
	public void printStudentsEnrolledCourse(String courseCode) {
		Course courseChosen = null;
		for(int i =0; i < courses.size();i++) {
			if(courses.get(i).getCode().equals(courseCode)) {
				courseChosen = courses.get(i);
			}
		}
		for(int j=0; j < students.size(); j++) {
			for(int k = 0; k < students.get(j).getCourses().size(); k++) {
				if(courseChosen.getCode().equals(students.get(j).getCourses().get(k).getCode())) {
					System.out.println(students.get(j).getName());
				}
				
				}
		
	    }
	}
	public void printCoursesEnrolledByStudent(int studentID) {
		Student studentChosen = null;
		for(int i =0; i < students.size();i++) {
			if(students.get(i).getId() == studentID) {
				studentChosen = students.get(i);
			}
		}
		for(int i = 0; i < studentChosen.getCourses().size(); i++) {
			System.out.println("Code: "+studentChosen.getCourses().get(i).getCode()+ " || " + "Name: " + studentChosen.getCourses().get(i).getName()+ " || " + "Credit: " + studentChosen.getCourses().get(i).getCredit());
		}
	}
	public void printAllStudentsOrderByName() {
		ArrayList<String> studentsName = new ArrayList<String>();
		for(int i = 0; i < students.size(); i++) {
			studentsName.add(students.get(i).getName());
		}
		Collections.sort(studentsName);
	}
	
}
