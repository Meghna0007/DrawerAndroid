package com.opm.b2b;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBqueries {

   public static FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList=new ArrayList<>();
   public static List<CategoryPageModel> categoryPageModelList = new ArrayList<>();

    public static void loadCategories(CategoryAdapter categoryAdapter, Context context){

        firebaseFirestore.collection("CATEGORIES").orderBy("index")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult()){
                        categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString(),
                                documentSnapshot.get("displayName").toString()));
                    }
                    categoryAdapter.notifyDataSetChanged();

                } else {
                    String error=task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public static void loadFragmentData(CategoryPageAdapter adapter,Context context){
        firebaseFirestore.collection("CATEGORIES").document("FMCG").collection("TOP_DEALS").orderBy("index").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult()) {
                        // categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));
                        if ((long) documentSnapshot.get("view_type") == 0) {
                            List<SliderModel> sliderModelList = new ArrayList<>();
                            long no_of_banners = (long) documentSnapshot.get("no_of_banners");
                            for (long x = 1; x < no_of_banners+1; x++) {
                                sliderModelList.add(new SliderModel(documentSnapshot.get("banner_" + x).toString(), documentSnapshot.get("banner_" + x + "_bg").toString()));
                            }
                            categoryPageModelList.add(new CategoryPageModel(0, sliderModelList));

                        } else if ((long) documentSnapshot.get("view_type") == 1) {
                            categoryPageModelList.add(new CategoryPageModel(1, documentSnapshot.get("strip_ad_banner").toString(), documentSnapshot.get("background").toString()));


                        } else if ((long) documentSnapshot.get("view_type") == 2) {

                            List<HorizonantleProductScrollModel>horizonantleProductScrollModelList=new ArrayList<>();
                            long no_of_products = (long) documentSnapshot.get("no_of_products");
                            for (long x = 1; x < no_of_products+1; x++) {
                                horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(documentSnapshot.get("product_id_" + x).toString(), documentSnapshot.get("product_image_" + x).toString(),documentSnapshot.get("product_title_" + x).toString(),documentSnapshot.get("product_subtitle_" + x).toString(),documentSnapshot.get("product_price_" + x).toString()));
                            }
                            categoryPageModelList.add(new CategoryPageModel(2,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),horizonantleProductScrollModelList));

                        } else if ((long) documentSnapshot.get("view_type") == 3) {

                            List<HorizonantleProductScrollModel>GridLayoutModelList=new ArrayList<>();
                            long no_of_products = (long) documentSnapshot.get("no_of_products");
                            for (long x = 1; x < no_of_products+1; x++) {
                               /* String productPrice = documentSnapshot.get("product_price_" + x).toString();
                                if (!productPrice.contains("Rs")) {
                                    productPrice = "Rs." +productPrice +"/-";
                                }*/
                                GridLayoutModelList.add(new HorizonantleProductScrollModel(documentSnapshot.get("product_id_" + x).toString(),
                                        documentSnapshot.get("product_image_" + x).toString(),
                                        documentSnapshot.get("product_title_" + x).toString(),
                                        documentSnapshot.get("product_subtitle_" + x).toString(),
                                        documentSnapshot.get("product_price_" + x).toString()));
                            }
                            categoryPageModelList.add(new CategoryPageModel(3,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),GridLayoutModelList));



                        }


                    }
                    adapter.notifyDataSetChanged();

                } else {
                    String error=task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
