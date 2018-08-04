package hospital.hospital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import hospital.demo.DepartmentType;
import hospital.human.Doctor;
import hospital.human.Nurse;
import hospital.human.Patient;
import hospital.human.TreatmentPlan;

public class Hospital {
	private static Hospital instance;
	private static final int DAYS = 10;
	private static final int ROOMS = 3;

	private int countDays = DAYS;
	private int countRooms = ROOMS;

	private Map<Integer, List<Patient>> cardiologyDepartment;
	private Map<Integer, List<Patient>> orthologyDepartment;
	private Map<Integer, List<Patient>> virosologyDepartment;

	private Set<Cardboard> cardboardsAllPatients;
	private Set<Doctor> doctors;
	private Set<Nurse> nurses;

	private Hospital() {
		this.cardiologyDepartment = new HashMap<>();
		this.orthologyDepartment = new HashMap<>();
		this.virosologyDepartment = new HashMap<>();
		
		for (int count = 1; count <= DAYS; count++) {
			this.cardiologyDepartment.put(count, new ArrayList<>());
			this.orthologyDepartment.put(count, new ArrayList<>());
			this.virosologyDepartment.put(count, new ArrayList<>());
		}
	}

	public static Hospital getInstance() {
		if (instance == null) {
			instance = new Hospital();
		}

		return instance;
	}

	public void patientIntake(Patient patient) {
		// Create cardboard each patient
		this.creatCardboardCurrentPatient(patient);

		// Current doctor prepares a plan for the patient
		this.preparePlan(patient);

		// accommodate the patient based on his diagnosis and gender
		this.accommodationThePatient(patient);
	}

	private void accommodationThePatient(Patient patient) {
		DepartmentType diagnosis = patient.getPlan().getDiagnosis();
		// add patient to department if there is a free room
		switch (diagnosis) {
		case CARDIOLOGY:
			this.addPatientToCardiology(patient,this.cardiologyDepartment);
			break;
		case ORTHOLOGY:
			this.addPatientToCardiology(patient,this.orthologyDepartment);
			break;
		case VIRUSOLOGY:
			this.addPatientToCardiology(patient,this.virosologyDepartment);
			break;
		}
	}

	private void addPatientToCardiology(Patient newPatient,Map<Integer,List<Patient>> department) {
		for (Entry<Integer, List<Patient>> rooms : department.entrySet()) {
			boolean sameGender = false;
			if(rooms.getValue().size() >= 3) {
				continue;
			}
			
			if (rooms.getValue().isEmpty()) {
				rooms.getValue().add(newPatient);
			}
			
			for (Patient patient : rooms.getValue()) {
				if (patient.isMale().equals(newPatient.isMale())) {
					sameGender = true;
					break;
				}
			}
			
			if (sameGender) {
				rooms.getValue().add(newPatient);
				break;
			}
			
		}
	}

	private void preparePlan(Patient patient) {
		Doctor doctor = this.getDoctorByName(patient.getDoctorsName());

		TreatmentPlan plan = doctor.makePlan();
	}

	private Doctor getDoctorByName(String doctorsName) {
		Doctor doc = null;

		for (Doctor doctor : this.doctors) {
			if (doctor.getName().equals(doctorsName)) {
				doc = doctor;
				break;
			}
		}
		return doc;
	}

	private void creatCardboardCurrentPatient(Patient patient) {
		Cardboard cardboard = Cardboard.createCardboard(patient.getAge(), patient.getName(), patient.getPhone());

		while (!isThereAFreeDoctor()) {
			continue;
		}

		Doctor freeDoctor = this.getFreeDoctor();

		cardboard.setDoctorName(freeDoctor.getName());

		this.cardboardsAllPatients.add(cardboard);
	}

	private Doctor getFreeDoctor() {
		Doctor doc = null;

		for (Doctor doctor : this.doctors) {
			if (doctor.isFree()) {
				doc = doctor;
				break;
			}
		}
		return doc;
	}

	private boolean isThereAFreeDoctor() {
		for (Doctor doctor : this.doctors) {
			if (doctor.isFree()) {
				return true;
			}
		}

		return false;
	}

	public void addDoctor(Doctor doctor) {
		if (doctor != null) {
			this.doctors.add(doctor);
		}
	}

	public void addNurse(Nurse nurse) {
		if (nurse != null) {
			this.nurses.add(nurse);
		}
	}

	public void addCardboard(Cardboard card) {
		if (card != null) {
			this.cardboardsAllPatients.add(card);
		}
	}
}
