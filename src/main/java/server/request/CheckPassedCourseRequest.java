package server.request;

import java.sql.SQLException;

import repository.CourseRepository;
import repository.StudentRepository;
import domain.Course;
import domain.Student;
import domain.exceptions.StudentNotFoundException;

public class CheckPassedCourseRequest extends AbstractRequest {

	private String studentId;

	public CheckPassedCourseRequest(String id, String studentId) {
		super(id);
		this.studentId = studentId;
	}

	@Override
	public void process() {
		Student bebe;
			try {
				bebe = StudentRepository.getInstance().findByName(studentId);
				Course crs = CourseRepository.getInstance().findById("course-5-2-1");
				bebe.hasPassed(crs);
			} catch (StudentNotFoundException e) {
				e.printStackTrace();
		}	 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
			
	}
}
