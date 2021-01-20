package com.example.OPM_B2B;

public class CategoryModel {

    private String categoryIconLink;
    private String categoryName;
    private int imageId;

    public CategoryModel(String categoryIconLink, String categoryName) {
        this.categoryIconLink = categoryIconLink;
        this.categoryName = categoryName;
    }

    public CategoryModel(int imageId, String categoryName) {
        this.imageId = imageId;
        this.categoryName = categoryName;
    }

    public String getCategoryIconLink() {
        return categoryIconLink;
    }

    public void setCategoryIconLink(String categoryIconLink) {
        this.categoryIconLink = categoryIconLink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    //  public void setCategoryName(String categoryName) {
    //  this.categoryName = categoryName;

}
