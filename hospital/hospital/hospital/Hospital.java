package hospital.hospital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
	private static final int ROOMS = 10;
	private static final int BEDS = 3;
	private static final int DAYS = 10;
	private int days = DAYS;
	private int countDays = 0;

	private Map<Integer, List<Patient>> cardiologyDepartment;
	private Map<Integer, List<Patient>> orthologyDepartment;
	private Map<Integer, List<Patient>> virosologyDepartment;

	private Set<Cardboard> cardboardsAllPatients = new HashSet<>();
	private Set<Doctor> doctors = new HashSet<>();
	private Set<Nurse> nurses = new HashSet<>();

	private Hospital() {
		this.cardiologyDepartment = new HashMap<>();
		this.orthologyDepartment = new HashMap<>();
		this.virosologyDepartment = new HashMap<>();

		for (int count = 1; count <= ROOMS; count++) {
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

	public void startWork() {
		while (this.days >= 0) {
			try {
				Thread.sleep(5000);

				for (Nurse nurse : this.nurses) {
					Map<Integer, List<Patient>> department = this.getDepartmentForTheNurse(nurse.getDepartmentType());
					
					this.walkThroughtDepartment(department, nurse);
				}

				for (Doctor doctor : this.doctors) {
					if (doctor.getPatient() != null) {
						doctor.visitation();
					}
				}

				Boolean isEndWork = checkDepartmentsForPatients();
				
				if (isEndWork) {
					System.out.println("FINISH THE WORK PROCESS");
					return;
				}
				this.days--;
				System.out.println("<<<<<<<<<<<<<<<< " + (++countDays )+ " passed >>>>>>>>>>>>>>>>");
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
		System.out.println("FINISH THE WORK PROCESS");
	}

	private boolean checkDepartmentsForPatients() {
		List<Boolean> flags = new LinkedList<Boolean>();
		
		boolean cardiologyFlag = this.checkCurrentDepartment(this.cardiologyDepartment);
		flags.add(cardiologyFlag);
		
		boolean orthologyFlag = this.checkCurrentDepartment(this.orthologyDepartment);
		flags.add(orthologyFlag);
		
		boolean virosologyFlag = this.checkCurrentDepartment(this.virosologyDepartment);
		flags.add(virosologyFlag);
		
		flags.removeIf(f -> f.equals(false));
		
		return flags.size() == 3;
	}

	

	private boolean checkCurrentDepartment(Map<Integer, List<Patient>> department) {
		for (List<Patient> room : department.values()) {
			if (room.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public void patientIntake(Patient patient) {
		// Create cardboard each patient
		this.creatCardboardCurrentPatient(patient);
		// Current doctor prepares a plan for the patient
		this.preparePlan(patient);
		// accommodate the patient based on his diagnosis and gender
		this.accommodationThePatient(patient);

		String gender = this.takeGender(patient.isMale());

		System.out.println(String.format(
				"Patient %s with gender %s it was accepted with diagnousis %s. Patient's doctor: doc. %s. ",
				patient.getName(), gender, patient.getPlan().getDiagnosis(), patient.getDoctorsName()));
	}

	private void walkThroughtDepartment(Map<Integer, List<Patient>> department, Nurse nurse) {
		Patient unsubscribedPatient = null;
		
		int roomNumber = 0;
		
		for (Entry<Integer, List<Patient>> rooms : department.entrySet()) {
			List<Patient> currentRoom = rooms.getValue();

			for (Patient patient : currentRoom) {
				if (patient.getPlan().getPeriod() <= 0) {
					unsubscribedPatient = patient;
					roomNumber = rooms.getKey();
					break;
				}
				
				nurse.giveMeds(patient);
				
				if (patient.getPlan().getPeriod() <= 0) {
					unsubscribedPatient = patient;
					roomNumber = rooms.getKey();
					break;
				}
			}

			if (unsubscribedPatient != null) {
				break;
			}
		}

		if (unsubscribedPatient != null) {
			int patientIndex = department.get(roomNumber).indexOf(unsubscribedPatient);
			
			department.get(roomNumber).remove(patientIndex);
			
			Doctor doctor = unsubscribedPatient.getDoctor();

			doctor.unsubscribedItsPatient();

			System.out.println(String.format("Patient %s gender %s with diagnousis %s it was unsubscribed.",
					unsubscribedPatient.getName(), this.takeGender(unsubscribedPatient.isMale()),
					unsubscribedPatient.getPlan().getDiagnosis()));
		}

	}

	private Map<Integer, List<Patient>> getDepartmentForTheNurse(DepartmentType departmentType) {
		switch (departmentType) {
		case CARDIOLOGY:
			return this.cardiologyDepartment;
		case ORTHOLOGY:
			return this.orthologyDepartment;
		default:
			return this.virosologyDepartment;
		}
	}

	private String takeGender(Boolean isMale) {
		if (isMale) {
			return "male";
		}

		return "female";
	}

	private void accommodationThePatient(Patient patient) {
		DepartmentType diagnosis = patient.getPlan().getDiagnosis();
		// add patient to department if there is a free room
		switch (diagnosis) {
		case CARDIOLOGY:
			this.addPatientToDepartment(patient, this.cardiologyDepartment, DepartmentType.CARDIOLOGY);
			break;
		case ORTHOLOGY:
			this.addPatientToDepartment(patient, this.orthologyDepartment, DepartmentType.ORTHOLOGY);
			break;
		case VIRUSOLOGY:
			this.addPatientToDepartment(patient, this.virosologyDepartment, DepartmentType.VIRUSOLOGY);
			break;
		}
	}

	private void addPatientToDepartment(Patient newPatient, Map<Integer, List<Patient>> department,
			DepartmentType depType) {
		newPatient.setDepartment(depType);

		int roomNumber = 0;
		for (Entry<Integer, List<Patient>> rooms : department.entrySet()) {
			boolean sameGender = false;

			List<Patient> currentRoom = rooms.getValue();
			if (currentRoom.size() >= 3) {
				continue;
			}

			if (currentRoom.isEmpty()) {
				currentRoom.add(newPatient);
				roomNumber = rooms.getKey();
				break;
			}

			for (Patient patient : currentRoom) {
				if (patient.isMale().equals(newPatient.isMale())) {
					sameGender = true;
					break;
				}
			}

			if (sameGender) {
				currentRoom.add(newPatient);
				roomNumber = rooms.getKey();
				break;
			}
		}

		newPatient.setRoomNumber(roomNumber);
	}

	private void preparePlan(Patient patient) {
		Doctor doctor = this.getDoctorByName(patient.getDoctorsName());

		TreatmentPlan plan = doctor.makePlan();

		patient.setTreatmentPlan(plan);
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
			System.out.println("No free doctor. Please wait!");
			continue;
		}
		System.out.println("There is a free doctor");
		Doctor freeDoctor = this.getFreeDoctor();

		cardboard.setDoctorName(freeDoctor.getName());
		patient.setDoctor(freeDoctor);
		freeDoctor.setFree(false);
		freeDoctor.setPatient(patient);
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
