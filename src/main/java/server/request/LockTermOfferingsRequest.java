package server.request;

import repository.TermRepository;
import domain.Term;

public class LockTermOfferingsRequest extends AbstractRequest {

	private String termId;
	private Term term;

	public LockTermOfferingsRequest(String id, String termId) {
		super(id);
		this.termId = termId;
		term = TermRepository.getInstance().findByName(termId);
	}
	
	@Override
	public void process() {
//			long start = System.currentTimeMillis();
			term.lockAllOfferings();
	}

}
