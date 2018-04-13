package easter.family;

import easter.appliances.FleshPot;
import easter.appliances.Table;
import easter.eggs.Egg;
import easter.exceptions.EggException;
import easter.exceptions.FleshPotException;
import easter.exceptions.KidException;

public class Kid extends Thread {
	private static final int MIN_LENGTH_NAME = 2;
	private static final int SLEEPING_TIME_BETWEEN_TAKE_AND_PUT_THE_EGGS = 200;
	private String name;
	private static final FleshPot FLESHPOT = FleshPot.getInstance();
	private static final Table TABLE = Table.getInstance();
	private final int id;
	private static int nextId = 0;

	public Kid(String name) throws KidException {
		this.setKidName(name);
		this.id = ++nextId;
	}

	private void setKidName(String name) throws KidException {
		if (name != null && name.length() > MIN_LENGTH_NAME) {

			this.name = name;
		} else {
			throw new KidException();
		}
	}

	@Override
	public void run() {
		while (true) {
			Egg egg = null;

			try {
				egg = FLESHPOT.getEgg();
				egg.setNameOfKid(this.name);
				egg.setIntIdKid(this.getKidId());
				Thread.sleep(SLEEPING_TIME_BETWEEN_TAKE_AND_PUT_THE_EGGS);
			} catch (InterruptedException | FleshPotException | EggException ex) {
				ex.printStackTrace();
				return;
			}
			TABLE.putEggInJar(egg);
		}
	}

	public int getKidId() {
		return id;
	}
}
