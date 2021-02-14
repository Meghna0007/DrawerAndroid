package com.opm.b2b;

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

        RelativeLayout fmcg1 = findViewById(R.id.fmcg1);
        fmcg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(FMCGActivity.this, ViewAllActivity.class);
                categoryIntent.putExtra("collectionName" , "Biscuits and Rusk");
                categoryIntent.putExtra("displayName" , "Biscuits and Rusk");
                categoryIntent.putExtra("fmcg" , "true");
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
                categoryIntent.putExtra("displayName" , "Namkeen");
                categoryIntent.putExtra("fmcg" , "true");
                startActivity(categoryIntent);
                finish();
            }
        });

        RelativeLayout fmcg3 = findViewById(R.id.fmcg3);
        fmcg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(FMCGActivity.this, ViewAllActivity.class);
                categoryIntent.putExtra("collectionName" , "Drinks and Juices");
                categoryIntent.putExtra("displayName" , "Drinks and Juices");
                categoryIntent.putExtra("fmcg" , "true");
                startActivity(categoryIntent);
                finish();
            }
        });

        RelativeLayout fmcg4 = findViewById(R.id.fmcg4);
        fmcg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(FMCGActivity.this, ViewAllActivity.class);
                categoryIntent.putExtra("collectionName" , "Noodles and Pasta");
                categoryIntent.putExtra("displayName" , "Noodles and Pasta");
                categoryIntent.putExtra("fmcg" , "true");
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