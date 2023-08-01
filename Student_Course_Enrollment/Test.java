package homework5;

import java.io.IOException;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) throws IOException {
		
		School school = new School();
		school.readCourses();

		System.out.println(school.getCourses().get(0).getName());
		school.readStudents();
		ArrayList<Course> course = school.getStudents().get(0).getCourses();
		System.out.println(course.get(0).getCode());
		school.calculateTotalCreditFor(666);
		System.out.println(school.getCoursesNoOneEnrolled().get(0).getCode());
		school.printStudentsEnrolledCourse("CS105");
		school.printCoursesEnrolledByStudent(666);
		school.printAllStudentsOrderByName();

		
	}

}
