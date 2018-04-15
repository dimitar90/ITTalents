package pharmacy.people;

import java.util.Random;

import pharm.pharmacies.JustPharmacy;
import pharm.pharmacies.MainPharmacy;
import pharm.pharmacies.PharmacyException;
public class Clients implements Runnable{
	private static final int SLEEPING_TIME_ORDER = 1000;
	private MainPharmacy pharmacy;
	private static Random random = new Random();
	
	public Clients() throws PharmacyException{
		this.pharmacy = MainPharmacy.getPharmacy();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(SLEEPING_TIME_ORDER);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			JustPharmacy ph = pharmacy.getRandomPharmacy();
			try {
				ph.sellMedicine(TypeArgument.values()[this.random.nextInt(TypeArgument.values().length)]);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}
	


	
}
