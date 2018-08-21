package peasant_brigade.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public interface IArchiveFile {
	public default void process(String name, int secondsWorking) throws IOException {
		File archiveFile = new File("backup " + LocalDate.now() + ".txt");

		try (PrintWriter pw = new PrintWriter(archiveFile)) {
			archiveFile.createNewFile();
			pw.print(String.format("Chappie name -> %s. seconds working -> %d", name, secondsWorking));
			pw.close();
		}
	}
}
