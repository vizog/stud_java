package domain;

public class StudyRecord {
	private double grade;
	private Offering offering;
	
	public StudyRecord(Offering offering) {
		this.offering = offering;
		this.grade = 0;
	}

	public StudyRecord(Offering offering, double grade) {
		this.grade = grade;
		this.offering = offering;
	}

	public double getGrade() {
		return grade;
	}

	public Offering getOffering() {
		return offering;
	}

	boolean isPassRec(Course c) {
		return (offering.getCourse().equals(c)) && (grade >= 10);
	}

	public int getUnits() {
		return offering.getUnits();
	}

	public double getWeightedScore() {
		return offering.getUnits() * grade;
	}
	
	
}
