package com.example.armag.mnnitcart0.fragments.buy;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.armag.mnnitcart0.R;
import com.example.armag.mnnitcart0.adapters.CategoryAdapter;
import com.example.armag.mnnitcart0.adapters.ProductAdapter;
import com.example.armag.mnnitcart0.models.CategoryItem;
import com.example.armag.mnnitcart0.models.ProductItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.armag.mnnitcart0.MetaData.CATEGORY_LIST_MAIN;
import static com.example.armag.mnnitcart0.MetaData.PRODUCTS_INFO;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryHomeFragment extends Fragment {

    public static final String TAG = "CategoryHomeFragment";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mProductsDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ListView mProductsListView;
    private ProductAdapter mProductAdapter;


    @Override
    public void onPause() {
        super.onPause();
        detachDatabaseReadListener();
        mProductAdapter.clear();

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "cid";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mCid;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CategoryHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cid Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryHomeFragment newInstance(int cid, String param2) {
        CategoryHomeFragment fragment = new CategoryHomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, cid);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCid = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_home, container, false);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mProductsDatabaseReference = mFirebaseDatabase.getReference().child(PRODUCTS_INFO);
        mProductsListView = view.findViewById(R.id.product_listView);
        List<ProductItem> productItems = new ArrayList<>();

        mProductAdapter = new ProductAdapter(getContext(), R.layout.item_product_list, productItems,mCid);
        mProductsListView.setAdapter(mProductAdapter);
        mProductAdapter.notifyDataSetChanged();
        attachDatabaseReadListener();
        mProductsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackgroundColor(Color.LTGRAY);
                int selectedIid = (int)l;
                if (mListener != null) {
                    mListener.openItem(selectedIid);
                }
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int iid) {
        if (mListener != null) {
            mListener.openItem(iid);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
        mProductAdapter.clear();
    }

    private void attachDatabaseReadListener() {

        if(mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ProductItem messageItem = dataSnapshot.getValue(ProductItem.class);
                    mProductAdapter.add(messageItem);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mProductsDatabaseReference.addChildEventListener(mChildEventListener);
        }



    }

    private void detachDatabaseReadListener() {
        if(mChildEventListener != null) {
            mProductsDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }

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
        void openItem(int iid);
    }
}
