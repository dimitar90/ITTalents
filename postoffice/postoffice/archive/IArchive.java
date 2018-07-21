package postoffice.archive;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import postoffice.order.Order;

public interface IArchive {
	public void processArchive(Map<LocalTime,Order> tempArchive,LocalDate date) throws Exception;
}
