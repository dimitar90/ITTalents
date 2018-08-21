package peasant_brigade.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import peasant_brigade.premises.DBManager;
import peasant_brigade.vegetable.Vegetable;

public interface IArchiveNabrano {
	public default void process(List<Vegetable> vegetables) {
		String sql = "INSERT INTO nabrano (damsel_name,veggie_type,quantity) VALUES(?,?,?) ";

		int countVaggies = 0;
		String damselName = vegetables.stream().findFirst().get().getDamselName();
		String veggieType = vegetables.stream().findFirst().get().getType().toString();

		try {
			PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
			ps.setString(1, damselName);
			ps.setString(2, veggieType);
			ps.setInt(3, countVaggies);

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}
}
