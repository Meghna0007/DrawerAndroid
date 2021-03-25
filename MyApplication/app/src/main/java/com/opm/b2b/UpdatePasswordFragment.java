package com.opm.b2b;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdatePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdatePasswordFragment() {
        // Required empty public constructor
    }

     private EditText oldPassword,newPassword,confirmPassword;
     private Button updateBtn;
     private Dialog loadingDialog;
     private String email;


     // TODO: Rename and change types and number of parameters
    public static UpdatePasswordFragment newInstance(String param1, String param2) {
        UpdatePasswordFragment fragment = new UpdatePasswordFragment();
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
       View view= inflater.inflate(R.layout.fragment_update_password, container, false);

        oldPassword=view.findViewById(R.id.oldPassword);
        newPassword=view.findViewById(R.id.new_Password);
        confirmPassword=view.findViewById(R.id.confirm_newPassword);
        updateBtn=view.findViewById(R.id.updatePassword_btn);

        //////////////Loading Dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //////////////Loading Dialog
        email=getArguments().getString("Email");



        oldPassword.addTextChangedListener(new TextWatcher() {
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
        newPassword.addTextChangedListener(new TextWatcher() {
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
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });

       return view;
    }
    private void checkInputs() {

        if (!TextUtils.isEmpty(oldPassword.getText()) && oldPassword.getText().length() >= 6) {
            if (!TextUtils.isEmpty(newPassword.getText()) && newPassword.getText().length() >= 6) {
                if (!TextUtils.isEmpty(confirmPassword.getText()) && confirmPassword.length() >= 6) {
                    updateBtn.setEnabled(true);
                    updateBtn.setTextColor(Color.WHITE);
                } else {
                    updateBtn.setEnabled(false);
                    updateBtn.setTextColor(Color.BLACK);
                }
            } else {
                updateBtn.setEnabled(false);
                updateBtn.setTextColor(Color.BLACK);
            }
        }else {
            updateBtn.setEnabled(false);
            updateBtn.setTextColor(Color.BLACK);
        }


    }
    private void checkEmailAndPassword() {


                if (newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                   ///////////////upfdate Password
                      loadingDialog.show();
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword.getText().toString());

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        user.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()){
                                                   oldPassword.setText(null);
                                                    newPassword.setText(null);
                                                    confirmPassword.setText(null);
                                                    getActivity().finish();
                                                    Toast.makeText(getContext(), "Password Updated successfully", Toast.LENGTH_SHORT).show();

                                                }else{
                                                    String error=task.getException().getMessage();
                                                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

                                                }
                                                loadingDialog.dismiss();
                                            }
                                        });

                                    }else {
                                        loadingDialog.dismiss();
                                        String error=task.getException().getMessage();
                                        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                } else {
                    confirmPassword.setError("Password doesn't match!");
                }




    }
}