package postoffice.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import postoffice.archive.TypeArchive;
import postoffice.citizen.Citizen;
import postoffice.citizen.Collector;
import postoffice.citizen.Postman;

public class Demo {
	public static int random(int min, int max) {
		return new Random().nextInt(max - min) + min;
	}

	public static String getRandomName() {
		return NAMES[Demo.random(0, NAMES.length)];
	}

	public static final String[] NAMES = { "Zahir", "Boni", "Neven", "Nikifora", "Kolio", "Shibil", "Karagios",
			"Martin", "Sebastian", "Rumqna", "Rumen", "Hristina", "Kalin", "Malin", "Elvira", "Ivan", "Koko", "Mitko",
			"Deqn", "Rosen", "Stefka" };

	public static final String[] ADDRESSES = { "Vitoshka 21", "Kustendil 33", "Studentski grad 14", "Smolqn - vilata",
			"Sliven karandila", "5 kiusheta", "Mladost 5" };

	private static final int MIN_AGE = 18;

	private static final int MAX_AGE = 40;

	public static String getRandomAddress() {
		return null;
	}

	public static void main(String[] args) {
		PostOffice.ARCHIVE_TYPE = TypeArchive.values()[new Random().nextInt(TypeArchive.values().length - 1)];
		PostOffice postOffice = PostOffice.getInstance();
		

		List<Postman> postmen = new ArrayList<Postman>();
		for(int count = 0; count < 5; count++) {
			postmen.add(new Postman(getRandomName(), random(MIN_AGE, MAX_AGE), getRandomAddress(), random(1, 10)));
		}
		
		postOffice.addPostmen(postmen);
		
		List<Collector> collectors = new ArrayList<Collector>();
		for(int count = 0; count < 5; count++) {
			collectors.add(new Collector(getRandomName(), random(MIN_AGE, MAX_AGE), getRandomAddress()));
		}
		
		postOffice.addCollectors(collectors);
		
		List<Citizen> citizens = new ArrayList<Citizen>();
		for(int count = 0; count < 5; count++) {
			citizens.add(new Citizen(Demo.getRandomName(), Demo.random(MIN_AGE, MAX_AGE), Demo.getRandomAddress()));
		}
		
		for (Citizen citizen : citizens) {
			citizen.start();
		}
		
		postOffice.startWork();
	}

}
