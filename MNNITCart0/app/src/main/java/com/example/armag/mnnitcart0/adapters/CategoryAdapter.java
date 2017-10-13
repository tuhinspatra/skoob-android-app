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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.armag.mnnitcart0.R;
import com.example.armag.mnnitcart0.models.CategoryItem;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static com.example.armag.mnnitcart0.MetaData.CATEGORY_IMAGE_DIR;

/**
 * Created by armag on 22/9/17.
 */

public class CategoryAdapter extends ArrayAdapter<CategoryItem> {

    public CategoryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CategoryItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_category, parent, false);
        }
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progress_item_category);
        CategoryItem categoryItem = getItem(position);
        titleTextView.setText(categoryItem.getTitle());
        if(categoryItem.getTitle() != null) {
            iconImageView.setVisibility(View.VISIBLE);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child(CATEGORY_IMAGE_DIR + categoryItem.getUrl());
            Glide.with(iconImageView.getContext())
                    .using(new FirebaseImageLoader())
                    .load(storageReference)
                    .into(iconImageView);
            progressBar.setVisibility(View.GONE);


        } else {
            iconImageView.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        CategoryItem categoryItem = getItem(position);
        return Integer.parseInt(categoryItem.getCid());
    }
}
