package com.example.presentacionequipo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditIntegrantActivity extends AppCompatActivity {

    private static final String TAG = "EditIntegrantActivity";
    DatabaseHelper mDatabaseHelper;
    private int selectedID;
    private TextView selectedName;
    private TextView selectedIden;
    private TextView phoneSelected;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_integrant_layout);
        mDatabaseHelper = new DatabaseHelper(this);

        selectedName = findViewById(R.id.selectedName);
        selectedIden = findViewById(R.id.selectedIden);
        phoneSelected = findViewById(R.id.phoneSelected);
        imageView = findViewById(R.id.profileImage);

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
            Bitmap profileImage = stringToBitMap(integrant.getString(5));
            Log.d(TAG, "Profile Picture: " + profileImage);
            imageView.setImageBitmap(profileImage);
        }
    }

    public void backToIntegrats(View view) {
        Intent getBackToIntegrants = new Intent(EditIntegrantActivity.this, IntegrantsActivity.class);
        startActivity(getBackToIntegrants);
    }

    public void deleteIntegrant(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmDialogMessage)
                .setTitle(R.string.confirmDialogTitle)
                .setPositiveButton(R.string.confirmDelete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "ID: " + selectedID);
                        boolean deleted = mDatabaseHelper.deleteIntegrant(selectedID);
                        if (deleted == true){
                            toastMessage("El integrante fue eliminado correctamente.");
                        }else{
                            toastMessage("Hubo un error eliminando el integrante.");
                        }
                        Intent getBackToIntegrants = new Intent(EditIntegrantActivity.this, IntegrantsActivity.class);
                        startActivity(getBackToIntegrants);
                    }
                })
                .setNegativeButton(R.string.cancelDelete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // CANCEL
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public Bitmap stringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
