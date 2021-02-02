package com.opm.b2b.ui.slideshow;

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

import com.opm.b2b.R;


public class PoliciesFragment extends Fragment {

    private PoliciesViewModel policiesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        policiesViewModel =
                new ViewModelProvider(this).get(PoliciesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_policies, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        policiesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}