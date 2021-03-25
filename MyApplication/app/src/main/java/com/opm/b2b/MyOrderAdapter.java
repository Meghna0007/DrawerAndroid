package com.opm.b2b;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Viewholder> {
    private List<MyOrderItemModel> myOrderItemModelList;
    private Dialog loadingDialog;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList,Dialog loadingDialog) {
        this.myOrderItemModelList = myOrderItemModelList;
        this.loadingDialog=loadingDialog;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.Viewholder holder, int position) {

        String resource = myOrderItemModelList.get(position).getProductImage();
        String title = myOrderItemModelList.get(position).getProductTitle();
        String orderStatus=myOrderItemModelList.get(position).getOrderStatus();
        Date date;
        switch (orderStatus){
            case "Ordered":
                date=myOrderItemModelList.get(position).getOrderedDate();
                break;
            case "Packed":
                date=myOrderItemModelList.get(position).getPackedDate();
                break;
            case "Shipped":
                date=myOrderItemModelList.get(position).getShippedDate();
                break;
            case "Delivered":
                date=myOrderItemModelList.get(position).getDeliveredDate();
                break;
            case "Cancelled":
                date=myOrderItemModelList.get(position).getCancelledDate();
                break;
            default:
                date=myOrderItemModelList.get(position).getCancelledDate();


        }


        holder.setData(resource, title,orderStatus,date,position);

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private ImageView orderIndicator;
        private TextView productTitle;
        private TextView deliveryStatus;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.Order_Image);
            orderIndicator = itemView.findViewById(R.id.order_indicator);
            productTitle = itemView.findViewById(R.id.Order_title);
            deliveryStatus = itemView.findViewById(R.id.order_delivered_date);
            orderIndicator = itemView.findViewById(R.id.order_indicator);


        }

        private void setData(String resource, String title, String orderStatus,Date date, int position) {
            Glide.with(itemView.getContext()).load(resource).into(productImage);
            productTitle.setText(title);
            if (orderStatus.equals("Cancelled")) {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.red)));
            } else {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.successGreen)));

            }
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(" EEE, dd-MMM-YYYY hh:mm aa");
            deliveryStatus.setText(orderStatus+String.valueOf(simpleDateFormat.format(date)));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailsIntent=new Intent(itemView.getContext(),OrderDetailsActivity.class);
                    orderDetailsIntent.putExtra("Position",position);
                    itemView.getContext().startActivity(orderDetailsIntent);
                }
            });

        }

    }

}
