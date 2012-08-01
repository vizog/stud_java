package domain;

import java.util.List;

import repository.StudentRepository;

public class Student extends BaseDomain {
	private String id;
	private String name;

	private List<StudyRecord> studyRecords;

	public Student(String id, String name) {
		this.id = id;
		this.name = name;
		this.studyRecords = null;
	}

	public void takeOffering(Offering c) {
		getStudyRecords().add(new StudyRecord(c));
	}

	public void takeOffering(Offering c, double grade) {
		getStudyRecords().add(new StudyRecord(c, grade));
	}

	public boolean hasTaken(Offering c) {
		for (StudyRecord e : getStudyRecords())
			if (e.getOffering() == c)
				return true;
		return false;
	}

	public boolean hasTakenThisTerm(Offering c) {
		for (StudyRecord rec : getStudyRecords())
			if (rec.isCurrentTermRec(c.getCourse()))
				return true;
		return false;
	}

	public boolean hasPassed(Course c) {
		for (StudyRecord rec : getStudyRecords()) {
			if (rec.isPassRec(c))
				return true;
		}
		return false;
	}

	public double getGPA() {
		int totalUnits = 0;
		double weightedScoreSum = 0;

		for (StudyRecord rec : getStudyRecords()) {
			if (rec.getGrade() != -1) {
				totalUnits += rec.getUnits();
				weightedScoreSum += rec.getWeightedScore();
			}
		}
		return weightedScoreSum / totalUnits;
	}

	public double getTermGPA(Term term) {
		int totalUnits = 0;
		double weightedScoreSum = 0;

		for (StudyRecord rec : getStudyRecords()) {
			if (rec.getOffering().getTerm().equals(term) && rec.getGrade() != -1) {
				totalUnits += rec.getUnits();
				weightedScoreSum += rec.getWeightedScore();
			}
		}
		return totalUnits == 0 ? 0: weightedScoreSum / totalUnits;
	}

	public List<StudyRecord> getStudyRecords() {
		if (studyRecords == null)
			studyRecords = StudentRepository.getInstance()
					.findStudyRecordsForStudent(this);
		return studyRecords;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
