package pharm.pharmacies;

import pharmacy.demo.Demo;

public class Medicament {
	private static final int MIN_PRICE_MEDICINE = 5;
	private static final int MAX_PRICE_MEDCINE = 50;
	private MedicamentType type;
	private float price;
	private int mostSellingCount = 0;
	private float totalPrice = 0.0f;
	
//	public Medicament(MedicamentType type){
//		this.type = type;
//		this.price = Demo.random(MIN_PRICE_MEDICINE, MAX_PRICE_MEDCINE);
//	}
	
	public Medicament() {
		this.price = Demo.random(MIN_PRICE_MEDICINE, MAX_PRICE_MEDCINE);
	}
	
	public Medicament setTypes(MedicamentType types) {
		this.type = types;
		return this;
	}
	
	public Medicament setCounts(int counts) {
		this.mostSellingCount = counts;
		return this;
	}
	
	public Medicament setTotalPice(float medSum) {
		this.totalPrice = medSum;
		return this;
	}
	
	public MedicamentType geMedType() {
		return this.type;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setMostSelligCount(int count) {
		this.mostSellingCount = count; 
	}
	
	public void setTotalPrice(float amount) {
		this.totalPrice = amount;
	}
}
