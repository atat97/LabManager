package com.example.manager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginButton = findViewById(R.id.login_button);
        final EditText loginEdit = findViewById(R.id.login_edit);
        final EditText passwordEdit = findViewById(R.id.password_edit);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        if(currentUser != null){
            finish();
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
        }

        
        loginButton.setOnClickListener(v -> {
            String login = loginEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();
            if(login.equals("") | password.equals("")){
                Toast.makeText(MainActivity.this, "Wprowadź login i hasło!", Toast.LENGTH_SHORT).show();
            }else{
                validateUser(login, password);
            }
        });
    }
    
    private void validateUser (String username, String password){
        progressDialog.setMessage("Weryfikacja...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Logowanie udane!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }else{
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Logowanie nieudane!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}