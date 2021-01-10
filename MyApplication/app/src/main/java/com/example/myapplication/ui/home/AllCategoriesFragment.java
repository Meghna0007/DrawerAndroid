package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CategoryAdapter;
import com.example.myapplication.CategoryModel;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AllCategoriesFragment extends Fragment {

    private AllCategoriesViewModel allCategoriesViewModel;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        allCategoriesViewModel =
                new ViewModelProvider(this).get(AllCategoriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_all_categories, container, false);



        /*final TextView textView = root.findViewById(R.id.text_home);
        allCategoriesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


        recyclerView = root.findViewById(R.id.all_categories_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel(R.drawable.logo, "Foods"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Biscuit"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Namkin"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Noodles and Pasta"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Beverages"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Dairy"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Kitchen"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Chocolate"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "PersonalCare"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Soap"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        return root;
    }
}