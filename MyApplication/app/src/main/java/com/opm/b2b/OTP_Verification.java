package com.opm.b2b;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OTP_Verification extends AppCompatActivity {

    private TextView phoneNo;
    private EditText otp;
    private Button verifyButton;
    private String userNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p__verification);

        phoneNo=findViewById(R.id.phone_no);
        otp=findViewById(R.id.OTP);
        verifyButton=findViewById(R.id.verify);
        userNo=getIntent().getStringExtra("mobileNo");
        phoneNo.setText("Verification code has been sent to +91 "+ userNo);

        Random random =new Random();
        int OTP_number=random.nextInt(999999-111111)+111111;
        String SMS_API="https://www.fast2sms.com/dev/bulkV2";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,SMS_API , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
               Map<String,String>header=new HashMap<>();

               header.put("authorization","FEuQKyJoB1s78DZNUzAHbp0CMjO3Y59S4mRLTVhxietafwIcvqE8ocyt0WOhQKugIfzqm1HxvGPj2ANX");

               return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>body=new HashMap<>();
              body.put("message","text");
                body.put("language","english");
                body.put("route","q");
                body.put("numbers","8888888888,9999999999,6666666666");
                body.put("flash","0");
                return body;
            }
        };

    }
}