package com.rohg007.android.instiflo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rohg007.android.instiflo.R;
import com.rohg007.android.instiflo.models.Product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaggeredProductCardAdapter extends RecyclerView.Adapter<StaggeredProductViewHolder> {

    private List<Product> productList;

    public StaggeredProductCardAdapter(List<Product> productList){
        this.productList=productList;
    }

    @Override
    public int getItemViewType(int position) {
        return position%3;
    }

    @NonNull
    @Override
    public StaggeredProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.staggered_product_card_first;
        if(viewType==1)
            layoutId = R.layout.staggered_product_card_second;
        else if(viewType==2)
            layoutId = R.layout.staggered_product_card_third;

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new StaggeredProductViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull StaggeredProductViewHolder holder, int position) {
        if (productList != null && position < productList.size()) {
            Product product = productList.get(position);
            String price = "Rs. "+product.productPrice;
            holder.productTitle.setText(product.productTitle);
            holder.productPrice.setText(price);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
