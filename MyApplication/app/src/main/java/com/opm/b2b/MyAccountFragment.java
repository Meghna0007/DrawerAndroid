package com.opm.b2b;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyAccountFragment() {

    }
    private Button viewAllAddress,signoutBtn;
    public static final int MANAGE_ADDRESS=1;
    private CircleImageView profileView,currentOrderImage;
    private TextView name,email,tvCurrentOrderStatus;
    private LinearLayout layoutContainer,recent_orders_containers;
    private Dialog loadingDialog;
    private ImageView orderIndicator,packedIndicator,shippedIndicator,deliveredIndicator;
    private ProgressBar O_P_progress,P_S_progress,S_D_progress;
    private TextView YourRecentOrdersTitle;
    private TextView addressname,address,pincode;
    private FloatingActionButton settingButton;
    // TODO: Rename and change types and number of parameters
    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_account, container, false);

        //////////////Loading Dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //////////////Loading Dialog
        profileView=view.findViewById(R.id.Profile_Image);
        name=view.findViewById(R.id.UserName);
        email=view.findViewById(R.id.UserEmail);
        layoutContainer=view.findViewById(R.id.layout_container);
        currentOrderImage=view.findViewById(R.id.current_OrderImage);
        tvCurrentOrderStatus=view.findViewById(R.id.tvcurrent_OrderStatus);
        orderIndicator=view.findViewById(R.id.Order_Indicator);
        packedIndicator=view.findViewById(R.id.Packed_indictor);
        shippedIndicator=view.findViewById(R.id.shipped_indicator);
        deliveredIndicator=view.findViewById(R.id.delivered_indicator);
        O_P_progress=view.findViewById(R.id.order_packed_progress);
        P_S_progress=view.findViewById(R.id.order_shipped_progress);
        S_D_progress=view.findViewById(R.id.shipped_delivered_progress);
        YourRecentOrdersTitle=view.findViewById(R.id.your_recent_orders_title);
        recent_orders_containers=view.findViewById(R.id.recentOrderContainer);
        settingButton=view.findViewById(R.id.SettingButton);
        addressname=view.findViewById(R.id.AddressFullname);
        address=view.findViewById(R.id.Addresss);
        pincode=view.findViewById(R.id.AddressPincode);
        signoutBtn=view.findViewById(R.id.signout);


        DBqueries.loadOrders(getContext(),null,loadingDialog);
        layoutContainer.getChildAt(1).setVisibility(View.GONE);

       loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                for (MyOrderItemModel orderItemModel : DBqueries.myOrderItemModelList){
                    if (!orderItemModel.isCancellationRequested()) {
                        if (!orderItemModel.getOrderStatus().equals("Delivered") && !orderItemModel.getOrderStatus().equals("Cancelled")){
                            layoutContainer.getChildAt(1).setVisibility(View.VISIBLE);
                            Glide.with(getContext()).load(orderItemModel.getProductImage()).apply(new RequestOptions().placeholder(R.drawable.fksmall)).into(currentOrderImage);
                            tvCurrentOrderStatus.setText(orderItemModel.getOrderStatus());
                            switch (orderItemModel.getOrderStatus()){
                                case "Ordered":
                                    orderIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                                    break;
                                case "Packed":
                                    orderIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                                    packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                                    O_P_progress.setProgress(100);
                                    break;
                                case "Shipped":
                                    orderIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                                    packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                                    shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                                    O_P_progress.setProgress(100);
                                    P_S_progress.setProgress(100);
                                    break;
                                case "Out for Delivery":
                                    orderIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                                    packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                                    shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                                    deliveredIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                                    O_P_progress.setProgress(100);
                                    P_S_progress.setProgress(100);
                                    S_D_progress.setProgress(100);
                                    break;
                            }
                        }
                    }
                }
                int i=0;
                for (MyOrderItemModel myOrderItemModel : DBqueries.myOrderItemModelList){
                    if (i<4) {
                        if (myOrderItemModel.getOrderStatus().equals("Delivered")) {
                            Glide.with(getContext()).load(myOrderItemModel.getProductImage())
                                    .apply(new RequestOptions().placeholder(R.drawable.fksmall)).into((CircleImageView) recent_orders_containers.getChildAt(i));
                            i++;
                        }
                    }else {
                        break;
                    }
                }
                if (i == 0) {
                    YourRecentOrdersTitle.setText("No recent Orders.");

                }
                if (i<3){
                    for (int x=i;x<4;x++){
                        recent_orders_containers.getChildAt(x).setVisibility(View.GONE);
                    }
                }
                loadingDialog.show();
                loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        loadingDialog.setOnDismissListener(null);
                        if (DBqueries.addressesModelList.size()==0){
                            addressname.setText("No Address");
                            address.setText("-");
                            pincode.setText("-");
                        }else {
                           setAddress();
                        }
                    }
                });
                DBqueries.loadAddresses(getContext(),loadingDialog,false);
            }
       });
        DBqueries.loadOrders(getContext(),null,loadingDialog);

        viewAllAddress=view.findViewById(R.id.viewAllAddressesBtn);
        viewAllAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressIntent=new Intent(getContext(),MyAddress.class);
                myAddressIntent.putExtra("MODE",MANAGE_ADDRESS);
                startActivity(myAddressIntent);
            }
        });
        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                DBqueries.clearData();
                Intent registerIntent=new Intent(getContext(),RegisterActivity.class);
                startActivity(registerIntent);
                getActivity().finish();
            }
        });
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateUserInfo=new Intent(getContext(),Update_userInfoActivity.class);
                updateUserInfo.putExtra("Name",name.getText());
                updateUserInfo.putExtra("Email",email.getText());
                updateUserInfo.putExtra("Photo",DBqueries.profile);
                updateUserInfo.putExtra("PanCard",DBqueries.panCard);
                updateUserInfo.putExtra("AadharCard",DBqueries.aadharCard);
                updateUserInfo.putExtra("BusinessName",DBqueries.businessName);
                updateUserInfo.putExtra("GstNo",DBqueries.gstNo);
                startActivity(updateUserInfo);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        name.setText(DBqueries.fullname);
        email.setText(DBqueries.email);
        if (!DBqueries.profile.equals("")){
            Glide.with(getContext()).load(DBqueries.profile).apply(new RequestOptions().placeholder(R.drawable.profile_pic)).into(profileView);
        }else {
            profileView.setImageResource(R.drawable.profile_pic);
        }
        if (!loadingDialog.isShowing()){
            if (DBqueries.addressesModelList.size()==0){
                addressname.setText("No Address");
                address.setText("-");
                pincode.setText("-");
            }else {
                setAddress();
            }

        }
    }

    private void setAddress() {
        String nametext,mobileNo;
        nametext = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getName();
        mobileNo = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getMobileNo();
        if (DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getAlternateMobileNo().equals("")) {
            addressname.setText(nametext + " - " + mobileNo);
        } else {
            addressname.setText(nametext + " - " + mobileNo + " or " + DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getAlternateMobileNo());

        }
        String flatNo = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getFlatNo();
        String locality = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getLocality();
        String landmark = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getLandmark();
        String city = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getCity();
        String state = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getState();
        if (landmark.equals("")) {
            address.setText(flatNo + " " + locality + " " + city + " " + state);

        } else{
            address.setText(flatNo + " " + locality + " " + landmark + " " + city + " " + state);
        }
        pincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getPincode());

    }
}