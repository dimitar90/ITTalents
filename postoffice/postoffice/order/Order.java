package postoffice.order;

import postoffice.citizen.Citizen;

public abstract class Order {
	private static final String ORDER_PARAMETERS_MSG_ONE = "Citizens can't be null";
	public static final String ORDER_PARAMETERS_MSG_TWO = "Invalid credentials";
	private Citizen sender;
	private Citizen receiver;
	
	public Order(Citizen sender, Citizen receiver) throws OrderException {
		this.validateParameters(sender,receiver);
		this.sender = sender;
		this.receiver = receiver;
	}
	
	private void validateParameters(Citizen sender, Citizen receiver) throws OrderException {
		if (sender == null || receiver == null) {
			throw new OrderException(ORDER_PARAMETERS_MSG_ONE);
		}
	}

	public abstract boolean isFragile();
	
	public abstract boolean isLetter();
	
	public abstract long getDuration();
	
	protected abstract double getTax();

	public String getSenderName() {
		return this.receiver.getCitizenName();
	}

	public String getReceiverName() {
		return this.receiver.getCitizenName();
	}
}
