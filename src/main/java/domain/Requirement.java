package domain;
import java.util.ArrayList;
import java.util.List;
import repository.ProgramRepository;

public class Requirement extends BaseDomain {
	private int id;
	private Course course;
	List<Requirement> prerequisites = new ArrayList<Requirement>();

	public Requirement(int id, List<Requirement> prerequisites, Course course) {
		this.id = id;
		this.prerequisites = prerequisites;
		this.course = course;
	}
	
	public int getId() {
		return id;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public List<Requirement> getPrerequisites() {
		if(prerequisites == null) {
			prerequisites = ProgramRepository.getInstance().findPrerequisitesForRequirement(this);
		}
		return prerequisites;
	}
}
