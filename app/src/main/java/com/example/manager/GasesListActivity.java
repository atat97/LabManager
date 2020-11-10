package com.example.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GasesListActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference gases = database.getReference().child("Gases").child("NO2");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaseslist);
        gases.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(GasesListActivity.this, "Key: " + snapshot.getKey() + "Value: " + snapshot.getValue() , Toast.LENGTH_SHORT).show();
            /*
            for (int i = 0; i < values.size(); i++){
                String value = values.get(i).toString();
                Toast.makeText(GasesListActivity.this, value , Toast.LENGTH_SHORT).show();
            }

             */

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GasesListActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

    }


}