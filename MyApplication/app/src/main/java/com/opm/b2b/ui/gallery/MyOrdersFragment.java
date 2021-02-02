package com.opm.b2b.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.opm.b2b.R;


public class MyOrdersFragment extends Fragment {

    private MyOrdersViewModel myOrdersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myOrdersViewModel =
                new ViewModelProvider(this).get(MyOrdersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);

        //  myOrdersViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
        //    @Override
        // public void onChanged(@Nullable String s) {
        //    textView.setText(s);
        //}
        //   });
        return root;
    }
}