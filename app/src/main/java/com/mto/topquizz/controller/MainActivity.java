package com.mto.topquizz.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.mto.topquizz.R;
import com.mto.topquizz.model.User;

public class MainActivity extends AppCompatActivity {

    private TextView textWelcome;
    private EditText inputName;
    private Button validate;
    private final User user = new User();

    public static final int GAME_ACTIVITY_REQUEST_CODE = 59;
    private SharedPreferences preferences;
    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREV_KEY_FIRSTNAME = "PREV_KEY_FIRSTNAME";
    public static final String BUTTON_STATE = "buttonState";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {

            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            preferences.edit().putInt(PREF_KEY_SCORE, score).apply();

            loadUser();

        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putBoolean(BUTTON_STATE, validate.isEnabled());

        super.onSaveInstanceState(outState);

    }

    protected void loadUser() {

        if(!preferences.getAll().get(PREV_KEY_FIRSTNAME).equals("")) {

            System.out.println("===DEBUG=== ENTERED IN FUNCTION loadUser() ===DEBUG===");
            System.out.println("===DEBUG=== USER IN MEMORY: " +
                    preferences.getAll().get(PREV_KEY_FIRSTNAME) +
                    " ===DEBUG===");

            user.setFirstName((String) preferences.getAll().get(PREV_KEY_FIRSTNAME));
            textWelcome.setText("Welcome back " + user.getFirstName() + " !\n Your previous score was: " + preferences.getAll().get(PREF_KEY_SCORE));

            System.out.println("===DEBUG=== USER CREATED FROM MEMORY: "
                    + user.getFirstName() +
                    " ===DEBUG===");

            inputName.setHint("New user ?");
            inputName.setText("");
            validate.setEnabled(true);

        } else {

            validate.setEnabled(false);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MediaPlayer game_start = MediaPlayer.create(MainActivity.this, R.raw.game_start);

        super.onCreate(savedInstanceState);
        // When activity is create, load the activity_main.xml
        setContentView(R.layout.activity_main);

        // Initialize memory access
        // MODE_PRIVATE : ACCESS FROM OUR APPLICATION ONLY
        preferences = getPreferences(MODE_PRIVATE);

        preferences.getAll().clear();

        // References graphic elements
        textWelcome = findViewById(R.id.textWelcome);
        inputName = findViewById(R.id.inputName);
        validate = findViewById(R.id.validate);

        loadUser();

        inputName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // If character in textInputName length is different than 0, enable click on button
                validate.setEnabled(charSequence.toString().length() != 0);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        validate.setOnClickListener(new View.OnClickListener() {

            // This method is called everytime the user click on the button
            @Override
            public void onClick(View view) {

                // User click button = create user + show next view(activity)
                // Intent takes current view as first parameter & class of next view as second parameter

                if(!(inputName.getText().equals(user.getFirstName()))) {

                    user.setFirstName(inputName.getText().toString());

                    System.out.println("===DEBUG=== NEW USER CREATED: " +
                            user.getFirstName() + " ===DEBUG===");

                    preferences.edit().putString(PREV_KEY_FIRSTNAME, user.getFirstName()).apply();

                    System.out.println("===DEBUG=== NEW USER CREATED IN MEMORY: " +
                            preferences.getAll().get(PREV_KEY_FIRSTNAME) +
                            " ===DEBUG===");

                }

                game_start.start();

                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);

            }

        });

    }
}