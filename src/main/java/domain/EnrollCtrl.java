package domain;

import java.util.List;

import repository.OfferingRepository;
import repository.StudentRepository;
import repository.TermRepository;
import domain.exceptions.EnrollmentRulesViolationException;
import domain.exceptions.StudentNotFoundException;
import domain.exceptions.UnknownOfferingsException;

public class EnrollCtrl {
	public void enroll(Student s, Offering offering)
			throws EnrollmentRulesViolationException {
		hasPassedPre(s, offering);
		hasNotPassed(s, offering);
		hasNotTakenThisTerm(s, offering);
		//#ADDED
		isNotMoreThan20Unitis(s, offering);
		//###
		s.takeOffering(offering);
	}


	public void hasPassedPre(Student s, Offering o) throws EnrollmentRulesViolationException {
		if (!o.hasPassedPre(s))
			throw new EnrollmentRulesViolationException("Has not passed pre for: " + o.getCourse());
	}

	public void hasNotPassed(Student s, Offering o) throws EnrollmentRulesViolationException {
		if (s.hasPassed(o.getCourse()))
			throw new EnrollmentRulesViolationException("Has already passed: " + o.getCourse());
	}

	public void hasNotTakenThisTerm(Student s, Offering o) throws EnrollmentRulesViolationException {
		 if(s.hasTakenThisTerm(o))
			 throw new EnrollmentRulesViolationException("Has already taken this course in this term: " + o.getCourse());
	}

	public List<Offering> getCurrentOfferings() {
		return TermRepository.getInstance().findCurrentTerm().getOfferings();
	}
	//#ADDED
	private void isNotMoreThan20Unitis(Student s, Offering offering) throws EnrollmentRulesViolationException {
		if(offering.getCourse().getUnits() + s.getCurrentTermUnits() > 20)
			throw new EnrollmentRulesViolationException("This enrollment makes current term units more than 20 (student already has " +s.getCurrentTermUnits() + " units");
	}
	//###	
	public void enroll(String studentName, String offeringId)
			throws UnknownOfferingsException, StudentNotFoundException,
			EnrollmentRulesViolationException {
		Student student = StudentRepository.getInstance().findByName(
				studentName);
		if (student == null)
			throw new StudentNotFoundException();

		Offering offering = OfferingRepository.getInstance().findById(
				offeringId);

		enroll(student, offering);

		StudentRepository.getInstance().save(student);
	}
}
