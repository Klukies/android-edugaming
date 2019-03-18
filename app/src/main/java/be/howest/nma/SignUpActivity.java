package be.howest.nma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.howest.nma.api.URLs;

public class SignUpActivity extends AppCompatActivity {
    private EditText usernameView;
    private EditText emailView;
    private EditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernameView = (EditText) findViewById(R.id.username);
        emailView = (EditText) findViewById(R.id.email);
        passwordView = (EditText) findViewById(R.id.password);
    }

    public void backToLogin(View view) {
        finish();
    }

    public void signUp(View view) {
        passwordView.onEditorAction(EditorInfo.IME_ACTION_DONE);
        JsonObject json = new JsonObject();
        json.addProperty("username", usernameView.getText().toString());
        json.addProperty("email", emailView.getText().toString());
        json.addProperty("password", passwordView.getText().toString());

        if (validateUser()) {
            System.out.println(URLs.REGISTER_URL);

            Ion.with(this)
                    .load("POST", URLs.REGISTER_URL)
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
                                validateRegister(result);
                            }
                        }
                    });
        }
    }

    private void validateRegister(JsonObject result) {
        if (result.has("error")) {
            makeToaster(result.get("error").toString());
        } else {
            storeToken(result.get("accessToken").toString());
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }
    }

    private void storeToken(String accessToken) {
        SharedPreferences preferences = this.getSharedPreferences("authentication", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.accessToken), accessToken);
        editor.apply();
    }

    private boolean validateUser() {
        if (TextUtils.isEmpty(usernameView.getText().toString())) {
            usernameView.setError("Please fill in a username");
            return false;
        }
        if (!validateEmail(emailView.getText().toString())) {
            return false;
        }
        if (TextUtils.isEmpty(passwordView.getText().toString())) {
            passwordView.setError("Please fill in a password");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = ((Pattern) pattern).matcher(email);
        return matcher.matches();
    }

    private void makeToaster(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
