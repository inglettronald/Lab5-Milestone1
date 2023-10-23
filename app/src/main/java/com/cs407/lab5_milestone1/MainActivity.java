package com.cs407.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.login);
        login.setOnClickListener(this::onLogin);

        preferences = getSharedPreferences("lab5_milestone1", Context.MODE_PRIVATE);

        String oldUser = preferences.getString("username", "");
        // Someone is still logged in!
        if (!oldUser.isEmpty()) {
            Intent intent = new Intent(this, NotesActivity.class);
            startActivity(intent);
        }
    }

    protected void onLogin(View view) {
        EditText username = findViewById(R.id.username);
        preferences.edit().putString("username", username.getText().toString()).apply();

        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }
}