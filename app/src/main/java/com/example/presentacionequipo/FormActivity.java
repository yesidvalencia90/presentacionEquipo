package com.example.presentacionequipo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.*;

public class FormActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText phone;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
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

        if(phone.getText().toString().length() < 10){
            phone.setError("Por favor ingresa un numero valido");
        }else{
            insertPhone = phone.getText().toString();
        }

        if(insertName != "" && insertLastName != "" && insertPhone != ""){
            addData(insertName, insertLastName, insertPhone);
            name.setText("");
            lastName.setText("");
            phone.setText("");
        }else{
            toastMessage("Algo salio mal, por favor intentalo de nuevo.");
        }
    }

    public void addData(String name, String lastName, String phone){
        boolean insertData = mDatabaseHelper.addData(name, lastName, phone);
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