package com.cs407.lab5_milestone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    public static ArrayList<Notes> notes = new ArrayList<>();

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

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(name);

        ArrayList<String> displayNotes = new ArrayList<>();
        for (Notes note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s\n", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item, displayNotes);
        ListView notesListView = findViewById(R.id.list);
        notesListView.setAdapter(adapter);

        notesListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), NoteWritingActivity.class);
            intent.putExtra("noteId", i);
            startActivity(intent);
        });
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
            Intent intent = new Intent(this, NoteWritingActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}