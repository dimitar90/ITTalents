package postoffice.citizen;

import java.util.ArrayList;
import java.util.List;

import postoffice.order.Order;
import postoffice.post.PostOffice;

public class Collector extends Citizen {
	private static final long WALK_TIME_EACH_COLLECTOR = 2 * 60 * 60 * 1000;
	
	private List<Order> orders;
	private static PostOffice postOffice = PostOffice.getInstance();

	public Collector(String name, int age, String address) {
		super(name, age, address);
		this.orders = new ArrayList<>();
	}

	public void collectTheLetters(List<Order> orders) {
		this.orders.addAll(orders);
	}

	@Override
	public void run() {
		while (true) {
			// until no orders - looping
			if (this.orders.isEmpty()) {
				continue;
			}
			//Put each order from the package into the post office
			for (Order order : orders) {
				postOffice.putOrderInPostOffice(order);
			}
			
			try {
				Thread.sleep(WALK_TIME_EACH_COLLECTOR);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
