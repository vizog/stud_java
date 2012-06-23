package domain;

import java.util.ArrayList;
import java.util.List;

import repository.OfferingRepository;
import repository.StudentRepository;
import repository.TermRepository;

import domain.exceptions.EnrollmentRulesViolationException;
import domain.exceptions.StudentNotFoundException;
import domain.exceptions.UnknownOfferingsException;


public class EnrollCtrl {
	public void enroll(Student s, List<Offering> ol) throws EnrollmentRulesViolationException {
		if (!hasPassedPre(s, ol) || !hasNotPassed(s, ol) || !noDuplicates(ol) )
			throw new EnrollmentRulesViolationException("Enrollment Rules Violated");

		for (Offering o : ol)
			s.takeOffering(o);
	}

	public boolean hasPassedPre(Student s, List<Offering> ol) {
		for (Offering o : ol) {
			if (!o.hasPassedPre(s))
				return false;
		}
		return true;
	}

	public boolean hasNotPassed(Student s, List<Offering> ol) {
		for (Offering o : ol) {
			if (s.hasPassed(o.getCourse()))
				return false;
		}
		return true;
	}


	public boolean noDuplicates(List<Offering> ol) {
		for (int i = 0; i < ol.size() - 1; i++)
			for (int j = i + 1; j < ol.size(); j++)
				if (ol.get(i).hasSameCourse(ol.get(j)))
					return false;
		return true;
	}


	public List<Offering> getCurrentOfferings() {
		return TermRepository.getInstance().findCurrentTerm().getOfferings();
	}

	public void enroll(String studentName, ArrayList<String> selectedOfferings) throws UnknownOfferingsException, StudentNotFoundException, EnrollmentRulesViolationException {
		Student student = StudentRepository.getInstance().findByName(studentName);
		if (student == null)
			throw new StudentNotFoundException();

		ArrayList<Offering> offerings = new ArrayList<Offering>();
		ArrayList<String> missingIds = new ArrayList<String>();
		for (String offeringId : selectedOfferings) {
			Offering offering = OfferingRepository.getInstance().findById(offeringId);
			if (offering == null)
				missingIds.add(offeringId);
			else
				offerings.add(offering);
		}
		if (missingIds.size() > 0) {
			System.out.println(missingIds);
			throw new UnknownOfferingsException(missingIds);
		}

		enroll(student, offerings);
		
		StudentRepository.getInstance().save(student);
	}
}
