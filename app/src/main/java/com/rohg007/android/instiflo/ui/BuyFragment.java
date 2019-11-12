package com.rohg007.android.instiflo.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rohg007.android.instiflo.R;
import com.rohg007.android.instiflo.adapters.StaggeredProductCardAdapter;
import com.rohg007.android.instiflo.models.Product;

import java.util.ArrayList;

public class BuyFragment extends Fragment {

    private ArrayList<Product> mProductList = Product.getTestProducts();

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;
            Product product = mProductList.get(position);
            Intent intent = new Intent(getActivity(),ProductDetails.class);
            intent.putExtra("Title",product.getProductTitle());
            intent.putExtra("Price",product.getProductPrice());
            startActivity(intent);
        }
    };

    public BuyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.buy_recycler_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position%3==2 ? 2 : 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        StaggeredProductCardAdapter adapter = new StaggeredProductCardAdapter(Product.getTestProducts());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        final FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_add_shopping_cart_black_24dp));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(dy>0)
                    fab.hide();
                else if(dy<0)
                    fab.show();
            }
        });

        int largePadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing_small);

        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding,smallPadding));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddProduct.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
