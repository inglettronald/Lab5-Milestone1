package com.cs407.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteWritingActivity extends AppCompatActivity {

    private int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_writing);
        EditText text = findViewById(R.id.noteContent);
        noteId = getIntent().getIntExtra("noteId", -1);

        if (noteId != -1) {
            Notes note = NotesActivity.notes.get(noteId);
            String content = note.getContent();
            text.setText(content);
        }

        Button delete = findViewById(R.id.delete);
        Button save = findViewById(R.id.save);
        delete.setOnClickListener(this::delete);
        save.setOnClickListener(this::save);
    }

    private void save(View view) {
        String newContent = ((EditText)findViewById(R.id.noteContent)).getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences preferences = getSharedPreferences("lab5_milestone1", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/DD/YYYY HH:mm:ss");
        String date = dateFormat.format(new Date());
        Log.i("Info", "Printing noteId before using in condition: " + noteId);
        if (noteId == -1) {
            title = "NOTES_" + NotesActivity.notes.size();
            Log.i("Info", "printing content to be saved: " + newContent);
            dbHelper.saveNotes(username, title, date, newContent);
        } else {
            title = "NOTES_" + (noteId + 1);
            Log.i("Info", "Printing noteId from update condition: " + noteId);
            dbHelper.updateNotes(newContent, date, title, username);
        }

        navigateBack(view);
    }

    private void delete(View view) {
        String newContent = ((EditText)findViewById(R.id.noteContent)).getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences preferences = getSharedPreferences("lab5_milestone1", Context.MODE_PRIVATE);
        // why do we need this?
        String username = preferences.getString("username", "");
        String title = "NOTES_" + (noteId + 1);

        dbHelper.deleteNotes(newContent, title);

        navigateBack(view);
    }

    private void navigateBack(View view) {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }
}