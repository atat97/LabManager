package com.example.manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        Button inUseButton = findViewById(R.id.inUse_button);
        Button archiveButton = findViewById(R.id.archive_button);

        inUseButton.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, GasesListActivity.class)));
        archiveButton.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, ArchiveActivity.class)));
    }
}