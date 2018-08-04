package hospital.human;

import java.util.Random;

public class Patient extends Human{
	private int age;
	private Boolean gender;
	private Doctor doc;
	private TreatmentPlan plan;
	
	Patient(String name, String phone,int age) {
		super(name, phone);
		this.gender = new Random().nextBoolean();
		this.age = age;
	}
	
	public TreatmentPlan getPlan() {
		return this.plan;
	}
	
	public void setDoctor(Doctor d) {
		if (d != null) {
			this.doc = d;
		}
	}
	
	public Boolean isMale() {
		return this.gender;
	}

	public int getAge() {
		return age;
	}

	public String getDoctorsName() {
		return this.doc.getName();
	}
}
