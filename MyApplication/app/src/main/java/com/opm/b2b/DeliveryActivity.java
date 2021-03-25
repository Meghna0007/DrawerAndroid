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
import android.view.WindowManager;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    public static Dialog loadingDialog;
    private Dialog paymentMethodDialog;
    private ImageButton paytm,cod,gpay,upi;
    private ConstraintLayout orderConfirmationLayout;
    private ImageButton continueShoppingBtn;
    private TextView ordeId;
    private boolean successResponse = false;
    public static boolean fromCart;
   private  String order_id;
   public static boolean codOrderConfirmed=false;
   private  String paymentMethod = "PAYTM";
   public static boolean getQtyIDs=true;
   private FirebaseFirestore firebaseFirestore;
   public  static CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


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
        firebaseFirestore =FirebaseFirestore.getInstance();
        getQtyIDs=true;

        order_id = UUID.randomUUID().toString().substring(0, 28);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);
        // List<CartItemModel> cartItemModelList = new ArrayList<>();
        //cartItemModelList.add(new CartItemModel(0, R.drawable.product, 1, "Apple", "Rs.499/-", "Rs.600/-"));
        //cartItemModelList.add(new CartItemModel(0, R.drawable.g1, 1, "Mango", "Rs.499/-", "Rs.600/-"));
        //cartItemModelList.add(new CartItemModel(0, R.drawable.product, 1, "Grapes", "Rs.499/-", "Rs.600/-"));
        // cartItemModelList.add(new CartItemModel(1, "Price (3 items)", "Rs.599/-", "Free", "Rs.100/-", "Rs.499"));

         cartAdapter = new CartAdapter(cartItemModelList, totalAmount, false);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddNewAddressBtn.setVisibility(View.VISIBLE);
        changeOrAddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQtyIDs=false;
                Intent myAddressIntent = new Intent(DeliveryActivity.this, MyAddress.class);
                myAddressIntent.putExtra("MODE", SELECT_ADDRESS);
                startActivity(myAddressIntent);
            }
        });

        ContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean allProductAvailable=true;
                for (CartItemModel cartItemModel : cartItemModelList){
                    if (cartItemModel.isQtyError()){
                        allProductAvailable=false;
                        break;
                    }
                    if (cartItemModel.getType()==CartItemModel.CART_ITEM){
                    if (!cartItemModel.isCOD()){
                        cod.setEnabled(false);
                        cod.setAlpha(0.5f);
                        break;
                    }else {
                        cod.setEnabled(true);
                        cod.setAlpha(1f);
                    }
                }}
                if (allProductAvailable){
                    paymentMethodDialog.show();
                }
            }
        });

        cod.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
               paymentMethod="COD";
               placeOrderdetails();
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
                paymentMethod="PAYTM";
                placeOrderdetails();


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ///////////accessingQuantity
        if (getQtyIDs) {
            loadingDialog.show();
            for (int x = 0; x < cartItemModelList.size() - 1; x++) {

                for (int y = 0; y < cartItemModelList.get(x).getProductQuantity(); y++) {
                    String quantityDocumentName = UUID.randomUUID().toString().substring(0, 20);
                    Map<String, Object> timeStamp = new HashMap<>();
                    timeStamp.put("time", FieldValue.serverTimestamp());

                    int finalX = x;
                    int finalY = y;
                    firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(x).getProductId())
                            .collection("QUANTITY").document(quantityDocumentName).set(timeStamp).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        cartItemModelList.get(finalX).getQtyIDs().add(quantityDocumentName);

                                        if (finalY + 1 == cartItemModelList.get(finalX).getProductQuantity()) {
                                            firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(finalX).getProductId())
                                                    .collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING)
                                                    .limit(cartItemModelList.get(finalX).getStockQuantity())
                                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        List<String> serverQuantity = new ArrayList<>();
                                                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                                            serverQuantity.add(queryDocumentSnapshot.getId());
                                                        }
                                                        long availableQty = 0;
                                                        boolean noLongerAvailable = true;
                                                        for (String qtyId : cartItemModelList.get(finalX).getQtyIDs()) {
                                                            cartItemModelList.get(finalX).setQtyError(false);
                                                            if (!serverQuantity.contains(qtyId)) {


                                                                if (noLongerAvailable) {
                                                                    cartItemModelList.get(finalX).setInStock(false);
                                                                } else {
                                                                    cartItemModelList.get(finalX).setQtyError(true);
                                                                    cartItemModelList.get(finalX).setMaxQuantity(availableQty);
                                                                    Toast.makeText(DeliveryActivity.this, "Sorry ! all products may not be available in required quantity...", Toast.LENGTH_SHORT).show();
                                                                }

                                                            } else {
                                                                availableQty++;
                                                                noLongerAvailable = false;
                                                            }

                                                        }
                                                        cartAdapter.notifyDataSetChanged();

                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                    loadingDialog.dismiss();
                                                }
                                            });
                                        }

                                    } else {
                                        loadingDialog.dismiss();
                                        String error = task.getException().getMessage();
                                        Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        } else {
            getQtyIDs = true;
        }
        ///////////accessingQuantity

        name = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getName();
        mobileNo = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getMobileNo();
        if (DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getAlternateMobileNo().equals("")) {
            fullname.setText(name + " - " + mobileNo);
        } else {
            fullname.setText(name + " - " + mobileNo + " or " + DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getAlternateMobileNo());

        }
        String flatNo = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getFlatNo();
        String locality = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getLocality();
        String landmark = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getLandmark();
        String city = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getCity();
        String state = DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getState();
        if (landmark.equals("")) {
            fullAddress.setText(flatNo + " " + locality + " " + city + " " + state);

        } else{
            fullAddress.setText(flatNo + " " + locality + " " + landmark + " " + city + " " + state);
    }
        pincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getPincode());

        if (codOrderConfirmed){
            showConfirmationLayot();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        loadingDialog.dismiss();

        if (getQtyIDs) {
          for (int x = 0; x < cartItemModelList.size() - 1; x++) {
            if (!successResponse) {
              for (String qtyID : cartItemModelList.get(x).getQtyIDs()) {
                  int finalX = x;
                  firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(x)
                        .getProductId()).collection("QUANTITY").document(qtyID).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                           if (qtyID.equals(cartItemModelList.get(finalX).getQtyIDs().get(cartItemModelList.get(finalX).getQtyIDs().size() -1))){
                               cartItemModelList.get(finalX).getQtyIDs().clear();
                           }
                            }
                        });

            }
        }else {
                cartItemModelList.get(x).getQtyIDs().clear();
            }
    }
}
    }



    @Override
    public void onBackPressed() {
        if (successResponse){
            finish();
            return;
        }
        super.onBackPressed();
    }

    private void showConfirmationLayot(){
        successResponse=true;
        codOrderConfirmed=false;
       getQtyIDs=false;
        for (int x=0;x < cartItemModelList.size() -1;x++){
            for (String qtyID: cartItemModelList.get(x).getQtyIDs()){
                firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(x)
                        .getProductId()).collection("QUANTITY").document(qtyID)
                        .update("user_ID",FirebaseAuth.getInstance().getUid());

            }

        }


        if(Main4Activity.main4Activity!=null){
            Main4Activity.main4Activity.finish();
            Main4Activity.main4Activity=null;
            Main4Activity.showCart=false;
        }else {
            Main4Activity.resetMain4Activity=true;
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
        }
        ContinueBtn.setEnabled(false);
        changeOrAddNewAddressBtn.setEnabled(false); 
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //////////////"ORDERID
        ordeId.setText("ORDER ID"+order_id);
        orderConfirmationLayout.setVisibility(View.VISIBLE);
        continueShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeliveryActivity.this, Main4Activity.class));
                finish();
            }
        });

    }

    private void placeOrderdetails(){

        String userID=FirebaseAuth.getInstance().getUid();
        loadingDialog.show();
        for (CartItemModel cartItemModel : cartItemModelList){
            if (cartItemModel.getType() ==CartItemModel.CART_ITEM) {

                Map<String,Object>orderDetails = new HashMap<>();
                orderDetails.put("ORDER ID",order_id);
                orderDetails.put("Product Id",cartItemModel.getProductId());
                orderDetails.put("Product Image",cartItemModel.getProductImage());
                orderDetails.put("Product Title",cartItemModel.getProductTitle());
                orderDetails.put("User Id",userID);
                orderDetails.put("Product Quantity",cartItemModel.getProductQuantity());
                if (cartItemModel.getCuttedPrice() !=(null)) { orderDetails.put("Cutted Price", cartItemModel.getCuttedPrice());
                }else { orderDetails.put("Cutted Price", ""); }
                orderDetails.put("Product Price",cartItemModel.getProductPrice());

                orderDetails.put("Ordered date",FieldValue.serverTimestamp());
                orderDetails.put("Packed date",FieldValue.serverTimestamp());
                orderDetails.put("Shipped date",FieldValue.serverTimestamp());
                orderDetails.put("Cancelled date",FieldValue.serverTimestamp());
                orderDetails.put("Delivered date",FieldValue.serverTimestamp());
                orderDetails.put("Order Status","Ordered");

                orderDetails.put("Payment Method",paymentMethod);
                orderDetails.put("Address",fullAddress.getText());
                orderDetails.put("FullName",fullname.getText());
                orderDetails.put("PinCode",pincode.getText());
                orderDetails.put("Delivery Price",cartItemModelList.get(cartItemModelList.size() -1).getDeliveryPrice());

                orderDetails.put("Cancellation requested",false);
               /* orderDetails.put("Set Piece","");
                orderDetails.put("Per Piece","");
                orderDetails.put("Product Weight","");*/



                firebaseFirestore.collection("ORDERS").document(order_id)
                        .collection("OrderItems").document(cartItemModel.getProductId())
                .set(orderDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                           String error=task.getException().getMessage();
                            Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                Map<String,Object> orderDetails = new HashMap<>();
                orderDetails.put("Total Items",cartItemModel.getTotalItems());
                orderDetails.put("Total Items Price",cartItemModel.getTotalItemPrice());
                orderDetails.put("Delivery Price",cartItemModel.getDeliveryPrice());
                orderDetails.put("Total Amount",cartItemModel.getTotalAmount());
                orderDetails.put("Saved Amount",cartItemModel.getSavedAmount());
                orderDetails.put("Payment Status","not Paid");
                orderDetails.put("Order Status","Cancelled");

                firebaseFirestore.collection("ORDERS").document(order_id)
                        .set(orderDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful()){
                         if (paymentMethod.equals("PAYTM")){
                             paytm();
                         }else {
                             cod();
                         }
                     }else{
                         String error=task.getException().getMessage();
                         Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();
                     }
                    }
                });


            }
        }
    }
    private void paytm() {
        getQtyIDs=false;
        paymentMethodDialog.dismiss();
        loadingDialog.show();
        if (ContextCompat.checkSelfPermission(DeliveryActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DeliveryActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        final String M_id = "hqsyxD08234743733195";
        final String customer_id = FirebaseAuth.getInstance().getUid();

        String url = "https://jiggered-dents.000webhostapp.com/Paytm_OPM/Paytm_PHP_Checksum-master/index.php";
        final String callBackUrl = "https://pguat.paytm.com/paytm/paytmCallback.jsp";

        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryActivity.this);

        /////Response Status

            Map<String,Object>updateStatus=new HashMap<>();
            updateStatus.put("Payment Status","Paid");
            updateStatus.put("Order Status","Ordered");
            firebaseFirestore.collection("ORDERS").document(order_id).update(updateStatus)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Map<String,Object> userOrder=new HashMap<>();
                                userOrder.put("order_id",order_id);
                                userOrder.put("time",FieldValue.serverTimestamp());
                                firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USERS_ORDERS")
                                        .document(order_id).set(userOrder).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                           if (task.isSuccessful()){
                                               showConfirmationLayot();
                                           }else {
                                               Toast.makeText(DeliveryActivity.this, "failed to update user's OrderList", Toast.LENGTH_SHORT).show();
                                           }
                                    }
                                });

                            }else {
                                Toast.makeText(DeliveryActivity.this, "Order Cancelled", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


        ///////Response Status

    }
    private void cod() {
        getQtyIDs=false;
        paymentMethodDialog.dismiss();
        Intent otpIntent=new Intent(DeliveryActivity.this,OTP_Verification.class);

        otpIntent.putExtra("mobileNo",mobileNo.substring(0,10));
        otpIntent.putExtra("OrderID",order_id);

        startActivity(otpIntent);
    }
}