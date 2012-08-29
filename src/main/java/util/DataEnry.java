package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domain.Course;
import domain.Offering;
import domain.Student;
import domain.StudyRecord;
import domain.Term;

public class DataEnry {

	private static final int numTerms = 4;
	private static final int numCourses = 50;
	static int numStudent = 10000;
	static List<Course> courses = new ArrayList<Course>(numCourses);
	static Student[] students = new Student[numStudent];
	static Term[] terms = new Term[numTerms];
	static List<Offering> offerings = new ArrayList<Offering>(numTerms
			* numCourses);

	public static void main(String[] args) {
		temp();
//		student();
//		course();
//		term();
//		offering();
//		studyRecord();
//		print();
	}


	private static void temp() {
		for(int i = 1000; i< 2000; i++)
			System.out.println("INSERT INTO offering (id, course_id, section, exam_date, term_name, locked) VALUES('course-1-"+i+"', 'course-1', 1, NULL, 'term-1', NULL);");
	}


	private static void term() {
		for (int i = 0; i < terms.length; i++) {
			terms[i] = new Term("term-" + (i + 1));
		}
	}

	private static void student() {
		String name = "";
		for (int i = 0; i < students.length; i++) {
			name = "student-" +(i + 1);
			students[i] = new Student(name, name);
			// System.out.println("insert into student(id,name) values('"+name+"', '"+name+"')");
		}

	}

	private static void course() {
		String name, name2, name3 = "";
		for (int i = 1; i <= 10; i++) {
			name = "course-" + i;
			courses.add(new Course(name, name, 3));
			// System.out.println("insert into course values('"+name+"', '"+name+"', 3);");
			for (int j = 1; j <= 2; j++) {
				name2 = name + "-" + j;
				courses.add(new Course(name2, name2, 2));
				for (int k = 1; k <= 1; k++) {
					name3 = name2 + "-" + k;
					courses.add(new Course(name3, name3, 2));
				}
			}
		}
	}

	private static void offering() {
		for (int i = 0; i < terms.length; i++) {
			for (Course course : courses) {
				Offering o = new Offering(course.getName() + "-" + (i + 1),
						course, terms[i], null, 1);
				offerings.add(o);
				terms[i].addOffering2(o);
			}
		}
	}

	private static void studyRecord() {

		for (Student stud : students) {
			Set<String> passed = new HashSet<String>();
			for (int i = 0; i < terms.length; i++) {
				int currentTermCourses = 0;
				Term term = terms[i];
				for (Offering off : term.getOfferings()) {
					String courseId = off.getCourse().getId();
					if (i == terms.length - 1) {
						// courses 31 to 33 have been taken (not passed just
						// taken)
//						if (passed.add(courseId) && courseId.matches("")) // take only if hasn't
//														// passed before
//							stud.addStudyRec(new StudyRecord(off, -1));
					} else {// not current term

						// courses 30 to 50 and 30-x to 35-x should not be taken
						if (courseId.matches("course-[1-8]\\D.*") ) {
							if ( (! passed.contains(courseId)) && currentTermCourses < 10) {
								stud.addStudyRec(new StudyRecord(off, 16, 0));
								passed.add(courseId);
								currentTermCourses++;
							}
						}
					}
				}
			}
		}
	}

	
	
	private static void print() {
		System.out.println("use edu_perf;");
		System.out.println("insert into program values('prog_1');");
		
		//term_regulation:
		System.out.println("insert into term_regulation values('default', 20, 0, 0);");

		
		//term:
		System.out.println("insert into term values('term-1', '2009-09-23','default');");
		System.out.println("insert into term values('term-2', '2010-02-23','default');");
		System.out.println("insert into term values('term-3', '2010-09-23','default');");
		System.out.println("insert into term values('term-4', '2011-02-23','default');");
		
		
		
		for(Student st: students)
			System.out.println("insert into student values('"+st.getId()+"', '"+st.getName()+"','prog_1');");
		
		for(Course c: courses)
			 System.out.println("insert into course values('"+c.getId()+"', '"+c.getName()+"', "+c.getUnits()+");");
		
		for (int i = 0; i < courses.size(); i++) {
			Course c = courses.get(i);
			System.out.println("insert into requirement values("+(i+1)+", '"+c.getId()+"','prog_1');");
		}
		
		printOfferings();

		for(Student st: students) {
			for (StudyRecord sr : st.getStudyRecords()) {
				System.out.println("insert into study_record(student_id,offering_id,grade) values('"+st.getId()+"', '"+sr.getOffering().getId()+"',"+sr.getGrade()+");");
			}
		}
		
		printPrerequisites();
	}


	private static void printOfferings() {
		for(Term term: terms)
			for(Offering off: term.getOfferings())
				System.out.println("insert into offering(id,course_id,term_name) values('"+off.getId()+"','"+off.getCourse().getId()+"','"+term.getName()+"');");
	}


	private static void printPrerequisites() {
		for(int i = 0; i < courses.size(); i += 5) {
			int base = i + 1;
			for(int j = 1; j <= 4;  j++) {
				System.out.println("insert into prerequisites values(" + base +"," + (j+base)+");");
			}
			System.out.println("insert into prerequisites values(" + (base + 1) +"," + (base + 2)+");");
			System.out.println("insert into prerequisites values(" + (base + 3) +"," + (base + 4)+");");
		}
	}

}
