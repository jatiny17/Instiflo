package com.rohg007.android.instiflo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rohg007.android.instiflo.R;
import com.rohg007.android.instiflo.models.Event;
import com.rohg007.android.instiflo.models.Product;
import com.rohg007.android.instiflo.ui.ProductDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsViewHolder> {

    private ArrayList<Event> eventArrayList;
    private Context mContext;


    public MyEventsAdapter(ArrayList<Event> eventArrayList,Context context){
        this.eventArrayList=eventArrayList;
        this.mContext = context;
    }
    @NonNull
    @Override
    public MyEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_products_view,parent,false);
        return new MyEventsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventsViewHolder holder, final int position){
        final Event event = eventArrayList.get(position);
        String title = event.getEventTitle();
        //String imageUrl = event.getProductImageUrl();
    /*    if(event.getProductSellPrice()!= 0) {
            String price = "Selling price - Rs. " + event.getProductSellPrice();
            holder.sellPriceTextView.setText(price);
        }
        if(event.getProductRentPrice()!= 0) {
            String price = "Rent price - Rs. " + event.getProductRentPrice() +"/day";
            holder.rentPriceTextView.setText(price);
        }

     */
        /*if(imageUrl!=null) {
            Picasso.get()
                    .load(imageUrl)
                    .into(holder.eventImageView);
        }*/
        holder.titleTextView.setText(title);
        /*holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            Event event = eventArrayList.get(position);
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetails.class);
                intent.putExtra("productId",event.getProductId());
                mContext.startActivity(intent);
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }

}
