package easter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import easter.appliances.FleshPot;
import easter.appliances.Table;
import easter.eggs.Egg;
import easter.eggs.EggFactory;
import easter.eggs.EggVariety;
import easter.family.GrandMother;
import easter.family.Kid;
import easter.family.Mother;

public class Demo {
	private static final int COUNT_EGGS = 50;
	private static final int COUNT_OF_KIDS = 3;
	private static Random rnd = new Random();

	public static int random(int min, int max) {
		return new Random().nextInt(max - min) + min;
	}

	public static void main(String[] args) {

		try {
			EggFactory eggFactory = EggFactory.getInstance();
			Mother mother = Mother.getInstance();

			List<Kid> kids = new ArrayList<>();

			FleshPot fleshPot = FleshPot.getInstance();

			Table table = Table.getInstance();

			for (int count = 0; count < COUNT_EGGS; count++) {
				Egg egg = null;

				int probability = rnd.nextInt(3);

				switch (probability) {
				case 1:
					egg = eggFactory.getEgg(EggVariety.DUCK_EGG);
					break;
				case 2:
					egg = eggFactory.getEgg(EggVariety.GOOSE_EGG);
					break;
				default:
					egg = eggFactory.getEgg(EggVariety.HEN_EGG);
					break;
				}
				fleshPot.addEgg(egg);
			}

			for (int count = 0; count < COUNT_OF_KIDS; count++) {
				kids.add(new Kid("Kid number " + (count + 1)));
			}

			for (Kid kid : kids) {
				fleshPot.inviteKids(kid);
			}

			fleshPot.openTheLid();
			mother.start();
			GrandMother grandModher = GrandMother.getInstance();
			grandModher.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
