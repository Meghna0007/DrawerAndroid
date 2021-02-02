package com.opm.b2b.ui.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.opm.b2b.CategoryAdapter;;
import com.opm.b2b.CategoryPageAdapter;
import com.opm.b2b.R;

import static com.opm.b2b.DBqueries.categoryModelList;
import static com.opm.b2b.DBqueries.categoryPageModelList;
import static com.opm.b2b.DBqueries.loadCategories;
import static com.opm.b2b.DBqueries.loadFragmentData;

public class AllCategoriesFragment extends Fragment {
    private CategoryPageAdapter adapter;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;
    private ImageView noInternetConnection;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_all_categories, container, false);
        noInternetConnection=root.findViewById(R.id.no_internet_connection);
        ConnectivityManager connectivityManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected()==true){
            noInternetConnection.setVisibility(View.GONE);
            categoryRecyclerView = root.findViewById(R.id.all_categories_recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoryRecyclerView.setLayoutManager(layoutManager);
            categoryAdapter = new CategoryAdapter(categoryModelList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            if(categoryModelList.size()==0){
                loadCategories(categoryAdapter,getContext());
            }else {
                categoryAdapter.notifyDataSetChanged();
            }
            homePageRecyclerView = root.findViewById(R.id.home_page_recylerView);
            LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
            testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            homePageRecyclerView.setLayoutManager(testingLayoutManager);
            adapter = new CategoryPageAdapter(categoryPageModelList);
            homePageRecyclerView.setAdapter(adapter);
            if(categoryPageModelList.size()==0){
                loadFragmentData(adapter,getContext());
            }else {
                categoryAdapter.notifyDataSetChanged();
            }

        }else {
            Glide.with(this).load(R.drawable.no_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
        }

        return root;
    }
}