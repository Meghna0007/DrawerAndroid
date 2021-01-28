package com.example.OPM_B2B;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.OPM_B2B.Main4Activity.showCart;

public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager ProductImageViewPager;
    private TabLayout viewpagerIndicate;
   private FloatingActionButton addtoWishlistBtn;
   private  static  boolean ALREADY_ADDED_TO_WISHLIST =false;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;

    private Button buyNowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
buyNowBtn=findViewById(R.id.buy_now_button);
        ProductImageViewPager = findViewById(R.id.product_images_lviewpager);
        viewpagerIndicate = findViewById(R.id.viewPager_indicater);
        addtoWishlistBtn=findViewById(R.id.wishlistt);
        productDetailsViewPager = findViewById(R.id.product_details_viewPager);
        productDetailsTabLayout = findViewById(R.id.product_details_tablayout);
        List<Integer> productImages = new ArrayList<>();
        productImages.add(R.drawable.profile_pic);
        productImages.add(R.drawable.g1);
        productImages.add(R.drawable.g2);
        productImages.add(R.drawable.g3);
        productImages.add(R.drawable.g4);


        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
        ProductImageViewPager.setAdapter(productImagesAdapter);


        viewpagerIndicate.setupWithViewPager(ProductImageViewPager, true);
        addtoWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(ALREADY_ADDED_TO_WISHLIST){
                  ALREADY_ADDED_TO_WISHLIST =false;
                 addtoWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
              }else{
                  ALREADY_ADDED_TO_WISHLIST =true;
                addtoWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.red));
              }
            }
        });

        productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount()));
        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent=new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
                startActivity(deliveryIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();
            return true;
        } else if (id == R.id.main_search_icon) {

            return true;
        }
        if (id == R.id.action_settings) {
            Intent cartIntent=new Intent(ProductDetailsActivity.this,Main4Activity.class);
            showCart=true;
            startActivity(cartIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}