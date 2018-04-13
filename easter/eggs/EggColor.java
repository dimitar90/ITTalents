package easter.eggs;

public enum EggColor {
	GREEN(1), RED(2), BLUE(3), YELLOW(4), ORANGE(5);

	int id;

	private EggColor(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
