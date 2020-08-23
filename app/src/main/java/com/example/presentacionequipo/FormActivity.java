package com.example.presentacionequipo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.widget.Toast.*;

public class FormActivity extends AppCompatActivity {

    private static final String TAG = "FormActivity";
    private EditText name;
    private EditText lastName;
    private EditText cc;
    private EditText phone;
    private DatabaseHelper mDatabaseHelper;
    private int SELECT_PHOTO = 1;
    private Uri uri;
    private ImageView imageView;
    private EditText selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        cc = findViewById(R.id.cc);
        phone = findViewById(R.id.phone);
        Button selectImage = findViewById(R.id.selectImage);
        imageView = findViewById(R.id.profileImage);
        selectedImage = findViewById(R.id.selectedImage);
        mDatabaseHelper = new DatabaseHelper(this);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, SELECT_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO
            && resultCode == RESULT_OK
            && data != null
            && data.getData() != null){
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
                selectedImage.setText(bitMapToString(bitmap));
                Log.d(TAG, "Image: " + bitmap.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        String insertImage = "";

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

        if(!selectedImage.getText().toString().isEmpty()){
            insertImage = selectedImage.getText().toString();
        }

        if(insertName != "" && insertLastName != "" && insertPhone != "" && insertCc != ""){
            Boolean integrantExists = validateIdentification(insertCc);
            if(integrantExists == true){
                toastMessage("Ya existe un Integrante con esta identificacion, por favor verifica los datos.");
                cc.setText("");
                cc.requestFocus();
            }else {
                addData(insertName, insertLastName, insertCc, insertPhone, insertImage);
                name.setText("");
                lastName.setText("");
                cc.setText("");
                phone.setText("");
                name.requestFocus();
                imageView.setImageResource(0);
                selectedImage.setText("");
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

    public void addData(String name, String lastName, String cc, String phone, String img){
        boolean insertData = mDatabaseHelper.addData(name, lastName, cc, phone, img);
        if(insertData){
            toastMessage("El Integrate fue creado correctamente!");
        }else{
            toastMessage("Hubo un error creando el integrante, por favor intenta de nuevo!");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, LENGTH_SHORT).show();
    }

    public String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}