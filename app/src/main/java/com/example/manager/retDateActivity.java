package com.example.manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class retDateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ret_date);
        getSupportActionBar().hide();

        EditText editDate = findViewById(R.id.editTextDate);
        Button buttonConfDate = findViewById(R.id.confDateButton);

        Intent intent = getIntent();
        Gas gas_out = intent.getParcelableExtra("gas_out");
        editDate.setText(gas_out.getRet_date());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        getWindow().setLayout((displayMetrics.widthPixels),(int)(displayMetrics.heightPixels*.6));

        buttonConfDate.setOnClickListener(v -> {
            if(!editDate.getText().toString().equals("")){
                gas_out.setRet_date(editDate.getText().toString());
            }else{
                Toast.makeText(this, "Wprowadź datę!", Toast.LENGTH_SHORT).show();
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("gas_out", gas_out);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}