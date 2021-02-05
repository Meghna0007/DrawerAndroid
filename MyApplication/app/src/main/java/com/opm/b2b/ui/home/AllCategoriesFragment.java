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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.opm.b2b.CategoryAdapter;;
import com.opm.b2b.CategoryPageAdapter;
import com.opm.b2b.CategoryPageModel;
import com.opm.b2b.R;

import java.util.ArrayList;

import static com.opm.b2b.DBqueries.categoryModelList;

import static com.opm.b2b.DBqueries.lists;
import static com.opm.b2b.DBqueries.loadCategories;
import static com.opm.b2b.DBqueries.loadFragmentData;
import static com.opm.b2b.DBqueries.loadedCategoriesNames;

public class AllCategoriesFragment extends Fragment {
   public static SwipeRefreshLayout swipeRefreshLayout;
    private CategoryPageAdapter adapter;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;
    private ImageView noInternetConnection;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_all_categories, container, false);
        swipeRefreshLayout=root.findViewById(R.id.refresh_layout);
        noInternetConnection=root.findViewById(R.id.no_internet_connection);
         connectivityManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();

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

            if(lists.size()==0){
                loadedCategoriesNames.add("FMCG");
                lists.add(new ArrayList<CategoryPageModel>());
                adapter = new CategoryPageAdapter(lists.get(0));
                loadFragmentData(adapter,getContext(),0,"Fmcg");
            }else {
                adapter = new CategoryPageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }

            homePageRecyclerView.setAdapter(adapter);

        }else {
            Glide.with(this).load(R.drawable.no__conn).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
        }
////////////////////////////////refresh

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                categoryModelList.clear();
                lists.clear();
                loadedCategoriesNames.clear();
                if(networkInfo !=null && networkInfo.isConnected()==true) {
                    noInternetConnection.setVisibility(View.GONE);


                    loadCategories(categoryAdapter,getContext());

                    loadedCategoriesNames.add("FMCG");
                    lists.add(new ArrayList<CategoryPageModel>());
                    loadFragmentData(adapter,getContext(),0,"Fmcg");

                }else {
                    Glide.with(getContext()).load(R.drawable.no__conn).into(noInternetConnection);
                    noInternetConnection.setVisibility(View.VISIBLE);
                }
            }
        });

        return root;
    }
}