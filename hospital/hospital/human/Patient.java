package hospital.human;

import java.util.Random;

import hospital.demo.DepartmentType;

public class Patient extends Human {
	private int age;
	private Boolean gender;
	private Doctor doc;
	private TreatmentPlan plan;
	private int roomNumber;
	private DepartmentType department;
	
	public Patient(String name, String phone, int age) throws HumanException {
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

	public void setTreatmentPlan(TreatmentPlan plan) {
		this.plan = plan;
	}

	public void setRoomNumber(int number) {
		this.roomNumber = number;
	}

	public int getRoom() {
		return this.roomNumber;
	}

	public void setDepartment(DepartmentType depType) {
		this.department = depType;
	}
	
	public DepartmentType getDepartment() {
		return this.department;
	}

	public Doctor getDoctor() {
		return this.doc;
	}
}
