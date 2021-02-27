package com.opm.b2b;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.opm.b2b.DeliveryActivity.SELECT_ADDRESS;
import static com.opm.b2b.MyAccountFragment.MANAGE_ADDRESS;
import static com.opm.b2b.MyAddress.refreshItem;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.Viewholder> {

    private List<AddressesModel>addressesModelList;
    private int MODE;
    private int preSelectedPosition;


    public AddressAdapter(List<AddressesModel> addressesModelList,int MODE) {
        this.addressesModelList = addressesModelList;
        this.MODE=MODE;
        preSelectedPosition = DBqueries.selectedAddresss;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout, parent, false);
        return new AddressAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        String name = addressesModelList.get(position).getFullname();
        String mobileNo=addressesModelList.get(position).getMobileNo();
        String address = addressesModelList.get(position).getAddress();
        String pincode = addressesModelList.get(position).getPincode();
        Boolean selected= addressesModelList.get(position).getSelected();
        holder.setData(name,address ,pincode,selected,position,mobileNo);
    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView fullname;
        private TextView address;
        private TextView pincode;
        private ImageView icon;
        private LinearLayout optionContainer;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            fullname=itemView.findViewById(R.id.fullname_MyAddress);
            address=itemView.findViewById(R.id.address_MyAddress);
            pincode=itemView.findViewById(R.id.pincode_MyAddress);
            icon=itemView.findViewById(R.id.iconView);
            optionContainer=itemView.findViewById(R.id.option_container);
        }
        private void setData(String username, String useraddress , String userpincode,Boolean selected, int position,String mobileNo) {
            fullname.setText(username+" - "+mobileNo);
            address.setText(useraddress);
            pincode.setText(userpincode);


            if(MODE==SELECT_ADDRESS){

                icon.setImageResource(R.drawable.ic_baseline_check_24);
                if(selected){
                    icon.setVisibility(View.VISIBLE);
                    preSelectedPosition=position;
                }else{
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(preSelectedPosition !=position) {
                            addressesModelList.get(position).setSelected(true);
                            addressesModelList.get(preSelectedPosition).setSelected(false);
                            refreshItem(preSelectedPosition, position);
                            preSelectedPosition = position;
                            DBqueries.selectedAddresss=position;
                        }
                    }
                });

            }else if(MODE== MANAGE_ADDRESS){
                optionContainer.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.vertical_dot);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition=position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition=-1;
                    }
                });

            }


        }
    }
}