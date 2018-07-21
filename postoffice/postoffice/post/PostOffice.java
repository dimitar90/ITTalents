package postoffice.post;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import postoffice.archive.Archive;
import postoffice.archive.IArchive;
import postoffice.archive.TypeArchive;
import postoffice.citizen.Collector;
import postoffice.citizen.Postman;
import postoffice.order.Order;

public class PostOffice {
	
	public static final int MAX_BOXES = 25;
	private static final int MAX_SIZE_REPO = 50;
	public static TypeArchive ARCHIVE_TYPE;
	
	private static volatile PostOffice instance;
	private static Object mutex = new Object();

	private List<Order> repository;
	private Map<Integer, List<Order>> postBoxes;
	private TreeMap<LocalDate, TreeMap<LocalTime, Order>> archive;

	private Set<Collector> collectors = new HashSet<>();
	private Set<Postman> postmen = new HashSet<>();
	
	//the post office is singleton
	private PostOffice() {
		this.postBoxes = new HashMap<>();
		this.repository = new LinkedList<>();

		this.archive = new TreeMap<>(new Comparator<LocalDate>() {
			@Override
			public int compare(LocalDate o1, LocalDate o2) {
				if (o1.isAfter(o2)) {
					return 1;
				}

				if (o1.isBefore(o2)) {
					return -1;
				}

				return 0;
			}
		});

		
		
		fillPostBoxes();
	}

	public static PostOffice getInstance() {
		synchronized (mutex) {
			if (instance == null) {
				instance = new PostOffice();
			}
		}

		return instance;
	}

	public void startWork() {
		while (true) {
			if (this.repository.size() < MAX_SIZE_REPO) {
				collect();
			} else {
				deliver();
			}
		}
	}

	public void extractAllOrdersByData(LocalDate date) {
		TreeMap<LocalTime,Order> orders = this.archive.get(date); 
		
		for (Map.Entry<LocalTime, Order> entry : orders.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
	
	public void extractLettersPercentFromOrders(LocalDate date) {
		int countLetters = 0;
		TreeMap<LocalTime,Order> orders = this.archive.get(date);
		
		for(Order order : orders.values()) {
			if (order.isLetter()) {
				countLetters++;
			}
		}
		
		System.out.println("Letters % is " + ((countLetters / orders.size()) * 100));
	}
	
	public void extractFrigelePercetFromOrders() {
		int countFragile = 0;
		int countPercel = 0;
		
		for(TreeMap<LocalTime,Order> orders : this.archive.values()) {
			
			for(Order order : orders.values()) {
				if (!order.isLetter()) {
					countPercel++;
				}
				
				if (order.isFragile()) {
					countFragile++;
				}
			}
			
			System.out.println("Fragile parcels % is " + ((countFragile / countPercel) * 100));
		}
		
	}
	
	public void extractPostmanWork() {
		Set<Postman> postmenByWork = new TreeSet<>(new Comparator<Postman>() {
			@Override
			public int compare(Postman o1, Postman o2) {
				if (o1.getCountSendedOrders() == o2.getCountSendedOrders()) {
					return o1.getCitizenName().compareTo(o2.getCitizenName());
				}
				
				return o1.getCountSendedOrders() - o2.getCountSendedOrders();
			}
		});
		
		postmenByWork.addAll(this.postmen);
		
		for(Postman postman : postmenByWork) {
			System.out.println(postman.getCitizenName() + " " + postman.getCountSendedOrders());
		}
	}
	
	public synchronized void  putLetterInBox(Order letter, int boxNumber) {
		this.postBoxes.get(boxNumber).add(letter);
	}
	
	// put the orders into the repository and into the archive
	public synchronized void putOrderInPostOffice(Order order) {
		// sort with comparator the orders by date and time
		LocalDate now = LocalDate.now();

		if (!this.archive.containsKey(now)) {
			//by date
			this.archive.put(now, new TreeMap<>(new Comparator<LocalTime>() {

				@Override
				public int compare(LocalTime o1, LocalTime o2) {
					if (o1.isBefore(o2)) {
						return -1;
					}

					if (o1.isAfter(o2)) {
						return 1;
					}
					return 0;
				}
			}));
		}
		//by time
		this.archive.get(now).put(LocalTime.now(), order);
		
		if (!this.repository.contains(order)) {
			this.repository.add(order);
		}
	}

	private void deliver() {
		// allocate the orders to the postmen and start delivery
		List<Postman> freePostmen = new ArrayList<>();
		
		for (Postman postman : this.postmen) {
			if (postman.isFree()) {
				freePostmen.add(postman);
			}
		}	
		
		// count of equal pieces of orders each postman
		int countDeliveryOrders = this.repository.size() / freePostmen.size();
		
		for (Postman postman : freePostmen) {
			
			List<Order> ordersForCurrentPostman = new ArrayList<>();
			
			for (int count = 0; count < countDeliveryOrders; count++) {
				Order currentOrder = this.repository.remove(0);
				ordersForCurrentPostman.add(currentOrder);
			}
			//deliver each current package of orders
			postman.deliverOrders(ordersForCurrentPostman);
		}
	}

	private void collect() {
		//gather letters from the boxes and post them into the postOffice
		int currentBox = 0;//untill 25 boxes
		
		
		while(currentBox < MAX_BOXES) {
			List<Order> ordersFromOneBox = new ArrayList<>();
			
			for (Collector collector : this.collectors) {
				if (currentBox >= MAX_BOXES) {
					break;
				}
				//get all orders from the current box
				ordersFromOneBox.addAll(this.postBoxes.get(currentBox));
				//empty the box
				this.postBoxes.get(currentBox).clear();
				//set to the collector
				collector.collectTheLetters(ordersFromOneBox);
				currentBox++;
			}
		}
	}
	
	public void addCollectors(List<Collector> collectors) {
		if (!collectors.isEmpty()) {
			this.collectors.addAll(collectors);	
		}
	}
	
	public void addPostmen(List<Postman> postmen) {
		if (!postmen.isEmpty()) {
			this.postmen.addAll(postmen);	
		}
	}
	
	private void fillPostBoxes() {
		for (int numBox = 0; numBox < MAX_BOXES; numBox++) {
			this.postBoxes.put(numBox, new ArrayList<>());
		}
	}
	
	
	
	private class Archiver extends Thread{
		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep(24*60*60*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
				
				LocalDate currentDate = LocalDate.now();
				TreeMap<LocalTime,Order> tempArchive = archive.get(currentDate);
				
				IArchive arch = Archive.makeArchive(PostOffice.ARCHIVE_TYPE);
				
				try {
					arch.processArchive(tempArchive,currentDate);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
