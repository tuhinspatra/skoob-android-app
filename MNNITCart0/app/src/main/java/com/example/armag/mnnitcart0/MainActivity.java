package com.example.armag.mnnitcart0;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.armag.mnnitcart0.fragments.AboutFragment;
import com.example.armag.mnnitcart0.fragments.BuySellFragment;
import com.example.armag.mnnitcart0.fragments.CategoryFragment;
import com.example.armag.mnnitcart0.fragments.ProfileFragment;
import com.example.armag.mnnitcart0.fragments.ShowCartFragment;
import com.example.armag.mnnitcart0.fragments.WishlistFragment;
import com.example.armag.mnnitcart0.fragments.buy.CategoryHomeFragment;
import com.example.armag.mnnitcart0.fragments.buy.ItemDetailsFragment;
import com.example.armag.mnnitcart0.fragments.sell.SubmitItemFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BuySellFragment.OnFragmentInteractionListener,
        CategoryFragment.OnFragmentInteractionListener,
        ItemDetailsFragment.OnFragmentInteractionListener,
        CategoryHomeFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener,
        WishlistFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        ShowCartFragment.OnFragmentInteractionListener,
        SubmitItemFragment.OnFragmentInteractionListener{



    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final String TAG = "WelcomeActivity";
    public static final int RC_SIGN_IN = 123;
    private String mUsername;
    FragmentTransaction tx;

    // Global variables to keep track of user's state
    private Fragment currentFragment;
    private int mIid;
    private int mCid;
    // TODO: set mUid!!!
    private int mUid=101;
    boolean buyer = true; // user is buyer or seller ( at a particular time)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // currentFragment = null;
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Hello skooBer!");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx = getSupportFragmentManager().beginTransaction();
                currentFragment = ShowCartFragment.newInstance(mUid,null);
                tx.replace(R.id.main_frame, currentFragment,null);
                tx.addToBackStack(null);
                tx.commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    // user is signed in
                    Log.w(TAG, "signed in");
                    onSignInIntialize(user);

                } else {
                    // user is signed out. Launch sign in flow.
                    Log.w(TAG, "Not signed in");
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);


                }
            }
        };


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "onSaveInstanceState");

        super.onSaveInstanceState(outState);
        //outState.putLong();
        Fragment currentFragment = this.getSupportFragmentManager().findFragmentById(R.id.main_frame);
        this.currentFragment = currentFragment;
        if(currentFragment!=null) {
            getSupportFragmentManager().putFragment(outState,"currentFragment",currentFragment);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            tx = getSupportFragmentManager().beginTransaction();
            currentFragment = AboutFragment.newInstance(null,null);
            tx.replace(R.id.main_frame, currentFragment,null);
            tx.addToBackStack(null);
            tx.commit();
            return true;
        } else if( id == R.id.sign_out_menu) {
            signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_buy) {
           // TODO: if buyer presses back button make him seller again if he was previously and vice-versa
            buyer = true;
            tx = getSupportFragmentManager().beginTransaction();
            currentFragment = CategoryFragment.newInstance(null,null);
            tx.replace(R.id.main_frame, currentFragment,null);
            tx.addToBackStack(null);
            tx.commit();

        } else if (id == R.id.nav_sell) {

            buyer = false;
            tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.main_frame, CategoryFragment.newInstance(null,null),null);
            tx.addToBackStack(null);
            tx.commit();

        } else if (id == R.id.nav_profile) {

            tx = getSupportFragmentManager().beginTransaction();
            currentFragment = ProfileFragment.newInstance(null,null);
            tx.replace(R.id.main_frame, currentFragment,null);
            tx.addToBackStack(null);
            tx.commit();

        } else if (id == R.id.nav_wishlist) {

            tx = getSupportFragmentManager().beginTransaction();
            currentFragment =  WishlistFragment.newInstance(null,null);
            tx.replace(R.id.main_frame,currentFragment,null);
            tx.addToBackStack(null);
            tx.commit();

        } else if (id == R.id.nav_exit) {

            exit_prompt();

        } else if (id == R.id.nav_cart) {

            tx = getSupportFragmentManager().beginTransaction();
            currentFragment = ShowCartFragment.newInstance(mUid,null);
            tx.replace(R.id.main_frame, currentFragment,null);
            tx.addToBackStack(null);
            tx.commit();

        } else if (id == R.id.nav_share) {

            // share app
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_sign_out) {

            // remove fragments and sign out
            signOut();


        } else if (id == R.id.nav_about) {

            tx = getSupportFragmentManager().beginTransaction();
            currentFragment = AboutFragment.newInstance(null,null);
            tx.replace(R.id.main_frame, currentFragment,null);
            tx.addToBackStack(null);
            tx.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void exit_prompt() {

        // prompt user all progress will be lost
        AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
        ab.setMessage("All progress will be lost. Are you sure you want to exit?").setCancelable(false).setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
                System.exit(0);

            }
        }).setNegativeButton("Keep Skoobing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = ab.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            if(resultCode == Activity.RESULT_OK) {
                Log.e(TAG,"Signed in!" );

            } else if(resultCode == Activity.RESULT_CANCELED) {
                Log.e(TAG,"Signed in cancelled" );
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
        if(mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
            //save which fragment the user was in
        }


    }

    @Override
    protected void onResume() {

        Log.e(TAG, "onResume");
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }



    private void onSignInIntialize(FirebaseUser user) {
        Log.e(TAG, "onSignInIntialize");

        if(user.getDisplayName() != null) {
            mUsername = user.getDisplayName().toString();
        } else {
            mUsername = "User";
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        View navHeaderView =  navigationView.getHeaderView(0);
        TextView emailHeaderview = (TextView)navHeaderView.findViewById(R.id.email_drawer_header_textView);
        emailHeaderview.setText(user.getEmail());
        TextView usernameHeaderview = (TextView)navHeaderView.findViewById(R.id.username_drawer_header_textView);
        usernameHeaderview.setText(mUsername);
        tx = getSupportFragmentManager().beginTransaction();
        if(currentFragment == null) {
            currentFragment = BuySellFragment.newInstance(null,null);
        } else if(currentFragment instanceof  CategoryFragment) {
            currentFragment = CategoryFragment.newInstance(null,null);
        } else if(currentFragment instanceof  CategoryHomeFragment) {
            currentFragment = CategoryHomeFragment.newInstance(mCid,null);
        } else if(currentFragment instanceof  ItemDetailsFragment) {
            currentFragment = ItemDetailsFragment.newInstance(mIid,null);
        } else if(currentFragment instanceof  SubmitItemFragment) {
            currentFragment = SubmitItemFragment.newInstance(null,null);
        } else if(currentFragment instanceof  AboutFragment) {
            currentFragment = AboutFragment.newInstance(null,null);
        } else if(currentFragment instanceof  WishlistFragment) {
            currentFragment = WishlistFragment.newInstance(null,null);
        } else if(currentFragment instanceof  ShowCartFragment) {
            currentFragment = ShowCartFragment.newInstance(mUid,null);
        } else if(currentFragment instanceof  ProfileFragment) {
            currentFragment = ProfileFragment.newInstance(null,null);
        }
        Log.e(TAG, "" + currentFragment);
        tx.replace(R.id.main_frame,currentFragment,null);
        tx.commit();

    }



    @Override
    public void willBuySell(boolean willBuy) {
        // Replace BuySellFragment with BuyMainFragment or SellMainFragment according to willBuy
        if(willBuy) {
            buyer = true;
        } else {
            buyer = false;
        }
        tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main_frame, CategoryFragment.newInstance(null,null),null);
        tx.addToBackStack(null);
        tx.commit();

    }

    public void addQuantityToCart(int iid, int noOfItems) {
        // add the iid and noOfItems to cart;
       Log.e(TAG, "iid:"+ iid + ",noOfItems:"+noOfItems+ " to add");
    }

    @Override
    public void openCategoryFragment(int cid) {
        tx = getSupportFragmentManager().beginTransaction();
        mCid = cid;
        if(buyer) {
            currentFragment = CategoryHomeFragment.newInstance(cid, null);
            tx.replace(R.id.main_frame, currentFragment,null);
        } else {
            currentFragment = SubmitItemFragment.newInstance(null, null);
            tx.replace(R.id.main_frame, currentFragment,null);
        }

        tx.addToBackStack(null);
        tx.commit();
    }

    @Override
    public void openItem(int iid) {
        tx = getSupportFragmentManager().beginTransaction();
        currentFragment = ItemDetailsFragment.newInstance(iid,null);
        mIid = iid;
        tx.replace(R.id.main_frame, currentFragment,null);
        tx.addToBackStack(null);
        tx.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i(TAG, "TODO");
    }

    @Override
    public void addItemToCart(int iid) {
        Log.e(TAG,"add "+iid);
    }
}
