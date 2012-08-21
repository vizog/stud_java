package domain;

import java.util.List;

import repository.ProgramRepository;

public class Program extends BaseDomain {
	private String id;
	private List<Requirement> requirements;

	public Program(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}
	
	public List<Requirement> getRequirements() {
		if(requirements == null)
			requirements = ProgramRepository.getInstance().findRequirements(id);
		return requirements;
	}

	public boolean hasPassedPreRequirements(Student s, Course course) {
		Requirement req = ProgramRepository.getInstance().findRequirementForCourse(id, course);
		for (Requirement pre : req.getPrerequisites()) {
			if(! s.hasPassed(pre))
				return false;
		}
		return true;
	}
}
