package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class AddActivity extends AppCompatActivity {

    EditText nameEditText, phoneEditText;
    DatabaseSource databaseSource;
    ContactModel contactModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseSource = new DatabaseSource(this);
        nameEditText = findViewById(R.id.nameEditTextId);
        phoneEditText = findViewById(R.id.phoneEditTextId);

        contactModel = (ContactModel) getIntent().getSerializableExtra("model");
        if (contactModel != null) {
            nameEditText.setText(contactModel.getName());
            phoneEditText.setText(contactModel.getNumber());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.saveId) {

            if (contactModel != null) {
                String updateName=nameEditText.getText().toString();
                String updateNumber=phoneEditText.getText().toString();
                ContactModel updateModel=new ContactModel(updateName,updateNumber);
                boolean updateStatus=databaseSource.update(updateModel);
                if(updateStatus){
                    Toast.makeText(this, "updated!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Updating failed", Toast.LENGTH_SHORT).show();
                }
                Intent intent=new Intent(this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                if (name.equals("")) {
                    nameEditText.setError("Insert name");
                    nameEditText.requestFocus();
                } else if (phone.equals("")) {
                    phoneEditText.setError("Insert Phone number");
                } else {
                    ContactModel contactModel = new ContactModel(name, phone);
                    boolean status = databaseSource.addContact(contactModel);
                    if (status) {
                        Toast.makeText(this, "Saved ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error!!!", Toast.LENGTH_SHORT).show();
                    }
                    nameEditText.setText("");
                    phoneEditText.setText("");
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    //finish();
                }

            }
        }
        return super.onOptionsItemSelected(item);
    }
}