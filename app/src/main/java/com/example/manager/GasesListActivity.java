package com.example.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        /*
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaseslist);

        final TextView textView_NO2 = findViewById(R.id.textView_NO2);
        final TextView textView_NO2_value = findViewById(R.id.textView_NO2_value);
        final Button button_NO2 = findViewById(R.id.button_NO2);


        gases_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Read
                List<Gas> gasList = new ArrayList<>();
                for(int i = 0; i < snapshot.getChildrenCount(); i++){
                    gasList.add(snapshot.child(String.valueOf(i)).getValue(Gas.class));
                }
                textView_NO2.setText(gasList.get(0).getName());
                textView_NO2_value.setText(gasList.get(0).getValue());

                //textView_NO2.setText(snapshot.child("0").child("name").getValue().toString() + ": ");
                //textView_NO2_value.setText(snapshot.child("0").child("value").getValue().toString());

                //TextView textView = new TextView(getBaseContext());
                //textView.setText("Name: " + snapshot.child(String.valueOf(0)).child("name").getValue() + " Value: " + snapshot.child(String.valueOf(0)).child("value").getValue());
                //linearLayout.addView(textView);
                /*
                for(int i = 0; i < snapshot.getChildrenCount(); i++){



                    Toast.makeText(GasesListActivity.this, i + " Name: " + snapshot.child(String.valueOf(i)).child("name").getValue() + " Value: " + snapshot.child(String.valueOf(i)).child("amount").getValue() , Toast.LENGTH_SHORT).show();
                }
                */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GasesListActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

        button_NO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GasesListActivity.this, ChangeValuePopUp.class));
            }
        });

    }


}