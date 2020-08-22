package com.example.presentacionequipo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnForm;
    Button btnIntegrants;
    Button btnAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnForm = (Button) findViewById(R.id.btnForm);
        btnIntegrants = (Button) findViewById(R.id.btnIntegrants);
        btnAge = (Button) findViewById(R.id.btnAge);
    }

    public void showForm(View view) {
        Intent callForm = new Intent(this, FormActivity.class);
        startActivity(callForm);
    }

    public void showDatePicker(View view) {
        Intent callAge = new Intent(this, AgeActivity.class);
        startActivity(callAge);
    }
}