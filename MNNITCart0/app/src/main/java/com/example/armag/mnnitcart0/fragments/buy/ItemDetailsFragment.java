package com.example.armag.mnnitcart0.fragments.buy;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.armag.mnnitcart0.R;
import com.example.armag.mnnitcart0.models.ProductDetailsItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.armag.mnnitcart0.MetaData.PRODUCTS_DETAIL_INFO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemDetailsFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = "ItemDetailsFragment";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mProductDetailsDatabaseReference;

    TextView description_ans;
    TextView description_title;
    TextView copies_ans;
    TextView copies_title;
    TextView pages_title;
    TextView pages_ans;
    TextView publisher_title;
    TextView seller_ans;
    TextView seller_title;
    TextView publisher_ans;
    Button skoobIt;
    Button addCart;
    Button addWishlist;
    private ValueEventListener mDetailsListener;
    ItemDetailsFragment mThis = this;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "iid";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mIid;
    private String mParam2;

    private ItemDetailsFragment.OnFragmentInteractionListener mListener;

    public ItemDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param iid Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemDetailsFragment newInstance(int iid, String param2) {
        ItemDetailsFragment fragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, iid);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIid = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mProductDetailsDatabaseReference = mFirebaseDatabase.getReference()
                .child(PRODUCTS_DETAIL_INFO).child(mIid+"");
        description_ans = view.findViewById(R.id.description_ans);
        description_title = view.findViewById(R.id.description_title);
        copies_ans = view.findViewById(R.id.copies_ans);
        copies_title = view.findViewById(R.id.copies_title);
        pages_ans = view.findViewById(R.id.pages_ans);
        pages_title = view.findViewById(R.id.pages_title);
        publisher_title = view.findViewById(R.id.publisher_title);
        publisher_ans = view.findViewById(R.id.publisher_ans);
        seller_ans = view.findViewById(R.id.seller_ans);
        seller_title = view.findViewById(R.id.seller_title);
        skoobIt = view.findViewById(R.id.skoob_it);
        addCart = view.findViewById(R.id.add_cart);
        addWishlist = view.findViewById(R.id.add_wishlist);
        ValueEventListener detailsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ProductDetailsItem productDetailsItem = dataSnapshot.getValue(ProductDetailsItem.class);
                if(productDetailsItem.getDescription()==null){
                    Log.e(TAG, "getDescription returned null");
                } else {
                    description_ans.setText(productDetailsItem.getDescription());
                    publisher_ans.setText(productDetailsItem.getPublisher());
                    seller_ans.setText(productDetailsItem.getSellerUid());
                    String copies = productDetailsItem.getCopiesSold();
                    if(Integer.parseInt(copies)==-1){
                        copies = "Unknown";
                    }
                    copies_ans.setText(copies);
                    String pages = productDetailsItem.getNoOfPages().trim();
                    if(Integer.parseInt(pages)==-1){
                        pages = "Unknown";
                    }
                    pages_ans.setText(pages);
                    seller_ans.setVisibility(View.VISIBLE);
                    seller_title.setVisibility(View.VISIBLE);
                    publisher_title.setVisibility(View.VISIBLE);
                    publisher_ans.setVisibility(View.VISIBLE);
                    description_ans.setVisibility(View.VISIBLE);
                    description_title.setVisibility(View.VISIBLE);
                    copies_ans.setVisibility(View.VISIBLE);
                    copies_title.setVisibility(View.VISIBLE);
                    pages_title.setVisibility(View.VISIBLE);
                    pages_ans.setVisibility(View.VISIBLE);
                    skoobIt.setOnClickListener(mThis);
                    addCart.setOnClickListener(mThis);
                    addWishlist.setOnClickListener(mThis);



                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDetailsListener = detailsListener;
        mProductDetailsDatabaseReference.addValueEventListener(mDetailsListener);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ItemDetailsFragment.OnFragmentInteractionListener) {
            mListener = (ItemDetailsFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mDetailsListener!=null) {
            mProductDetailsDatabaseReference.removeEventListener(mDetailsListener);
        }

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), "This feature is coming Soon...", Toast.LENGTH_SHORT).show();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void addItemToCart(int iid);
    }
}
