package com.example.OPM_B2B;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class FMCGActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foods);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("FMCG");

        RelativeLayout biscuitLayout = findViewById(R.id.biscuitBackground);
        biscuitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(FMCGActivity.this, ViewAllActivity.class);
                categoryIntent.putExtra("collectionName" , "Biscuits and Rusk");
                startActivity(categoryIntent);
                finish();
            }
        });

        RelativeLayout fmcg2 = findViewById(R.id.fmcg2);
        fmcg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(FMCGActivity.this, ViewAllActivity.class);
                categoryIntent.putExtra("collectionName" , "Namkeen");
                startActivity(categoryIntent);
                finish();
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}