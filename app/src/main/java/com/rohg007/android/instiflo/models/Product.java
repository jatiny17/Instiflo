package com.rohg007.android.instiflo.models;

import java.util.ArrayList;

public class Product {

    private static final String LOG_TAG = Product.class.getSimpleName();
    public String productTitle;
    public int productPrice;
    public int category;

    public Product()
    {
        productTitle="";
        productPrice=0;
        category=0;
    }

    public Product(String productTitle, int productPrice){
        this.productTitle=productTitle;
        this.productPrice=productPrice;
        this.category=0;
    }

    public Product(String productTitle, int productPrice, int category){
        this.productTitle=productTitle;
        this.productPrice=productPrice;
        this.category=category;
    }


    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getCategory() { return category; }

    public void setCategory(int category) { this.category = category; }

    public static ArrayList<Product> getTestProducts(){
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product("Product 1",99,1));
        productList.add(new Product("Product 2",199,1));
        productList.add(new Product("Product 3",299,1));
        productList.add(new Product("Product 4",399,1));
        productList.add(new Product("Product 5",99,2));
        productList.add(new Product("Product 6",199,2));
        productList.add(new Product("Product 7",299,2));
        productList.add(new Product("Product 8",399,2));
        productList.add(new Product("Product 9",199,3));
        productList.add(new Product("Product 10",299,3));

        return productList;
    }
}
