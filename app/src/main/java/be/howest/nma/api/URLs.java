package be.howest.nma.api;

public class URLs {
    //desktop
    //private static final String ROOT_URL = "http://192.168.1.29:3000";

    //gf
    //public static final String ROOT_URL = "http://192.168.0.17:3000";

    //device
    public static final String ROOT_URL = "http://10.0.2.2:3000";

    //Auth urls
    public static final String LOGIN_URL= ROOT_URL + "/auth/login";
    public static final String REGISTER_URL = ROOT_URL + "/auth/register";

    //Coach urls
    private static final String COACH_URL = ROOT_URL + "/coach";
    public static final String GAMES_URL = ROOT_URL + "/games";
    public static final String COACH_USERNAME_URL = COACH_URL + "/username";
    public static final String COACH_GAME_URL = COACH_URL + "/game";
    public static final String COACH_PRICE_URL = COACH_URL + "/price";
    public static final String COACH_SUMMARY_URL = COACH_URL + "/summary";
    public static final String COACH_DESCRIPTION_URL = COACH_URL + "/description";
    public static final String COACH_IMAGE_URL = COACH_URL + "/image";

    //Reservation urls
    public static final String RESERVATIONS_URL = ROOT_URL + "/reservations";
    public static final String CANCEL_RESERVATION_URL = RESERVATIONS_URL + "/cancel";
}
