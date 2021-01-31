package com.example.OPM_B2B;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        String ProductsetofPiece=wishlistModelList.get(position).getSetOfPiece();
        String ProductWeight=wishlistModelList.get(position).getProductWeight();
        String ProductperPiece=wishlistModelList.get(position).getPerPiece();

        holder.setData(resource, title, productPrice, cuttedPrice,ProductperPiece,ProductsetofPiece ,ProductWeight);



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
        //private View priceCutDivider;
        private ImageButton deleteBtn;
        private TextView product_quantity;
        private TextView productWeight;
        private TextView setOfPiece;
        private TextView perPiece;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.ProductImage);
            productTitle = itemView.findViewById(R.id.Wishlist_Title2);
            productPrice = itemView.findViewById(R.id.product_Price_wishlist);
            cuttedPrice = itemView.findViewById(R.id.product_cutted_price_wishlist);
            productWeight=itemView.findViewById(R.id.productWeight);
            setOfPiece=itemView.findViewById(R.id.setOfPiece);
            perPiece=itemView.findViewById(R.id.perPiece);
            //priceCutDivider = itemView.findViewById(R.id.priceCutDivider);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            product_quantity = itemView.findViewById(R.id.product_quantity);

        }

        private void setCategoryIcon(String iconUrl) {
            //categoryIcon = imageView;
            //categoryIcon = itemView.findViewById(R.id.category_icon);
            // categoryIcon.setImageResource(id);
            Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.mipmap.opm_launcher)).into(productImage);

        }

        private void setData(String resource, String title, String price, String cuttedPriceValue, String ProductperPiece,String ProductsetofPiece ,String ProductWeight) {
            //productImage.setImageResource(resource);
            setCategoryIcon(resource);
            productTitle.setText(title);
            productPrice.setText(price);
            cuttedPrice.setText(cuttedPriceValue);
            perPiece.setText(ProductperPiece);
            setOfPiece.setText(ProductsetofPiece);
            productWeight.setText(ProductWeight);
            if (wishlist) {
                deleteBtn.setVisibility(View.VISIBLE);
            } else {
                deleteBtn.setVisibility(View.GONE);
                product_quantity.setVisibility(View.VISIBLE);
            }
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(itemView.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                    }
                });

            product_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Add To Cart", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
