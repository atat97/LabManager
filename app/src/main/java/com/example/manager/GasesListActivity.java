package com.example.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class GasesListActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    DatabaseReference gases_ref = reference.child("Gases");



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO: Jedno z dwóch:
        // Określona z góry ilość butli (na sztywno wpisane w bazie danych) -> poszczególne TextView dla każdej butli -> pobieranie wartości i zapisywanie nowych do bazy danych
        //      Jeśli to rozwiązanie to: Jakie gazy? Jakie wartości dla nich? (aktualnie: nazwa i wartość (napełnienie) w %)
        //      ((AKTUALNIE WPROWADZONE))
        // Swobodne dodawanie nowych butli -> pobieranie butli z bazy danych -> generowanie TextView dla każdej -> aktualizacja bazy - aktualizacja layoutu
        //      Dosyć trudne rozwiązanie ale umożliwia dodawanie butli "w locie"
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaseslist);

        LinearLayout gasesLinLayout = findViewById(R.id.gases_linear_layout);
        TableLayout tableLayout = new TableLayout(this);
        ProgressDialog progressDialog = new ProgressDialog(this);


        gases_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Read gases from database
                List<Gas> gasList = new ArrayList<>();
                for(int i = 0; i < snapshot.getChildrenCount(); i++){
                    gasList.add(snapshot.child(String.valueOf(i)).getValue(Gas.class));
                }

                //Display gases
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                tableLayout.setShrinkAllColumns(true);
                tableLayout.setStretchAllColumns(true);



                for(int i = 0; i < gasList.size(); i++){
                    displayGas(gasList, i, tableLayout);
                }

                gasesLinLayout.addView(tableLayout);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GasesListActivity.this, "Failed to read from database.", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        button_NO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GasesListActivity.this, ChangeValuePopUp.class));
            }
        });


         */
    }

    protected void displayGas(List<Gas> gasList, int i, TableLayout tableLayout){
        //First row: NAME & VALUE
        TableRow rowNameAndValue = new TableRow(GasesListActivity.this);
        TextView textName = new TextView(GasesListActivity.this);
        textName.setText(gasList.get(i).getName());
        rowNameAndValue.addView(textName);
        TextView textValue = new TextView(GasesListActivity.this);
        textValue.setText(gasList.get(i).getValue());
        rowNameAndValue.addView(textValue);
        tableLayout.addView(rowNameAndValue);

        //Second row: USER
        TableRow rowUser = new TableRow(GasesListActivity.this);
        TextView textUser = new TextView(GasesListActivity.this);
        textUser.setText(gasList.get(i).getUser());
        rowUser.addView(textUser);
        tableLayout.addView(rowUser);

        //Third row: LOCATION
        TableRow rowLocation = new TableRow(GasesListActivity.this);
        TextView textLocation = new TextView(GasesListActivity.this);
        textLocation.setText(gasList.get(i).getLocation());
        rowLocation.addView(textLocation);
        tableLayout.addView(rowLocation);

        //Fourth row: ACQUISITION DATE
        TableRow rowAcqDate = new TableRow(GasesListActivity.this);
        TextView textAcqDate = new TextView(GasesListActivity.this);
        textAcqDate.setText(gasList.get(i).getAcq_date());
        rowAcqDate.addView(textAcqDate);
        tableLayout.addView(rowAcqDate);

        //Fifth row: EDIT BUTTON
    }
}