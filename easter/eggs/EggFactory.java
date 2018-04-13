package easter.eggs;

public class EggFactory {
	private static EggFactory factory;

	private EggFactory() {

	}

	public Egg getEgg(EggVariety eggVariety) {
		switch (eggVariety) {
		case DUCK_EGG:
			return new Egg(EggVariety.DUCK_EGG);
		case GOOSE_EGG:
			return new Egg(EggVariety.GOOSE_EGG);
		default:
			return new Egg(EggVariety.HEN_EGG);
		}
	}

	public static EggFactory getInstance() {
		if (factory == null) {
			factory = new EggFactory();
		}

		return factory;
	}
}
