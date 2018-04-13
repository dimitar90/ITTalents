package easter.appliances;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import easter.eggs.Egg;
import easter.exceptions.FleshPotException;
import easter.family.Kid;

public class FleshPot {
	private static final String MSG_INVALID_PARAMS_FOR_KID = "The kid can't be null";
	private static final String MSG_EMPTY_FLESH_POT = "No more eggs!";
	private static FleshPot fleshPot;
	private Set<Kid> kids;
	private List<Egg> eggs;
	private int currenEgg = 0;

	private FleshPot() {
		this.kids = new HashSet<>();
		this.eggs = new LinkedList<>();
	}

	public synchronized static FleshPot getInstance() {
		if (fleshPot == null) {
			fleshPot = new FleshPot();
		}

		return fleshPot;
	}

	public void inviteKids(Kid k) throws FleshPotException {
		if (k != null) {
			this.kids.add(k);
		} else {
			throw new FleshPotException(MSG_INVALID_PARAMS_FOR_KID);
		}
	}

	public synchronized Egg getEgg() throws FleshPotException {
		if (this.eggs.size() > 0) {
			System.out.println("Kid Get egg number " + ++this.currenEgg);
			return this.eggs.remove(0);

		} else {
			throw new FleshPotException(MSG_EMPTY_FLESH_POT);
		}
	}

	public void addEgg(Egg e) {
		if (e != null) {
			this.eggs.add(e);
		}
	}

	public void openTheLid() {
		for (Kid k : kids) {
			if (!k.isAlive()) {
				k.start();
			}
		}

	}

}
