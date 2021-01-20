package com.example.OPM_B2B;

public class HorizonantleProductScrollModel {

    private int productImage;
    private String productTitle;
    private String productdescription;
    private String productprice;

    public HorizonantleProductScrollModel(int productImage, String productTitle, String productdescription, String productprice) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productdescription = productdescription;
        this.productprice = productprice;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }
}
