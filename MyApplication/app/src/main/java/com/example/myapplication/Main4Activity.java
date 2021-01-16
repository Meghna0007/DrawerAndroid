package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import android.view.Menu;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.myapplication.ui.home.AllCategoriesFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Main4Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final  int ALLCATEGORY_FRAGMENT=0;
    private static final  int  CART_FRAGMENT=1;

    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;
    private ImageView actionBarLogo;
    private static int currentFragment;
    private  NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        recyclerView=findViewById(R.id.all_categories_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        Toolbar toolbar = findViewById(R.id.toolbar);
        actionBarLogo=findViewById(R.id.actionbar_logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
                drawer.setDrawerListener(toggle);
        toggle.syncState();
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


        setFragment(new AllCategoriesFragment(),ALLCATEGORY_FRAGMENT);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.all_categories, R.id.my_orders, R.id.my_cart, R.id.policies, R.id.about_us)
                .setDrawerLayout(drawer)
                .build();
       /* NavController navController = Navigation.findNavController(this, R.id.container);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.container);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == ALLCATEGORY_FRAGMENT) {
            getMenuInflater().inflate(R.menu.main4, menu);

        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_cart) {
            myCart();
            return true;
        } else if (id == R.id.main_search_icon) {
            //todo: search
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void myCart(){
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Cart");
        actionBarLogo.setVisibility(View.GONE);
        invalidateOptionsMenu();
        setFragment(new MyCartFragment(),CART_FRAGMENT);
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    private void setFragment(Fragment fragment ,int fragmentNo) {
        currentFragment=fragmentNo;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // TODO: Add more if else for all drawer items
        if (id == R.id.all_categories) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            actionBarLogo.setVisibility(View.VISIBLE);
            invalidateOptionsMenu();
            setFragment(new AllCategoriesFragment(),ALLCATEGORY_FRAGMENT);
           // Toast.makeText(getApplicationContext(), "Home Clicked", Toast.LENGTH_SHORT).show();
            /*setFragment(new AllCategoriesFragment());
            return true;*/
        }else if(id==R.id.my_orders){

        }else if(id==R.id.my_cart){
            myCart();

        }else if(id==R.id.policies){

        }else if(id==R.id.about_us){

        }else if(id==R.id.sign_out){

        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        //   setFragment(new AllCategoriesFragment());
        return true;


    }
}