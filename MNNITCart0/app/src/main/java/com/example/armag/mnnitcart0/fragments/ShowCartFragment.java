package com.example.armag.mnnitcart0.fragments;

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
import com.example.armag.mnnitcart0.adapters.CartAdapter;
import com.example.armag.mnnitcart0.models.CartItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.armag.mnnitcart0.MetaData.CARTS;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowCartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowCartFragment extends Fragment {

    public static final String TAG = "ShowCartFragment";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCartDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ListView mCartListView;
    private CartAdapter mCartAdapter;


    @Override
    public void onPause() {
        super.onPause();
        detachDatabaseReadListener();
        mCartAdapter.clear();

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "uid";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mUid;
    private String mParam2;

    private ShowCartFragment.OnFragmentInteractionListener mListener;

    public ShowCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param uid Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowCartFragment newInstance(int uid, String param2) {
        ShowCartFragment fragment = new ShowCartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, uid);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUid = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_cart, container, false);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCartDatabaseReference = mFirebaseDatabase.getReference().child(CARTS).child(mUid+"");
        mCartListView = view.findViewById(R.id.cartListView);
        List<CartItem> cartItems = new ArrayList<>();

        mCartAdapter = new CartAdapter(getContext(), R.layout.item_cart_list, cartItems);
        mCartListView.setAdapter(mCartAdapter);
        mCartAdapter.notifyDataSetChanged();
        attachDatabaseReadListener();
        mCartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackgroundColor(Color.LTGRAY);
                int selectedIid = (int)l;
                if (mListener != null) {
                    Log.e(TAG, "Opening item");
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
        if (context instanceof ShowCartFragment.OnFragmentInteractionListener) {
            mListener = (ShowCartFragment.OnFragmentInteractionListener) context;
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
        mCartAdapter.clear();
    }

    private void attachDatabaseReadListener() {

        if(mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    CartItem cartItem = dataSnapshot.getValue(CartItem.class);
                    mCartAdapter.add(cartItem);
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
            mCartDatabaseReference.addChildEventListener(mChildEventListener);
        }



    }

    private void detachDatabaseReadListener() {
        if(mChildEventListener != null) {
            mCartDatabaseReference.removeEventListener(mChildEventListener);
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
