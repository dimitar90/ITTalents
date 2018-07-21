package postoffice.citizen;

public class CitizenDto {
	private String orderInfo;
	private String senderName;
	private String receiverName;
	private String orderDate;
	
	public CitizenDto(String orderInfo,String senderName,String receiverName,String orderDate) {
		this.orderDate = orderDate;
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.orderInfo = orderInfo;
	}
	
	public String getOrderDate() {
		return orderDate;
	}
	
	public String getOrderInfo() {
		return orderInfo;
	}
	
	public String getSenderName() {
		return senderName;
	}
	
	public String getReceiverName() {
		return receiverName;
	}

	public static CitizenDto createDto(String orderInfo, String senderName, String receiverName, String orderDate) {
		return new CitizenDto(orderInfo, senderName, receiverName, orderDate);
	}
}
