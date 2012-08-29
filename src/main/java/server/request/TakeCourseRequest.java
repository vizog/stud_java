package server.request;

import repository.OfferingRepository;
import repository.StudentRepository;
import repository.TermRepository;
import domain.EnrollCtrl;
import domain.Offering;
import domain.Student;
import domain.Term;
import domain.exceptions.EnrollmentRulesViolationException;
import domain.exceptions.StudentNotFoundException;

public class TakeCourseRequest extends AbstractRequest {

	private String studentId;

	public TakeCourseRequest(String id, String studentId) {
		super(id);
		this.studentId = studentId;
	}
	
	@Override
	public void process() {
		Student bebe;
		try {
			bebe = StudentRepository.getInstance().findByName(studentId);
			Offering offering = OfferingRepository.getInstance().findById("course-5-3");
			EnrollCtrl ctrl = new EnrollCtrl();
			ctrl.enroll(bebe, offering);
		} catch (StudentNotFoundException e) {
			throw new RuntimeException(e);
		} catch (EnrollmentRulesViolationException e) {
		}
	}

}
