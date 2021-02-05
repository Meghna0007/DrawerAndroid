package com.opm.b2b;

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

import static com.opm.b2b.DBqueries.lists;
import static com.opm.b2b.DBqueries.loadFragmentData;
import static com.opm.b2b.DBqueries.loadedCategoriesNames;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    CategoryPageAdapter adapter ;

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

        int listPosition=0;
        for(int x=0;x< loadedCategoriesNames.size();x++){

            if(loadedCategoriesNames.get(x).equals(title.toUpperCase())){
              listPosition=x;
            }

        }
        if (listPosition == 0) {

            loadedCategoriesNames.add(title.toUpperCase());
            lists.add(new ArrayList<CategoryPageModel>());
            adapter = new CategoryPageAdapter(lists.get(loadedCategoriesNames.size()-1));
            loadFragmentData(adapter,this,loadedCategoriesNames.size()-1,title);

        }else {
            adapter = new CategoryPageAdapter(lists.get(listPosition));
        }


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