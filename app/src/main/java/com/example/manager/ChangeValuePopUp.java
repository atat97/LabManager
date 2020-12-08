package com.example.manager;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChangeValuePopUp extends Activity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    DatabaseReference gases_ref = reference.child("Gases");

    Gas gas_in = new Gas();
    String key = "def";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changevaluepopup);

        EditText textName = findViewById(R.id.popup_name);
        EditText textValue = findViewById(R.id.popup_value);
        EditText textUser = findViewById(R.id.popup_user);
        EditText textLocation = findViewById(R.id.popup_location);
        TextView textAcqDate = findViewById(R.id.popup_acq_date);
        TextView textAcqDateName = findViewById(R.id.popup_acq_date_text);

        Button buttonUpdate = findViewById(R.id.button_update);
        Button buttonArchive = findViewById(R.id.button_archive);

        Intent intent = getIntent();
        if(intent.hasExtra("gas_in")){
            gas_in = intent.getParcelableExtra("gas_in");
            key = intent.getStringExtra("key");

            textName.setText(gas_in.getName());
            textValue.setText(gas_in.getValue());
            textUser.setText(gas_in.getUser());
            textLocation.setText(gas_in.getLocation());
            textAcqDate.setText(gas_in.getAcq_date());
        }else{
            buttonUpdate.setText(R.string.managepopup_add_button);
            ViewGroup layout = (ViewGroup) buttonArchive.getParent();
            layout.removeView(buttonArchive);
            layout = (ViewGroup) textAcqDate.getParent();
            layout.removeView(textAcqDate);
            layout = (ViewGroup)  textAcqDateName.getParent();
            layout.removeView(textAcqDateName);
        }


        buttonArchive.setOnClickListener(v -> {
            //TODO: Check for empty strings
            Gas gas_out = new Gas(
                    textName.getText().toString(),
                    textValue.getText().toString(),
                    textUser.getText().toString(),
                    textLocation.getText().toString(),
                    gas_in.getAcq_date(),
                    new SimpleDateFormat("dd.MM.yyyy",Locale.getDefault()).format(new Date())
            );
            gases_ref.child(key).removeValue();
            reference.child("Archive").push().setValue(gas_out);
            finish();
        });

        buttonUpdate.setOnClickListener(v -> {
            //TODO: Check for empty strings
            Gas gas_out = new Gas(
                    textName.getText().toString(),
                    textValue.getText().toString(),
                    textUser.getText().toString(),
                    textLocation.getText().toString(),
                    "",
                    ""
            );
            gases_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Gas already in the database
                    if(snapshot.hasChild(key)){
                        gas_out.setAcq_date(gas_in.getAcq_date());
                        gases_ref.child(key).setValue(gas_out);
                    }else{
                        gas_out.setAcq_date(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date()));
                        gases_ref.push().setValue(gas_out);
                    }
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        });


    }

    private void displayAddGasLayout(){

    }

    private void displayEditGasLayout(){

    }

    private void addGas(){

    }

    private void editGas(){

    }

}
