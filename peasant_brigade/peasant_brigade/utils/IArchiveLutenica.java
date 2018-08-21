package peasant_brigade.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import peasant_brigade.premises.DBManager;

public interface IArchiveLutenica {
	public default void process(LocalDate date, int quantity, String name) {
		String sql = "INSERT INTO lutenica (date,quantity,grandmother_name) VALUES (?,?,?) ";
		
		try {
			PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
			ps.setString(1, date.toString());
			ps.setInt(2, quantity);
			ps.setString(3, name);
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
