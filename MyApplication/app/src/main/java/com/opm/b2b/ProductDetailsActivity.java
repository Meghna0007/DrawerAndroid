package com.opm.b2b;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager ProductImageViewPager;
    private TextView ProductTitle ;
    private TextView ProductPrice;
    private TextView CuttedPrice;
    private TabLayout viewpagerIndicate;
   private FloatingActionButton addtoWishlistBtn;
   private  static  boolean ALREADY_ADDED_TO_WISHLIST =false;
   private ConstraintLayout productDetailsOnlycontainer;
   private  ConstraintLayout productDetailsTabLabscontainer;
    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();

    private String productDescription;
   private String productOtherDetails;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private Button buyNowBtn;
    private LinearLayout addtoCartBtn;

    private FirebaseFirestore firebaseFirestore;
    private TextView productOnlyDescriptionBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buyNowBtn=findViewById(R.id.buy_now_button);
        ProductTitle=findViewById(R.id.ProductTitle);
        ProductPrice=findViewById(R.id.main_price);
        CuttedPrice=findViewById(R.id.cutted_price);
        ProductImageViewPager = findViewById(R.id.product_images_lviewpager);
        viewpagerIndicate = findViewById(R.id.viewPager_indicater);

        addtoWishlistBtn=findViewById(R.id.wishlistt);
productOnlyDescriptionBody=findViewById(R.id.product_detailsBody);
        productDetailsViewPager = findViewById(R.id.product_details_viewPager);
        productDetailsTabLayout = findViewById(R.id.product_details_tablayout);
        productDetailsTabLabscontainer=findViewById(R.id.product_details_tabs_container);
        productDetailsOnlycontainer=findViewById(R.id.product_detailsContainer);
        addtoCartBtn = findViewById(R.id.AddToCart);

        firebaseFirestore=FirebaseFirestore.getInstance();
       List<String>productImages=new ArrayList<>();
        firebaseFirestore.collection("PRODUCTS").document("MVECkQALPZ5imJDzopTn")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                   DocumentSnapshot documentSnapshot=task.getResult();

                   for(long x=1;x< (long)documentSnapshot.get("no_of_product_images")+ 1;x++){

                       productImages.add(documentSnapshot.get("product_image_"+x).toString());
                   }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    ProductImageViewPager.setAdapter(productImagesAdapter);

                    ProductTitle.setText(documentSnapshot.get("product_title").toString());
                    ProductPrice.setText("Rs."+documentSnapshot.get("product_price").toString()+"/-");
                    CuttedPrice.setText("Rs."+documentSnapshot.get("cuttedPrice").toString()+"/-");

                   if((boolean)documentSnapshot.get("use_tab_layout")){
                      productDetailsTabLabscontainer.setVisibility(View.VISIBLE);
                      productDetailsOnlycontainer.setVisibility(View.GONE);
                      productDescription=documentSnapshot .get("product_description").toString();
                      productOtherDetails=documentSnapshot.get("product_other_details").toString();
                     //  ProductSpecificationFragment.productSpecificationModelList.add();
                      for(long x=1; x<(long)documentSnapshot.get("total_spec_titles")+1;x++){
                        productSpecificationModelList.add(new ProductSpecificationModel(0,documentSnapshot.get("spec_title_"+x).toString()));
                          for(long y=1;y<(long)documentSnapshot.get("spec_title_"+x+"_total_fields")+1; y++){

                              productSpecificationModelList.add(new ProductSpecificationModel(1,
                                      documentSnapshot.get("spec_title_"+x+"_field_"+y+"_name").toString(),
                                      documentSnapshot.get("spec_title_"+x+"_field_"+y+"_value").toString()));


                          }

                      }


                   }else{
                       productDetailsTabLabscontainer.setVisibility(View.GONE);
                       productDetailsOnlycontainer.setVisibility(View.VISIBLE);
                       productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());
                   }

                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(),productDescription,productOtherDetails,productSpecificationModelList));


                }else{
                    String error=task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this,error,Toast.LENGTH_SHORT).show();
                }
            }
        });

       // List<String> productImages = new ArrayList<>();






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
        addtoCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////add to cart
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
        if (id == R.id.my_cart) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}