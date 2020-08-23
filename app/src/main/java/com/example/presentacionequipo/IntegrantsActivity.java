package com.example.presentacionequipo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
            integrantsListView.add(data.getString(1) + " " + data.getString(2) + " - " + data.getString(3));
            //integrantsListView.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, integrantsListView);
        integrantsList.setAdapter(adapter);

        integrantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String identification = adapterView.getItemAtPosition(i).toString().split(" - ")[1];
                Cursor id = mDatabaseHelper.getIdByName(identification);
                int itemID = -1;
                while(id.moveToNext()){
                    itemID = id.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "Name: " + identification + " ID: " + itemID);
                    Intent editScreenIntent = new Intent(IntegrantsActivity.this, EditIntegrantActivity.class);
                    editScreenIntent.putExtra("id", itemID);
                    startActivity(editScreenIntent);
                }else{
                    toastMessage("No hay ningun ID asociado a este usuario!");
                }
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
