package com.opm.b2b;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.opm.b2b.Main4Activity.showCart;
import static com.opm.b2b.RegisterActivity.setSignUpFragment;

public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager ProductImageViewPager;
    private DocumentSnapshot documentSnapshot;
    private TextView ProductTitle ;
    private TextView ProductPrice;
    private TextView CuttedPrice;
    private TabLayout viewpagerIndicate;
    public static FloatingActionButton addtoWishlistBtn;
    public   static  boolean ALREADY_ADDED_TO_WISHLIST =false;
    private ConstraintLayout productDetailsOnlycontainer;
    private  ConstraintLayout productDetailsTabLabscontainer;
    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    private String productDescription;
    private String productOtherDetails;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private Button buyNowBtn;
    private LinearLayout addtoCartBtn;
    private Dialog signinDialog;
    private FirebaseFirestore firebaseFirestore;
    private TextView productOnlyDescriptionBody;
    private FirebaseUser currentUser;
    private String productId;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buyNowBtn = findViewById(R.id.buy_now_button);
        ProductTitle = findViewById(R.id.ProductTitle);
        ProductPrice = findViewById(R.id.main_price);
        CuttedPrice = findViewById(R.id.cutted_price);
        ProductImageViewPager = findViewById(R.id.product_images_lviewpager);
        viewpagerIndicate = findViewById(R.id.viewPager_indicater);

        addtoWishlistBtn = findViewById(R.id.wishlistt);
        productOnlyDescriptionBody = findViewById(R.id.product_detailsBody);
        productDetailsViewPager = findViewById(R.id.product_details_viewPager);
        productDetailsTabLayout = findViewById(R.id.product_details_tablayout);
        productDetailsTabLabscontainer = findViewById(R.id.product_details_tabs_container);
        productDetailsOnlycontainer = findViewById(R.id.product_detailsContainer);
        addtoCartBtn = findViewById(R.id.AddToCart);
        //////////////Loading Dialog
        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //////////////Loading Dialog

        firebaseFirestore = FirebaseFirestore.getInstance();
        List<String> productImages = new ArrayList<>();
        productId = getIntent().getStringExtra("product_id_");
        firebaseFirestore.collection("PRODUCTS").document(productId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    documentSnapshot = task.getResult();

                    for (long x = 1; x < (long) documentSnapshot.get("no_of_product_images") + 1; x++) {

                        productImages.add(documentSnapshot.get("product_image_" + x).toString());
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    ProductImageViewPager.setAdapter(productImagesAdapter);

                    ProductTitle.setText(documentSnapshot.get("product_title").toString());
                    ProductPrice.setText("Rs." + documentSnapshot.get("product_price").toString() + "/-");
                    CuttedPrice.setText("Rs." + documentSnapshot.get("cuttedPrice").toString() + "/-");

                    if ((boolean) documentSnapshot.get("use_tab_layout")) {
                        productDetailsTabLabscontainer.setVisibility(View.VISIBLE);
                        productDetailsOnlycontainer.setVisibility(View.GONE);
                        productDescription = documentSnapshot.get("product_description").toString();
                        productOtherDetails = documentSnapshot.get("product_other_details").toString();
                        //  ProductSpecificationFragment.productSpecificationModelList.add();
                        for (long x = 1; x < (long) documentSnapshot.get("total_spec_titles") + 1; x++) {
                            productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + x).toString()));
                            for (long y = 1; y < (long) documentSnapshot.get("spec_title_" + x + "_total_fields") + 1; y++) {

                                productSpecificationModelList.add(new ProductSpecificationModel(1,
                                        documentSnapshot.get("spec_title_" + x + "_field_" + y + "_name").toString(),
                                        documentSnapshot.get("spec_title_" + x + "_field_" + y + "_value").toString()));


                            }

                        }


                    } else {
                        productDetailsTabLabscontainer.setVisibility(View.GONE);
                        productDetailsOnlycontainer.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());
                    }

                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModelList));

                    if (currentUser != null) {
                        if (DBqueries.wishlist.size() == 0) {
                            DBqueries.loadWishlist(ProductDetailsActivity.this,loadingDialog,false);
                        } else {
                            loadingDialog.dismiss();
                        }


                    }else {
                        loadingDialog.dismiss();
                    }


                    if (DBqueries.wishlist.contains(productId)) {
                        ALREADY_ADDED_TO_WISHLIST = true;
                        addtoWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.red));
                    } else {
                        ALREADY_ADDED_TO_WISHLIST = false;
                    }


                } else {
                    loadingDialog.dismiss();
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewpagerIndicate.setupWithViewPager(ProductImageViewPager, true);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        addtoWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (currentUser == null) {
                    signinDialog.show();
                } else {
                      addtoWishlistBtn.setEnabled(false);
                    if (ALREADY_ADDED_TO_WISHLIST) {
                        int index = DBqueries.wishlist.indexOf(productId);
                        DBqueries.removeFromWishlist(index, ProductDetailsActivity.this);
                        addtoWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                    } else {
                        addtoWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.red));

                        Map<String, Object> addProduct = new HashMap<>();
                        addProduct.put("product_id_" + String.valueOf(DBqueries.wishlist.size()), productId);


                        firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                .set(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Map<String, Object> updatListSize = new HashMap<>();
                                    updatListSize.put("list_size", (long) (DBqueries.wishlist.size() + 1));
                                    firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA")
                                            .document("MY_WISHLIST")
                                            .update(updatListSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                if (DBqueries.wishlistModelList.size() != 0) {
                                                    DBqueries.wishlistModelList.add(new WishlistModel(documentSnapshot.get("product_image_1").toString(),
                                                            documentSnapshot.get("product_title").toString(),
                                                            documentSnapshot.get("cuttedPrice").toString(),
                                                            documentSnapshot.get("product_price").toString(),
                                                            documentSnapshot.get("setPiece_").toString(),
                                                            documentSnapshot.get("perPiece_").toString(),
                                                            documentSnapshot.get("productWeight_").toString()));
                                                }
                                                ALREADY_ADDED_TO_WISHLIST = true;
                                                addtoWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.red));
                                                DBqueries.wishlist.add(productId);
                                                Toast.makeText(ProductDetailsActivity.this, "Added to wishlist successfully!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                addtoWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                                String error = task.getException().getMessage();
                                                Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                            }
                                            addtoWishlistBtn.setEnabled(true);
                                        } // onComplete closed
                                    }); // onCompleteListener closed
                                } // if task success closed
                                else {
                                    addtoWishlistBtn.setEnabled(true);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } // else aready added to wishlist false

                    //ALREADY_ADDED_TO_WISHLIST=true;
                    //addtoWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.red));

                } // else not current user null closed
            }  // onClick wishlist button closed
        }); // onclick listener

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
                if (currentUser == null) {

                    signinDialog.show();

                } else {
                    Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }
            }
        });

        addtoCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signinDialog.show();
                } else {
                    // Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                    //  startActivity(deliveryIntent);
                }
            }
        });
        signinDialog = new Dialog(ProductDetailsActivity.this);
        signinDialog.setContentView(R.layout.signin_dialog);
        signinDialog.setCancelable(true);
        signinDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogCompleteProfileBtn = signinDialog.findViewById(R.id.CompleteYourProfile_button);
        Button dialogSignup = signinDialog.findViewById(R.id.Signup_button);
        Intent registerIntent = new Intent(ProductDetailsActivity.this, RegisterActivity.class);
        dialogCompleteProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });
        dialogSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });
        /* }});}*/
    } //onCreate of Activity closed


    @Override
    protected void onStart() {
        super.onStart();
        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null) {
            if (DBqueries.wishlist.size() == 0) {
                DBqueries.loadWishlist(ProductDetailsActivity.this,loadingDialog,false);
            } else {
                loadingDialog.dismiss();
            }
        }else{
            loadingDialog.dismiss();
        }
        if(DBqueries.wishlist.contains(productId)){
            ALREADY_ADDED_TO_WISHLIST=true;
            addtoWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.red));

        }else{
            ALREADY_ADDED_TO_WISHLIST=false;
        }
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
            if(currentUser==null){
                signinDialog.show();
            }else {
                Intent cartIntent=new Intent(ProductDetailsActivity.this,Main4Activity.class);
                showCart=true;
                startActivity(cartIntent);
                return true;
            }}
        return super.onOptionsItemSelected(item);
    }

}