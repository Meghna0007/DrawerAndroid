 package com.opm.b2b;
import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.opm.b2b.ui.home.AllCategoriesFragment;

import java.util.ArrayList;
import java.util.List;


   public class DBqueries {

       public static FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
       public static List<CategoryModel> categoryModelList=new ArrayList<>();
       public static List<List<CategoryPageModel>>lists=new ArrayList<>();
       public static List<String>loadedCategoriesNames=new ArrayList<>();

       public static void loadCategories(RecyclerView categoryRecyclerView, Context context){
                 firebaseFirestore.collection("CATEGORIES").orderBy("index")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                             for(QueryDocumentSnapshot documentSnapshot :task.getResult()){
                                 categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),
                                         documentSnapshot.get("categoryName").toString(),documentSnapshot.get("displayName").toString()));}
                                CategoryAdapter categoryAdapter=new CategoryAdapter(categoryModelList);
                             categoryRecyclerView.setAdapter(categoryAdapter);
                                 categoryAdapter.notifyDataSetChanged();
                        }else{ String error=task.getException().getMessage();
                               Toast.makeText(context,error,Toast.LENGTH_SHORT).show();}}});}

    public static void loadFragmentData(final RecyclerView CategoryPageRecyclerView,final Context context, final int index, String categoryName){
        firebaseFirestore.collection("CATEGORIES")
                .document(categoryName.toUpperCase())
                .collection("TOP_DEALS")
                .orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult()) {
                        // categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));
                        if ((long) documentSnapshot.get("view_type") == 0) {
                            List<SliderModel> sliderModelList = new ArrayList<>();
                            long no_of_banners = (long) documentSnapshot.get("no_of_banners");
                            for (long x = 1; x < no_of_banners+1; x++) {
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
                           List<WishlistModel>viewAllProductList=new ArrayList<>();
                            List<HorizonantleProductScrollModel>horizonantleProductScrollModelList=new ArrayList<>();
                            long no_of_products = (long) documentSnapshot.get("no_of_products");
                            for (long x = 1; x < no_of_products+1; x++) {
                                horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(
                                        documentSnapshot.get("product_id_" + x).toString()
                                        ,documentSnapshot.get("product_image_" + x).toString()
                                        ,documentSnapshot.get("product_title_" + x).toString()
                                        ,documentSnapshot.get("product_subtitle_" + x).toString()
                                        ,documentSnapshot.get("product_price_" + x).toString()));

                               viewAllProductList.add(new WishlistModel(documentSnapshot.get("product_image_"+x).toString(),
                                       documentSnapshot.get("productTitle_"+x).toString(),
                                       documentSnapshot.get("cuttedPrice_"+x).toString(),
                                       documentSnapshot.get("product_price_"+x).toString(),
                                       documentSnapshot.get("setPiece_"+x).toString(),
                                       documentSnapshot.get("perPiece_"+x).toString(),
                                       documentSnapshot.get("productWeight_"+x).toString()));
                            }
                            lists.get(index).add(new CategoryPageModel(2,
                                    documentSnapshot.get("layout_title").toString()
                                    ,documentSnapshot.get("layout_background").toString(),
                                    horizonantleProductScrollModelList,viewAllProductList));
                        }else if ((long) documentSnapshot.get("view_type") == 3) {
                            List<HorizonantleProductScrollModel>GridLayoutModelList=new ArrayList<>();
                            long no_of_products = (long) documentSnapshot.get("no_of_products");
                            for (long x = 1; x < no_of_products+1; x++) {

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
                    CategoryPageAdapter categoryPageAdapter=new CategoryPageAdapter(lists.get(index));
                    CategoryPageRecyclerView.setAdapter(categoryPageAdapter);
                    categoryPageAdapter.notifyDataSetChanged();
                    AllCategoriesFragment.swipeRefreshLayout.setRefreshing(false);
                   }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show(); }}});}}
