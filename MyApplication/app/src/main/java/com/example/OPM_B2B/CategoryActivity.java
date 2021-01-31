package com.example.OPM_B2B;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView = findViewById(R.id.category_recyler_view);
        ///////////Baner Slider

        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

        /*sliderModelList.add(new SliderModel(R.drawable.slide1, "green"));
        sliderModelList.add(new SliderModel(R.drawable.slide2, "green"));
        sliderModelList.add(new SliderModel(R.drawable.grocery, "green"));


        sliderModelList.add(new SliderModel(R.drawable.gst, "green"));
        sliderModelList.add(new SliderModel(R.drawable.g2, "green"));
        sliderModelList.add(new SliderModel(R.drawable.g3, "green"));
        sliderModelList.add(new SliderModel(R.drawable.aad, "green"));
        sliderModelList.add(new SliderModel(R.drawable.g1, "green"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher, "green"));


        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round, "green"));
        sliderModelList.add(new SliderModel(R.drawable.grocery, "green"));
        sliderModelList.add(new SliderModel(R.drawable.gst, "green"));
*/
        ////////////////horizon


        /*List<HorizonantleProductScrollModel> horizonantleProductScrollModelList = new ArrayList<>();
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product, "MariaLite", "Healthy_Wealthy", "Rs.30/-"));
*/

        ////////////////horizon


        ////////////////////////////////////////////

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);
        List<CategoryPageModel> categoryPageModelList = new ArrayList<>();
        //   categoryPageModelList.add(new CategoryPageModel(0,sliderModelList));
        //   categoryPageModelList.add(new CategoryPageModel(1,R.drawable.g4,"#000000"));
        //  categoryPageModelList.add(new CategoryPageModel(2,"Deals of the day",horizonantleProductScrollModelList));
        /*categoryPageModelList.add(new CategoryPageModel(3, "Biscuitttt", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Namkin", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Noodles&Pasta", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Beverges", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Dairy", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Choclate", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Personal Care", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Soaps", horizonantleProductScrollModelList));
        categoryPageModelList.add(new CategoryPageModel(3, "Foods", horizonantleProductScrollModelList));
        */
        CategoryPageAdapter adapter = new CategoryPageAdapter(categoryPageModelList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.main_search_icon) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}