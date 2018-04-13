package easter.appliances;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import easter.eggs.Egg;
import easter.eggs.EggColor;
import easter.exceptions.EggException;

public class Table {
	private static final int MULTIPLIER_TO_CONVERT_SLEEP_TIME_IN_SECONDS = 1000;
	private static Table table;
	private ConcurrentHashMap<EggColor, CopyOnWriteArrayList<Egg>> jars;

	private Table() {
		this.jars = new ConcurrentHashMap<EggColor, CopyOnWriteArrayList<Egg>>();
		this.prepareJarsWithPaint();
	}

	private void prepareJarsWithPaint() {
		for (EggColor color : EggColor.values()) {
			this.jars.put(color, new CopyOnWriteArrayList<Egg>());
		}
	}

	public synchronized static Table getInstance() {
		if (table == null) {
			table = new Table();
		}

		return table;
	}

	public Egg getEggFromJar() {
		// If there is available egg
		while (true) {
			for (EggColor color : this.jars.keySet()) {
				for (int e = 0; e < this.jars.get(color).size(); e++) {
					if (this.jars.get(color).get(e).isReady()) {
						Egg egg = this.jars.get(color).get(e);
						this.jars.get(color).remove(egg);
						System.out.println("Mother took egg with color " + egg.getColor());
						return egg;
					}

				}
			}
		}

	}

	public synchronized void putEggInJar(Egg egg) {
		// put the egg in the jar if there is a space
		int timeForPaint = egg.getTimeForPaint();
		try {
			Thread.sleep(timeForPaint * MULTIPLIER_TO_CONVERT_SLEEP_TIME_IN_SECONDS);

			EggColor jar = getFreeJar();

			egg.setColor(jar);

			this.jars.get(jar).add(egg);

			LocalDateTime d = LocalDateTime.now();

			egg.setReadyToTrue();
			
			egg.setTimeOfPaint(d);
			System.out.println("Egg painted in " + egg.getColor());
		} catch (InterruptedException | EggException e1) {
			e1.printStackTrace();
		}
	}

	private EggColor getFreeJar() {
		while (true) {
			EggColor j = getRandomJar();
			if (jars.get(j).size() < 2) {
				return j;
			}
		}
	}

	private EggColor getRandomJar() {
		Random random = new Random();
		return EggColor.values()[random.nextInt(EggColor.values().length)];
	}
}
