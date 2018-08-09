package hospital.human;

import java.util.*;

import hospital.demo.Demo;
import hospital.demo.DepartmentType;

public class TreatmentPlan {
	private static final DepartmentType[] DIAGNOSIS = {DepartmentType.CARDIOLOGY,DepartmentType.ORTHOLOGY,DepartmentType.VIRUSOLOGY};
	private static final String[] MEDICAMENTS = {"aspirin,analgin,neutrophilic,syrup,antibiotic,painkillers"};
	private static final int PERIOD = 5;
	private DepartmentType diagnosis;
	private Set<String> medicaments;
	private int period;
	
	TreatmentPlan(DepartmentType diagnosis,Set<String> medicaments,int period){
		this.diagnosis = diagnosis;
		this.medicaments = medicaments;
		this.period = period;
	}	
	
	public DepartmentType getDiagnosis() {
		return this.diagnosis;
	}

	public static TreatmentPlan createPlan() {
		Random random = new Random();
		
		DepartmentType diagnosis = DIAGNOSIS[random.nextInt(DIAGNOSIS.length)];
		String medicament = MEDICAMENTS[random.nextInt(MEDICAMENTS.length)];
		
		int count = Demo.random(1, 5);
		HashSet<String> currentMeds = new HashSet<>();
		
		for(int index = 0; index < count; index++) {
			currentMeds.add(medicament);
		}
		
//		int period = Demo.random(3, PERIOD + 1);
		int period = 1;
		
		return new TreatmentPlan(diagnosis, currentMeds, period);
	}
	
	public void decrementPeriod() {
		if (this.period > 0) {
			this.period--;
		}
	}
	
	public int getPeriod() {
		return this.period;
	}
}
