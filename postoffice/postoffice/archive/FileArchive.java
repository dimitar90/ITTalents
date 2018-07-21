package postoffice.archive;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import postoffice.citizen.CitizenDto;
import postoffice.order.Order;

public class FileArchive extends Archive{

	@Override
	public void processArchive(Map<LocalTime, Order> orders,LocalDate date) throws IOException {
		//Create file, save FILE
		File archiveFile = new File("backup-" + date + ".txt");
		List<CitizenDto> citizensDto = new ArrayList<>();
		
		for(Entry<LocalTime, Order> entry : orders.entrySet()) {
				Order order = entry.getValue();
				
				CitizenDto dto = CitizenDto.createDto(order.toString(),order.getSenderName(),order.getReceiverName(),date.toString());
				citizensDto.add(dto);
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String jsonStr = gson.toJson(citizensDto);
		
		try(PrintWriter pw = new PrintWriter(archiveFile)){
			archiveFile.createNewFile();
			pw.print(jsonStr);
			pw.close();
		}
	}

	
}
