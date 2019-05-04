package be.howest.nma;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import be.howest.nma.api.URLs;
import be.howest.nma.classes.RecyclerViewAdapter;
import be.howest.nma.classes.Reservation;

public class CoachReservationsActivity extends AppCompatActivity {
    private List<Reservation> reservationList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_reservations);
        recyclerView = (RecyclerView) findViewById(R.id.reservationRecyclerView);
        getReservations();
    }

    private void getReservations() {
        Ion.with(this)
                .load("GET", URLs.RESERVATIONS_URL)
                .setHeader("x-access-token", getToken())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject reservationsObject) {
                        if (e != null) {
                            if (e.getMessage() == null) {
                                makeToaster("Couldn't connect to api");
                            } else {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            createReservationList(reservationsObject);
                        }
                    }
                });
    }

    private void createReservationList(JsonObject reservationsObject) {
        JsonArray reservations = reservationsObject.get("reservations").getAsJsonArray();
        System.out.println(reservations);
        for (JsonElement reservationElement : reservations) {
            JsonObject reservation = reservationElement.getAsJsonObject();
            reservationList.add(new Reservation(
                    Integer.parseInt(reservation.get("user_id").toString()),
                    reservation.get("reservation_time").toString(),
                    reservation.get("confirmed").getAsBoolean(),
                    reservation.get("user").getAsJsonObject().get("name").toString(),
                    reservation.get("user").getAsJsonObject().get("email").toString()
            ));
        }
        setRvAdapter(reservationList);
    }

    private void setRvAdapter(List<Reservation> reservationList) {
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, reservationList, getToken());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }

    private void makeToaster(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        return sharedPreferences.getString(getString(R.string.accessToken), null);
    }
}
