package be.howest.nma;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import be.howest.nma.api.URLs;

public class CoachProfileActivity extends AppCompatActivity {
    private ImageView coachImageEdit;
    private EditText editUsername;
    private int current_game_id;
    private Spinner games_spinner;
    private EditText editPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);
        coachImageEdit = (ImageView) findViewById(R.id.coachImageEdit);
        editUsername = (EditText) findViewById(R.id.editUsername);
        games_spinner = (Spinner) findViewById(R.id.game_spinner);
        editPrice = (EditText) findViewById(R.id.editPrice);

        JsonObject coach = getCoach();
        setFields(coach);
    }

    private JsonObject getCoach() {
        SharedPreferences sharedPreferences = getSharedPreferences("coach", Context.MODE_PRIVATE);
        String coachString = sharedPreferences.getString(getString(R.string.coach), null);
        JsonParser parser = new JsonParser();
        return parser.parse(coachString).getAsJsonObject();
    }

    private void getUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString(getString(R.string.accessToken), null);

        Ion.with(this)
                .load("GET", URLs.COACH_URL)
                .setHeader("x-access-token", accessToken)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            if (e.getMessage() == null) {
                                makeToaster("Couldn't connect to api");
                            } else {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            setFields(result);
                        }
                    }
                });
    }

    private void setFields(JsonObject coach) {
        System.out.println(coach);
        String coach_img_url = URLs.ROOT_URL + coach.get("img_url").getAsString();
        Picasso.get().load(coach_img_url).error(R.drawable.finished_logo).resize(150,150).centerCrop().into(coachImageEdit);
        editUsername.setHint(coach.get("username").getAsString());
        current_game_id = coach.get("game_id").getAsInt();
        setGames();
        editPrice.setHint(coach.get("price").getAsString());
    }

    private void setGames() {
        Ion.with(this)
                .load("GET", URLs.GAMES_URL)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray games) {
                        if (e != null) {
                            if (e.getMessage() == null) {
                                makeToaster("Couldn't connect to api");
                            } else {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            createSpinner(games);
                        }
                    }
                });
    }

    private void createSpinner(JsonArray games) {
        ArrayList<String> gameNames = new ArrayList<>();

        for (JsonElement game : games) {
            gameNames.add(game.getAsJsonObject().get("title").getAsString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.games_spinner, gameNames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        games_spinner.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.colorText), PorterDuff.Mode.SRC_ATOP);
        games_spinner.setAdapter(dataAdapter);
        games_spinner.setSelection(current_game_id);
        games_spinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
                //todo: Update game_id
                System.out.println(position + 1);
            }
            @Override
            public void onNothingSelected (AdapterView<?> parent) {

            }
        });
    }

    private void makeToaster(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
