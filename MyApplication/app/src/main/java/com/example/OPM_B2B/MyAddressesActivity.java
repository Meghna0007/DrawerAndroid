package com.example.OPM_B2B;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.example.OPM_B2B.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressesActivity extends AppCompatActivity {
    private Button deliverHEREBtn;
    private RecyclerView myAddressRecyclerView;
    private static AddressesAdapter addressesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myAddressRecyclerView=findViewById(R.id.address_Recyclerview);
        deliverHEREBtn=findViewById(R.id.deliver_here_btn);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressRecyclerView.setLayoutManager(layoutManager);


        List<AddressesModel> addressesModelList=new ArrayList<>();
        addressesModelList.add(new AddressesModel("John","202020","kanpur abc acb",true));
        addressesModelList.add(new AddressesModel("Johna","202020","kanpur abc acb",false));
        addressesModelList.add(new AddressesModel("Johnc","202020","kanpur abc acb",false));
        addressesModelList.add(new AddressesModel("Johnv","202020","kanpur abc acb",false));
        addressesModelList.add(new AddressesModel("Johnx","202020","kanpur abc acb",false));

        int mode=getIntent().getIntExtra("MODE",-1);

        if(mode==SELECT_ADDRESS){
            deliverHEREBtn.setVisibility(View.VISIBLE);
        }else {
            deliverHEREBtn.setVisibility(View.GONE);
        }
        addressesAdapter=new AddressesAdapter(addressesModelList,mode);
    myAddressRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)myAddressRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    addressesAdapter.notifyDataSetChanged();

    }
    public static void refreshItem(int deselect,int select){
       addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}