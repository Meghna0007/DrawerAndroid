package com.opm.b2b;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.opm.b2b.DeliveryActivity.SELECT_ADDRESS;

public class MyAddress extends AppCompatActivity {
    private Button deliverHEREBtn;
    private LinearLayout addNewAddressBtn;
    private int previousAddresses;
    private TextView addressesSaved;
private Dialog loadingDialog;
    private RecyclerView myAddressRecyclerView;
    private static AddressAdapter addressesAdapter;
private int mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //////////////Loading Dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                addressesSaved.setText(String.valueOf(DBqueries.addressesModelList.size()) + "saved addresses");

            }
        });
        //////////////Loading Dialog

        myAddressRecyclerView = findViewById(R.id.address_Recyclerview);
        deliverHEREBtn = findViewById(R.id.deliver_here_btn);
        addNewAddressBtn = findViewById(R.id.add_new_address_btn);
        addressesSaved = findViewById(R.id.addressSaved);
        previousAddresses = DBqueries.selectedAddresss;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressRecyclerView.setLayoutManager(layoutManager);


        // List<AddressesModel> addressesModelList=new ArrayList<>();
       /* addressesModelList.add(new AddressesModel("John","202020","kanpur abc acb",true));
        addressesModelList.add(new AddressesModel("Johna","202020","kanpur abc acb",false));
        addressesModelList.add(new AddressesModel("Johnc","202020","kanpur abc acb",false));
        addressesModelList.add(new AddressesModel("Johnv","202020","kanpur abc acb",false));
        addressesModelList.add(new AddressesModel("Johnx","202020","kanpur abc acb",false));
*/
        mode = getIntent().getIntExtra("MODE", -1);

        if (mode == SELECT_ADDRESS) {
            deliverHEREBtn.setVisibility(View.VISIBLE);
        } else {
            deliverHEREBtn.setVisibility(View.GONE);
        }

        deliverHEREBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DBqueries.selectedAddresss != previousAddresses) {
                    int previousAddressIndex = previousAddresses;
                    loadingDialog.show();
                    Map<String, Object> updateSelection = new HashMap<>();
                    updateSelection.put("selected_" + String.valueOf(previousAddresses + 1), false);
                    updateSelection.put("selected_" + String.valueOf(DBqueries.selectedAddresss + 1), true);

                    previousAddresses = DBqueries.selectedAddresss;

                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                            .collection("USER_DATA").document("MY_ADDRESSES")
                            .update(updateSelection).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                finish();
                            } else {
                                previousAddresses = previousAddressIndex;
                                String error = task.getException().getMessage();
                                Toast.makeText(MyAddress.this, error, Toast.LENGTH_SHORT).show();
                            }
                            loadingDialog.dismiss();
                        }
                    });
                }else {
                    finish();
                }
            }
        });


        addressesAdapter = new AddressAdapter(DBqueries.addressesModelList, mode,loadingDialog);
        myAddressRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator) myAddressRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();

        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAddressIntent = new Intent(MyAddress.this, AddAddressActivity.class);
                if (mode != SELECT_ADDRESS){
                    addAddressIntent.putExtra("INTENT", "manage");

                }else {
                    addAddressIntent.putExtra("INTENT", "null");
                }
                startActivity(addAddressIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        addressesSaved.setText(String.valueOf(DBqueries.addressesModelList.size()) + "saved addresses");

    }

    public static void refreshItem(int deselect, int select) {
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mode==SELECT_ADDRESS) {
                if (DBqueries.selectedAddresss != previousAddresses) {
                    DBqueries.addressesModelList.get(DBqueries.selectedAddresss).setSelected(false);
                    DBqueries.addressesModelList.get(previousAddresses).setSelected(true);
                    DBqueries.selectedAddresss = previousAddresses;
                }
            }
                finish();
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
        @Override
        public void onBackPressed(){
        if(mode==SELECT_ADDRESS) {
            if (DBqueries.selectedAddresss != previousAddresses) {
                DBqueries.addressesModelList.get(DBqueries.selectedAddresss).setSelected(false);
                DBqueries.addressesModelList.get(previousAddresses).setSelected(true);
                DBqueries.selectedAddresss = previousAddresses;
            }
        }
            super.onBackPressed();
        }
    }
