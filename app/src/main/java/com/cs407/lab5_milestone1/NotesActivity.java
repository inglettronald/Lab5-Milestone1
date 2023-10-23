package com.cs407.lab5_milestone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class NotesActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        preferences = getSharedPreferences("lab5_milestone1", Context.MODE_PRIVATE);

        TextView welcome = findViewById(R.id.welcome);
        String name = preferences.getString("username", "");

        if (name.isEmpty()) {
            welcome.setText("Welcome to notes app!");
        } else {
            welcome.setText("Welcome " + name + " to notes app!");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            preferences.edit().clear().apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.addNote) {
            // TODO
        }

        return super.onOptionsItemSelected(item);
    }

}