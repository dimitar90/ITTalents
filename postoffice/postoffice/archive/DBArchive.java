package postoffice.archive;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Map.Entry;

import postoffice.order.Order;

public class DBArchive extends Archive{

	@Override
	public void processArchive(Map<LocalTime, Order> orders,LocalDate date) {
		// Connect to db, insert into table
		String sql = "INSERT INTO post_office_archive (order,sender_name,receiver_name,date_of_sending) VALUES (?,?,?,?)";
		try {
			for (Entry<LocalTime, Order> entry : orders.entrySet()) {
				Order order = entry.getValue();

				PreparedStatement ps = DBManager.getConnection().prepareStatement(sql);
				ps.setString(1, order.toString());
				ps.setString(2, order.getSenderName());
				ps.setString(3, order.getReceiverName());
				ps.setString(4, date.toString());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
