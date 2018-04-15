package pharmacy.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pharm.pharmacies.JustPharmacy;
import pharm.pharmacies.Medicament;


public class JsonArchivator extends Thread {

	private static final int SLEEPING_TIME_FOR_ACHIVATOR = 10_000;
	private List<JustPharmacy> pharmacies = new ArrayList<>();

	public JsonArchivator(List<JustPharmacy> pharmacies) {
		this.pharmacies = pharmacies;
		this.setDaemon(true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(SLEEPING_TIME_FOR_ACHIVATOR);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + " it was interrupted");
				return;
			}

			processMostSellingMed();
			System.out.println("F I R S T     F I L E");
			processMostIncomeMed();
			System.out.println("S E C O N D     F I L E");
		}
	}

	private void processMostIncomeMed() {
		for (JustPharmacy ph : this.pharmacies) {
			Medicament m = ph.getMostIncomMedForSerialization();
			serializeMostIncomeMed(m);
		}
	}

	private void serializeMostIncomeMed(Medicament m) {
		File file = new File("mostIncomeMedicament.json");

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String jsonStr = gson.toJson(m);

		try (PrintStream ps = new PrintStream(file)) {
			file.createNewFile();
			ps.println(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private void processMostSellingMed() {
		for (JustPharmacy ph : this.pharmacies) {
			Medicament m = ph.getMostSellingMedForSerialization();
			serizalizeMostSellingMed(m);
		}
	}

	private void serizalizeMostSellingMed(Medicament m) {
		File file = new File("mostSellingMedicament.json");

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String jsonStr = gson.toJson(m);

		try (PrintStream ps = new PrintStream(file)) {
			file.createNewFile();
			ps.println(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
