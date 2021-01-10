package com.example.manager;

import android.annotation.SuppressLint;
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

public class GasesListActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    DatabaseReference inUse_ref = reference.child("InUse");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaseslist);
        getSupportActionBar().hide();

        LinearLayout gasesLinLayout = findViewById(R.id.gases_linear_layout);
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);


        inUse_ref.addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(GasesListActivity.this, "Nie udało się odczytać bazy danych.", Toast.LENGTH_SHORT).show();
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



    @SuppressLint("SetTextI18n")
    private void displayGas(Gas gas, String key, TableLayout tableLayout){
        //First row: NAME & VALUE
        TableRow rowName = new TableRow(GasesListActivity.this);
        TextView textName = new TextView(GasesListActivity.this);
        textName.setText("Nazwa - " + gas.getName());
        textName.setGravity(Gravity.CENTER);
        rowName.addView(textName);
        tableLayout.addView(rowName);

        //Second row: VALUE
        TableRow rowValue = new TableRow(GasesListActivity.this);
        TextView textValue = new TextView(GasesListActivity.this);
        textValue.setText("Wartość - " + gas.getValue());
        textValue.setGravity(Gravity.CENTER);
        rowValue.addView(textValue);
        tableLayout.addView(rowValue);

        //Third row: USER
        TableRow rowUser = new TableRow(GasesListActivity.this);
        TextView textUser = new TextView(GasesListActivity.this);
        textUser.setText("Użytkownik - " + gas.getUser());
        textUser.setGravity(Gravity.CENTER);
        rowUser.addView(textUser);
        tableLayout.addView(rowUser);

        //Fourth row: LOCATION
        TableRow rowLocation = new TableRow(GasesListActivity.this);
        TextView textLocation = new TextView(GasesListActivity.this);
        textLocation.setText("Lokalizacja - " + gas.getLocation());
        textLocation.setGravity(Gravity.CENTER);
        rowLocation.addView(textLocation);
        tableLayout.addView(rowLocation);

        //Fifth row: ACQUISITION DATE
        TableRow rowAcqDate = new TableRow(GasesListActivity.this);
        TextView textAcqDate = new TextView(GasesListActivity.this);
        textAcqDate.setText("Data dodania - " + gas.getAcq_date());
        textAcqDate.setGravity(Gravity.CENTER);
        rowAcqDate.addView(textAcqDate);
        tableLayout.addView(rowAcqDate);

        //Sixth row: EDIT BUTTON
        TableRow rowButton = new TableRow(GasesListActivity.this);
        Button manageButton = new Button(GasesListActivity.this);
        manageButton.setText(R.string.manage_gas_button);

        manageButton.setOnClickListener(v ->
                startActivity(new Intent(GasesListActivity.this, ChangeValuePopUp.class).putExtra("gas_in", gas).putExtra("key", key)));
        rowButton.addView(manageButton);
        tableLayout.addView(rowButton);
    }
}