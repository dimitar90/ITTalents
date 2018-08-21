package peasant_brigade.vegetable;

public enum VegetableType {
	TOMATO(3), EGGPLANT(6), PEPPER(9);
	
	private int seconds;
	
	private VegetableType(int seconds){
		this.seconds = seconds;
	}
	
	public int getSeconds() {
		return this.seconds;
	}
}
