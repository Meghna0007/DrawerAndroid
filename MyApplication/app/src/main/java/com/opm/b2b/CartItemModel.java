package com.opm.b2b;

import java.util.ArrayList;
import java.util.List;

public class CartItemModel {

    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1;

    private int type;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    ///////////////////cartitem
    private String productId;
    private String productImage;
    private Long productQuantity;
    private Long maxQuantity;
    private String productTitle;
    private String productPrice;
    private String cuttedPrice;
    private boolean inStock;
    private List<String>qtyIDs;

    public CartItemModel(int type,String productId, String productImage, Long productQuantity, String productTitle, String productPrice, String cuttedPrice,boolean inStock,Long maxQuantity) {
        this.type = type;
        this.productId=productId;
        this.productImage = productImage;
       this.productQuantity = productQuantity;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.inStock=inStock;
        this.maxQuantity=maxQuantity;
        qtyIDs=new ArrayList<>();
    }

    public List<String> getQtyIDs() {
        return qtyIDs;
    }

    public void setQtyIDs(List<String> qtyIDs) {
        this.qtyIDs = qtyIDs;
    }

    public Long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

  public Long getProductQuantity() {
        return productQuantity;
    }


    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
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

///////////cartitem

    ////////////////cart total

    public CartItemModel(int type) {
        this.type = type;
    }

    //////////////cart total

}
