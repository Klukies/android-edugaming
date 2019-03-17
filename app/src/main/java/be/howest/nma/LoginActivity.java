package be.howest.nma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import be.howest.nma.api.URLs;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameView;
    private EditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.usernameView = (EditText) findViewById(R.id.username);
        this.passwordView = (EditText) findViewById(R.id.password);
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        passwordView.onEditorAction(EditorInfo.IME_ACTION_DONE);
        JsonObject json = new JsonObject();
        json.addProperty("username", usernameView.getText().toString());
        json.addProperty("password", passwordView.getText().toString());

        if (validateUser()) {
            System.out.println(URLs.LOGIN_URL);

            Ion.with(this)
                    .load("POST", URLs.LOGIN_URL)
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
                                validateLogin(result);
                            }
                        }
                    });
        }
    }

    private void validateLogin(JsonObject result) {
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

    private void makeToaster(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    private boolean validateUser() {
        if (TextUtils.isEmpty(usernameView.getText().toString())) {
            usernameView.setError("Please fill in a username");
            return false;
        }
        if (TextUtils.isEmpty(passwordView.getText().toString())) {
            passwordView.setError("Please fill in a password");
            return false;
        }
        return true;
    }
}
