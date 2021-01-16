package com.example.myapplication.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.CategoryAdapter;
import com.example.myapplication.CategoryModel;
import com.example.myapplication.CategoryPageAdapter;
import com.example.myapplication.CategoryPageModel;
import com.example.myapplication.GridProductViewAdapter;
import com.example.myapplication.HorizonantleProductScrollModel;
import com.example.myapplication.HorizontalProductScrollAdapter;
import com.example.myapplication.R;
import com.example.myapplication.SliderAdapter;
import com.example.myapplication.SliderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AllCategoriesFragment extends Fragment {


    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView testing;

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
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        ///////////Baner Slider

        List<SliderModel>sliderModelList =new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.g1,"black"));
        sliderModelList.add(new SliderModel(R.drawable.g2,"black"));
        sliderModelList.add(new SliderModel(R.drawable.g1,"black"));




        sliderModelList.add(new SliderModel(R.drawable.g2,"black"));
        sliderModelList.add(new SliderModel(R.drawable.g3,"black"));
        sliderModelList.add(new SliderModel(R.drawable.g4,"black"));
        sliderModelList.add(new SliderModel(R.drawable.grocery,"black"));
        sliderModelList.add(new SliderModel(R.drawable.g1,"black"));
        sliderModelList.add(new SliderModel(R.drawable.g3,"black"));



        sliderModelList.add(new SliderModel(R.drawable.g4,"green"));
        sliderModelList.add(new SliderModel(R.drawable.g3,"green"));
        sliderModelList.add(new SliderModel(R.drawable.g4,"green"));

        ////////////////horizon


        List<HorizonantleProductScrollModel> horizonantleProductScrollModelList=new ArrayList<>();
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));



     ////////////////horizon



        ////////////////////////////////////////////
        testing = root.findViewById(R.id.home_page_recylerView);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);
        List<CategoryPageModel>categoryPageModelList = new ArrayList<>();
        categoryPageModelList.add(new CategoryPageModel(0,sliderModelList));
        categoryPageModelList.add(new CategoryPageModel(1,R.drawable.g4,"#000000"));
        categoryPageModelList.add(new CategoryPageModel(2,"Deals of the day",horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3,"Biscuit",horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3,"Namkin",horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3,"Noodles&Pasta",horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3,"Beverges",horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3,"Dairy",horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3,"Choclate",horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3,"Personal Care",horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3,"Soaps",horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3,"Foods",horizonantleProductScrollModelList));
        CategoryPageAdapter adapter=new CategoryPageAdapter(categoryPageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        ////////////////////////////////////////


        return root;
    }

}