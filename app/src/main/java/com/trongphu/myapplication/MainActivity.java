package com.trongphu.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse("content://com.ddona.hello.provider.EngineerProvider/engineer");
        ContentValues values = new ContentValues();
        values.put("name", "phunt");
        values.put("gen", "3559123   ");
        values.put("single_id", "phunt");
        getContentResolver().insert(uri, values);

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String gen = cursor.getString(2);
                String single_id = cursor.getString(3);
                Log.d("phunt", "query" + id + "-" + name + " - " + gen + "- " + single_id);
            }
        }
    }
}
