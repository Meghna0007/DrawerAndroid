package com.opm.b2b;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridProductViewAdapter extends BaseAdapter {
    List<HorizonantleProductScrollModel> horizonantleProductScrollModelList;

    public GridProductViewAdapter(List<HorizonantleProductScrollModel> horizonantleProductScrollModelList) {
        this.horizonantleProductScrollModelList = horizonantleProductScrollModelList;
    }

    @Override
    public int getCount() {
        return horizonantleProductScrollModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent=new Intent(parent.getContext(),ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("product_id_",horizonantleProductScrollModelList.get(position).getProductid());
                    parent.getContext().startActivity(productDetailsIntent);
                }
            });


            ImageView productImage = view.findViewById(R.id.hS_product_Image);
            TextView productTitle = view.findViewById(R.id.hS_product_title);
            TextView productDescription = view.findViewById(R.id.hS_product_Description);
            TextView productPrice = view.findViewById(R.id.hS_product_Price);

            Glide.with(parent.getContext()).load(horizonantleProductScrollModelList.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.fksmall)).into(productImage);
            productTitle.setText(horizonantleProductScrollModelList.get(position).getProductTitle());
            productDescription.setText(horizonantleProductScrollModelList.get(position).getProductdescription());
            productPrice.setText("Rs."+horizonantleProductScrollModelList.get(position).getProductprice()+"/-");
        } else {
            view = convertView;
        }
        return view;
    }
}
