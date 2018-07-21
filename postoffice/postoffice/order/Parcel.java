package postoffice.order;

import java.util.Random;

import postoffice.citizen.Citizen;

public class Parcel extends Order {
	private static final float NORMAL_TAX = 2f;
	private static final float AVRG_TAX = 3f;
	private static final float EXPANSION_TAX = 1.5f;
	private boolean fragile;
	private float tax;
	private float length;
	private float width;
	private float height;

	public Parcel(Citizen sender, Citizen receiver, float length, float width, float height) throws OrderException {
		super(sender, receiver);
		this.validateParameters(length,width,height);
		this.fragile = new Random().nextBoolean();
		this.length = length;
		this.width = width;
		this.height = height;
		this.tax = (float) calculateTax();
	}

	private void validateParameters(float length, float width, float height) throws OrderException {
		if (length < 0 || width < 0 || height < 0) {
			throw new OrderException(Order.ORDER_PARAMETERS_MSG_TWO);
		}
	}

	private double calculateTax() {
		if (this.height < 60 && this.width < 60 && this.length < 60) {
			this.tax = NORMAL_TAX;
		} else {
			this.tax = AVRG_TAX;
		}

		if (isFragile()) {
			this.tax = tax * EXPANSION_TAX;
		}
		return this.tax;
	}

	public static Order getParcel(Citizen sender, Citizen receiver, float length, float width, float height) throws OrderException {
		return new Parcel(sender, receiver, length, width, height);
	}

	@Override
	public boolean isLetter() {
		return false;
	}

	@Override
	protected double getTax() {
		return tax;
	}

	@Override
	public boolean isFragile() {
		return fragile;
	}

	@Override
	public long getDuration() {
		return 5000;
	}

	@Override
	public String toString() {
		return (fragile ? "Fragile" : "Ordinary") + " order";
	}
}
