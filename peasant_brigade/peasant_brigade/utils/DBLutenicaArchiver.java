package peasant_brigade.utils;

import peasant_brigade.utils.IArchiver;

public class DBLutenicaArchiver implements IArchiver {
    private static DBLutenicaArchiver instance;

    private DBLutenicaArchiver() {

    }

    public static DBLutenicaArchiver getInstance() {
        if (instance == null) {
            instance = new DBLutenicaArchiver();
        }

        return instance;
    }
}
