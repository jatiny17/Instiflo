package com.rohg007.android.instiflo.models;

import java.util.ArrayList;

public class Product {

    private static final String LOG_TAG = Product.class.getSimpleName();
    private String productTitle;
    private int productPrice;

    public Product()
    {
        productTitle = "";
        productPrice = 0;
    }

    public Product(String productTitle, int productPrice){
        this.productTitle=productTitle;
        this.productPrice=productPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public int getProductPrice() {
        return productPrice;
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
