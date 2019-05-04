package be.howest.nma.classes;

public class Reservation {
    private int user_id;
    private String reservation_time;
    private boolean confirmed;
    private String username;
    private String email;

    public Reservation(int user_id, String reservation_time, boolean confirmed, String username, String email) {
        this.user_id = user_id;
        this.reservation_time = reservation_time;
        this.confirmed = confirmed;
        this.username = username;
        this.email = email;
    }

    public String getReservation_time() {
        return reservation_time;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getUser_id() {
        return user_id;
    }
}
