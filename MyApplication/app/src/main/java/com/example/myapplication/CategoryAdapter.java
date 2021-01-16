package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        String icon = categoryModelList.get(position).getCategoryIconLink();
        int iconImageId = categoryModelList.get(position).getImageId();
        String name = categoryModelList.get(position).getCategoryName();
        holder.setCategory(name);
        //TODO changes for icon setting

        holder.setCategoryIcon(iconImageId);


    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryIcon;
        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            categoryName = itemView.findViewById(R.id.category_name);
        }

        private void setCategoryIcon(int id) {
            //categoryIcon = imageView;
            //categoryIcon = itemView.findViewById(R.id.category_icon);
           // categoryIcon.setImageResource(id);
            //todo set category icons

        }

        private void setCategory(final String name) {
            //categoryIcon.setImageIcon(Icon.createWithContentUri(text));
            categoryName.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent categoryIntent = new Intent(itemView.getContext(),CategoryActivity.class);
                    categoryIntent.putExtra("CategoryName",name);
                    itemView.getContext().startActivity(categoryIntent);
                }
            });
        }
    }



}
