package domain;
import java.util.List;

import repository.CourseRepository;



public class Course extends BaseDomain {
	private String id;
	private String name;
	private int units;
	
	List<Course> prerequisites;

	public Course(String id, String name, int units) {
		this.id = id;
		this.name = name;
		this.units = units;
		prerequisites = null;
	}
	
	public void addPre(Course c) {
		getPrerequisites().add(c);
	}

	public List<Course> getPrerequisites() {
		if (prerequisites == null)
			prerequisites = CourseRepository.getInstance().findPrerequisitesForCourse(this);
		return prerequisites;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + " {");
		for (Course pre : getPrerequisites())
			sb.append(pre.getName() + ", ");
		sb.append("}");
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public boolean hasPassedPre(Student s) {
		for (Course pre : getPrerequisites())
			if (!s.hasPassed(pre))
				return false;
		return true;
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
