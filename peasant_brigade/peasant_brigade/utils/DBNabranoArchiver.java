package peasant_brigade.utils;

import peasant_brigade.utils.IArchiver;

public class DBNabranoArchiver implements IArchiver {
    private static DBNabranoArchiver instance;

    private DBNabranoArchiver() {

    }

    public static DBNabranoArchiver getInstance() {
        if (instance == null) {
            instance = new DBNabranoArchiver();
        }

        return instance;
    }
}
