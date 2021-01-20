package com.example.OPM_B2B.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.OPM_B2B.CategoryAdapter;
import com.example.OPM_B2B.CategoryModel;
import com.example.OPM_B2B.CategoryPageAdapter;
import com.example.OPM_B2B.CategoryPageModel;
import com.example.OPM_B2B.HorizonantleProductScrollModel;
import com.example.OPM_B2B.R;
import com.example.OPM_B2B.SliderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllCategoriesFragment extends Fragment {


    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView testing;
    private List<CategoryModel> categoryModelList;
    private FirebaseFirestore firebaseFirestore;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_all_categories, container, false);



        /*final TextView textView = root.findViewById(R.id.text_home);
        allCategoriesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


        categoryRecyclerView = root.findViewById(R.id.all_categories_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        categoryModelList = new ArrayList<CategoryModel>();

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("CATEGORIES").orderBy("index")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                  for(QueryDocumentSnapshot documentSnapshot :task.getResult()){
                      categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));
                  }
                    categoryAdapter.notifyDataSetChanged();

                } else {
                    String error=task.getException().getMessage();
                    Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                }
            }
        });




      /*  categoryModelList.add(new CategoryModel(R.drawable.logo, "Foods"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Biscuit"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Namkin"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Noodles and Pasta"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Beverages"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Dairy"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Kitchen"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Chocolate"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "PersonalCare"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Soap"));*/


        ///////////Baner Slider

        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.g1, "black"));
        sliderModelList.add(new SliderModel(R.drawable.g2, "black"));
        sliderModelList.add(new SliderModel(R.drawable.g1, "black"));


        sliderModelList.add(new SliderModel(R.drawable.g2, "black"));
        sliderModelList.add(new SliderModel(R.drawable.g3, "black"));
        sliderModelList.add(new SliderModel(R.drawable.g4, "black"));
        sliderModelList.add(new SliderModel(R.drawable.grocery, "black"));
        sliderModelList.add(new SliderModel(R.drawable.g1, "black"));
        sliderModelList.add(new SliderModel(R.drawable.g3, "black"));


        sliderModelList.add(new SliderModel(R.drawable.g4, "green"));
        sliderModelList.add(new SliderModel(R.drawable.g3, "green"));
        sliderModelList.add(new SliderModel(R.drawable.g4, "green"));

        ////////////////horizon


        List<HorizonantleProductScrollModel> horizonantleProductScrollModelList = new ArrayList<>();
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));


        ////////////////horizon


        ////////////////////////////////////////////
        testing = root.findViewById(R.id.home_page_recylerView);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);
        List<CategoryPageModel> categoryPageModelList = new ArrayList<>();
        categoryPageModelList.add(new CategoryPageModel(0, sliderModelList));
        categoryPageModelList.add(new CategoryPageModel(1, R.drawable.g4, "#000000"));
        categoryPageModelList.add(new CategoryPageModel(2, "Deals of the day", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Biscuit", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Namkin", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Noodles&Pasta", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Beverges", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Dairy", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Choclate", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Personal Care", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Soaps", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Foods", horizonantleProductScrollModelList));
        CategoryPageAdapter adapter = new CategoryPageAdapter(categoryPageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        ////////////////////////////////////////


        return root;
    }

}