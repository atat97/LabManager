package com.example.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChangeValuePopUp extends Activity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    DatabaseReference gases_ref = reference.child("Gases");

    Gas gas_in = new Gas();
    int id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changevaluepopup);
        TableLayout tableLayout = findViewById(R.id.change_value_tableLayout);

        TextView textName = findViewById(R.id.popup_name);
        TextView textValue = findViewById(R.id.popup_value);
        TextView textUser = findViewById(R.id.popup_user);
        TextView textLocation = findViewById(R.id.popup_location);
        TextView textAcqDate = findViewById(R.id.popup_acq_date);

        Button buttonUpdate = findViewById(R.id.button_update);
        Button buttonArchive = findViewById(R.id.button_archive);

        Intent intent = getIntent();
        if(intent.hasExtra("gas_in")){
            gas_in = intent.getParcelableExtra("gas_in");
            id = intent.getIntExtra("id", 0);

            textName.setText(gas_in.getName());
            textValue.setText(gas_in.getValue());
            textUser.setText(gas_in.getUser());
            textLocation.setText(gas_in.getLocation());
            textAcqDate.setText(gas_in.getAcq_date());
        }else{
            //TODO: Change the button text to "add"
            buttonUpdate.setText("Add");


            gases_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    id = (int)snapshot.getChildrenCount();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }




        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Check for empty strings
                Gas gas_out = new Gas();
                gas_out.setName(textName.getText().toString());
                gas_out.setValue(textValue.getText().toString());
                gas_out.setUser(textUser.getText().toString());
                gas_out.setLocation(textLocation.getText().toString());
                if(textAcqDate.getText().equals("")){
                    String date = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
                    gas_out.setAcq_date(date);
                }


                gases_ref.child(String.valueOf(id)).setValue(gas_out);
                finish();

            }
        });


    }
}
