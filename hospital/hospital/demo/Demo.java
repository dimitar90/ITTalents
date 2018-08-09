package hospital.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hospital.hospital.Hospital;
import hospital.human.Doctor;
import hospital.human.HumanException;
import hospital.human.Nurse;
import hospital.human.Patient;

public class Demo {

	public static int random(int min, int max) {
		return new Random().nextInt(max - min) + min;
	}

	public static void main(String[] args) throws HumanException {
		Hospital hospital = Hospital.getInstance();

		List<Doctor> doctors = new ArrayList<>();
		List<Patient> patients = new ArrayList<>();
		List<Nurse> nurses = new ArrayList<>();
		
		try {
			for (int count = 0; count < 5; count++) {
				patients.add(new Patient("Patient  " + (count + 1), "0877112233", (20 + count)));
			}

			for (int count = 0; count < 10; count++) {
				nurses.add(new Nurse("Nurse  " + (count + 1), "0877112233", (30 + count)));
			}

			for (int count = 0; count < 5; count++) {
				doctors.add(new Doctor("Doctor  " + (count + 1), "0877112233", "spec level " + (count + 1)));
			}

			for (Doctor doctor : doctors) {
				hospital.addDoctor(doctor);
			}

			for (Nurse nurse : nurses) {
				hospital.addNurse(nurse);
			}

			for (Patient patient : patients) {
				hospital.patientIntake(patient);
			}

			hospital.startWork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
