package com.opm.b2b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeliveryActivity extends AppCompatActivity {
    public static final int SELECT_ADDRESS = 0;
    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddNewAddressBtn;
    private TextView totalAmount;
    public static List<CartItemModel> cartItemModelList;
    private TextView fullname;
    private String name,mobileNo;
    private TextView fullAddress;
    private TextView pincode;
    private Button ContinueBtn;
    private Dialog loadingDialog;
    private Dialog paymentMethodDialog;
    private ImageButton paytm,cod,gpay,upi;
    private ConstraintLayout orderConfirmationLayout;
    private ImageButton continueShoppingBtn;
    private TextView ordeId;
    private boolean successResponse = false;
    public static boolean fromCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");


        deliveryRecyclerView = findViewById(R.id.deliveryRecyclerView);
        changeOrAddNewAddressBtn = findViewById(R.id.changeOrAdd_AddressBtn);
        totalAmount = findViewById(R.id.total_cart_Amount);
        fullname = findViewById(R.id.fullName);
        fullAddress = findViewById(R.id.address);
        pincode = findViewById(R.id.pincode);
        ContinueBtn = findViewById(R.id.cart_continue_btn);
        orderConfirmationLayout = findViewById(R.id.order_confirmation_layout);
        ordeId = findViewById(R.id.ORDERID);
        continueShoppingBtn = findViewById(R.id.CONTINUE_Button);
        //////////////Loading Dialog
        loadingDialog = new Dialog(DeliveryActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //////////////Loading Dialog

        //////////////payment Dialog
        paymentMethodDialog = new Dialog(DeliveryActivity.this);
        paymentMethodDialog.setContentView(R.layout.payment_method);
        paymentMethodDialog.setCancelable(true);
        paymentMethodDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        paymentMethodDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paytm = paymentMethodDialog.findViewById(R.id.paytm_Button);
        cod=paymentMethodDialog.findViewById(R.id.COD_Button);
        gpay=paymentMethodDialog.findViewById(R.id.gpay_button);
        upi=paymentMethodDialog.findViewById(R.id.upi_button);
        //////////////payment Dialog
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);
        // List<CartItemModel> cartItemModelList = new ArrayList<>();
        //cartItemModelList.add(new CartItemModel(0, R.drawable.product, 1, "Apple", "Rs.499/-", "Rs.600/-"));
        //cartItemModelList.add(new CartItemModel(0, R.drawable.g1, 1, "Mango", "Rs.499/-", "Rs.600/-"));
        //cartItemModelList.add(new CartItemModel(0, R.drawable.product, 1, "Grapes", "Rs.499/-", "Rs.600/-"));
        // cartItemModelList.add(new CartItemModel(1, "Price (3 items)", "Rs.599/-", "Free", "Rs.100/-", "Rs.499"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList, totalAmount, false);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddNewAddressBtn.setVisibility(View.VISIBLE);
        changeOrAddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressIntent = new Intent(DeliveryActivity.this, MyAddress.class);
                myAddressIntent.putExtra("MODE", SELECT_ADDRESS);
                startActivity(myAddressIntent);
            }
        });

        ContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethodDialog.show();
            }
        });

        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent otpIntent=new Intent(DeliveryActivity.this,OTP_Verification.class);

              otpIntent.putExtra("mobileNo",mobileNo.substring(0,10));
              startActivity(otpIntent);
            }
        });
        gpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        upi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethodDialog.dismiss();
                loadingDialog.show();
                if (ContextCompat.checkSelfPermission(DeliveryActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DeliveryActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
                }

                final String M_id = "hqsyxD08234743733195";
                final String customer_id = FirebaseAuth.getInstance().getUid();
                final String order_id = UUID.randomUUID().toString().substring(0, 28);
                String url = "https://jiggered-dents.000webhostapp.com/Paytm_OPM/Paytm_PHP_Checksum-master/index.php";
                final String callBackUrl = "https://pguat.paytm.com/paytm/paytmCallback.jsp";

                RequestQueue requestQueue = Volley.newRequestQueue(DeliveryActivity.this);


            }
        });

/*
if(Main4Activity.main4Activity!=null){
    Main4Activity.main4Activity.finish();
    Main4Activity.main4Activity=null;
    Main4Activity.showCart=false;
}
if (ProductDetailsActivity.productDetailsActivity !=null){
    ProductDetailsActivity.productDetailsActivity.finish();
    ProductDetailsActivity.productDetailsActivity=null;
}
if (fromCart){
    loadingDialog.show();
 Map<String, Object> updateCartlist = new HashMap<>();
 long cartListSize=0;
 List<Integer>indexList= new ArrayList<>();
        for (int x = 0; x < DBqueries.cartList.size(); x++) {
            if (!cartItemModelList.get(x).isInStock()){
               updateCartlist.put("product_id_" + cartListSize, cartItemModelList.get(x).getProductId());
               cartListSize++;
            }else {
                indexList.add(x);
            }

        }
        updateCartlist.put("list_size",cartListSize);
    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
            .document("MY_CART").set(updateCartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()){
                for (int x=0;x<indexList.size();x++){
                    DBqueries.cartList.remove(indexList.get(x).intValue());
                    DBqueries.cartItemModelList.remove(indexList.get(x).intValue());
                     DBqueries.cartItemModelList.remove(DBqueries.cartItemModelList.size()-1);
                }
            }else {
                String error = task.getException().getMessage();
                Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();
            }
            loadingDialog.dismiss();
        }
    });
}*/
       /*     ContinueBtn.setEnabled(false);,l.cl   ,pp.,.
        changeOrAddNewAddressBtn.setEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    //////////////"ORDERID
        ordeId.setText("ORDER ID"+inResponse.getString("ORDERID"));
        orderConfirmationLayout.setVisibility(View.VISIBLE);
        continueShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        name=DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getFullname();
        mobileNo=DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getMobileNo();
        fullname.setText(name +" - "+mobileNo);
        fullAddress.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getAddress());
        pincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getPincode());
    }
}