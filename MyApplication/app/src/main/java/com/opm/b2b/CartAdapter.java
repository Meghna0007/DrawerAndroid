package com.opm.b2b;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
                boolean inStock=cartItemModelList.get(position).isInStock();
                Long productQuantity =cartItemModelList.get(position).getProductQuantity();
                Long maxQuantity =cartItemModelList.get(position).getMaxQuantity();
                boolean  qtyError=cartItemModelList.get(position).isQtyError();
                List<String>qtyIds=cartItemModelList.get(position).getQtyIDs();
                long stockQty=cartItemModelList.get(position).getStockQuantity();
                boolean COD=cartItemModelList.get(position).isCOD();
                ((CartItemViewholder) holder).setItemDetails(productId,resource, title, productPrice, cuttedPrice,position,inStock,String.valueOf(productQuantity),maxQuantity,qtyError,qtyIds,stockQty,COD);

                break;
            case CartItemModel.TOTAL_AMOUNT:
                int totalItems=0;
                int totalItemPrice=0;
                String deliveryPrice;
                int totalAmount;
                int savedAmount=0;
                for (int x=0;x<cartItemModelList.size();x++){
                    if(cartItemModelList.get(x).getType()==CartItemModel.CART_ITEM && cartItemModelList.get(x).isInStock()){

                        int quantity = cartItemModelList.get(x).getProductQuantity().intValue();
                        totalItems += quantity;
                        totalItemPrice=totalItemPrice + Integer.parseInt(cartItemModelList.get(x).getProductPrice())*quantity;

                        if (!TextUtils.isEmpty(cartItemModelList.get(x).getCuttedPrice())){
                            savedAmount=savedAmount + (Integer.parseInt(cartItemModelList.get(x).getCuttedPrice()) - Integer.parseInt(cartItemModelList.get(x).getProductPrice())) *quantity;

                        }
                    }
                }
                if (totalItemPrice>500){
                    deliveryPrice="Free";
                    totalAmount=totalItemPrice;
                }else {
                    deliveryPrice="60";
                    totalAmount=totalItemPrice + 60;
                }



                // totalItems = cartItemModelList.get(position).getTotalItems();
               //  totalItemPrice = cartItemModelList.get(position).getTotalItemPrice();
               // deliveryPrice = cartItemModelList.get(position).getDeliveryPrice();
               // String totalAmount = cartItemModelList.get(position).getTotalAmount();
              //  String savedAmount = cartItemModelList.get(position).getSaveAmount();
                cartItemModelList.get(position).setTotalItems(totalItems);
                cartItemModelList.get(position).setTotalItemPrice(totalItemPrice);
                cartItemModelList.get(position).setDeliveryPrice(deliveryPrice);
                cartItemModelList.get(position).setTotalAmount(totalAmount);
                cartItemModelList.get(position).setSavedAmount(savedAmount);

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
        private ImageView codIndicator;

        public CartItemViewholder(@NonNull View itemView) {
            super(itemView);
            remove_item_icon=itemView.findViewById(R.id.remove_item_icon);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cuutted_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            codIndicator=itemView.findViewById(R.id.codIndicator);
        }

        private void setItemDetails(String productId,String resource, String title, String productPriceText, String cuttedPriceText,int position,boolean inStock,String quantity,Long maxQuantity,boolean qtyError,List<String> qtyIds,long stockQty,boolean COD) {
           // productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.fksmall)).into(productImage);
        productTitle.setText(title);
        if (COD){
            codIndicator.setVisibility(View.VISIBLE);
        }else{
            codIndicator.setVisibility(View.INVISIBLE);
        }
        if(inStock) {
            productPrice.setText("Rs."+productPriceText+"/-");
            productPrice.setTextColor(Color.parseColor("#000000"));
            cuttedPrice.setText("Rs."+cuttedPriceText+"/-");
            productQuantity.setText("Qty:"+ quantity);
if (!showDelteBtn) {
    if (qtyError) {
        productQuantity.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
        productQuantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.red)));
        productQuantity.setCompoundDrawableTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.red)));

    } else {
        productQuantity.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
      //  productQuantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.black)));
        productQuantity.setCompoundDrawableTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.black)));

    }
}
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

                    quantityNo.setHint("Max "+ String.valueOf(maxQuantity));
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(quantityNo.getText())) {
                                if (Long.valueOf(quantityNo.getText().toString()) <= maxQuantity && Long.valueOf(quantityNo.getText().toString()) != 0 ) {
                                    if (itemView.getContext()instanceof Main4Activity){
                                        cartItemModelList.get(position).setProductQuantity(Long.valueOf(quantityNo.getText().toString()));
                                    }else {
                                        if (DeliveryActivity.fromCart) {
                                            cartItemModelList.get(position).setProductQuantity(Long.valueOf(quantityNo.getText().toString()));
                                        }else{
                                            DeliveryActivity.cartItemModelList.get(position).setProductQuantity(Long.valueOf(quantityNo.getText().toString()));
                                        }
                                    }

                                    productQuantity.setText("Qty:" + quantityNo.getText());
                                    notifyItemChanged(cartItemModelList.size()-1);
                                   // notifyItemChanged(0);

                                    if (!showDelteBtn){
                                        DeliveryActivity.loadingDialog.show();
                                        DeliveryActivity.cartItemModelList.get(position).setQtyError(false);

                                        int intialQty=Integer.parseInt(quantity);
                                        int finalQty = Integer.parseInt(quantityNo.getText().toString()) ;
                                        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

                                        if (finalQty>intialQty) {


                                            for (int y = 0; y < finalQty-intialQty; y++) {
                                                String quantityDocumentName = UUID.randomUUID().toString().substring(0, 20);
                                                Map<String, Object> timeStamp = new HashMap<>();
                                                timeStamp.put("time", FieldValue.serverTimestamp());


                                                int finalY = y;
                                                firebaseFirestore.collection("PRODUCTS").document(productId)
                                                        .collection("QUANTITY").document(quantityDocumentName).set(timeStamp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        qtyIds.add(quantityDocumentName);

                                                        if (finalY + 1 == finalQty-intialQty) {

                                                            firebaseFirestore.collection("PRODUCTS").document(productId)
                                                                    .collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING)
                                                                    .limit(stockQty)
                                                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task.isSuccessful()) {

                                                                        List<String> serverQuantity = new ArrayList<>();

                                                                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                                                            serverQuantity.add(queryDocumentSnapshot.getId());
                                                                        }
                                                                        long availableQty = 0;

                                                                        for (String qtyId : qtyIds) {
                                                                                     if (!serverQuantity.contains(qtyId)) {
                                                                                     DeliveryActivity.cartItemModelList.get(position).setQtyError(true);
                                                                                     DeliveryActivity.cartItemModelList.get(position).setMaxQuantity(availableQty);
                                                                                    Toast.makeText(itemView.getContext(), "Sorry ! all products may not be available in required quantity...", Toast.LENGTH_SHORT).show();

                                                                            }else {
                                                                                availableQty++;
                                                                            }
                                                                        }
                                                                        DeliveryActivity.cartAdapter.notifyDataSetChanged();
                                                                    } else {
                                                                        String error = task.getException().getMessage();
                                                                        Toast.makeText(itemView.getContext(), error, Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    DeliveryActivity.loadingDialog.dismiss();
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }else if(intialQty>finalQty){
                                            for (int x=0; x < intialQty-finalQty;x++) {
                                               String qtyId= qtyIds.get(qtyIds.size()-1 -x);
                                                int finalX = x;
                                                firebaseFirestore.collection("PRODUCTS").document(productId)
                                                        .collection("QUANTITY").document(qtyId).delete()
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                               qtyIds.remove(qtyId);
                                                                DeliveryActivity.cartAdapter.notifyDataSetChanged();
                                                                if (finalX + 1== intialQty-finalQty){
                                                                    DeliveryActivity.loadingDialog.dismiss();
                                                                }
                                                            }
                                                        });

                                            }
                                        }
                                    }

                                }else {
                                    Toast.makeText(itemView.getContext(), "Max quantity :"+maxQuantity.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }
                                quantityDialog.dismiss();

                        }
                    });
                    quantityDialog.show();
                }
            });
        }else {
            productPrice.setText("Out of Stock");
            productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
            cuttedPrice.setText("");

           productQuantity.setVisibility(View.INVISIBLE);
        }
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
                        DBqueries.removeFromCart(position,itemView.getContext(),cartTotalAmount);
                    }
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
                if (DeliveryActivity.fromCart) {
                    cartItemModelList.remove(cartItemModelList.size() - 1);
                    DeliveryActivity.cartItemModelList.remove(DeliveryActivity.cartItemModelList.size() -1);
                }
                if (showDelteBtn){
                    cartItemModelList.remove(cartItemModelList.size() - 1);

                }
                parent.setVisibility(View.GONE);
            }else {
                parent.setVisibility(View.VISIBLE);

            }


        }
    }


}
