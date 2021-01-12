package com.example.myapplication;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {
 private List<HorizonantleProductScrollModel> horizonantleProductScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizonantleProductScrollModel> horizonantleProductScrollModelList) {
        this.horizonantleProductScrollModelList = horizonantleProductScrollModelList;
    }


    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {

        int resource=horizonantleProductScrollModelList.get(position).getProductImage();
       String title=horizonantleProductScrollModelList.get(position).getProductTitle();
        String description=horizonantleProductScrollModelList.get(position).getProductdescription();
        String price=horizonantleProductScrollModelList.get(position).getProductprice();

        holder.setProductImage(resource);
        holder.setProductTitle(title);
        holder.setProductDescription(description);
        holder.setProductPrice(price);


    }

    @Override
    public int getItemCount() {
        return horizonantleProductScrollModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      private ImageView productImage;
      private TextView productTitle;
        private TextView productDescription;
        private TextView productPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.hS_product_Image);
            productTitle=itemView.findViewById(R.id.hS_product_title);
            productDescription=itemView.findViewById(R.id.hS_product_Description);
            productPrice=itemView.findViewById(R.id.hS_product_Price);

        }

        private void setProductImage(int resource){
            productImage.setImageResource(resource);
        }
        private void setProductTitle(String title){
            productTitle.setText(title);
        }
        private void setProductDescription(String description){
            productDescription.setText(description);
        }
        private void setProductPrice(String price){
            productPrice.setText(price);
        }

    }
}
