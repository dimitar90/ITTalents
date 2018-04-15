package pharmacy.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pharm.pharmacies.JustPharmacy;
import pharm.pharmacies.MainPharmacy;
import pharmacy.people.Clients;
import pharmacy.people.Provider;

 public class Demo {
	private static final int COUNT_PHARMACIES = 4;
	private static final int COUNT_PROVIDERS = 1;
	private static final int COUNT_CLIENTS = 5;

	public static int random(int min, int max) {
		return new Random().nextInt(max - min) + min;
	}

	public static void main(String[] args) {

		List<JustPharmacy> pharmacies = new ArrayList<>();

		try {
			MainPharmacy pharmacy = MainPharmacy.getPharmacy();
			
			for (int count = 0; count < COUNT_PHARMACIES; count++) {
				pharmacies.add(new JustPharmacy("Pharm " + (count + 1)));
			}

			JsonArchivator ja = new JsonArchivator(pharmacies);
			pharmacy.addPharmacy(pharmacies);
			for (int count = 0; count < COUNT_CLIENTS; count++) {
				new Thread(new Clients()).start();
				
			}
			
			for (int count = 0; count < COUNT_PROVIDERS; count++) {
				new Thread(new Provider()).start();
				
			}
			ja.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
