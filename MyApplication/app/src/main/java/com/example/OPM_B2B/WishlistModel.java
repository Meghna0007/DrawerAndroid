package com.example.OPM_B2B;

import android.widget.TextView;

public class WishlistModel {
    private String productImage;
    private String productTitle;
    private String productPrice;
    private String cuttedPrice;
    private String paymentMethod;
    private String productWeight;
    private String setOfPiece;
    private String perPiece;

    public WishlistModel(String productImage, String productTitle, String productPrice, String cuttedPrice, String productWeight ,String setOfPiece,String perPiece) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productWeight =productWeight;
        this.setOfPiece=setOfPiece;
        this.perPiece=perPiece;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public String getSetOfPiece() {
        return setOfPiece;
    }

    public void setSetOfPiece(String setOfPiece) {
        this.setOfPiece = setOfPiece;
    }

    public String getPerPiece() {
        return perPiece;
    }

    public void setPerPiece(String perPiece) {
        this.perPiece = perPiece;
    }
}
