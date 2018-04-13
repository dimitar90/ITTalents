package easter.family;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import easter.dbmanager.DBManager;
import easter.eggs.EggColor;
import easter.eggs.EggVariety;

public class GrandMother extends Thread {
	private static final String MSG_AFTER_EXCEPTION = "Exception in the grandmother work";
	private static final int SLEEPING_TIME_GRAND_MOTHER = 15_000;
	private static final String GOUNT_OF_TYPE_EGG_AND_COLOR_QUERY = "SELECT COUNT(*) FROM eggs E JOIN jars J ON (E.jar_id = J.id) JOIN egg_types ET ON(ET.id = E.type_id)"
			+ "WHERE J.color = (?) AND ET.name = (?)";
	private static final String ID_OF_MAX_COUNT_EGGS_PAINTED_OF_THE_JAR_QUERY = "SELECT J.id as ID, J.color as Color"
			+ "FROM eggs E" + "JOIN jars J" + "ON(E.jar_id = J.id)" + "GROUP BY E.id desc" + "LIMIT 1";
	private static final String NAME_OF_KID_THAT_PAINTED_LESS_EGGS = "SELECT K.name as Name" + "FROM eggs E"
			+ "		JOIN kids K" + "ON(E.kid_id = K.id)" + "GROUP BY E.id asc" + "LIMIT 1;";
	private static GrandMother grandMother;

	private GrandMother() {
		this.setDaemon(true);
	}

	public static GrandMother getInstance() {
		if (grandMother == null) {
			grandMother = new GrandMother();
		}

		return grandMother;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(SLEEPING_TIME_GRAND_MOTHER);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				this.countOfAllPaintedEggs();
				this.countOfAllColorfulEggs();
				this.countOfGooseGreenEggs();
				this.jarPaintedMostEggs();
				this.nameOfTheKidThatPaintedLessEggs();
			} catch (SQLException e) {
				System.out.println(MSG_AFTER_EXCEPTION);
				e.printStackTrace();
			}

		}
	}

	private void nameOfTheKidThatPaintedLessEggs() throws SQLException {

		PreparedStatement ps = DBManager.getInstence().getConnection()
				.prepareStatement(NAME_OF_KID_THAT_PAINTED_LESS_EGGS);
		ps.executeQuery();
		ResultSet result = ps.getResultSet();
		result.next();
		String name = result.getString(1);
		ps.close();
		System.out.println(String.format("Kid that painted less eggs", name));
		System.out.println("*******************************************************");

	}

	private void jarPaintedMostEggs() throws SQLException {
		PreparedStatement ps = DBManager.getInstence().getConnection()
				.prepareStatement(ID_OF_MAX_COUNT_EGGS_PAINTED_OF_THE_JAR_QUERY);
		ps.executeQuery();

		ResultSet result = ps.getResultSet();
		result.next();
		int id = result.getInt(1);
		String jar = result.getString(2);
		ps.close();
		System.out.println(String.format("Jar with id %d and color %s is the painter", id, jar));
		System.out.println("*******************************************************");
	}

	private void countOfGooseGreenEggs() throws SQLException {
		String color = EggColor.ORANGE.toString();
		String variety = EggVariety.GOOSE_EGG.toString();
		PreparedStatement ps = DBManager.getInstence().getConnection()
				.prepareStatement(GOUNT_OF_TYPE_EGG_AND_COLOR_QUERY);
		ps.setString(1, color);
		ps.setString(2, variety);
		ps.executeQuery();

		ResultSet result = ps.getResultSet();
		result.next();
		int count = result.getInt(1);
		ps.close();
		System.out.println(String.format("Count of %s eggs. Painted in %s -> %d", color, variety, count));
		System.out.println("*******************************************************");
	}

	private void countOfAllColorfulEggs() throws SQLException {
		PreparedStatement ps = DBManager.getInstence().getConnection()
				.prepareStatement("SELECT COUNT(*) FROM eggs WHERE is_partycolor = (?)");
		ps.setBoolean(1, true);
		ps.executeQuery();

		ResultSet res = ps.getResultSet();
		res.next();
		int count = res.getInt(1);
		ps.close();
		System.out.println(String.format("Count of all eggs that are colored: %d", count));
		System.out.println("*******************************************************");
	}

	private void countOfAllPaintedEggs() throws SQLException {
		PreparedStatement ps = DBManager.getInstence().getConnection().prepareStatement("SELECT COUNT(*) FROM eggs");
		ps.executeQuery();

		ResultSet res = ps.getResultSet();
		res.next();
		int count = res.getInt(1);
		ps.close();
		System.out.println(String.format("Count of all painted eggs is: %d", count));
		System.out.println("*******************************************************");
	}
}
