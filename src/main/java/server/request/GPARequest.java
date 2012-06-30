package server.request;

import repository.StudentRepository;
import repository.TermRepository;
import domain.Student;
import domain.Term;
import domain.exceptions.StudentNotFoundException;

public class GPARequest extends AbstractRequest {

	public GPARequest(String id) {
		super(id);
	}
	
	@Override
	public void process() {
		Student bebe;
		try {
			bebe = StudentRepository.getInstance().findByName("bebe");
			Term term88_89_1 = TermRepository.getInstance().findByName("88-89-1");
			bebe.getTermGPA(term88_89_1);
		} catch (StudentNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
