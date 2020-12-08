package com.example.manager;

import android.content.Intent;
import android.os.Bundle;
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

public class GasesListActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    DatabaseReference gases_ref = reference.child("Gases");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaseslist);

        LinearLayout gasesLinLayout = findViewById(R.id.gases_linear_layout);
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        gases_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Clear the view
                gasesLinLayout.removeAllViews();
                tableLayout.removeAllViews();
                //Read gases from database
                Map<String, Gas> gasMap = new HashMap<>();
                for(DataSnapshot snap : snapshot.getChildren()){
                    gasMap.put(snap.getKey(),snap.getValue(Gas.class));
                }
                //Display gases
                for(String key : gasMap.keySet()){
                    Gas gas = gasMap.get(key);
                    displayGas(gas, key,tableLayout);
                }
                //Add the table layout to the view
                gasesLinLayout.addView(tableLayout);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GasesListActivity.this, "Failed to read from database.", Toast.LENGTH_SHORT).show();
            }
        });

        Button add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(v -> startActivity(new Intent(GasesListActivity.this, ChangeValuePopUp.class)));

        Button logout_button = findViewById(R.id.logout_button);
        logout_button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            finish();
        });
    }



    private void displayGas(Gas gas, String key, TableLayout tableLayout){
        //First row: NAME & VALUE
        TableRow rowNameAndValue = new TableRow(GasesListActivity.this);
        TextView textName = new TextView(GasesListActivity.this);
        textName.setText(gas.getName());
        rowNameAndValue.addView(textName);
        TextView textValue = new TextView(GasesListActivity.this);
        textValue.setText(gas.getValue());
        rowNameAndValue.addView(textValue);
        tableLayout.addView(rowNameAndValue);

        //Second row: USER
        TableRow rowUser = new TableRow(GasesListActivity.this);
        TextView textUser = new TextView(GasesListActivity.this);
        textUser.setText(gas.getUser());
        rowUser.addView(textUser);
        tableLayout.addView(rowUser);

        //Third row: LOCATION
        TableRow rowLocation = new TableRow(GasesListActivity.this);
        TextView textLocation = new TextView(GasesListActivity.this);
        textLocation.setText(gas.getLocation());
        rowLocation.addView(textLocation);
        tableLayout.addView(rowLocation);

        //Fourth row: ACQUISITION DATE
        TableRow rowAcqDate = new TableRow(GasesListActivity.this);
        TextView textAcqDate = new TextView(GasesListActivity.this);
        textAcqDate.setText(gas.getAcq_date());
        rowAcqDate.addView(textAcqDate);
        tableLayout.addView(rowAcqDate);

        //Fifth row: EDIT BUTTON
        TableRow rowButton = new TableRow(GasesListActivity.this);
        Button manageButton = new Button(GasesListActivity.this);
        manageButton.setText(R.string.manage_gas_button);


        manageButton.setOnClickListener(v ->
                startActivity(new Intent(GasesListActivity.this, ChangeValuePopUp.class).putExtra("gas_in", gas).putExtra("key", key)));
        rowButton.addView(manageButton);
        tableLayout.addView(rowButton);
    }
}