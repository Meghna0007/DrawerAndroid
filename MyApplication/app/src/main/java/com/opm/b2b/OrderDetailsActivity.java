package com.opm.b2b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {
 private int position;

 private TextView title,price,quantity;
 private ImageView productImage,orderedIndicator,packedIndicator,shippedIndicator,deliveredIndicator;
 private ProgressBar O_P_progress,P_S_progress,S_D_progress;
 private TextView orderedTitle,packedTitle,shippedTitle,deliveredTitle;
 private TextView orderedDate,packedDate,shippedDate,deliveredDate;
 private TextView orderedBody,packedBody,shippedBody,deliveredBody;
 private TextView fullName,address,pinCode;
 private TextView totalItems,totalItemsPrice,deliveryPrice,totalamount,savedAmount;
 private SimpleDateFormat simpleDateFormat;
 private Button cancelOrderBtn;
 private Dialog cancelDialog,loadingDialog;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Order details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

     //////////////loading Dialog
     loadingDialog = new Dialog(OrderDetailsActivity.this);
     loadingDialog.setContentView(R.layout.loading_progress_dialog);
     loadingDialog.setCancelable(false);
     loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
     loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

     //////////////cancel Dialog
     cancelDialog = new Dialog(OrderDetailsActivity.this);
     cancelDialog.setContentView(R.layout.order_cancel_dialog);
     cancelDialog.setCancelable(true);
     cancelDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
    // cancelDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    // cancelDialog.show();
     //////////////cancel Dialog

        position=getIntent().getIntExtra("Position", -1);
        MyOrderItemModel model=DBqueries.myOrderItemModelList.get(position);
        title=findViewById(R.id.product_Title);
        price=findViewById(R.id.product_Pricee);
        quantity=findViewById(R.id.product_Quantity);
        productImage=findViewById(R.id.product_Image);

        orderedIndicator=findViewById(R.id.Ordered_indicator);
        packedIndicator=findViewById(R.id.Packed_Indicator);
        shippedIndicator=findViewById(R.id.Shipping_Indicator);
        deliveredIndicator=findViewById(R.id.delivered_Indictor);

        O_P_progress=findViewById(R.id.Order_Packed_Progress);
        P_S_progress=findViewById(R.id.Packed_ShippingProgress);
        S_D_progress=findViewById(R.id.Shiping_Delivered_Progress);

        orderedTitle=findViewById(R.id.Ordered_title);
        packedTitle=findViewById(R.id.Packed_Title);
        shippedTitle=findViewById(R.id.Shipping_Title);
        deliveredTitle=findViewById(R.id.DeliveredTitle);

        orderedDate=findViewById(R.id.Ordered_Date);
        packedDate=findViewById(R.id.Packed_Date);
        shippedDate=findViewById(R.id.Shipping_Date);
        deliveredDate=findViewById(R.id.DeliveredDate);

        orderedBody=findViewById(R.id.Ordered_Body);
        packedBody=findViewById(R.id.Packed_Body);
        shippedBody=findViewById(R.id.Shipping_Body);
        deliveredBody=findViewById(R.id.DeliveredBody);

        fullName=findViewById(R.id.fullName);
        address=findViewById(R.id.address);
        pinCode=findViewById(R.id.pincode);

        totalItems=findViewById(R.id.total_items);
        totalItemsPrice=findViewById(R.id.total_items_price);
        deliveryPrice=findViewById(R.id.delivery_price);
        totalamount=findViewById(R.id.total_price);
        savedAmount=findViewById(R.id.savedItem);

        cancelOrderBtn=findViewById(R.id.cancel_button);


        title.setText(model.getProductTitle());
        price.setText("Rs."+model.getProductPrice()+"/-");
        quantity.setText("Qty :"+String.valueOf(model.getProductQuantity()));

     Glide.with(this).load(model.getProductImage()).into(productImage);

    simpleDateFormat=new SimpleDateFormat("EEE, dd-MMM-YYYY  hh:mm aa");
     switch (model.getOrderStatus()){
         case "Ordered":
             orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             orderedDate.setText(String.valueOf(simpleDateFormat.format(model.getOrderedDate())));

             P_S_progress.setVisibility(View.GONE);
             S_D_progress.setVisibility(View.GONE);
             O_P_progress.setVisibility(View.GONE);

             packedIndicator.setVisibility(View.GONE);
             packedBody.setVisibility(View.GONE);
             packedDate.setVisibility(View.GONE);
             packedTitle.setVisibility(View.GONE);

             shippedIndicator.setVisibility(View.GONE);
             shippedBody.setVisibility(View.GONE);
             shippedDate.setVisibility(View.GONE);
             shippedTitle.setVisibility(View.GONE);

             deliveredIndicator.setVisibility(View.GONE);
             deliveredBody.setVisibility(View.GONE);
             deliveredDate.setVisibility(View.GONE);
             deliveredTitle.setVisibility(View.GONE);

             break;
         case "Packed":
             orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             orderedDate.setText(String.valueOf(simpleDateFormat.format(model.getOrderedDate())));

             packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             packedDate.setText(String.valueOf(simpleDateFormat.format(model.getPackedDate())));

             O_P_progress.setProgress(100);


             P_S_progress.setVisibility(View.GONE);
             S_D_progress.setVisibility(View.GONE);

             shippedIndicator.setVisibility(View.GONE);
             shippedBody.setVisibility(View.GONE);
             shippedDate.setVisibility(View.GONE);
             shippedTitle.setVisibility(View.GONE);

             deliveredIndicator.setVisibility(View.GONE);
             deliveredBody.setVisibility(View.GONE);
             deliveredDate.setVisibility(View.GONE);
             deliveredTitle.setVisibility(View.GONE);

             break;
         case "Shipped":
             orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             orderedDate.setText(String.valueOf(simpleDateFormat.format(model.getOrderedDate())));

             packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             packedDate.setText(String.valueOf(simpleDateFormat.format(model.getPackedDate())));

             shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             shippedDate.setText(String.valueOf(simpleDateFormat.format(model.getShippedDate())));

             O_P_progress.setProgress(100);
             P_S_progress.setProgress(100);



             S_D_progress.setVisibility(View.GONE);



             deliveredIndicator.setVisibility(View.GONE);
             deliveredBody.setVisibility(View.GONE);
             deliveredDate.setVisibility(View.GONE);
             deliveredTitle.setVisibility(View.GONE);

             break;
         case "Out for Delivery":
             orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             orderedDate.setText(String.valueOf(simpleDateFormat.format(model.getOrderedDate())));

             packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             packedDate.setText(String.valueOf(simpleDateFormat.format(model.getPackedDate())));

             shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             shippedDate.setText(String.valueOf(simpleDateFormat.format(model.getDeliveredDate())));

             deliveredIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             deliveredDate.setText(String.valueOf(simpleDateFormat.format(model.getDeliveredDate())));


             O_P_progress.setProgress(100);
             P_S_progress.setProgress(100);
             S_D_progress.setProgress(100);

             deliveredTitle.setText("Out for Delivery");
             deliveredBody.setText("Your order is out for delivery.");
             break;
         case "Delivered":

             orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             orderedDate.setText(String.valueOf(simpleDateFormat.format(model.getOrderedDate())));

             packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             packedDate.setText(String.valueOf(simpleDateFormat.format(model.getPackedDate())));

             shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             shippedDate.setText(String.valueOf(simpleDateFormat.format(model.getDeliveredDate())));

             deliveredIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
             deliveredDate.setText(String.valueOf(simpleDateFormat.format(model.getDeliveredDate())));


             O_P_progress.setProgress(100);
             P_S_progress.setProgress(100);
             S_D_progress.setProgress(100);

             break;
         case "Cancelled":
             if (model.getPackedDate().after(model.getOrderedDate())){
                 if (model.getShippedDate().after(model.getPackedDate())){

                     orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                     orderedDate.setText(String.valueOf(simpleDateFormat.format(model.getOrderedDate())));

                     packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                     packedDate.setText(String.valueOf(simpleDateFormat.format(model.getPackedDate())));

                     shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                     shippedDate.setText(String.valueOf(simpleDateFormat.format(model.getDeliveredDate())));

                     deliveredIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                     deliveredDate.setText(String.valueOf(simpleDateFormat.format(model.getCancelledDate())));
                     deliveredTitle.setText("Cancelled");
                     deliveredBody.setText("Your order has been cancelled");


                     O_P_progress.setProgress(100);
                     P_S_progress.setProgress(100);
                     S_D_progress.setProgress(100);


                 }else {

                     orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                     orderedDate.setText(String.valueOf(simpleDateFormat.format(model.getOrderedDate())));

                     packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                     packedDate.setText(String.valueOf(simpleDateFormat.format(model.getPackedDate())));

                     shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                     shippedDate.setText(String.valueOf(simpleDateFormat.format(model.getCancelledDate())));
                     shippedTitle.setText("Cancelled");
                     shippedBody.setText("Your order has been cancelled");

                     O_P_progress.setProgress(100);
                     P_S_progress.setProgress(100);



                     S_D_progress.setVisibility(View.GONE);



                     deliveredIndicator.setVisibility(View.GONE);
                     deliveredBody.setVisibility(View.GONE);
                     deliveredDate.setVisibility(View.GONE);
                     deliveredTitle.setVisibility(View.GONE);
                 }
             }else {
                 orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.successGreen)));
                 orderedDate.setText(String.valueOf(simpleDateFormat.format(model.getOrderedDate())));

                 packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                 packedDate.setText(String.valueOf(simpleDateFormat.format(model.getCancelledDate())));
                 packedTitle.setText("Cancelled");
                 packedBody.setText("Your order has been cancelled");

                 O_P_progress.setProgress(100);


                 P_S_progress.setVisibility(View.GONE);
                 S_D_progress.setVisibility(View.GONE);

                 shippedIndicator.setVisibility(View.GONE);
                 shippedBody.setVisibility(View.GONE);
                 shippedDate.setVisibility(View.GONE);
                 shippedTitle.setVisibility(View.GONE);

                 deliveredIndicator.setVisibility(View.GONE);
                 deliveredBody.setVisibility(View.GONE);
                 deliveredDate.setVisibility(View.GONE);
                 deliveredTitle.setVisibility(View.GONE);

             }

             break;
     }
     if (model.isCancellationRequested()){
         cancelOrderBtn.setVisibility(View.VISIBLE);
         cancelOrderBtn.setEnabled(false);
         cancelOrderBtn.setText("Cancellation in process.");
         cancelOrderBtn.setTextColor(getResources().getColor(R.color.red));
         cancelOrderBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
     }else {
         if (model.getOrderStatus().equals("ordered") || model.getOrderStatus().equals("Packed")){
             cancelOrderBtn.setVisibility(View.VISIBLE);
             cancelOrderBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                 cancelDialog.findViewById(R.id.No_Btn).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         cancelDialog.dismiss();
                     }
                 });
                 cancelDialog.findViewById(R.id.Yes_Btn).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         cancelDialog.dismiss();
                         loadingDialog.show();
                         Map<String,Object> map=new HashMap<>();
                         map.put("ORDER ID",model.getOrderID());
                         map.put("product Id",model.getProductId());
                         map.put("Order Cancelled",false);
                         FirebaseFirestore.getInstance().collection("CANCELLED ORDERS").document().set(map)
                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        FirebaseFirestore.getInstance().collection("ORDERS").document(model.getOrderID()).collection("OrderItems").document(model.getProductId()).update("Cancellation requested",true)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            model.setCancellationRequested(true);
                                                            cancelOrderBtn.setEnabled(false);
                                                            cancelOrderBtn.setText("Cancellation in process.");
                                                            cancelOrderBtn.setTextColor(getResources().getColor(R.color.red));
                                                            cancelOrderBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));

                                                        }else {

                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(OrderDetailsActivity.this, error, Toast.LENGTH_SHORT).show();

                                                        }
                                                        loadingDialog.dismiss();
                                                    }
                                                });
                                    }else {
                                        loadingDialog.dismiss();
                                        String error = task.getException().getMessage();
                                        Toast.makeText(OrderDetailsActivity.this, error, Toast.LENGTH_SHORT).show();

                                    }
                                    }
                                 });


                     }
                 });
                     cancelDialog.show();

                 }
             });
         }
     }

     fullName.setText(model.getFullName());
     address.setText(model.getAddress());
     pinCode.setText(model.getPinCode());
     Long totalItemsPriceValue;

     totalItemsPriceValue=model.getProductQuantity()*Long.valueOf(model.getProductPrice());
     totalItemsPrice.setText("Rs."+totalItemsPriceValue+"/-");

     if (model.getDeliveryPrice().equalsIgnoreCase("FREE")){
         deliveryPrice.setText(model.getDeliveryPrice());
         totalamount.setText(totalItemsPrice.getText());
     }else {
         deliveryPrice.setText("Rs." + model.getDeliveryPrice() + "/-");
         totalamount.setText("Rs."+ (totalItemsPriceValue + Long.valueOf(model.getDeliveryPrice()))+"/-");

     }
     if (model.getCuttedPrice().equals("")){
         savedAmount.setText("You saved Rs."+ model.getProductQuantity()*(Long.valueOf(model.getCuttedPrice())-Long.valueOf(model.getProductPrice())) +" on this order");
     }else {
         savedAmount.setText("You saved Rs.0/- on this order");
     }

     totalItems.setText("Price("+model.getProductQuantity()+" items)");





 }
 @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}