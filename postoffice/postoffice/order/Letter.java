package postoffice.order;

import postoffice.citizen.Citizen;

public class Letter extends Order{
	private static final double TAX = 0.5;
	private double tax = TAX;
	
	public Letter(Citizen sender, Citizen receiver) throws OrderException {
		super(sender, receiver);
	}
	
	@Override
	public boolean isFragile() {
		return false;
	}

	@Override
	public boolean isLetter() {
		return true;
	}
	
	@Override
	protected double getTax() {
		return tax;
	}

	public static Letter getLetter(Citizen sender,Citizen receiver) throws OrderException {
		return new Letter(sender, receiver);
	}

	@Override
	public long getDuration() {
		return 3000;
	}


}
