package be.howest.nma;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import be.howest.nma.api.URLs;

public class CoachReservationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_reservations);
        setReservations();
    }

    private void setReservations() {
        Ion.with(this)
                .load("GET", URLs.RESERVATIONS_URL)
                .setHeader("x-access-token", getToken())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject reservations) {
                        if (e != null) {
                            if (e.getMessage() == null) {
                                makeToaster("Couldn't connect to api");
                            } else {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            System.out.println(reservations);
                            //todo: set recyclerview to reservations
                        }
                    }
                });
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
