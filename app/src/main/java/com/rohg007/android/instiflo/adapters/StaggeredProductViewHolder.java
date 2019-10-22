package com.rohg007.android.instiflo.adapters;

import android.view.View;
import android.widget.TextView;

import com.rohg007.android.instiflo.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaggeredProductViewHolder extends RecyclerView.ViewHolder {

    TextView productTitle;
    TextView productPrice;

    StaggeredProductViewHolder(@NonNull View itemView){
        super(itemView);
        productTitle = itemView.findViewById(R.id.product_title);
        productPrice = itemView.findViewById(R.id.product_price);
    }
}
