package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseSource databaseSource;
    ArrayList<ContactModel> arrayList;
    FloatingActionButton floatButton;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatButton = findViewById(R.id.floatingButtonId);
        EditText editText=findViewById(R.id.editTextId);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        recyclerView = findViewById(R.id.recyclerViewId);
        databaseSource = new DatabaseSource(this);
        arrayList = databaseSource.getAllContact();
        //sort the arraylist here
        Collections.sort(arrayList);
        contactAdapter = new ContactAdapter(this, arrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(contactAdapter);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }
    public void filter(String s){
        ArrayList<ContactModel> filteredList=new ArrayList<>();

        for(ContactModel model:arrayList){
            if(model.getName().toLowerCase().startsWith(s.toLowerCase())){
                filteredList.add(model);
            }
        }
        contactAdapter.filterList(filteredList);
    }

}