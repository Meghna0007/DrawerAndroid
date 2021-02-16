package com.opm.b2b;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.opm.b2b.ProductDetailsActivity.cartItem;
import static com.opm.b2b.RegisterActivity.setSignUpFragment;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.opm.b2b.ui.home.AllCategoriesFragment;
import com.google.android.material.navigation.NavigationView;



public class Main4Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int ALLCATEGORY_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDERS_FRAGMENT = 2;
    private static final int ACCOUNT_FRAGMENT=3;
    private static final int WISHLIST_FRAGMENT=4;
    public static Boolean showCart=false;
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;
    private ImageView noInternetConnection;
    private ImageView actionBarLogo;
    public static int currentFragment = -1;
    private NavigationView navigationView;
    private Dialog signinDialog;
    public static DrawerLayout drawer;
    private TextView badgeCount;
private int scrollFlags;
private  AppBarLayout.LayoutParams params;
    //public static boolean isCartFragmentOpened = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        recyclerView = findViewById(R.id.all_categories_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        Toolbar toolbar = findViewById(R.id.toolbar);
        actionBarLogo = findViewById(R.id.actionbar_logo);
        setSupportActionBar(toolbar);
        //   getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // getSupportActionBar().setTitle("OPM  B2B");

        currentFragment = ALLCATEGORY_FRAGMENT;

       params=(AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        scrollFlags= params.getScrollFlags();
         drawer = findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

       /* navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        int id = menuItem.getItemId();
                        // TODO: Add more if else for all drawer items
                        if (id == R.id.all_categories) {
                            setFragment(new AllCategoriesFragment());
                            return true;
                        }
                        setFragment(new AllCategoriesFragment());
                        return true;
                    }
                });*/


        frameLayout = findViewById(R.id.main_framelayout);

        if (showCart) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("My Cart", new MyCartFragment(), -2);

        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new AllCategoriesFragment(), ALLCATEGORY_FRAGMENT);

        }

        signinDialog=new Dialog(Main4Activity.this);
        signinDialog.setContentView(R.layout.signin_dialog);
        signinDialog.setCancelable(true);
        signinDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogCompleteProfileBtn=signinDialog.findViewById(R.id.CompleteYourProfile_button);
        Button dialogSignup=signinDialog.findViewById(R.id.Signup_button);

        Intent registerIntent=new Intent(Main4Activity.this,RegisterActivity.class);


        dialogCompleteProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinDialog.dismiss();
                setSignUpFragment=false;
                startActivity(registerIntent);
            }
        });
        dialogSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinDialog.dismiss();
                setSignUpFragment=true;
                startActivity(registerIntent);
            }
        });
    }
    protected void onStart(){
        super.onStart();
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null) {
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(false);
        }else{
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(true);

        }
 invalidateOptionsMenu();
    }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.all_categories, R.id.my_orders, R.id.my_cart, R.id.policies, R.id.about_us)
                .setDrawerLayout(drawer)
                .build();
       /* NavController navController = Navigation.findNavController(this, R.id.container);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.container);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (currentFragment == ALLCATEGORY_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main4, menu);

        }

        return true;

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (currentFragment == ALLCATEGORY_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main4, menu);

            MenuItem cartItem = menu.findItem(R.id.action_cart);
            cartItem.setActionView(R.layout.badge_layout);
            ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
            badgeIcon.setImageResource(R.drawable.cart);
            badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);
            if (currentUser != null) {
                if (DBqueries.cartList.size() == 0) {
                    DBqueries.loadCartList(Main4Activity.this, false, new Dialog(Main4Activity.this), badgeCount);

                    if (DBqueries.cartList.size() == 0) {
                        badgeCount.setVisibility(View.GONE);
                    }
                } else {
                    if (DBqueries.cartList.size() > 0)
                        badgeCount.setVisibility(View.VISIBLE);
                }
                if (DBqueries.cartList.size() > 0 && DBqueries.cartList.size() < 99) {
                    badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                } else if (DBqueries.cartList.size() > 0 && DBqueries.cartList.size() >= 99) {
                    {
                        badgeCount.setText("99");
                    }
                }
            }

            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {
                        signinDialog.show();
                    } else {
                        gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
                    }

                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_cart) {
            //isCartFragmentOpened = true;
           if(currentUser==null) {
               signinDialog.show();
           }else {
               gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
           }
            return true;
        } else if (id == R.id.main_search_icon) {
            //todo: search
            return true;
        }else if(id== android.R.id.home){
            if(showCart){
                showCart=false;
                finish();
                return true;
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoFragment(String title, Fragment fragment, int fragmentNo) {

        if (fragmentNo == ALLCATEGORY_FRAGMENT) {
            actionBarLogo.setVisibility(View.VISIBLE);
            navigationView.getMenu().getItem(0).setChecked(true);
            navigationView.getMenu().getItem(2).setChecked(false);

        } else {
            actionBarLogo.setVisibility(View.GONE);
        }

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if (fragmentNo == CART_FRAGMENT||showCart) {
            navigationView.getMenu().getItem(2).setChecked(true);
            navigationView.getMenu().getItem(0).setChecked(false);
            params.setScrollFlags(0);

        }else {
            params.setScrollFlags(scrollFlags);
        }


    }
MenuItem menuItem;
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
menuItem=item;
        if (currentUser != null) {
            drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    int id = menuItem.getItemId();

                    if (id == R.id.all_categories) {
                        // getSupportActionBar().setDisplayShowTitleEnabled(false);
                        actionBarLogo.setVisibility(View.VISIBLE);
                        invalidateOptionsMenu();
                        setFragment(new AllCategoriesFragment(), ALLCATEGORY_FRAGMENT);
                        // Toast.makeText(getApplicationContext(), "Home Clicked", Toast.LENGTH_SHORT).show();
            /*setFragment(new AllCategoriesFragment());
            return true;*/
                    } else if (id == R.id.my_orders) {
                        gotoFragment("My Orders", new MyOrdersFragment(), ORDERS_FRAGMENT);
                    } else if (id == R.id.my_cart) {
                        gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);

                    } else if (id == R.id.my_account) {
                        gotoFragment("My Account", new MyAccountFragment(), ACCOUNT_FRAGMENT);
                    } else if (id == R.id.my_wishlist) {
                        gotoFragment("My Wishlist", new My_WishlistFragment(), WISHLIST_FRAGMENT);


                    } else if (id == R.id.policies) {

                    } else if (id == R.id.about_us) {

                    } else if (id == R.id.sign_out) {
                        FirebaseAuth.getInstance().signOut();
                        DBqueries.clearData();
                        Intent registerIntent=new Intent(Main4Activity.this,RegisterActivity.class);
                        startActivity(registerIntent);
                        finish();


                    }
                }
            });

            return true;
        } else {
            signinDialog.show();
            return false;
        }

    }

    private void setFragment(Fragment fragment, int fragmentNo) {
       // if (fragmentNo != currentFragment) {
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
       // }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentFragment == CART_FRAGMENT) {
                gotoFragment("All Categories", new AllCategoriesFragment(), ALLCATEGORY_FRAGMENT);
                //gotoFragment(new AllCategoriesFragment(),ALLCATEGORY_FRAGMENT);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == ALLCATEGORY_FRAGMENT) {
                currentFragment= -1;
                super.onBackPressed();
            } else   if (showCart) {
                showCart=false;
                finish();

            } else {
                actionBarLogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setFragment(new AllCategoriesFragment(), ALLCATEGORY_FRAGMENT);
                navigationView.getMenu().getItem(0).setChecked(true);
            }

        }
    }
}