package com.opm.b2b.ui.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.opm.b2b.CategoryAdapter;;
import com.opm.b2b.CategoryModel;
import com.opm.b2b.CategoryPageAdapter;
import com.opm.b2b.CategoryPageModel;
import com.opm.b2b.DBqueries;
import com.opm.b2b.HorizonantleProductScrollModel;
import com.opm.b2b.Main4Activity;
import com.opm.b2b.R;
import com.opm.b2b.SliderModel;

import java.util.ArrayList;
import java.util.List;

import static com.opm.b2b.DBqueries.categoryModelList;

import static com.opm.b2b.DBqueries.lists;
import static com.opm.b2b.DBqueries.loadCategories;
import static com.opm.b2b.DBqueries.loadFragmentData;
import static com.opm.b2b.DBqueries.loadedCategoriesNames;

public class AllCategoriesFragment extends Fragment {
    private List<CategoryModel> categoryModelFakeList = new ArrayList<>();
    private List<CategoryPageModel> categoryPageModelFakeList = new ArrayList<>();
    public static SwipeRefreshLayout swipeRefreshLayout;
    private CategoryPageAdapter adapter;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;
    private ImageView noInternetConnection;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private Button retryBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_all_categories, container, false);

        swipeRefreshLayout = root.findViewById(R.id.refresh_layout);

        noInternetConnection = root.findViewById(R.id.no_internet_connection);
        homePageRecyclerView = root.findViewById(R.id.home_page_recylerView);
        categoryRecyclerView = root.findViewById(R.id.all_categories_recyclerview);
        retryBtn = root.findViewById(R.id.retryButton);
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.blue), getContext().getResources().getColor(R.color.blue), getContext().getResources().getColor(R.color.blue));


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);


///////////////categories fake list
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
///////////////categories fake list

        /////////////////home page fake list

        List<SliderModel> sliderModelsFakeList = new ArrayList<>();
        sliderModelsFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelsFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelsFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelsFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelsFakeList.add(new SliderModel("null", "#dfdfdf"));

        List<HorizonantleProductScrollModel> HorizonantleProductScrollModelFakeList = new ArrayList<>();
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("", "", "", "", ""));
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("", "", "", "", ""));
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("", "", "", "", ""));
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("", "", "", "", ""));
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("", "", "", "", ""));
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("", "", "", "", ""));
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("", "", "", "", ""));

        categoryPageModelFakeList.add(new CategoryPageModel(0, sliderModelsFakeList));
        categoryPageModelFakeList.add(new CategoryPageModel(1, "", "#dfdfdf"));
        categoryPageModelFakeList.add(new CategoryPageModel(2, "", "#dfdfdf", HorizonantleProductScrollModelFakeList, new ArrayList<>()));
        categoryPageModelFakeList.add(new CategoryPageModel(3, "", "#dfdfdf", HorizonantleProductScrollModelFakeList));

        /////////////////home page fake list
        categoryAdapter = new CategoryAdapter(categoryModelFakeList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        adapter = new CategoryPageAdapter(categoryPageModelFakeList);


        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() == true) {
            Main4Activity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            if (categoryModelList.size() == 0) {
                loadCategories(categoryRecyclerView, getContext());
            } else {
                categoryAdapter = new CategoryAdapter(categoryModelList);
                categoryAdapter.notifyDataSetChanged();
            }

            categoryRecyclerView.setAdapter(categoryAdapter);
            if (lists.size() == 0) {
                loadedCategoriesNames.add("FMCG");
                lists.add(new ArrayList<CategoryPageModel>());
                loadFragmentData(homePageRecyclerView, getContext(), 0, "Fmcg");
            } else {
                adapter = new CategoryPageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);


        } else {
            Main4Activity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_interneet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
        }
////////////////////////////////refresh

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reloadPage();

            }
        });
/////////////////////refresh
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reloadPage();
            }
        });

        return root;
    }

    private void reloadPage() {

        networkInfo = connectivityManager.getActiveNetworkInfo();
        /*categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();*/
        DBqueries.clearData();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            Main4Activity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);

            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            categoryAdapter = new CategoryAdapter(categoryModelFakeList);
            adapter = new CategoryPageAdapter(categoryPageModelFakeList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            homePageRecyclerView.setAdapter(adapter);

            loadCategories(categoryRecyclerView, getContext());

            loadedCategoriesNames.add("FMCG");
            lists.add(new ArrayList<CategoryPageModel>());
            loadFragmentData(homePageRecyclerView, getContext(), 0, "Fmcg");

        } else {
            Main4Activity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            Toast.makeText(getContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(getContext()).load(R.drawable.no_interneet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);

            swipeRefreshLayout.setRefreshing(false);

        }

    }
}