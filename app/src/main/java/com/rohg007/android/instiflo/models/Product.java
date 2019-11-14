package com.rohg007.android.instiflo.models;

import java.util.ArrayList;

public class Product {

    private static final String LOG_TAG = Product.class.getSimpleName();
    private String productId;
    private String productTitle;
    private String productImageUrl;
    private int productCategory;
    private int productPrice;
    private int productCount;
    private String productDescription;
    private int rentDuration;
    private String ownerId;
    private ArrayList<String> buyerId;
    private float productRating;
    private int noOfUsersRated;


    public Product(String productTitle, int i)
    {
        this.productTitle = "";
        this.productCategory = 0;
        this.productPrice=0;
        this.productCount=0;
        this.productDescription="";
        this.productImageUrl="";
    }
    public Product(String productTitle, String productImageUrl){
        this.productTitle=productTitle;
        this.productImageUrl=productImageUrl;
    }


    public Product(String productid,String ownerId,String productTitle, int productCategory,int rentDuration, int productPrice, int productCount, String productDescription, String productImageUrl) {
        this.productId = productid;
        this.ownerId=ownerId;
        this.productTitle = productTitle;
        this.productCategory = productCategory;
        this.rentDuration= rentDuration;
        this.productPrice=productPrice;
        this.productCount=productCount;
        this.productDescription=productDescription;
        this.productImageUrl=productImageUrl;
        this.noOfUsersRated=0;
        this.productRating=Float.parseFloat("0");
    }
    public static String getLogTag() {
        return LOG_TAG;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }


    public int getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(int productCategory) {
        this.productCategory = productCategory;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getRentDuration() {
        return rentDuration;
    }

    public void setRentDuration(int rentDuration) {
        this.rentDuration = rentDuration;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public ArrayList<String> getBuyerId() {
        return buyerId;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public float getProductRating() {
        return productRating;
    }

    public void setProductRating(float productRating) {
        this.productRating = productRating;
    }

    public int getNoOfUsersRated() {
        return noOfUsersRated;
    }

    public void setNoOfUsersRated(int noOfUSersRated) {
        this.noOfUsersRated = noOfUSersRated;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
    public static ArrayList<Product> getTestProducts(){
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product("Product 1",99));
        productList.add(new Product("Product 2",199));
        productList.add(new Product("Product 3",299));
        productList.add(new Product("Product 4",399));
        productList.add(new Product("Product 5",99));
        productList.add(new Product("Product 6",199));
        productList.add(new Product("Product 7",299));
        productList.add(new Product("Product 8",399));
        productList.add(new Product("Product 9",199));
        productList.add(new Product("Product 10",299));
        return productList;
    }

}
