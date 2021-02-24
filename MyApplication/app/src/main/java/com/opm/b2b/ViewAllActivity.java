package com.opm.b2b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static com.opm.b2b.DBqueries.getStringValue;

public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private GridView gridView;
    public static List<WishlistModel> wishlistModelList;
    public static List<HorizonantleProductScrollModel> horizonantleProductScrollModelList;
    private WishlistAdapter wishlistAdapter;
    private String fmcg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Intent intent = getIntent();
        String collectionName = intent.getStringExtra("collectionName");
        String displayName = intent.getStringExtra("displayName");
        fmcg = intent.getStringExtra("fmcg");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(displayName);
        recyclerView = findViewById(R.id.recycler_view);
        gridView = findViewById(R.id.grid_view);

        int layout_code = getIntent().getIntExtra("layout_code", -1);

        if (layout_code == 1) {

            gridView.setVisibility(View.VISIBLE);
            GridProductViewAdapter gridProductViewAdapter = new GridProductViewAdapter(horizonantleProductScrollModelList);
            gridView.setAdapter(gridProductViewAdapter);
        } else if (collectionName == null && !CollectionUtils.isEmpty(wishlistModelList)) {
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView.setLayoutManager(layoutManager);
            //List<WishlistModel> wishlistModelList = new ArrayList<>();
            wishlistAdapter = new WishlistAdapter(wishlistModelList, false);
            //populateListFromFirebase(wishlistModelList, collectionName);
            recyclerView.setAdapter(wishlistAdapter);
            wishlistAdapter.notifyDataSetChanged();
        } else {

            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView.setLayoutManager(layoutManager);
            List<WishlistModel> wishlistModelList = new ArrayList<>();
            wishlistAdapter = new WishlistAdapter(wishlistModelList, false);
            populateListFromFirebase(wishlistModelList, collectionName);
            recyclerView.setAdapter(wishlistAdapter);
            wishlistAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (fmcg != null && fmcg.equals("true")) {
                finish();
                Intent categoryIntent = new Intent(ViewAllActivity.this, FMCGActivity.class);
                startActivity(categoryIntent);
            } else {
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


   private void populateListFromFirebase(List<WishlistModel> wishlistModels, String collectionName) {

        firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseFirestore.collection(collectionName).orderBy("index")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult()){

                        /*wishlistModels.add(new WishlistModel(DBqueries.getStringValue(documentSnapshot.get("product_id")),
                                DBqueries.getStringValue(documentSnapshot.get("icon")),
                                DBqueries.getStringValue(documentSnapshot.get("productTitle")),
                                getStringValue(documentSnapshot.get("productPrice")),
                                getStringValue(documentSnapshot.get("cuttedPrice")),
                                getStringValue(documentSnapshot.get("productWeight")),
                                getStringValue(documentSnapshot.get("setPiece")),
                                getStringValue(documentSnapshot.get("perPiece"))));*/
                    }
                    wishlistAdapter.notifyDataSetChanged();

                } else {
                    String error=task.getException().getMessage();
                    Toast.makeText(ViewAllActivity.this,error,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


