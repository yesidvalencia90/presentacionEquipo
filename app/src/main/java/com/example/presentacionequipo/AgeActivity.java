package com.example.presentacionequipo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;
import java.util.TimeZone;

public class AgeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button datePicker;
    private TextView dateOfBrith;
    public static final String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Deciembre"};

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);

        datePicker = findViewById(R.id.datePicker);
        dateOfBrith = findViewById(R.id.selectedDate);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        dateOfBrith.setVisibility(View.VISIBLE);
        String age = getAge(year, month, day);
        dateOfBrith.setText("Tu fecha de nacimiento es: " + months[(month+1)] + " " + day + " del " + year);
        Toast.makeText(getApplicationContext(), "Tu edad es: " + age +" años", Toast.LENGTH_LONG).show();
    }

    private String getAge(int year, int month, int day){

        Calendar dateOfBith = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dateOfBith.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dateOfBith.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dateOfBith.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        Integer ageInt = new Integer(age);
        String currentAge = ageInt.toString();
        return currentAge;
    }

    public void backToMain(View view) {
        Intent backToMain = new Intent(this, MainActivity.class);
        startActivity(backToMain);
    }
}