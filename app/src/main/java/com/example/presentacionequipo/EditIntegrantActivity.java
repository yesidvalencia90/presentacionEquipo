package com.example.presentacionequipo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditIntegrantActivity extends AppCompatActivity {

    private static final String TAG = "EditIntegrantActivity";
    DatabaseHelper mDatabaseHelper;
    private int selectedID;
    private TextView selectedName;
    private TextView selectedIden;
    private TextView phoneSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_integrant_layout);
        mDatabaseHelper = new DatabaseHelper(this);

        selectedName = findViewById(R.id.selectedName);
        selectedIden = findViewById(R.id.selectedIden);
        phoneSelected = findViewById(R.id.phoneSelected);

        Intent getExtras = getIntent();
        selectedID = getExtras.getIntExtra("id", -1);
        if(selectedID > -1){
            Cursor integrant = mDatabaseHelper.getIntegrantById(selectedID);
            populateIntegrantInfo(integrant);
        }else {
            toastMessage("Ocurrio un error.");
            Intent getBackToIntegrants = new Intent(EditIntegrantActivity.this, IntegrantsActivity.class);
            startActivity(getBackToIntegrants);
        }
    }

    private void populateIntegrantInfo(Cursor integrant) {
        while(integrant.moveToNext()){
            Log.d(TAG, "Nombre: " + integrant.getString(1)
                    + " APELLIDO: " + integrant.getString(2) + " CC: "
                    + integrant.getString(3) + " TELEFONO: "
                    + integrant.getString(4) + " FOTO "
                    + integrant.getString(5));
            selectedName.setText(integrant.getString(1) + " " + integrant.getString(2));
            selectedIden.setText(integrant.getString(3));
            phoneSelected.setText(integrant.getString(4));
            selectedName.setVisibility(View.VISIBLE);
            selectedIden.setVisibility(View.VISIBLE);
            phoneSelected.setVisibility(View.VISIBLE);
        }
    }

    public void backToIntegrats(View view) {
        Intent getBackToIntegrants = new Intent(EditIntegrantActivity.this, IntegrantsActivity.class);
        startActivity(getBackToIntegrants);
    }

    public void deleteIntegrant(View view) {
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
