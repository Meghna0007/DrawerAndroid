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
    private List<CategoryPageModel>categoryPageModelFakeList=new ArrayList<>();

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

        List<HorizonantleProductScrollModel>HorizonantleProductScrollModelFakeList=new ArrayList<>();
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("","","","",""));
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("","","","",""));
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("","","","",""));
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("","","","",""));
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("","","","",""));
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("","","","",""));
        HorizonantleProductScrollModelFakeList.add(new HorizonantleProductScrollModel("","","","",""));

        categoryPageModelFakeList.add(new CategoryPageModel(1,"","#ffffff"));
        categoryPageModelFakeList.add(new CategoryPageModel(2,"","#ffffff",HorizonantleProductScrollModelFakeList,new ArrayList<>()));
        categoryPageModelFakeList.add(new CategoryPageModel(3,"","#ffffff",HorizonantleProductScrollModelFakeList));

        categoryRecyclerView = findViewById(R.id.category_recyler_view);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);
        adapter=new CategoryPageAdapter(categoryPageModelFakeList);

        int listPosition=0;
        for(int x=0;x< loadedCategoriesNames.size();x++){
            if(loadedCategoriesNames.get(x).equals(title.toUpperCase())){
              listPosition=x;
            } }
        if (listPosition == 0) {
            loadedCategoriesNames.add(title.toUpperCase());
            lists.add(new ArrayList<CategoryPageModel>());
            loadFragmentData(categoryRecyclerView,this,loadedCategoriesNames.size()-1,title);
        }else {
            adapter = new CategoryPageAdapter(lists.get(listPosition));
        }
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true; }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_search_icon) { return true; }
        return super.onOptionsItemSelected(item);
    }
}