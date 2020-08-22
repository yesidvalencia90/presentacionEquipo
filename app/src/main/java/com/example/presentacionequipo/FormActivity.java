package com.example.presentacionequipo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.*;

public class FormActivity extends AppCompatActivity {

    EditText name;
    EditText lastName;
    EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
    }

    public void cancelForm(View view) {
        Intent callForm = new Intent(this, MainActivity.class);
        startActivity(callForm);
    }

    public void saveForm(View view) {

        if(!name.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "name = " + name.getText(), Toast.LENGTH_LONG).show();
        }else{
            name.setError("Por favor ingresa el Nombre");
        }

        if(!lastName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "name = " + lastName.getText(), Toast.LENGTH_LONG).show();
        }else{
            lastName.setError("Por favor ingresa el Apellido");
        }

        if(phone.getText().toString().length() < 10){
            phone.setError("Por favor ingresa un numero valido");
        }else{
            //
        }
    }
}