package homework5;

import java.util.ArrayList;

public class Student {

	private int id;
	private String name;
	private ArrayList<Course> courses = new ArrayList<Course>();
	
	public Student(int id, String name, ArrayList<Course> courses) {
		super();
		this.id = id;
		this.name = name;
		this.courses = courses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
	
	
}
