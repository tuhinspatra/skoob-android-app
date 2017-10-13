package com.example.armag.mnnitcart0.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.armag.mnnitcart0.R;
import com.example.armag.mnnitcart0.models.ProductItem;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static com.example.armag.mnnitcart0.MetaData.PRODUCT_THUMBNAIL_DIR;

/**
 * Created by armag on 6/10/17.
 */

public class ProductAdapter extends ArrayAdapter<ProductItem> {

    public static String TAG = "ProductAdapter";
    private static int mCid;

    public ProductAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ProductItem> objects, int cid) {
        super(context, resource, objects);
        mCid = cid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_product_list, parent, false);
        }

        ImageView thumbnailImageView = (ImageView) convertView.findViewById(R.id.thumbnail_imageView);
        ImageView rupeeImageView = (ImageView) convertView.findViewById(R.id.rupee_sign);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.title_product_textView);
        TextView mspTextView = (TextView) convertView.findViewById(R.id.msp_textView);
        RatingBar ratingRatingBar = (RatingBar) convertView.findViewById(R.id.rating_ratingBar);
        RelativeLayout relativeLayout = convertView.findViewById(R.id.item_outer_layout);
        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progress_item_product_list);


        ProductItem productItem = getItem(position);
        if(Integer.parseInt(productItem.getCid()) == mCid) {
            titleTextView.setText(productItem.getTitle());
            titleTextView.setVisibility(View.VISIBLE);
            mspTextView.setText(productItem.getMsp());
            mspTextView.setVisibility(View.VISIBLE);
            ratingRatingBar.setVisibility(View.VISIBLE);

            ratingRatingBar.setVisibility(View.VISIBLE);
            ratingRatingBar.setRating(Float.valueOf(productItem.getRating()));
            thumbnailImageView.setVisibility(View.VISIBLE);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child(PRODUCT_THUMBNAIL_DIR + productItem.getThumbnail());

            Glide.with(thumbnailImageView.getContext())
                    .using(new FirebaseImageLoader())
                    .load(storageReference)
                    .into(thumbnailImageView);
            relativeLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);



        } else {

            relativeLayout.setVisibility(View.GONE);
            thumbnailImageView.setVisibility(View.GONE);
            rupeeImageView.setVisibility(View.GONE);
            titleTextView.setVisibility(View.GONE);
            mspTextView.setVisibility(View.GONE);
            ratingRatingBar.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);


        }

        return convertView;


    }

    @Override
    public long getItemId(int position) {
        ProductItem productItem = getItem(position);
        return Integer.parseInt(productItem.getIid());
    }
}
