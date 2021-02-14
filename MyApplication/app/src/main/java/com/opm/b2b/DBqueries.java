package com.opm.b2b;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.opm.b2b.ui.home.AllCategoriesFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBqueries {

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();
    public static List<List<CategoryPageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesNames = new ArrayList<>();
    public static List<String> wishlist = new ArrayList<>();
    public static List<WishlistModel> wishlistModelList = new ArrayList<>();
    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartItemModelList = new ArrayList<>();
    public static Integer cartListCount = cartList.size();

    public static void loadCategories(RecyclerView categoryRecyclerView, Context context) {
        categoryModelList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),
                                documentSnapshot.get("categoryName").toString(), documentSnapshot.get("displayName").toString()));
                    }
                    CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                    categoryRecyclerView.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void loadFragmentData(final RecyclerView CategoryPageRecyclerView, final Context context, final int index, String categoryName) {
        firebaseFirestore.collection("CATEGORIES")
                .document(categoryName.toUpperCase())
                .collection("TOP_DEALS")
                .orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                // categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));
                                if ((long) documentSnapshot.get("view_type") == 0) {
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_banners = (long) documentSnapshot.get("no_of_banners");
                                    for (long x = 1; x < no_of_banners + 1; x++) {
                                        sliderModelList.add(new SliderModel(
                                                documentSnapshot.get("banner_" + x).toString(),
                                                documentSnapshot.get("banner_" + x + "_bg").toString()));
                                    }
                                    lists.get(index).add(new CategoryPageModel(0, sliderModelList));

                                } else if ((long) documentSnapshot.get("view_type") == 1) {
                                    lists.get(index).add(new CategoryPageModel(1,
                                            documentSnapshot.get("strip_ad_banner").toString(),
                                            documentSnapshot.get("background").toString()));
                                } else if ((long) documentSnapshot.get("view_type") == 2) {
                                    List<WishlistModel> viewAllProductList = new ArrayList<>();
                                    List<HorizonantleProductScrollModel> horizonantleProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long x = 1; x < no_of_products + 1; x++) {
                                        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(
                                                documentSnapshot.get("product_id_" + x).toString()
                                                , documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_title_" + x).toString()
                                                , documentSnapshot.get("product_subtitle_" + x).toString()
                                                , documentSnapshot.get("product_price_" + x).toString()));

                                        viewAllProductList.add(new WishlistModel(documentSnapshot.get("product_id_" + x).toString(), documentSnapshot.get("product_image_" + x).toString(),
                                                documentSnapshot.get("productTitle_" + x).toString(),
                                                documentSnapshot.get("cuttedPrice_" + x).toString(),
                                                documentSnapshot.get("product_price_" + x).toString(),
                                                documentSnapshot.get("setPiece_" + x).toString(),
                                                documentSnapshot.get("perPiece_" + x).toString(),
                                                documentSnapshot.get("productWeight_" + x).toString()));
                                    }
                                    lists.get(index).add(new CategoryPageModel(2,
                                            documentSnapshot.get("layout_title").toString()
                                            , documentSnapshot.get("layout_background").toString(),
                                            horizonantleProductScrollModelList, viewAllProductList));
                                } else if ((long) documentSnapshot.get("view_type") == 3) {
                                    List<HorizonantleProductScrollModel> GridLayoutModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long x = 1; x < no_of_products + 1; x++) {

                                        GridLayoutModelList.add(new HorizonantleProductScrollModel(
                                                documentSnapshot.get("product_id_" + x).toString(),
                                                documentSnapshot.get("product_image_" + x).toString(),
                                                documentSnapshot.get("product_title_" + x).toString(),
                                                documentSnapshot.get("product_subtitle_" + x).toString(),
                                                documentSnapshot.get("product_price_" + x).toString()));
                                    }
                                    lists.get(index).add(new CategoryPageModel(3,
                                            documentSnapshot.get("layout_title").toString(),
                                            documentSnapshot.get("layout_background").toString(),
                                            GridLayoutModelList));
                                }
                            }
                            CategoryPageAdapter categoryPageAdapter = new CategoryPageAdapter(lists.get(index));
                            CategoryPageRecyclerView.setAdapter(categoryPageAdapter);
                            categoryPageAdapter.notifyDataSetChanged();
                            AllCategoriesFragment.swipeRefreshLayout.setRefreshing(false);
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadWishlist(final Context context, final Dialog dialog, final boolean loadProductData) {
        wishlist.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Object wishListSize = task.getResult().get("list_size");
                    if (wishListSize != null) {
                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                            wishlist.add(task.getResult().get("product_id_" + x).toString());

                            if (DBqueries.wishlist.contains(ProductDetailsActivity.productId)) {
                                ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
                                if (ProductDetailsActivity.addtoWishlistBtn != null) {
                                    ProductDetailsActivity.addtoWishlistBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.red));
                                }
                            } else {
                                if (ProductDetailsActivity.addtoWishlistBtn != null) {
                                    ProductDetailsActivity.addtoWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                }
                                ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                            }

                            if (loadProductData) {
                                wishlistModelList.clear();
                                String productId = task.getResult().get("product_id_" + x).toString();
                                firebaseFirestore.collection("PRODUCTS").document(productId)
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            wishlistModelList.add(new WishlistModel(productId,
                                                    task.getResult().get("product_image_1").toString(),
                                                    task.getResult().get("product_title").toString(),
                                                    task.getResult().get("cuttedPrice").toString(),
                                                    task.getResult().get("product_price").toString(),
                                                    task.getResult().get("setPiece_").toString(),
                                                    task.getResult().get("perPiece_").toString(),
                                                    task.getResult().get("productWeight_").toString()));
                                            My_WishlistFragment.wishlistAdapter.notifyDataSetChanged();
                                        } else {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }// if wishlist size not null if closed
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeFromWishlist(int index, final Context context) {
        String removeProductId = wishlist.get(index);
        wishlist.remove(index);
        Map<String, Object> updateWishlist = new HashMap<>();
        for (int x = 0; x < wishlist.size(); x++) {
            updateWishlist.put("product_id_" + x, wishlist.get(x));
        }
        updateWishlist.put("list_size", (long) wishlist.size());
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_WISHLIST").set(updateWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (wishlistModelList.size() != 0) {
                        wishlistModelList.remove(index);
                        My_WishlistFragment.wishlistAdapter.notifyDataSetChanged();
                    }
                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                    Toast.makeText(context, "Removed successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    if (ProductDetailsActivity.addtoWishlistBtn != null) {
                        ProductDetailsActivity.addtoWishlistBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.red));
                    }
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                //wishlist.add(index, removeProductId);
                ProductDetailsActivity.running_wishlist_query = false;
            }
        });
    }

    public static void loadCartList(final Context context, final boolean loadProductData, final Dialog dialog, final TextView badgeCount) {
        cartList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                   // Object wishListSize = task.getResult().get("list_size");

                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                            cartList.add(task.getResult().get("product_id_" + x).toString());

                            if (DBqueries.cartList.contains(ProductDetailsActivity.productId)) {
                                ProductDetailsActivity.ALREADY_ADDED_TO_CART = true;

                            } else {

                                ProductDetailsActivity.ALREADY_ADDED_TO_CART = false;
                            }

                            if (loadProductData) {
                                cartItemModelList.clear();
                                String productId = task.getResult().get("product_id_" + x).toString();
                                firebaseFirestore.collection("PRODUCTS").document(productId)
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            int index = 0;
                                            if (cartList.size() >= 2) {
                                                index = cartList.size() - 2;
                                            }
                                            cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productId,
                                                    task.getResult().get("product_image_1").toString(),
                                                    (long) 1,
                                                    task.getResult().get("product_title").toString(),
                                                    task.getResult().get("product_price").toString(),
                                                    task.getResult().get("cuttedPrice").toString()
                                            ));
                                            if (cartList.size() == 1) {
                                                cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                                            }
                                            if (cartList.size() == 0) {
                                                cartItemModelList.clear();
                                            }
                                            MyCartFragment.cartAdapter.notifyDataSetChanged();
                                        } else {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                        if (cartList.size() != 0) {
                            badgeCount.setVisibility(View.VISIBLE);
                        } else {
                            badgeCount.setVisibility(View.INVISIBLE);
                        }
                        if (DBqueries.cartList.size() > 0 && DBqueries.cartList.size() < 99) {
                            badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                        } else if (DBqueries.cartList.size() >= 99)  {
                            badgeCount.setText("99");
                        }

                    // if wishlist size not null if closed
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

        });

    }

    public static void removeFromCart(int index, final Context context) {
        String removeProductId = cartList.get(index);
        cartList.remove(index);
        Map<String, Object> updateCartlist = new HashMap<>();
        for (int x = 0; x < cartList.size(); x++) {
            updateCartlist.put("product_id_" + x, cartList.get(x));
        }
        updateCartlist.put("list_size", (long) cartList.size());
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_CART").set(updateCartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (cartItemModelList.size() != 0) {
                        cartItemModelList.remove(index);
                        MyCartFragment.cartAdapter.notifyDataSetChanged();
                    }
                    if (cartList.size() == 0) {
                        cartItemModelList.clear();
                    }
                    //ProductDetailsActivity.cartItem.setActionView(null);
                    Toast.makeText(context, "Removed successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    cartList.add(index, removeProductId);
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                ProductDetailsActivity.running_cart_query = false;
            }
        });
    }

    public static void clearData() {
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();
        wishlist.clear();
        wishlistModelList.clear();
        cartList.clear();
        cartItemModelList.clear();
    }


    public static String getStringValue(Object object) {
        if (object == null) {
            return "";
        } else {
            return object.toString();
        }
    }
}