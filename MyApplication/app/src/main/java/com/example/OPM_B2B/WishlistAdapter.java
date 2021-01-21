package com.example.OPM_B2B;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

   private List<WishlistModel> wishlistModelList;
   private Boolean wishlist;

    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {

        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String resource = wishlistModelList.get(position).getProductImage();
        String title = wishlistModelList.get(position).getProductTitle();
        String productPrice = wishlistModelList.get(position).getProductPrice();
        String cuttedPrice = wishlistModelList.get(position).getCuttedPrice();
        String paymentMethod = wishlistModelList.get(position).getPaymentMethod();
        holder.setData(resource, title, productPrice, cuttedPrice, paymentMethod);



    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private View priceCutDivider;
        private ImageButton deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.ProductImage);
            productTitle = itemView.findViewById(R.id.Wishlist_Title2);
            productPrice = itemView.findViewById(R.id.product_Price_wishlist);
            cuttedPrice = itemView.findViewById(R.id.product_cutted_price_wishlist);
            paymentMethod = itemView.findViewById(R.id.paymentMethod);
            priceCutDivider = itemView.findViewById(R.id.priceCutDivider);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);

        }

        private void setCategoryIcon(String iconUrl) {
            //categoryIcon = imageView;
            //categoryIcon = itemView.findViewById(R.id.category_icon);
            // categoryIcon.setImageResource(id);
            Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.mipmap.opm_launcher)).into(productImage);

        }

        private void setData(String resource, String title, String price, String cuttedPriceValue, String paymentMethodValue) {
            //productImage.setImageResource(resource);
            setCategoryIcon(resource);
            productTitle.setText(title);
            productPrice.setText(price);
            cuttedPrice.setText(cuttedPriceValue);
            paymentMethod.setText(paymentMethodValue);

            if (wishlist) {
                deleteBtn.setVisibility(View.VISIBLE);
            } else {
                deleteBtn.setVisibility(View.GONE);
            }
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(itemView.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }


}
