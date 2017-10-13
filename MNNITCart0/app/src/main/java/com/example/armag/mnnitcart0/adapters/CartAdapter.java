package com.example.armag.mnnitcart0.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.armag.mnnitcart0.R;
import com.example.armag.mnnitcart0.models.CartItem;

import java.util.List;


/**
 * Created by armag on 12/10/17.
 */

public class CartAdapter extends ArrayAdapter<CartItem>{

    public static String TAG = "CartAdapter";

    public CartAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CartItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_cart_list, parent, false);
        }
        TextView iidAns = (TextView) convertView.findViewById(R.id.cart_iid_ans);
        TextView iidTitle = (TextView) convertView.findViewById(R.id.cart_iid_title);
        TextView quanAns = (TextView) convertView.findViewById(R.id.cart_quan_ans);
        TextView quanTitle = (TextView) convertView.findViewById(R.id.cart_quan_title);
        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);


        CartItem cartItem = getItem(position);
        iidAns.setText(cartItem.getIid());
        quanAns.setText(cartItem.getQuantity());
        iidTitle.setVisibility(View.VISIBLE);
        iidAns.setVisibility(View.VISIBLE);
        quanAns.setVisibility(View.VISIBLE);
        quanTitle.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        return convertView;


    }


    @Override
    public long getItemId(int position) {
        CartItem cartItem = getItem(position);
        return Integer.parseInt(cartItem.getIid());
    }
}
