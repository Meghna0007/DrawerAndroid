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
        String displayName = categoryModelList.get(position).getDisplayName();
        String collectionName = categoryModelList.get(position).getCategoryName();

        holder.setCategory(displayName, collectionName );
        //TODO changes for icon setting

        holder.setCategoryIcon(icon);


    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryIcon;
        private TextView categoryName;
        private String collectionName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            categoryName = itemView.findViewById(R.id.category_name);
        }

        private void setCategoryIcon(String iconUrl) {

            //categoryIcon = imageView;
            //categoryIcon = itemView.findViewById(R.id.category_icon);
            // categoryIcon.setImageResource(id);
            Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.fksmall)).into(categoryIcon);

        }

        private void setCategory(final String displayName, String collectionName) {
            //categoryIcon.setImageIcon(Icon.createWithContentUri(text));
            //String name = categoryNameValue.replace("\\n", "\n");
            categoryName.setText(displayName);
            //String nameWithoutNewLine = name.replaceAll("\n", "");
            Class nextClass;
            if (collectionName.equals("FMCG")) {
                nextClass = FMCGActivity.class;
            } else {
                nextClass = ViewAllActivity.class;
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        //Intent categoryIntent = new Intent(itemView.getContext(), CategoryActivity.class);
                        Intent categoryIntent = new Intent(itemView.getContext(), nextClass);
                        categoryIntent.putExtra("displayName", displayName);
                        categoryIntent.putExtra("collectionName", collectionName);
                        itemView.getContext().startActivity(categoryIntent);
                }
            });

        }

    }}