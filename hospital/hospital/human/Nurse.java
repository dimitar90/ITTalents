package hospital.human;

public class Nurse extends Human{
	private int exp;
	
	Nurse(String name, String phone,int exp) {
		super(name, phone);
		this.exp = exp;
	}

	public void giveMeds() {
		//TODO give meds
	}
}
