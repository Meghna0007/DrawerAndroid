package com.opm.b2b;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private EditText password;
    private EditText confirmPassword;

    private Button signUpBtn;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    //////OTP
    Button btnGenerateOTP, btnSignIn;

    EditText etPhoneNumber, etOTP;

    String phoneNumber, otp;

    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;
//////////OTP
    public static boolean disableCloseBtn = false;

    private boolean isOtpValidated=false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_signout, container, false);
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        alreadyHaveAnAccount = view.findViewById(R.id.alreadyHaveAnAccount);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        email = view.findViewById(R.id.sign_up_email);
        password = view.findViewById(R.id.sign_up_password);
        confirmPassword = view.findViewById(R.id.sign_up_confirm_password);
        signUpBtn = view.findViewById(R.id.sign_up_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //////////OTP
        btnGenerateOTP = view.findViewById(R.id.btn_generate_otp);
        btnSignIn = view.findViewById(R.id.btn_sign_in);


        etPhoneNumber = view.findViewById(R.id._phone_number);
        etOTP = view.findViewById(R.id.et_otp);
        ////////OTP
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ////////////////OTP
        ///findViews();

        StartFirebaseLogin();

        btnGenerateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = etPhoneNumber.getText().toString();
                if (!phoneNumber.startsWith("+")) {
                    phoneNumber = "+91" + phoneNumber;
                }

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,                     // Phone number to verify
                        60,                           // Timeout duration
                        TimeUnit.SECONDS,                // Unit of timeout,
                        getActivity(),// Activity (for callback binding)
                        mCallback);                      // OnVerificationStateChangedCallbacks
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = etOTP.getText().toString();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);

                SigninWithPhone(credential);

                signOutFromPhone();
            }
        });
////////////////////OTP
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setFragment(new SigninFragment());
            }

        });

        // email
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //password
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //confirm password listener
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkInputs();

            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                checkEmailAndPassword();

            }
        });

    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }


    private void checkInputs() {

        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(password.getText()) && password.getText().length() >= 6) {
                if (!TextUtils.isEmpty(confirmPassword.getText()) && confirmPassword.getText().toString().equals(password.getText().toString())
                && isOtpValidated) {
                    signUpBtn.setEnabled(true);
                    signUpBtn.setTextColor(Color.BLACK);
                } else {
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.BLACK);
                }
            } else {
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.BLACK);
            }
        } else {
            signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(Color.BLACK);
        }

    }

    private void enableSignUpButton() {
        if (email.getText().toString().matches(emailPattern)) {
            if (password.getText().toString().equals(confirmPassword.getText().toString()) && isOtpValidated) {
                signUpBtn.setEnabled(true);
                signUpBtn.setTextColor(Color.WHITE);
            }
        }
    }
    private void checkEmailAndPassword() {

        if (email.getText().toString().matches(emailPattern)) {
            if (password.getText().toString().equals(confirmPassword.getText().toString()) && isOtpValidated) {
                signUpBtn.setEnabled(true);
                signUpBtn.setTextColor(Color.WHITE);

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Map<Object, String> userdata = new HashMap<>();
                                    userdata.put("email", email.getText().toString());
                                    userdata.put("phoneNumber", etPhoneNumber.getText().toString());

                                    firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                            .set(userdata)
                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   if (task.isSuccessful()) {
                                                     CollectionReference userDataReference = firebaseFirestore.collection("USERS")
                                                             .document(firebaseAuth.getUid()).collection("USER_DATA");

                                                      //////Maps
                                                       Map<String,Object> wishlistMap = new HashMap<>();
                                                       wishlistMap.put("list_size",(long) 0);

                                                       Map<String,Object> cartMap = new HashMap<>();
                                                       cartMap.put("list_size",(long) 0);

                                                       Map<String,Object> myAddressesMap = new HashMap<>();
                                                       myAddressesMap.put("list_size",(long) 0);
//////////////////////////////////////////////////////Maps
                                                       List<String> documentNames=new ArrayList<>();
                                                       documentNames.add("MY_WISHLIST");
                                                       documentNames.add("MY_CART");
                                                       documentNames.add("MY_ADDRESSES");

                                                     List<Map<String,Object>>documentFields=new ArrayList<>();
                                                     documentFields.add(wishlistMap);
                                                     documentFields.add(cartMap);
                                                       documentFields.add(myAddressesMap);

                                                     for(int x=0;x<documentNames.size();x++){

                                                         int finalX = x;
                                                         userDataReference.document(documentNames.get(x))
                                                                 .set(documentFields.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                             @Override
                                                             public void onComplete(@NonNull Task<Void> task) {
                                                                 if(task.isSuccessful()){
                                                                     if (finalX == documentNames.size() -1){
                                                                         Intent mainIntent = new Intent(getActivity(), Main4Activity.class);
                                                                         startActivity(mainIntent);
                                                                         getActivity().finish();
                                                                     }

                                                                 }else{
                                                                     signUpBtn.setEnabled(false);
                                                                     signUpBtn.setTextColor(Color.BLACK);
                                                                     String error = task.getException().getMessage();
                                                                     Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                                 }

                                                             }
                                                         });
                                                     }

                                                   } else {
                                                       String error = task.getException().getMessage();
                                                       Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                   }

                                               }
                                           });
                                } else {
                                    signUpBtn.setEnabled(false);
                                    signUpBtn.setTextColor(Color.BLACK);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                confirmPassword.setError("Password doesn't match!");
            }

        } else {
            email.setError("Invalid Email!");
        }

    }
    private  void mainIntent(){
        Intent mainIntent=new Intent(getActivity(),Main4Activity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }
    ////////////OTP
    private void SigninWithPhone(PhoneAuthCredential credential) {



        auth.signInWithCredential(credential)
        //auth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "OTP Validated", Toast.LENGTH_SHORT).show();
                            isOtpValidated = true;
                            enableSignUpButton();
                            //checkEmailAndPassword();
                        } else {
                            Toast.makeText(getContext(), "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                Toast.makeText(getContext(), "verification failed. " + e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(getContext(), "Code sent", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void signOutFromPhone() {

        FirebaseAuth.getInstance().signOut();

    }
    //////////OTP
}