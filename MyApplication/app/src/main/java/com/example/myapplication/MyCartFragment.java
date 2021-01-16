package com.example.myapplication;

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
 * Use the {@link MyCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCartFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public MyCartFragment() {
        // Required empty public constructor
    }
private RecyclerView cartItemRecyclerView;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCartFragment.
     */

    public static MyCartFragment newInstance(String param1, String param2) {
        MyCartFragment fragment = new MyCartFragment();
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
        View view= inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartItemRecyclerView=view.findViewById(R.id.cart_items_recylerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);
        List<CartItemModel>cartItemModelList=new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.product,1,"Apple","Rs.499/-","Rs.600/-"));
        cartItemModelList.add(new CartItemModel(0,R.drawable.g1,1,"Mango","Rs.499/-","Rs.600/-"));
        cartItemModelList.add(new CartItemModel(0,R.drawable.product,1,"Grapes","Rs.499/-","Rs.600/-"));
       cartItemModelList.add(new CartItemModel(1,"Price (3 items)","Rs.599/-","Free","Rs.100/-","Rs.499"));

        CartAdapter cartAdapter=new CartAdapter(cartItemModelList);
       cartItemRecyclerView.setAdapter(cartAdapter);
       cartAdapter.notifyDataSetChanged();



        return view;
    }
}