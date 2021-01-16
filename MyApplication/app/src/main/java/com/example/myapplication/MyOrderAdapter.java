package com.example.myapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import static android.graphics.BlendMode.COLOR;
import static android.graphics.Color.RED;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Viewholder> {
    private List<MyOrderItemModel> myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
   View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout,parent,false);
   return  new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
   private ImageView productImage;
   private ImageView deliveryIndicator;
   private TextView  productTitle;
   private TextView deliveryStatus;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.Order_Image);
            deliveryIndicator=itemView.findViewById(R.id.order_indicator);
            productTitle=itemView.findViewById(R.id.Order_title);
            deliveryStatus=itemView.findViewById(R.id.order_delivered_date);
        }
        private setData(int resouce,String title,String deliveryDate){
            productImage.setImageResource(resouce);
            if(deliveryDate.equals("Cancelled")){
                deliveryIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources(),(Color.BLACK)
            }
            productTitle.setText(title);
            deliveryStatus.setText(deliveryDate);
        }
    }
}
