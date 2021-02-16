package com.opm.b2b;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    List<CartItemModel> cartItemModelList;
    private TextView cartTotalAmount;
    private boolean showDelteBtn;

    public CartAdapter(List<CartItemModel> cartItemModelList,TextView cartTotalAmount,boolean showDelteBtn) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount=cartTotalAmount;
        this.showDelteBtn=showDelteBtn;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartTotalItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                return new CartItemViewholder(cartTotalItem);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amoutl_layout, parent, false);
                return new CartTotalAmountViewholder(cartTotalView);
            default:
                return null;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModelList.get(position).getType()) {
            case CartItemModel.CART_ITEM:
                String productId=cartItemModelList.get(position).getProductId();
                String resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                String productPrice = cartItemModelList.get(position).getProductPrice();
                String cuttedPrice = cartItemModelList.get(position).getCuttedPrice();
                ((CartItemViewholder) holder).setItemDetails(productId,resource, title, productPrice, cuttedPrice,position);

                break;
            case CartItemModel.TOTAL_AMOUNT:
                int totalItems=0;
                int totalItemPrice=0;
                String deliveryPrice;
                int totalAmount;
                int savedAmount=0;
                for (int x=0;x<cartItemModelList.size();x++){
                    if(cartItemModelList.get(x).getType()==CartItemModel.CART_ITEM){
                        totalItems++;
                        totalItemPrice=totalItemPrice + Integer.parseInt(cartItemModelList.get(x).getProductPrice());
                    }
                }
                if (totalItemPrice>500){
                    deliveryPrice="Free";
                    totalAmount=totalItemPrice;
                }else {
                    deliveryPrice="60";
                    totalAmount=totalItemPrice + 60;
                }



              //  String totalItems = cartItemModelList.get(position).getTotalItems();
               // String totalItemPrice = cartItemModelList.get(position).getTotalItemPrice();
              //  String deliveryPrice = cartItemModelList.get(position).getDeliveryPrice();
               // String totalAmount = cartItemModelList.get(position).getTotalAmount();
              //  String savedAmount = cartItemModelList.get(position).getSaveAmount();
                ((CartTotalAmountViewholder) holder).setTotalAmount(totalItems, totalItemPrice, deliveryPrice, totalAmount, savedAmount);


                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class CartItemViewholder extends RecyclerView.ViewHolder {
        private LinearLayout remove_item_icon;
        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView productQuantity;

        public CartItemViewholder(@NonNull View itemView) {
            super(itemView);
            remove_item_icon=itemView.findViewById(R.id.remove_item_icon);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cuutted_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
        }

        private void setItemDetails(String productId,String resource, String title, String productPriceText, String cuttedPriceText,int position) {
           // productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.fksmall)).into(productImage);
        productTitle.setText(title);
            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);

            if (showDelteBtn){
                remove_item_icon.setVisibility(View.VISIBLE);
            }else{
                remove_item_icon.setVisibility(View.GONE);
            }
            remove_item_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!ProductDetailsActivity.running_cart_query){
                        ProductDetailsActivity.running_cart_query=true;
                        DBqueries.removeFromCart(position,itemView.getContext());
                    }
                }
            });
            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog quantityDialog=new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantitydialog);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                    quantityDialog.setCancelable(false);
                    EditText quantityNo = quantityDialog.findViewById(R.id.Quantity_Number);
                    Button cancelBtn=quantityDialog.findViewById(R.id.cancel_Btn);
                    Button okBtn=quantityDialog.findViewById(R.id.ok_btn);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productQuantity.setText("Qty:"+ quantityNo.getText());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();
                }
            });
        }
    }


    class CartTotalAmountViewholder extends RecyclerView.ViewHolder {

        private TextView totalItems;
        private TextView totalItemPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;


        public CartTotalAmountViewholder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItemPrice = itemView.findViewById(R.id.total_items_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            savedAmount = itemView.findViewById(R.id.savedItem);
        }

        private void setTotalAmount(int totalItemText, int totalItemPriceText, String deliveryPriceText, int totalAmountText, int saveAmountText) {

            totalItems.setText("Price("+totalItemText+"items)");
            totalItemPrice.setText("Rs."+totalItemPriceText+"/-");
            if (deliveryPriceText.equals("FREE")) {
                deliveryPrice.setText(deliveryPriceText);
            }else{
                deliveryPrice.setText("Rs."+deliveryPriceText+"/-");
            }
            totalAmount.setText("Rs."+totalAmountText+"/-");
            cartTotalAmount.setText("Rs."+totalAmountText+"/-");
            savedAmount.setText("You saved Rs."+saveAmountText+"/- on this order.");
            LinearLayout parent =(LinearLayout)cartTotalAmount.getParent().getParent();

            if (totalItemPriceText==0){
                DBqueries.cartItemModelList.remove(DBqueries.cartItemModelList.size() -1);
                parent.setVisibility(View.GONE);
            }else {
                parent.setVisibility(View.VISIBLE);

            }


        }
    }


}
