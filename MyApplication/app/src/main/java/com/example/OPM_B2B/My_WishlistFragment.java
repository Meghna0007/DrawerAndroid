package com.example.OPM_B2B;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link My_WishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class My_WishlistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public My_WishlistFragment() {
        // Required empty public constructor
    }
private RecyclerView wishlist_RecyclerView;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment My_WishlistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static My_WishlistFragment newInstance(String param1, String param2) {
        My_WishlistFragment fragment = new My_WishlistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_my__wishlist, container, false);
        wishlist_RecyclerView=view.findViewById(R.id.wishlistRecyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlist_RecyclerView.setLayoutManager(linearLayoutManager);

        List<WishlistModel> wishlistModelList = new ArrayList<>();
        /*wishlistModelList.add(new WishlistModel(R.drawable.product, "Maggi", "12", "11", "Cash On Delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.product, "Noodles", "13", "11", "Cash On Delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.product, "Pasta", "14", "11", "Cash On Delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.product, "Maggi", "12", "11", "Cash On Delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.product, "Noodles", "13", "11", "Cash On Delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.product, "Pasta", "14", "11", "Cash On Delivery"));

        wishlistModelList.add(new WishlistModel(R.drawable.product, "Maggi", "12", "11", "Cash On Delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.product, "Noodles", "13", "11", "Cash On Delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.product, "Pasta", "14", "11", "Cash On Delivery"));*/

        WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList,true);
        wishlist_RecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();

        return view;
    }
}