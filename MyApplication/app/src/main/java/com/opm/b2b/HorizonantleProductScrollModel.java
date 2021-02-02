package com.opm.b2b;

public class HorizonantleProductScrollModel {
private  String productid;
    private String productImage;
    private String productTitle;
    private String productdescription;
    private String productprice;

    public HorizonantleProductScrollModel(String productid, String productImage, String productTitle, String productdescription, String productprice) {
        this.productid = productid;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productdescription = productdescription;
        this.productprice = productprice;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
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
