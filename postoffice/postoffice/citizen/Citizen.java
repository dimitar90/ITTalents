package postoffice.citizen;

import java.util.Random;

import postoffice.order.Letter;
import postoffice.order.Order;
import postoffice.order.OrderException;
import postoffice.order.Parcel;
import postoffice.post.Demo;
import postoffice.post.PostOffice;

public class Citizen extends Thread {
	private static final long CITIZEN_SLEEP_TIME = 2000;
	private static final int MIN_AGE_FOR_CITIZEN = 18;
	private static final int MAX_AGE_FOR_CITIZEN = 65;
	private static final int MIN_SIZE_ORDER = 20;
	private static final int MAX_SIZE_ORDER = 100;

	private static PostOffice postOffice = PostOffice.getInstance();
	
	private String name;
	private int age;
	private String address;

	public Citizen(String name, int age, String address) {
		this.name = name;
		this.age = age;
		this.address = address;
	}

	private void postLetterInBox(Citizen receiver) {
		// choose random number of box.
		int boxNumber = new Random().nextInt(PostOffice.MAX_BOXES);
		
		try {//Create letter with sender and receiver.
			Letter letter = createLetter(this, receiver);
			
			// Put letter into the box.
			postOffice.putLetterInBox(letter, boxNumber);
		} catch (OrderException e) {
			e.printStackTrace();
		}
		
		
	}

	private Letter createLetter(Citizen sender, Citizen receiver) throws OrderException {
		return Letter.getLetter(sender, receiver);
	}

	private void postOrderInOffice(Citizen receiver, float length, float width, float height) {
		
		
		try {// choose random Order (letter or parcel)
			Order order = createOrder(this, receiver, length, width, height);
			
			// put the order into the office depends on its kind
			if (order.isLetter()) {
				int boxNumber = new Random().nextInt(PostOffice.MAX_BOXES);

				postOffice.putLetterInBox(order, boxNumber);
			} else {
				postOffice.putOrderInPostOffice(order);
			}
		} catch (OrderException e) {
			e.printStackTrace();
		}
		
		
	}

	private Order createOrder(Citizen sender, Citizen receiver, float length, float width, float height) throws OrderException {
		Order order = null;
		
		if (new Random().nextBoolean()) {
			order = Letter.getLetter(sender, receiver);
		} else {
			order = Parcel.getParcel(sender, receiver, length, width, height);
		}
		
		return order;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(CITIZEN_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (new Random().nextBoolean()) {
				postLetterInBox(new Citizen(Demo.getRandomName(), Demo.random(MIN_AGE_FOR_CITIZEN, MAX_AGE_FOR_CITIZEN),
						Demo.getRandomAddress()));
			} else {
				float length = getRandomSize();
				float width = getRandomSize();
				float height = getRandomSize();
				
				postOrderInOffice(new Citizen(Demo.getRandomName(), Demo.random(MIN_AGE_FOR_CITIZEN, MAX_AGE_FOR_CITIZEN),
						Demo.getRandomAddress()), length, width, height);
			}
		}
	}
	
	public String getCitizenName() {
		return this.name;
	}
	
	private float getRandomSize() {
		float res = Demo.random(MIN_SIZE_ORDER, MAX_SIZE_ORDER);
		return res;
	}
}
