package postoffice.citizen;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import postoffice.order.Order;

public class Postman extends Citizen {
	private int exp;
	private boolean free;
	private List<Order> orders;
	private int countSendedOrders = 0;
	
	public Postman(String name, int age, String address,int exp) {
		super(name, age, address);
		this.orders = new ArrayList<>();
		this.exp = exp;
		this.free = true;
	}
	
	public void deliverOrders(List<Order> orders) {
		this.orders.addAll(orders);
		this.countSendedOrders += orders.size();
		
		if (!isAlive()) {
			start();
		}
	}
	
	
	public boolean isFree() {
		return free;
	}
	
	public void manageFree(boolean free) {
		this.free = free;
	}
	
	public int getCountSendedOrders() {
		return countSendedOrders;
	}
	
	@Override
	public void run() {
		
		while (true) {
			//until there is no orders looping
			if (this.orders.isEmpty()) {
				continue;
			}
			
			this.free = false;
			// deliver each order with related the time duration
			for (Order order : this.orders) {
				try {
					Thread.sleep(order.getDuration());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			this.orders.clear();
			this.free = true;
		}
	}

	
}
