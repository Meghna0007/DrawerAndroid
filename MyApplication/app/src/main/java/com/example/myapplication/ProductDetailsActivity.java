package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {
private ViewPager ProductImageViewPager;
private TabLayout viewpagerIndicate;

private ViewPager productDetailsViewPager;
private TabLayout productDetailsTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProductImageViewPager =findViewById(R.id.product_images_lviewpager);
        viewpagerIndicate=findViewById(R.id.viewPager_indicater);
        productDetailsViewPager=findViewById(R.id.product_details_viewPager);
        productDetailsTabLayout=findViewById(R.id.product_details_tablayout);
        List<Integer> productImages =new ArrayList<>();
        productImages.add(R.drawable.profile_pic);
        productImages.add(R.drawable.g1);
        productImages.add(R.drawable.g2);
        productImages.add(R.drawable.g3);
        productImages.add(R.drawable.g4);


       ProductImagesAdapter productImagesAdapter =new ProductImagesAdapter(productImages);
        ProductImageViewPager.setAdapter(productImagesAdapter);


        viewpagerIndicate.setupWithViewPager(ProductImageViewPager,true);

        productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTabLayout.getTabCount()));
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
        }if (id == R.id.my_cart) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}