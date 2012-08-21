package domain;



public class Course extends BaseDomain {
	private String id;
	private String name;
	private int units;
	
	public Course(String id, String name, int units) {
		this.id = id;
		this.name = name;
		this.units = units;
	}
	

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public boolean hasPassedPre(Student s) {
		return s.getProgram().hasPassedPreRequirements(s,this);
	}

	public int getUnits() {
		return units;
	}

	public String getId() {
		return id;
	}

	public boolean equals(Object obj) {
		Course other = (Course)obj;
		return id.equals(other.id);
	}
	
	
	
}
