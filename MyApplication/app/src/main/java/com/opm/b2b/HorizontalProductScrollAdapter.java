package com.opm.b2b;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {
    private List<HorizonantleProductScrollModel> horizonantleProductScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizonantleProductScrollModel> horizonantleProductScrollModelList) {
        this.horizonantleProductScrollModelList = horizonantleProductScrollModelList;
    }


    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {

        String resource = horizonantleProductScrollModelList.get(position).getProductImage();
        String title = horizonantleProductScrollModelList.get(position).getProductTitle();
        String description = horizonantleProductScrollModelList.get(position).getProductdescription();
        String price = horizonantleProductScrollModelList.get(position).getProductprice();
          holder.setData(resource,title,description,price);

    }

    @Override
    public int getItemCount() {
        if (horizonantleProductScrollModelList.size() > 8) {
            return 8;
        } else {


            return horizonantleProductScrollModelList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView productDescription;
        private TextView productPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.hS_product_Image);
            productTitle = itemView.findViewById(R.id.hS_product_title);
            productDescription = itemView.findViewById(R.id.hS_product_Description);
            productPrice = itemView.findViewById(R.id.hS_product_Price);


        }

        private void setData(String resource,String title,String description,String price) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.fksmall)).into(productImage);
            productPrice.setText("Rs."+price+"/-");
            productDescription.setText(description);
            productTitle.setText(title);
            if(!title.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        itemView.getContext().startActivity(productDetailsIntent);
                    }
                });
            }
        }
    }
}
