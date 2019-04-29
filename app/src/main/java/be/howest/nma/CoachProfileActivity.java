package be.howest.nma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import be.howest.nma.api.URLs;

public class CoachProfileActivity extends AppCompatActivity {
    private ImageView coachImageEdit;
    private int current_game_id;
    private Spinner games_spinner;
    private EditText editUsername, editPrice, editSummary, editDescription;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);
        coachImageEdit = (ImageView) findViewById(R.id.coachImageEdit);
        editUsername = (EditText) findViewById(R.id.editUsername);
        games_spinner = (Spinner) findViewById(R.id.game_spinner);
        editPrice = (EditText) findViewById(R.id.editPrice);
        editSummary = (EditText) findViewById(R.id.editSummary);
        editDescription = (EditText) findViewById(R.id.editDescription);
        JsonObject coach = getCoach();
        setFields(coach);
    }

    private JsonObject getCoach() {
        SharedPreferences sharedPreferences = getSharedPreferences("coach", Context.MODE_PRIVATE);
        String coachString = sharedPreferences.getString(getString(R.string.coach), null);
        JsonParser parser = new JsonParser();
        return parser.parse(coachString).getAsJsonObject();
    }

    private void setFields(JsonObject coach) {
        setImage(coach.get("img_url").getAsString());
        editUsername.setHint(coach.get("username").getAsString());
        current_game_id = coach.get("game_id").getAsInt() - 1;
        setGames();
        editPrice.setHint(coach.get("price").getAsString());
        setSummary(coach.get("summary"));
        setDescription(coach.get("description"));
    }

    private void setImage(String img_url) {
        String coach_img_url = URLs.ROOT_URL + img_url;
        Picasso.get().load(coach_img_url).error(R.drawable.finished_logo).resize(150,150).centerCrop().into(coachImageEdit);
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

    private void setSummary(JsonElement summary) {
        if (summary.isJsonNull()) {
            editSummary.setText("");
            editSummary.setHint("Please enter your summary here.");
        } else {
            editSummary.setText(summary.getAsString());
        }
    }

    private void setDescription(JsonElement description) {
        if (description.isJsonNull()) {
            editDescription.setText("");
            editDescription.setHint("Please enter your description here.");
        } else {
            editDescription.setText(description.getAsString());
        }
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
                if(position != current_game_id) {
                    saveGame(position + 1);
                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> parent) {

            }
        });
    }

    private void saveGame(final int new_game_id) {
        JsonObject json = new JsonObject();
        json.addProperty("game_id", new_game_id);

        System.out.println(URLs.COACH_GAME_URL);
        Ion.with(this)
                .load("POST", URLs.COACH_GAME_URL)
                .setHeader("x-access-token", getToken())
                .setJsonObjectBody(json)
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
                            updateGame(new_game_id);
                            makeToaster(result.get("success").getAsString());
                        }
                    }
                });
    }

    private void makeToaster(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public void saveUsername(View view) {
        final String new_username = editUsername.getText().toString();
        createPostRequest(new_username, URLs.COACH_USERNAME_URL, "username", editUsername);
    }

    public void savePrice(View view) {
        String new_price = editPrice.getText().toString();
        createPostRequest(new_price, URLs.COACH_PRICE_URL, "price", editPrice);
    }

    public void saveSummary(View view) {
        String new_summary = editSummary.getText().toString();
        createPostRequest(new_summary, URLs.COACH_SUMMARY_URL, "summary", editSummary);
    }

    public void saveDescription(View view) {
        String new_description = editDescription.getText().toString();
        createPostRequest(new_description, URLs.COACH_DESCRIPTION_URL, "description", editDescription);
    }

    private void createPostRequest(String data, String url, String coach_key, EditText edit_text) {
        if (data.equals("")) {
            edit_text.setError(coach_key + " can't be empty");
        } else {
            JsonObject json = new JsonObject();
            json.addProperty(coach_key, data);

            System.out.println(url);
            Ion.with(this)
                    .load("POST", url)
                    .setHeader("x-access-token", getToken())
                    .setJsonObjectBody(json)
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
                                makeToaster(result.get("success").getAsString());
                            }
                        }
                    });
            updateCoachData(coach_key, data);
            edit_text.setText(data);
        }
    }

    private void updateCoachData(String key, String value) {
        JsonObject coach = getCoach();
        if (key.equals("price")) {
            int price = Integer.parseInt(value);
            coach.addProperty(key, price);
        } else {
            coach.addProperty(key, value);
        }
        storeCoach(coach.toString());
    }

    private void updateGame(int current_game_id) {
        JsonObject coach = getCoach();
        coach.addProperty("game_id", current_game_id);
        storeCoach(coach.toString());
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        return sharedPreferences.getString(getString(R.string.accessToken), null);
    }

    private void storeCoach(String coach) {
        SharedPreferences preferences = this.getSharedPreferences("coach", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.coach), coach);
        editor.apply();
    }

    public void chooseNewPicture(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");;
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JsonObject json = new JsonObject();
            json.addProperty("image", imageToString(bitmap));

            Ion.with(this)
                    .load("POST", URLs.COACH_IMAGE_URL)
                    .setHeader("x-access-token", getToken())
                    .setJsonObjectBody(json)
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
                                makeToaster(result.get("success").getAsString());
                                updateCoachData("img_url", result.get("img_url").getAsString());
                            }
                        }
                    });
            coachImageEdit.setImageBitmap(bitmap);
        }
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}
