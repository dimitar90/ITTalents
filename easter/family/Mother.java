package easter.family;

import easter.appliances.Refrigerator;
import easter.appliances.Table;
import easter.eggs.Egg;

public class Mother extends Thread {
	private static final int SLEEPING_TIME_DURING_DO_THE_EGG_COLORFUL = 5000;
	private static final double CHANCE_TO_MAKE_THE_EGG_COLORFUL = 0.20;
	private static final int SLEEPING_TIME_POLISH_THE_EGG = 2000;
	private static Mother mother;
	private Table table = Table.getInstance();
	private Refrigerator refrigerator = Refrigerator.getInstance();

	private Mother() {

	}

	public static Mother getInstance() {
		if (mother == null) {
			mother = new Mother();
		}

		return mother;
	}

	@Override
	public void run() {
		while (true) {
			Egg e = table.getEggFromJar();

			// Polish the egg
			try {
				Thread.sleep(SLEEPING_TIME_POLISH_THE_EGG);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				return;
			}

			// 20% chance to make each egg colorful
			if (Math.random() <= CHANCE_TO_MAKE_THE_EGG_COLORFUL) {
				this.colorIt(e);
			}

			refrigerator.putEggInBox(e);
		}

	}

	private void colorIt(Egg e) {
		try {
			Thread.sleep(SLEEPING_TIME_DURING_DO_THE_EGG_COLORFUL);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		e.setColorfull(true);
		System.out.println("One egg is colorfull now");
	}
}
