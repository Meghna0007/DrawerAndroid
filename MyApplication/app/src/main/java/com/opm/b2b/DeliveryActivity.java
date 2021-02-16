package com.opm.b2b;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {
    public  static  final int SELECT_ADDRESS=0;
private RecyclerView deliveryRecyclerView;
private Button changeOrAddNewAddressBtn;
private TextView totalAmount;

private TextView fullname;
private TextView fullAddress;
private TextView pincode;
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
        changeOrAddNewAddressBtn=findViewById(R.id.changeOrAdd_AddressBtn);
        totalAmount=findViewById(R.id.total_cart_Amount);
        fullname=findViewById(R.id.fullName);
        fullAddress=findViewById(R.id.address);
        pincode=findViewById(R.id.pincode);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);
       // List<CartItemModel> cartItemModelList = new ArrayList<>();
        //cartItemModelList.add(new CartItemModel(0, R.drawable.product, 1, "Apple", "Rs.499/-", "Rs.600/-"));
        //cartItemModelList.add(new CartItemModel(0, R.drawable.g1, 1, "Mango", "Rs.499/-", "Rs.600/-"));
        //cartItemModelList.add(new CartItemModel(0, R.drawable.product, 1, "Grapes", "Rs.499/-", "Rs.600/-"));
       // cartItemModelList.add(new CartItemModel(1, "Price (3 items)", "Rs.599/-", "Free", "Rs.100/-", "Rs.499"));

        CartAdapter cartAdapter = new CartAdapter(DBqueries.cartItemModelList,totalAmount,false);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddNewAddressBtn.setVisibility(View.VISIBLE);
        changeOrAddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressIntent=new Intent(DeliveryActivity.this,MyAddress.class);
                myAddressIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myAddressIntent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        fullname.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getFullname());
        fullAddress.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getAddress());
        pincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddresss).getPincode());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

       if(id==android.R.id.home){
           finish();
           return true;
       }
        return super.onOptionsItemSelected(item);
    }
}