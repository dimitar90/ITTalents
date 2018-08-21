package peasant_brigade.utils;

import peasant_brigade.utils.IArchiver;

public class FileArchiver implements IArchiver {
    private static FileArchiver instance;

    private FileArchiver() {

    }

    public static FileArchiver getInstance() {
        if (instance == null) {
            instance = new FileArchiver();
        }

        return instance;
    }
}
