package easter.eggs;

import java.time.LocalDateTime;

import easter.exceptions.EggException;

public class Egg {
	private static final String MSG_INVALID_NAME_FOR_KID = "Invalid name for kid";
	private static final int MIN_LENGTH_FOR_NAME = 2;
	private static final String MSG_INVALID_TIME_OF_PAINT = "Invalid time of paint";
	private String nameOfKid;
	private EggColor color;
	private EggVariety variety;
	private boolean isColorfull;
	private LocalDateTime timeOfPaint;
	private boolean isReady = false;
	private int intIdKid;

	public Egg(EggVariety variety) {
		this.variety = variety;
	}

	public int getTimeForPaint() {
		return this.variety.getTimeForPaint();
	}

	public EggColor getColor() {
		return color;
	}

	public void setColor(EggColor color) {
		this.color = color;
	}

	public EggVariety getVariety() {
		return variety;
	}

	public void setVariety(EggVariety variety) {
		this.variety = variety;
	}

	public boolean isColorfull() {
		return isColorfull;
	}

	public void setColorfull(boolean isColorfull) {
		this.isColorfull = isColorfull;
	}

	public LocalDateTime getTimeOfPaint() {
		return timeOfPaint;
	}

	public void setTimeOfPaint(LocalDateTime timeOfPaint) throws EggException {
		if (timeOfPaint != null) {
			this.timeOfPaint = timeOfPaint;
		} else {
			throw new EggException(MSG_INVALID_TIME_OF_PAINT);
		}

	}

	public String getNameOfKid() {
		return nameOfKid;
	}

	public void setNameOfKid(String nameOfKid) throws EggException {
		if (nameOfKid != null && nameOfKid.length() > MIN_LENGTH_FOR_NAME) {
			this.nameOfKid = nameOfKid;
		} else {
			throw new EggException(MSG_INVALID_NAME_FOR_KID);
		}

	}

	public boolean isReady() {
		return isReady;
	}

	public void setReadyToTrue() {
		this.isReady = true;
	}

	public int getIntIdKid() {
		return intIdKid;
	}

	public void setIntIdKid(int intIdKid) {
		this.intIdKid = intIdKid;
	}

}
