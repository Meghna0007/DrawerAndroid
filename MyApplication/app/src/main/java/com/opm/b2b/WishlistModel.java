package com.opm.b2b;

public class WishlistModel {
    private String productImage;
    private String productTitle;
    private String productPrice;
    private String cuttedPrice;
    private String paymentMethod;
    private String productWeight;
    private String setOfPiece;
    private String perPiece;
    private String productId;
    private boolean inStock;
    public WishlistModel(String productId,String productImage, String productTitle, String productPrice, String cuttedPrice, String productWeight ,String setOfPiece,String perPiece,boolean inStock) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productWeight =productWeight;
        this.setOfPiece=setOfPiece;
        this.perPiece=perPiece;
        this.productId=productId;
        this.inStock=inStock;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
