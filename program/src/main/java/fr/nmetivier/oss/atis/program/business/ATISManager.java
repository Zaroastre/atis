package fr.nmetivier.oss.atis.program.business;

public class ATISManager {
    private static ATISManager instance = null;

    public static ATISManager getInstance() {
        if (ATISManager.instance == null) {
            ATISManager.instance = new ATISManager();
        }
        return ATISManager.instance;
    }
    private ATISManager() { }
}
