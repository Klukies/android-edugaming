package be.howest.nma.api;

public class VolleySingleton {
    private static final VolleySingleton ourInstance = new VolleySingleton();

    public static VolleySingleton getInstance() {
        return ourInstance;
    }

    private VolleySingleton() {
    }
}
