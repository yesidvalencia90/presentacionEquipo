package com.example.presentacionequipo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.*;

public class FormActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText cc;
    private EditText phone;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        cc = findViewById(R.id.cc);
        phone = findViewById(R.id.phone);
        mDatabaseHelper = new DatabaseHelper(this);
    }

    public void cancelForm(View view) {
        Intent callForm = new Intent(this, MainActivity.class);
        startActivity(callForm);
    }

    public void saveForm(View view) {

        String insertName = "";
        String insertLastName = "";
        String insertPhone = "";
        String insertCc = "";

        if(!name.getText().toString().isEmpty()) {
            insertName = name.getText().toString();
        }else{
            name.setError("Por favor ingresa el Nombre");
        }

        if(!lastName.getText().toString().isEmpty()) {
            insertLastName = lastName.getText().toString();
        }else{
            lastName.setError("Por favor ingresa el Apellido");
        }

        if(!cc.getText().toString().isEmpty()) {
            insertCc = cc.getText().toString();
        }else{
            cc.setError("Por favor ingresa una Identificacion");
        }

        if(phone.getText().toString().length() < 10){
            phone.setError("Por favor ingresa un numero valido");
        }else{
            insertPhone = phone.getText().toString();
        }

        if(insertName != "" && insertLastName != "" && insertPhone != "" && insertCc != ""){
            Boolean integrantExists = validateIdentification(insertCc);
            if(integrantExists == true){
                toastMessage("Ya existe un Integrante con esta identificacion, por favor verifica los datos.");
                cc.setText("");
                cc.requestFocus();
            }else {
                addData(insertName, insertLastName, insertCc, insertPhone);
                name.setText("");
                lastName.setText("");
                cc.setText("");
                phone.setText("");
                name.requestFocus();
            }
        }/*else{
            toastMessage("Algo salio mal, por favor intentalo de nuevo.");
        }*/
    }

    private Boolean validateIdentification(String cc) {
        Cursor ccOnTable = mDatabaseHelper.getIntegrantByIdentification(cc);
        Boolean exists = false;
        while(ccOnTable.moveToNext()){
            if(Integer.parseInt(ccOnTable.getString(3)) == Integer.parseInt(cc)){
                exists = true;
                //toastMessage("HERE: " + ccOnTable.getString(0) + " CC: " + cc + " Exists: " + exists);
            }
        }
        return exists;
    }

    public void addData(String name, String lastName, String cc, String phone){
        boolean insertData = mDatabaseHelper.addData(name, lastName, cc, phone);
        if(insertData){
            toastMessage("El Integrate fue creado correctamente!");
        }else{
            toastMessage("Hubo un error creando el integrante, por favor intenta de nuevo!");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, LENGTH_SHORT).show();
    }
}