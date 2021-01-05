package com.example.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ArchiveActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    DatabaseReference archive_ref = reference.child("Archive");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        LinearLayout archiveLinLayout = findViewById(R.id.archive_linear_layout);
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        archive_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Clear the view
                archiveLinLayout.removeAllViews();
                tableLayout.removeAllViews();
                //Read gases from database
                Map<String, Gas> gasMap = new HashMap<>();
                for(DataSnapshot snap : snapshot.getChildren()){
                    gasMap.put(snap.getKey(),snap.getValue(Gas.class));
                }
                //Display gases
                for(String key : gasMap.keySet()){
                    Gas gas = gasMap.get(key);
                    displayGas(gas, key, tableLayout);
                }
                //Add the table layout to the view
                archiveLinLayout.addView(tableLayout);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ArchiveActivity.this, "Failed to read from database.", Toast.LENGTH_SHORT).show();
            }
        });

        Button logout_button = findViewById(R.id.logout_button);
        logout_button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            finish();
        });
    }



    private void displayGas(Gas gas, String key, TableLayout tableLayout){
        //First row: NAME & VALUE
        TableRow rowName = new TableRow(ArchiveActivity.this);
        TextView textName = new TextView(ArchiveActivity.this);
        textName.setText("Nazwa - " + gas.getName());
        textName.setGravity(Gravity.CENTER);
        rowName.addView(textName);
        tableLayout.addView(rowName);

        //Second row: VALUE
        TableRow rowValue = new TableRow(ArchiveActivity.this);
        TextView textValue = new TextView(ArchiveActivity.this);
        textValue.setText("Wartość - " + gas.getValue());
        textValue.setGravity(Gravity.CENTER);
        rowValue.addView(textValue);
        tableLayout.addView(rowValue);

        //Third row: USER
        TableRow rowUser = new TableRow(ArchiveActivity.this);
        TextView textUser = new TextView(ArchiveActivity.this);
        textUser.setText("Użytkownik - " + gas.getUser());
        textUser.setGravity(Gravity.CENTER);
        rowUser.addView(textUser);
        tableLayout.addView(rowUser);

        //Fourth row: LOCATION
        TableRow rowLocation = new TableRow(ArchiveActivity.this);
        TextView textLocation = new TextView(ArchiveActivity.this);
        textLocation.setText("Lokacja - " + gas.getLocation());
        textLocation.setGravity(Gravity.CENTER);
        rowLocation.addView(textLocation);
        tableLayout.addView(rowLocation);

        //Fifth row: ACQUISITION DATE
        TableRow rowAcqDate = new TableRow(ArchiveActivity.this);
        TextView textAcqDate = new TextView(ArchiveActivity.this);
        textAcqDate.setText("Data dodania - " + gas.getAcq_date());
        textAcqDate.setGravity(Gravity.CENTER);
        rowAcqDate.addView(textAcqDate);
        tableLayout.addView(rowAcqDate);

        //Sixth row: ARCHIVE DATE
        TableRow rowRetDate = new TableRow(ArchiveActivity.this);
        TextView textRetDate = new TextView(ArchiveActivity.this);
        textRetDate.setText("Data oddania - " + gas.getRet_date());
        textRetDate.setGravity(Gravity.CENTER);
        rowRetDate.addView(textRetDate);
        tableLayout.addView(rowRetDate);

        //Seventh row: SEPARATOR
        TableRow rowButton = new TableRow(ArchiveActivity.this);
        Button manageButton = new Button(ArchiveActivity.this);
        manageButton.setText("");
        rowButton.addView(manageButton);
        tableLayout.addView(rowButton);
    }
}