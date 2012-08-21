package domain;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import repository.OfferingRepository;


public class Term extends BaseDomain{
	private String name;
	private Date startDate;
	private List<Offering> offerings;
	private TermRegulation termRegulation;

	public Term(String name) {
		this.name = name;
		this.startDate = null;
		offerings = null;
	}

	public Term(String name, Date startDate) {
		this.name = name;
		this.startDate = startDate;
		offerings = null;
	}
	public Term(String name, Date startDate, TermRegulation termRegulation) {
<<<<<<< HEAD
		this.name = name;
		this.startDate = startDate;
		this.termRegulation = termRegulation;
		offerings = null;
		
=======
		this(name, startDate);
		this.termRegulation = termRegulation;
>>>>>>> Modifiability Change 2: Program (final)
	}
	
	public void addOffering(Offering c) {
		getOfferings().add(c);
	}

	public void addAllOfferings(Collection<Offering> c) {
		getOfferings().addAll(c);
	}

	public List<Offering> getOfferings() {
		if (offerings == null)
			offerings = OfferingRepository.getInstance().findOfferingsForTerm(this);
		return offerings;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}

	public Date getStartDate() {
		return startDate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Term && ((Term) obj).getName().equalsIgnoreCase(this.getName()))
			return true;
		return super.equals(obj);
	}

	 public TermRegulation getTermRegulation(){
	  return termRegulation;
	 }
	
	 public void setTermRegulation(TermRegulation termRegulation){
	  this.termRegulation = termRegulation;
	 }
	
}
