package hospital.human;

public class Doctor extends Human {
	private String spec;
	private boolean free = true;
	private Patient patient;

	public Doctor(String name, String phone, String spec) throws HumanException {
		super(name, phone);
		this.spec = spec;
	}

	public TreatmentPlan makePlan() {
		// makes plan
		return TreatmentPlan.createPlan();
	}

	public void visitation() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(String.format("Doctor %s visited patient %s room number %d department %s ", this.getName(),
				this.patient.getName(), this.patient.getRoom(), this.patient.getDepartment()));
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
		}
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void unsubscribedItsPatient() {
		this.patient = null;
	}
}
