package domain;

import repository.OfferingRepository;

public class StudyRecord extends BaseDomain {
	private double grade;
	private Offering offering;
	private int id;
	
	public StudyRecord(Offering offering) {
		this.offering = offering;
		this.grade = -1; //set to -1; it means the record has no grade yet
	}

	public StudyRecord(Offering offering, double grade, int id) {
		this.grade = grade;
		this.offering = offering;
		this.id = id;
	}

	public double getGrade() {
		return grade;
	}

	public Offering getOffering() {
		if(offering == null)
			offering = OfferingRepository.getInstance().findByStudyRecord(this);
		return offering;
	}

	boolean isPassRec(Course c) {
		return (getOffering().getCourse().equals(c)) && (grade >= 10);
	}
	
	public boolean isCurrentTermRecNotCompleted(Course c) {
		return (getOffering().getCourse().equals(c)) && (grade == -1);
	}

	public int getUnits() {
		return getOffering().getUnits();
	}

	public double getWeightedScore() {
		return getOffering().getUnits() * grade;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
}
