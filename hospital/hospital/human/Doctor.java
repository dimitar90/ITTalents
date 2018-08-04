package hospital.human;

public class Doctor extends Human{
	private String spec;
	private boolean free;
	private Patient patient;
	
	Doctor(String name, String phone,String spec) {
		super(name, phone);
		this.spec = spec;
	}
	

	public TreatmentPlan makePlan() {
		//makes plan
		return TreatmentPlan.createPlan();
	}
	
	public void visitation() {
		//TODO visitation
	}
	public void setFree(boolean isFree) {
		this.free = isFree;
	}
	
	public boolean isFree() {
		return this.free;
	}
	
	public void setPatient(Patient p) {
		if (p != null) {
			this.patient = p;
			this.free = true;
		}
	}
}
