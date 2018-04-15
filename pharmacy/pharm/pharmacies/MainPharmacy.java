package pharm.pharmacies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainPharmacy extends InftoPharm {
	private static MainPharmacy pharmacy;
	private List<JustPharmacy> allPharmacies = new ArrayList<JustPharmacy>();
	private Random random = new Random();
	private MainPharmacy() throws PharmacyException {
		super("MainPharmacy");
	}

	public static MainPharmacy getPharmacy() throws PharmacyException {
		if (pharmacy == null) {
			pharmacy = new MainPharmacy();
		}
		return pharmacy;
	}

	public void deliveryOrder(int countMeds) throws InterruptedException {
		for (JustPharmacy pharmacy : allPharmacies) {
				pharmacy.acceptOrder(countMeds);
		}
	}



	public void addPharmacy(List<JustPharmacy> pharmacies) {
		this.allPharmacies.addAll(pharmacies);
	}

	public JustPharmacy getRandomPharmacy() {
		return this.allPharmacies.get(random.nextInt(this.allPharmacies.size()));
	}
}
