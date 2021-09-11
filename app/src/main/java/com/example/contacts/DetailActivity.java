package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    ContactModel contactModel;
    AppCompatButton numberButton;
    TextView nameTextView;
    ImageButton imageButton;
    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageButton = findViewById(R.id.detailsImageButtonId);
        numberButton = findViewById(R.id.detailsNumberButton);
        nameTextView = findViewById(R.id.detailTextViewId);
        contactModel = (ContactModel) getIntent().getSerializableExtra("CONTACT");


        numberButton.setText(contactModel.getNumber());
        nameTextView.setText(contactModel.getName());
        numberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + numberButton.getText().toString()));
                if (ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                } else {
                    startActivity(intent);
                }
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + numberButton.getText().toString()));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.operation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.renameMenuId) {
            String name = nameTextView.getText().toString();
            String number = numberButton.getText().toString();
            ContactModel contactModel1 = new ContactModel(name, number);
            Intent intent = new Intent(DetailActivity.this, AddActivity.class);
            intent.putExtra("model", contactModel1);
            startActivity(intent);
        } else if (item.getItemId() == R.id.deleteMenuId) {
            DatabaseSource databaseSource = new DatabaseSource(this);

            //write alertDialogue code here
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Alert");
            builder.setMessage("Do you want to delete?");
            builder.setIcon(R.drawable.ic_baseline_delete_24);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean status = databaseSource.delete(contactModel);
                    if (status) {
                        Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivity.this, "Deleting failed", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    //finish();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.setCancelable(false);
            AlertDialog alert=builder.create();
            alert.setTitle("Delete contact");
            alert.show();


        } else if (item.getItemId() == R.id.copyMenuId) {

            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("phoneNumber", numberButton.getText().toString());
            clipboard.setPrimaryClip(data);
            Toast.makeText(this, "copied to clipboard", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}