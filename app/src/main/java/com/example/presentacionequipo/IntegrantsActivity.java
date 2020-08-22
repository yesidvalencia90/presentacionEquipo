package com.example.presentacionequipo;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class IntegrantsActivity extends AppCompatActivity {

    private static final String TAG = "IntegrantsActivity";
    DatabaseHelper mDatabaseHelper;
    private ListView integrantsList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integrants_list);
        integrantsList = (ListView) findViewById(R.id.integrantsList);
        mDatabaseHelper = new DatabaseHelper(this);
        populateIntegratnsList();
    }

    private void populateIntegratnsList() {
        Log.d(TAG, "Display integrants in the ListView");
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> integrantsListView = new ArrayList<>();
        while(data.moveToNext()){
            integrantsListView.add(data.getString(1) + " " + data.getString(2) );
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, integrantsListView);
        integrantsList.setAdapter(adapter);
    }
}
