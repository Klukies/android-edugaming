package be.howest.nma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import be.howest.nma.api.URLs;

public class CoachActivity extends AppCompatActivity {
    private ImageView coachImage;
    private TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach);
        coachImage = (ImageView) findViewById(R.id.coachImage);
        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);

        JsonParser parser = new JsonParser();
        JsonObject coach = parser.parse(getCoachData()).getAsJsonObject();
        setWelcomeMessage(coach.get("username").getAsString());
        setCoachImage(coach.get("img_url").getAsString());
    }

    private void setWelcomeMessage(String username) {
        welcomeMessage.setText("Welcome " + username);
    }

    private void setCoachImage(String img_url) {
        String coach_img_url = URLs.ROOT_URL + img_url;
        Picasso.get()
                .load(coach_img_url)
                .error(R.drawable.finished_logo)
                .resize(150,150)
                .centerCrop()
                .into(coachImage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JsonParser parser = new JsonParser();
        JsonObject coach = parser.parse(getCoachData()).getAsJsonObject();
        setWelcomeMessage(coach.get("username").getAsString());
        setCoachImage(coach.get("img_url").getAsString());
    }

    private String getCoachData() {
        SharedPreferences sharedPreferences = getSharedPreferences("coach", Context.MODE_PRIVATE);
        return sharedPreferences.getString(getString(R.string.coach), null);
    }

    public void logout(View view) {
        SharedPreferences preferences = this.getSharedPreferences("authentication", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(getString(R.string.accessToken));
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void loadProfile(View view) {
        Intent intent = new Intent(this, CoachProfileActivity.class);
        startActivity(intent);
    }
}
