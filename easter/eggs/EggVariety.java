package easter.eggs;

public enum EggVariety {
	HEN_EGG(10, 1), GOOSE_EGG(5, 2), DUCK_EGG(3, 3);

	private int time;
	private int id;;

	private EggVariety(int time, int id) {
		this.time = time;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int getTimeForPaint() {
		return this.time;
	}
}
