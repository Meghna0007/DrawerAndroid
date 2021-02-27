package com.opm.b2b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OTP_Verification extends AppCompatActivity {

    private TextView phoneNo;
    private EditText etOTP;
    private Button verifyButton;
    private String userNo;
    private String apiKey;
    private String message;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    FirebaseAuth auth;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p__verification);

        phoneNo = findViewById(R.id.phone_no);
        etOTP = findViewById(R.id.OTP);
        verifyButton = findViewById(R.id.verify);
        userNo = getIntent().getStringExtra("mobileNo");
        userNo = "+91"+userNo;
        phoneNo.setText("Verification code has been sent to  " + userNo);

        StartFirebaseLogin();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                userNo,                     // Phone number to verify
                60,                           // Timeout duration
                TimeUnit.SECONDS,                // Unit of timeout,
                this,// Activity (for callback binding)
                mCallback);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = etOTP.getText().toString();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);

                SigninWithPhone(credential);
                DeliveryActivity.codOrderConfirmed=true;
                finish();
            }
        });
       /* apiKey = "FEuQKyJoB1s78DZNUzAHbp0CMjO3Y59S4mRLTVhxietafwIcvqE8ocyt0WOhQKugIfzqm1HxvGPj2ANX";

        Random random =new Random();
        int OTP_number=random.nextInt(999999-111111)+111111;
        message = "YourOTPforOPMis" + OTP_number;
                String url = "https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&message="+message+"&language=english&route=q&numbers="+userNo;
        String SMS_API="https://www.fast2sms.com/dev/bulkV2";*/
        /*StringRequest stringRequest=new StringRequest(Request.Method.GET,url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                verifyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (otp.getText().toString().equals(String.valueOf(OTP_number))){
                           DeliveryActivity.codOrderConfirmed=true;
                            finish();
                        }else {
                            Toast.makeText(OTP_Verification.this,"incorrect OTP !",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }*/
     /*   }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            finish();
            Toast.makeText(OTP_Verification.this,"failed to send the OTP verification code !",Toast.LENGTH_SHORT).show();
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
              body.put("message","Your OTP is " + OTP_number);
                body.put("language","english");
                body.put("route","q");
                body.put("numbers",userNo);

                return body;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        RequestQueue requestQueue= Volley.newRequestQueue(OTP_Verification.this);
        requestQueue.add(stringRequest);
    }*/

    }

    private void StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                //Toast.makeText(getContext(), "verification completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(OTP_Verification.this, "verification failed. " + e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(OTP_Verification.this, "Code sent", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void SigninWithPhone(PhoneAuthCredential credential) {

        auth.getCurrentUser().linkWithCredential(credential)
        //auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           /* startActivity(new Intent(OTP_Verification.this, Main4Activity.class));
                            finish();*/
                            //checkEmailAndPassword();
                            Toast.makeText(OTP_Verification.this, " OTP", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(OTP_Verification.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}