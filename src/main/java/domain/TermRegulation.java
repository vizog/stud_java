package domain;

public class TermRegulation extends BaseDomain {
<<<<<<< HEAD

=======
>>>>>>> Modifiability Change 2: Program (final)
    private String id;
    private int maxAllowedUnits;
    private boolean takeWithoutPassPresAllowed = false;
    private boolean reTakeCourseAllowed = false;
<<<<<<< HEAD

=======
>>>>>>> Modifiability Change 2: Program (final)
    public TermRegulation(int maxAllowedUnits, boolean allowReTakeCourse, boolean allowTakeWithoutPassPres) {
		super();
		this.maxAllowedUnits = maxAllowedUnits;
		this.reTakeCourseAllowed = allowReTakeCourse;
		this.takeWithoutPassPresAllowed = allowTakeWithoutPassPres;
	}
<<<<<<< HEAD

    public boolean isReTakeCourseAllowed() {
		return reTakeCourseAllowed;
	}

	public void setReTakeCourseAllowed(boolean reTakeCourseAllowed) {
		this.reTakeCourseAllowed = reTakeCourseAllowed;
	}

	public boolean isTakeWithoutPassPresAllowed() {
		return takeWithoutPassPresAllowed;
	}

	public void setTakeWithoutPassPresAllowed(boolean takeWithoutPassPresAllowed) {
		this.takeWithoutPassPresAllowed = takeWithoutPassPresAllowed;
	}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxAllowedUnits() {
        return maxAllowedUnits;
    }

    public void setMaxAllowedUnits(int maxAllowedUnits) {
        this.maxAllowedUnits = maxAllowedUnits;
    }

=======
    public boolean isReTakeCourseAllowed() {
		return reTakeCourseAllowed;
	}
	public void setReTakeCourseAllowed(boolean reTakeCourseAllowed) {
		this.reTakeCourseAllowed = reTakeCourseAllowed;
	}
	public boolean isTakeWithoutPassPresAllowed() {
		return takeWithoutPassPresAllowed;
	}
	public void setTakeWithoutPassPresAllowed(boolean takeWithoutPassPresAllowed) {
		this.takeWithoutPassPresAllowed = takeWithoutPassPresAllowed;
	}
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getMaxAllowedUnits() {
        return maxAllowedUnits;
    }
    public void setMaxAllowedUnits(int maxAllowedUnits) {
        this.maxAllowedUnits = maxAllowedUnits;
    }
>>>>>>> Modifiability Change 2: Program (final)
}
