package com.example.OPM_B2B;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {
Button Submit_Profile ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Submit_Profile.findViewById(R.id.Submit_Profile);

        Submit_Profile .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent=new Intent(ProfileActivity.this,Main4Activity.class);
                startActivity(deliveryIntent);
            }
        });
    }
}