package com.example.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeValuePopUp extends Activity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    DatabaseReference gases_ref = reference.child("Gases");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changevaluepopup);

        Intent intent = getIntent();
        Gas gas = intent.getParcelableExtra("gas");
        int id = intent.getIntExtra("id", 0);

        TextView textName = findViewById(R.id.popup_name);
        TextView textValue = findViewById(R.id.popup_value);
        TextView textUser = findViewById(R.id.popup_user);
        TextView textLocation = findViewById(R.id.popup_location);
        TextView textAcqDate = findViewById(R.id.popup_acq_date);

        Button buttonUpdate = findViewById(R.id.button_update);
        Button buttonArchive = findViewById(R.id.button_archive);

        textName.setText(gas.getName());
        textValue.setText(gas.getValue());
        textUser.setText(gas.getUser());
        textLocation.setText(gas.getLocation());
        textAcqDate.setText(gas.getAcq_date());

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gas.setName(textName.getText().toString());
                gas.setValue(textValue.getText().toString());
                gas.setUser(textUser.getText().toString());
                gas.setLocation(textLocation.getText().toString());
                gas.setAcq_date(textAcqDate.getText().toString());
                //Crash point Alpha :((((
                gases_ref.child(String.valueOf(id)).setValue(gas);
                finish();
            }
        });


    }
}
