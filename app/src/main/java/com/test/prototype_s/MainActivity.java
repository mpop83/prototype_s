package com.test.prototype_s;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.prototype_s.model.Item;
import com.test.prototype_s.model.Post;
import com.test.prototype_s.model.Product;
import com.test.prototype_s.server.GetPostsTask;
import com.test.prototype_s.server.GetProductsTask;
import com.test.prototype_s.server.NetTaskCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    // list and adapter for upper "Clothing" products
    private RecyclerView mRecyclerViewClothes;
    private RecyclerView.Adapter mAdapterClothes;
    private LinearLayoutManager mLayoutManagerClothes;
    // list and adapter for lower "lamps" products
    private RecyclerView mRecyclerViewLamps;
    private RecyclerView.Adapter mAdapterLamps;
    private LinearLayoutManager mLayoutManagerLamps;

    private int mPostsLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerViewClothes = (RecyclerView) findViewById(R.id.clothes_recycler_view);
        mRecyclerViewClothes.setHasFixedSize(true);
        mLayoutManagerClothes = new LinearLayoutManager(this);
        mLayoutManagerClothes.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewClothes.setLayoutManager(mLayoutManagerClothes);
        mRecyclerViewClothes.setItemAnimator(new DefaultItemAnimator());


        mRecyclerViewLamps = (RecyclerView) findViewById(R.id.lamps_recycler_view);
        mRecyclerViewLamps.setHasFixedSize(true);
        mLayoutManagerLamps = new LinearLayoutManager(this);
        mLayoutManagerLamps.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewLamps.setLayoutManager(mLayoutManagerLamps);
        mRecyclerViewLamps.setItemAnimator(new DefaultItemAnimator());

        mPostsLimit = getResources().getInteger(R.integer.posts_items);

        // launching the retrieval of data from the servr
        new GetProductsTask(this, clothesCallback).execute(GetProductsTask.ProdType.CLOTHES);
        new GetProductsTask(this, lampsCallback).execute(GetProductsTask.ProdType.LAMPS);
        new GetPostsTask(this, fashionCallback).execute(GetPostsTask.PostType.FASHION);
        new GetPostsTask(this, lifestyleCallback).execute(GetPostsTask.PostType.LIFESTYLE);

    }

    // adds data of a Post to a corresponding LinearLayout
    private void addPostToLayout(LinearLayout linearLayout, Post post, String categoryTitle) {
        View postLayout = LayoutInflater.from(this).inflate(R.layout.layout_post, linearLayout, false);
        TextView categoryView = (TextView) postLayout.findViewById(R.id.tv_post_category);
        categoryView.setText(categoryTitle);
        TextView titleView = (TextView) postLayout.findViewById(R.id.tv_post_title);
        titleView.setText(post.getTitle());
        ImageView imageView = (ImageView) postLayout.findViewById(R.id.iv_image);
        Picasso.with(this)
                .load(post.getImgUrl())
                .placeholder(android.R.drawable.gallery_thumb)
                .error(android.R.drawable.ic_dialog_alert)
//                .centerInside()
                .centerCrop()
                .resize(150, 150) //TODO - get dimension from res.dim
                .into(imageView);

        linearLayout.addView(postLayout);
    }


    //------ Callbacks for the results retrieved from server, via the tasks launched in onCreate

    private NetTaskCallback fashionCallback = new NetTaskCallback() {
        @Override
        public void taskFinished(List<? extends Item> result) {
            int limit = result.size() > mPostsLimit ? mPostsLimit : result.size();
            LinearLayout lifestyleLL = (LinearLayout)findViewById(R.id.layout_fashion);
            for (int i = 0; i < limit; i++) {
                addPostToLayout(lifestyleLL, (Post) result.get(i), getString(R.string.title_cat_fashion));
            }
        }
    };


    private NetTaskCallback lifestyleCallback = new NetTaskCallback() {
        @Override
        public void taskFinished(List<? extends Item> result) {
            int limit = result.size() > mPostsLimit ? mPostsLimit : result.size();
            LinearLayout lifestyleLL = (LinearLayout)findViewById(R.id.layout_lifestyle);
            for (int i = 0; i < limit; i++) {
                addPostToLayout(lifestyleLL, (Post) result.get(i), getString(R.string.title_cat_lifestyle));
            }
        }
    };


    private NetTaskCallback clothesCallback = new NetTaskCallback() {
        @Override
        public void taskFinished(List<? extends Item> result) {
            mAdapterClothes = new RVAdapter((List<Product>) result);
            mRecyclerViewClothes.setAdapter(mAdapterClothes);
        }
    };


    private NetTaskCallback lampsCallback = new NetTaskCallback() {
        @Override
        public void taskFinished(List<? extends Item> result) {
            mAdapterLamps = new RVAdapter((List<Product>) result);
            mRecyclerViewLamps.setAdapter(mAdapterLamps);
        }
    };
}
