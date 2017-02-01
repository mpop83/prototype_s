package com.test.prototype_s;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.prototype_s.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom RecycleView Adapter
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private List<Product> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mPrice;
        public TextView mTitle;
        public ImageView mImage;

        public ViewHolder(View view) {
            super(view);
            mPrice = (TextView) view.findViewById(R.id.tv_prod_price);
            mTitle = (TextView) view.findViewById(R.id.tv_prod_title);;
            mImage = (ImageView) view.findViewById(R.id.iv_image);;
        }
    }


    public RVAdapter(List<Product> data) {
        if (data == null) {
            mDataset = new ArrayList<>(); //init empty
        } else {
            mDataset = data;
        }
    }

    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_prod, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product p = mDataset.get(position);
        holder.mTitle.setText(p.getTitle());
        holder.mPrice.setText(p.getPrice() + " " + p.getCurrency());
        Picasso.with(holder.mImage.getContext())
                .load(p.getImgUrl())
                .placeholder(android.R.drawable.gallery_thumb)
                .error(android.R.drawable.ic_dialog_alert)
//                .centerCrop()
//                .resize(180, 200)
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}