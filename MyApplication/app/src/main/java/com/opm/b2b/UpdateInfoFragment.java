package com.opm.b2b;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.adapters.ToolbarBindingAdapter;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class UpdateInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateInfoFragment() {
        // Required empty public constructor
    }
   private CircleImageView circleImageView;
    private Button changePhoto,removeBtn,updateBtn,doneBtn;
    private EditText nameField,emailField,businessNameField,panNoField,aadharNoField,gstNumberField,password;
    private Dialog loadingDialog,passwordDialog;
    private Uri imageUri;
    private String name,email,businessName,gstNo,aadharCard,panCard,photo;
    private boolean updatePhoto=false;


    // TODO: Rename and change types and number of parameters
    public static UpdateInfoFragment newInstance(String param1, String param2) {
        UpdateInfoFragment fragment = new UpdateInfoFragment();
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

        View view= inflater.inflate(R.layout.fragment_update_info, container, false);

        circleImageView=view.findViewById(R.id.profile_image);
        nameField=view.findViewById(R.id.name_field);
        businessNameField=view.findViewById(R.id.BusinessName);
        emailField=view.findViewById(R.id.editTextTextEmailAddress);
        panNoField=view.findViewById(R.id.pan_no);
        aadharNoField=view.findViewById(R.id.aadhar_no);
        gstNumberField=view.findViewById(R.id.gst_no);
        changePhoto=view.findViewById(R.id.change_photo_btn);
        removeBtn=view.findViewById(R.id.remove_photo_btn);
        updateBtn=view.findViewById(R.id.UpdateBtn);


        //////////////Loading Dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //////////////Loading Dialog

        //////////////password Dialog
        passwordDialog = new Dialog(getContext());
        passwordDialog.setContentView(R.layout.password_confirmation_dialog);
        passwordDialog.setCancelable(true);
        passwordDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        passwordDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        password=passwordDialog.findViewById(R.id.password_);
        doneBtn=passwordDialog.findViewById(R.id.done_btn);
        //////////////password Dialog



        name=getArguments().getString("Name");
         email=getArguments().getString("Email");
         photo=getArguments().getString("Photo");
         panCard=getArguments().getString("PanCard");
        aadharCard=getArguments().getString("AadharCard");
         businessName=getArguments().getString("BusinessName");
        gstNo=getArguments().getString("GstNo");

        Glide.with(getContext()).load(photo).into(circleImageView);
        nameField.setText(name);
        emailField.setText(email);
        businessNameField.setText(businessName);
        aadharNoField.setText(aadharCard);
        panNoField.setText(panCard);
        gstNumberField.setText(gstNo);

        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {

                    if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, 1);

                    }else {
                        getActivity().requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
                    }
                }else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, 1);
                }
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        imageUri=null;
        updatePhoto=true;
        Glide.with(getContext()).load(R.drawable.profile_pic).into(circleImageView);

    }
});

        emailField.addTextChangedListener(new TextWatcher() {
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
        nameField.addTextChangedListener(new TextWatcher() {
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
        gstNumberField.addTextChangedListener(new TextWatcher() {
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
        businessNameField.addTextChangedListener(new TextWatcher() {
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
        aadharNoField.addTextChangedListener(new TextWatcher() {
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
        panNoField.addTextChangedListener(new TextWatcher() {
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

    private void checkEmailAndPassword(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
        String gstPattern="^[0-9]{2}[A-Z]{5}[0-9]{4}" + "[A-Z]{1}[1-9A-Z]{1}" + "Z[0-9A-Z]{1}$";

        if (gstNumberField.getText().toString().matches(gstPattern)) {

            if (emailField.getText().toString().matches(emailPattern)) {

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
             if (emailField.getText().toString().toLowerCase().trim().equals(email.toLowerCase().trim())){///////same email
                 loadingDialog.show();
                       updatePhoto(user);
             }else{///////////update email
                passwordDialog.show();
                 doneBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         loadingDialog.show();
                         String userPassword=password.getText().toString();
                         passwordDialog.dismiss();
                         AuthCredential credential = EmailAuthProvider
                                 .getCredential(email, userPassword);
                         user.reauthenticate(credential)
                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         if (task.isSuccessful()){
                                            user.updateEmail(emailField.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                         /////////updating photo
                                                     updatePhoto(user);
                                                        /////////updating photo
                                                    }else {
                                                        loadingDialog.dismiss();
                                                        String error=task.getException().getMessage();
                                                        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                         } else {
                                             loadingDialog.dismiss();
                                             String error=task.getException().getMessage();
                                             Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                 });

                     }
                 });
             }

            }else {
                gstNumberField.setError("Invalid Email!");
            }
        } else {
            emailField.setError("Invalid GST!");
        }

    }

    private void checkInputs() {

        if (!TextUtils.isEmpty(gstNumberField.getText())) {
            if (!TextUtils.isEmpty(emailField.getText())) {
                if (!TextUtils.isEmpty(nameField.getText())) {
                    if (!TextUtils.isEmpty(businessNameField.getText())) {
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
            } else {
                updateBtn.setEnabled(false);
                updateBtn.setTextColor(Color.BLACK);
            }
        }



    }
    private void updatePhoto(final FirebaseUser user){
        if (updatePhoto) {
            final   StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("profile/"+user.getUid() +".jpg");
            if (imageUri !=null){



                   Glide.with(getContext()).asBitmap().load(imageUri).circleCrop().into(new ImageViewTarget<Bitmap>(circleImageView) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = storageReference.putBytes(data);
                        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()){
                                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()){
                                                imageUri=task.getResult();
                                                DBqueries.profile=task.getResult().toString();
                                                Glide.with(getContext()).load(DBqueries.profile).into(circleImageView);

                                                Map<String,Object> updateData=new HashMap<>();
                                                updateData.put("AadharNo",aadharNoField.getText().toString());
                                                updateData.put("GstNo",gstNumberField.getText().toString());
                                                updateData.put("BusinessName",businessNameField.getText().toString());
                                                updateData.put("PanCard",panNoField.getText().toString());
                                                updateData.put("email",emailField.getText().toString());
                                                updateData.put("fullName",nameField.getText().toString());
                                                updateData.put("profile",DBqueries.profile);

                                                updateFields(user,updateData);


                                            }else {
                                                loadingDialog.dismiss();
                                                DBqueries.profile="";
                                                String error=task.getException().getMessage();
                                                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }else{
                                    loadingDialog.dismiss();
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        return;
                    }

                    @Override
                    protected void setResource(@Nullable Bitmap resource) {
                        circleImageView.setImageResource(R.drawable.profile_pic);
                    }
                });

            }else {//////////remove photo
                storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){


                            DBqueries.profile="";
                            Map<String,Object> updateData=new HashMap<>();
                            updateData.put("AadharNo",aadharNoField.getText().toString());
                            updateData.put("GstNo",gstNumberField.getText().toString());
                            updateData.put("BusinessName",businessNameField.getText().toString());
                            updateData.put("PanCard",panNoField.getText().toString());
                            updateData.put("email",emailField.getText().toString());
                            updateData.put("fullName",nameField.getText().toString());
                            updateData.put("profile","");

                            updateFields(user,updateData);

                        }else {
                            loadingDialog.dismiss();
                            String error=task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }else {
            Map<String,Object> updateData=new HashMap<>();
            updateData.put("AadharNo",aadharNoField.getText().toString());
            updateData.put("GstNo",gstNumberField.getText().toString());
            updateData.put("BusinessName",businessNameField.getText().toString());
            updateData.put("PanCard",panNoField.getText().toString());
            updateData.put("fullName",nameField.getText().toString());
            updateFields(user,updateData);
        }


    }
    private void updateFields(FirebaseUser user,Map<String,Object> updateData){
        FirebaseFirestore.getInstance().collection("USERS").document(user.getUid())
                .update(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                       if (updateData.size()>1){
                           DBqueries.fullname=nameField.getText().toString().trim();
                           DBqueries.email=emailField.getText().toString().trim();
                           DBqueries.businessName=businessNameField.getText().toString().trim();
                           DBqueries.gstNo=gstNumberField.getText().toString().trim();
                           DBqueries.aadharCard=aadharNoField.getText().toString().trim();
                           DBqueries.panCard=panNoField.getText().toString().trim();

                       }else {
                           DBqueries.fullname=nameField.getText().toString().trim();
                       }
                       getActivity().finish();
                    Toast.makeText(getContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show();

                }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){
            if (resultCode==getActivity().RESULT_OK){
                if (data != null){
                     imageUri=data.getData();
                     updatePhoto=true;
                    Glide.with(getContext()).load(imageUri).into(circleImageView);
                }else{
                    Toast.makeText(getContext(), "Image not found!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==2){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 1);

            }else {
                Toast.makeText(getContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }

    }
}