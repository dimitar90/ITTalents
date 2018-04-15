package pharmacy.people;

import java.util.HashSet;
import java.util.Set;

import pharm.pharmacies.MainPharmacy;
import pharm.pharmacies.PharmacyException;

public class Provider implements Runnable{
	private static final int SLEEPING_TIM_TO_DELIVER = 3000;
	private MainPharmacy pharmacy;
	private static final  int ORDER_MED_COUNT = 10;
	
	public Provider() throws PharmacyException{
		this.pharmacy = MainPharmacy.getPharmacy();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(SLEEPING_TIM_TO_DELIVER);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				this.pharmacy.deliveryOrder(ORDER_MED_COUNT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
