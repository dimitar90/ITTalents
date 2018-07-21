package postoffice.archive;

public abstract class Archive implements IArchive {

	public static IArchive makeArchive(TypeArchive type) {
		if (type.equals(TypeArchive.FILE)) {
			return new FileArchive();
		} else {
			return new DBArchive();
		}
	}
}
