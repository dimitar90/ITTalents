package easter.appliances;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import easter.dbmanager.DBManager;
import easter.eggs.Egg;
import easter.eggs.EggVariety;

public class Refrigerator {

	private static Refrigerator refrigerator;
	private ConcurrentHashMap<EggVariety, CopyOnWriteArrayList<Egg>> eggBoxes;
	private Father father;

	private Refrigerator() {
		father = new Father();
		father.start();
		this.eggBoxes = new ConcurrentHashMap<>();
		this.prepareTheBoxes();
	}

	private void prepareTheBoxes() {
		for (EggVariety v : EggVariety.values()) {
			this.eggBoxes.put(v, new CopyOnWriteArrayList<>());
		}
	}

	public synchronized static Refrigerator getInstance() {
		if (refrigerator == null) {
			refrigerator = new Refrigerator();
		}

		return refrigerator;
	}

	public void putEggInBox(Egg egg) {
		// put the egg in the box, based on its variety
		for (Entry<EggVariety, CopyOnWriteArrayList<Egg>> entry : this.eggBoxes.entrySet()) {
			if (entry.getKey().equals(egg.getVariety())) {
				entry.getValue().add(egg);
				// log here
				System.out.println("Mother PUTH EGG IN " + entry.getKey() + " box");
				break;
			}
		}

	}

	private class Father extends Thread {
		private static final int SLEEPING_TIME_OF_FATHER = 5_000;
		private Map<String, Integer> kidsData = new HashMap<>();
		private Map<String, Integer> colorsData = new HashMap<>();
		private Map<String, Integer> varietiesData = new HashMap<>();

		private Father() {
			this.setDaemon(true);
		}

		@Override
		public void run() {
			while (true) {
				// TYPE FILE
				try {
					Thread.sleep(SLEEPING_TIME_OF_FATHER);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				File file = new File("report.txt");

				try (PrintWriter printWriter = new PrintWriter(file)) {
					// file.createNewFile();
					for (Entry<EggVariety, CopyOnWriteArrayList<Egg>> entry : eggBoxes.entrySet()) {
						int count = 0;
						printWriter.println(entry.getKey());
						for (Egg egg : entry.getValue()) {
							printWriter.println(++count + " -" + egg.getColor() + "- of " + egg.getVariety() + "- "
									+ egg.isColorfull() + "- " + egg.getTimeOfPaint());
						}
						printWriter.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				// TYPE DB
				try {
					for (Entry<EggVariety, CopyOnWriteArrayList<Egg>> entry : eggBoxes.entrySet()) {
						for (Egg egg : entry.getValue()) {
							PreparedStatement psK = DBManager.getInstence().getConnection().prepareStatement(
									"INSERT INTO kids (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);

							String nameKid = egg.getNameOfKid();
							if (!this.kidsData.containsKey(nameKid)) {
								psK.setString(1, nameKid);
								psK.executeUpdate();

								ResultSet getKey = psK.getGeneratedKeys();
								getKey.next();
								int currentKey = getKey.getInt(1);
								this.kidsData.put(nameKid, currentKey);
							}

							int idKid = this.kidsData.get(nameKid);
							psK.close();

							PreparedStatement psJ = DBManager.getInstence().getConnection().prepareStatement(
									"INSERT INTO jars (color) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
							String color = egg.getColor().toString();
							if (!this.colorsData.containsKey(color)) {
								psJ.setString(1, color);
								psJ.executeUpdate();

								ResultSet getKey = psJ.getGeneratedKeys();
								getKey.next();
								int currentKey = getKey.getInt(1);
								this.colorsData.put(color, currentKey);
							}
							int idJar = this.colorsData.get(color);
							psJ.close();

							PreparedStatement psET = DBManager.getInstence().getConnection().prepareStatement(
									"INSERT INTO egg_types (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
							
							String variety = egg.getVariety().toString();
							if (!this.varietiesData.containsKey(variety)) {
								psET.setString(1, variety);
								psET.executeUpdate();
								ResultSet getKey = psET.getGeneratedKeys();
								getKey.next();
								int currentKey = getKey.getInt(1);
								this.varietiesData.put(variety, currentKey);
							}
							int idEggVariety = this.varietiesData.get(variety);
							psET.close();

							Timestamp eggTimeInTimeSpan = Timestamp.valueOf(egg.getTimeOfPaint());
							PreparedStatement psEggs = DBManager.getInstence().getConnection().prepareStatement(
									"INSERT INTO eggs (type_id, is_partycolor, date, jar_id, kid_id) VALUES (?, ?, ?, ?, ?)");
							psEggs.setInt(1, idEggVariety);
							psEggs.setBoolean(2, egg.isColorfull());
							psEggs.setTimestamp(3, eggTimeInTimeSpan);
							psEggs.setInt(4, idJar);
							psEggs.setInt(5, idKid);
							psEggs.executeUpdate();
							psEggs.close();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
