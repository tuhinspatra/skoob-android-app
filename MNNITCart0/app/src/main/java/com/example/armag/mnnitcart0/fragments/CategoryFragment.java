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
import android.widget.ProgressBar;
import com.example.armag.mnnitcart0.R;
import com.example.armag.mnnitcart0.adapters.CategoryAdapter;
import com.example.armag.mnnitcart0.models.CategoryItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.armag.mnnitcart0.MetaData.CATEGORY_LIST_MAIN;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    public static final String TAG = "CategoryFragment";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCategoriesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ListView mCategoryListView;
    private CategoryAdapter mCategoryAdapter;
    private ProgressBar progressBar;


    @Override
    public void onPause() {
        super.onPause();
        detachDatabaseReadListener();
        mCategoryAdapter.clear();

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCategoriesDatabaseReference = mFirebaseDatabase.getReference().child(CATEGORY_LIST_MAIN);
        mCategoryListView = view.findViewById(R.id.categoryListView);
        progressBar = view.findViewById(R.id.progress_category_fragment);
        progressBar.setVisibility(View.VISIBLE);
        List<CategoryItem> categoryItems = new ArrayList<>();

        mCategoryAdapter = new CategoryAdapter(getContext(), R.layout.item_category, categoryItems);
        mCategoryListView.setAdapter(mCategoryAdapter);
        mCategoryAdapter.notifyDataSetChanged();
        attachDatabaseReadListener();
        mCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackgroundColor(Color.LTGRAY);
                int selectedCid = (int)l;
                if (mListener != null) {
                    mListener.openCategoryFragment(selectedCid);
                }
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int cid) {
        if (mListener != null) {
            mListener.openCategoryFragment(cid);
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
        mCategoryAdapter.clear();
    }

    private void attachDatabaseReadListener() {

        if(mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    CategoryItem messageItem = dataSnapshot.getValue(CategoryItem.class);
                    progressBar.setVisibility(View.GONE);
                    mCategoryAdapter.add(messageItem);
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
            mCategoriesDatabaseReference.addChildEventListener(mChildEventListener);
        }



    }

    private void detachDatabaseReadListener() {
        if(mChildEventListener != null) {
            mCategoriesDatabaseReference.removeEventListener(mChildEventListener);
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
        void openCategoryFragment(int cid);
    }
}
