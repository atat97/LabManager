package com.example.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChangeValuePopUp extends Activity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference inUse_ref = reference.child("InUse");
    DatabaseReference moderators_ref = reference.child("Moderators");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String user_email = "";
    String admin_mail = "";
    String mod_mail = "";

    Gas gas_in = new Gas();
    String key = "def";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changevaluepopup);

        TextView textName = findViewById(R.id.popup_name);
        TextView textValue = findViewById(R.id.popup_value);
        TextView textUser = findViewById(R.id.popup_user);
        TextView textLocation = findViewById(R.id.popup_location);
        TextView textAcqDate = findViewById(R.id.popup_acq_date);
        //TextView textAcqDateName = findViewById(R.id.popup_acq_date_text);

        Button buttonUpdate = findViewById(R.id.button_update);
        Button buttonArchive = findViewById(R.id.button_archive);

        //READ ADMINS AND MODS:
        moderators_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                admin_mail = snapshot.child("admin").getValue().toString();
                mod_mail = snapshot.child("mod").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Intent intent = getIntent();
        if(intent.hasExtra("gas_in")){
            gas_in = intent.getParcelableExtra("gas_in");
            key = intent.getStringExtra("key");

            textName.setText(gas_in.getName());
            textValue.setText(gas_in.getValue());
            textUser.setText(gas_in.getUser());
            textLocation.setText(gas_in.getLocation());
            textAcqDate.setText(gas_in.getAcq_date());

            ViewGroup layout = (ViewGroup) buttonUpdate.getParent();
            layout.removeView(buttonUpdate);

            textName.setFocusable(false);
            textName.setClickable(false);
            textValue.setFocusable(false);
            textValue.setClickable(false);
            textUser.setFocusable(false);
            textUser.setClickable(false);
            textLocation.setFocusable(false);
            textLocation.setClickable(false);
            textAcqDate.setFocusable(false);
            textAcqDate.setClickable(false);
        }else{
            buttonUpdate.setText(R.string.manage_popup_add_button);
            ViewGroup layout = (ViewGroup) buttonArchive.getParent();
            layout.removeView(buttonArchive);
            textAcqDate.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date()));

        }


        buttonArchive.setOnClickListener(v -> {
            if(user != null){
                user_email = user.getEmail();
            }
            if(user_email.equals(mod_mail) || user_email.equals(admin_mail)){
                archiveGas(textName, textValue, textUser, textLocation);
            }else{
                Toast.makeText(this, "You lack permissions to do this!", Toast.LENGTH_SHORT).show();
            }
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
            inUse_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Gas already in the database
                    if(snapshot.hasChild(key)){
                        gas_out.setAcq_date(gas_in.getAcq_date());
                        inUse_ref.child(key).setValue(gas_out);
                    }else{
                        gas_out.setAcq_date(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date()));
                        inUse_ref.push().setValue(gas_out);
                    }
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        });


    }

    private void archiveGas(TextView textName, TextView textValue, TextView textUser, TextView textLocation) {
        //TODO: Check for empty strings
        Gas gas_out = new Gas(
                textName.getText().toString(),
                textValue.getText().toString(),
                textUser.getText().toString(),
                textLocation.getText().toString(),
                gas_in.getAcq_date(),
                new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date())
        );
        inUse_ref.child(key).removeValue();
        reference.child("Archive").push().setValue(gas_out);
        finish();
    }
}
