package pharm.pharmacies;

import java.util.concurrent.ConcurrentHashMap;

import pharmacy.people.TypeArgument;

public class JustPharmacy extends InftoPharm {
	private static final float INITIAL_MED_SUM = 0.0f;
	private static final Integer INITIAL_MEDICINE_COUNT = 5;
	private float money;
	private static ConcurrentHashMap<MedicamentType, Integer> medicines = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<MedicamentType, Integer> mosteSellingMed = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<MedicamentType, Float> mostIncomeMed = new ConcurrentHashMap<>();

	public JustPharmacy(String name) throws PharmacyException {
		super(name);
		generateMedicamentsMap();
		generateMostSellingMap();
		generateMostIncomeMap();

	}

	public Medicament sellMedicine(TypeArgument typeArg) {
		Medicament medicine = null;
		MedicamentType medType = this.getMedicineType(typeArg);

		synchronized (this) {

			while (!hasMed(medType)) {
				try {
					System.out.println(String.format("We don't have %s right now. Please wait", medType));
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		switch (typeArg) {
		case EYE:
		case LEG:
			medicine = new Medicament().setTypes(medType);
		case HEAD:
			medicine = new Medicament().setTypes(medType);
		case TEETH:
			medicine = new Medicament().setTypes(medType);
		default:
			medicine = new Medicament().setTypes(medType);
		}
		increaseMostIncomMed(medicine);
		increaseMostSellingMed(medType);
		decreaseCurrentMedicineCount(medType);

		synchronized (this) {
			if (medicine != null) {
				float price = medicine.getPrice();
				System.out.println(
						String.format("Client buy med -> %s for %.2fBGN. From %s", medType, price, this.getName()));
				this.increaseMoney(price);
			}
		}
		synchronized (this) {
			this.notifyAll();
		}

		return medicine;
	}

	private void generateMostIncomeMap() {
		for (MedicamentType med : MedicamentType.values()) {
			this.mostIncomeMed.put(med, INITIAL_MED_SUM);
		}
	}

	private void generateMostSellingMap() {
		for (MedicamentType med : MedicamentType.values()) {
			this.mosteSellingMed.put(med, INITIAL_MEDICINE_COUNT);
		}
	}

	private void generateMedicamentsMap() {
		for (MedicamentType med : MedicamentType.values()) {
			this.medicines.put(med, INITIAL_MEDICINE_COUNT);
		}
	}

	private synchronized void increaseMostIncomMed(Medicament medicine) {
		float amount = medicine.getPrice();
		MedicamentType type = medicine.geMedType();
		this.mostIncomeMed.put(type, this.mostIncomeMed.get(type) + amount);
	}

	private synchronized void increaseMostSellingMed(MedicamentType medType) {
		int count = this.mosteSellingMed.get(medType);
		this.mosteSellingMed.put(medType, this.mosteSellingMed.get(medType) + count);
	}

	private boolean hasMed(MedicamentType medType) {
		return this.medicines.get(medType) > 0;
	}

	synchronized void decreaseCurrentMedicineCount(MedicamentType medType) {
		this.medicines.put(medType, this.medicines.get(medType) - 1);

	}

	private void increaseMoney(float price) {
		if (price > 0) {
			this.money += price;
		}
	}

	public void acceptOrder(int count) {
		for (MedicamentType m : MedicamentType.values()) {
			this.medicines.put(m, count);
		}
		synchronized (this) {
			this.notifyAll();
		}
		System.out.println("Order was delivered");
	}

	private synchronized MedicamentType getMedicineType(TypeArgument type) {
		switch (type) {
		case EYE:
		case LEG:
			return MedicamentType.PARACETAMOL;
		case HEAD:
			return MedicamentType.VINAMIN_C;
		case TEETH:
			return MedicamentType.ESPUMIZAN;
		default:
			return MedicamentType.ASPIRIN;
		}
	}
	
	public Medicament getMostIncomMedForSerialization() {
		MedicamentType medType = getMostIncomType();
		float medSum = getMostIncomeSum();
		return new Medicament()
				.setTypes(medType)
				.setTotalPice(medSum);
	}
	
	

	private float getMostIncomeSum() {
		return this.mostIncomeMed
				.values()
				.stream()
				.sorted((v1,v2) -> Float.compare(v2, v1))
				.findFirst()
				.get()
				.floatValue();
	}

	private MedicamentType getMostIncomType() {
		return this.mostIncomeMed
				.entrySet()
				.stream()
				.sorted((m1,m2) -> Float.compare(m2.getValue(), m1.getValue()))
				.map(m -> m.getKey())
				.findFirst()
				.get();
	}

	public Medicament getMostSellingMedForSerialization() {
		MedicamentType medType = getMostSellingType();
		int medCount = getMostSellingCount();
		return new Medicament()
				.setTypes(medType)
				.setCounts(medCount);
	}

	

	private int getMostSellingCount() {
		return this.mosteSellingMed
				.values()
				.stream()
				.sorted((v1,v2) -> Integer.compare(v2, v1))
				.findFirst()
				.get()
				.intValue();
	}

	private MedicamentType getMostSellingType() {
		return this.mosteSellingMed
		.entrySet()
		.stream()
		.sorted((m1,m2) -> Integer.compare(m2.getValue(), m1.getValue()))
		.map(m -> m.getKey())
		.findFirst()
		.get();
	}
}
