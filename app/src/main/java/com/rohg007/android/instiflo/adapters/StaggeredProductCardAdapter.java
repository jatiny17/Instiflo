package com.rohg007.android.instiflo.adapters;
import android.content.Context;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.rohg007.android.instiflo.R;
import com.rohg007.android.instiflo.models.Product;

import java.util.HashSet;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class StaggeredProductCardAdapter extends RecyclerView.Adapter<StaggeredProductCardAdapter.StaggeredProductViewHolder> {

    private List<Product> productList;
    private View.OnClickListener onItemClickListener;
    private Context context;

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
        context=parent.getContext();
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new StaggeredProductViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull StaggeredProductViewHolder holder, int position) {
        if (productList != null && position < productList.size()) {
            Product product = productList.get(position);
            String price = "Rs. "+String.valueOf(product.getProductPrice());
            String url = product.getProductImageUrl();
            holder.productTitle.setText(product.getProductTitle());
            holder.productPrice.setText(price);
            //imageView.setImageBitmap(getBitmapFromURL(url));
            //Glide.with(context).load(product.getProductImageUrl()).into(holder.productimage);
            Picasso.get()
                    .load(url)
                    .into(holder.productimage);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        onItemClickListener=itemClickListener;
    }

    public class StaggeredProductViewHolder extends RecyclerView.ViewHolder {

        TextView productTitle;
        TextView productPrice;
        ImageView productimage;
        StaggeredProductViewHolder(@NonNull View itemView){
            super(itemView);
            productTitle = (TextView)itemView.findViewById(R.id.product_title);
            productPrice = (TextView)itemView.findViewById(R.id.product_price);
            productimage = (ImageView)itemView.findViewById(R.id.product_image);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }
}
