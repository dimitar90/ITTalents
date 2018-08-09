package hospital.human;

import java.util.Random;

import hospital.demo.DepartmentType;

public class Nurse extends Human {
	private int exp;
	private DepartmentType departmentType;
	private static int departmentCount = 0;
	public Nurse(String name, String phone, int exp) throws HumanException {
		super(name, phone);
		this.exp = exp;
		if (departmentCount >= 3) {
			departmentCount = 0;
		}
		
		this.departmentType = DepartmentType.values()[departmentCount++];
	}

	public void giveMeds(Patient patient) {
		patient.getPlan().decrementPeriod();
		
		System.out.println(String.format(
				"Nurse %s gave meds to patient %s room %d department %s",
				this.getName(), patient.getName(), patient.getRoom(), patient.getDepartment()));
	}

	public void setDepartment(DepartmentType department) {
		this.departmentType = department;
	}

	public DepartmentType getDepartmentType() {
		return this.departmentType;
	}
}
