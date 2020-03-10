//Author: Phung Khanh Chi

package com.example.ad_team10.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.ad_team10.R;
import com.example.ad_team10.models.CustomItem;

import java.util.List;

public class CustomItemAdapter extends ArrayAdapter<CustomItem> {
    public  boolean isRetrieval = false;
    public boolean isRequisition = false;
    public boolean isDisbursementDept = false;
    public int chosenId = -1;
    public CustomItemAdapter(Context context, int resource, List<CustomItem> items) {
        super(context, resource, items);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_item, parent, false);
        }

        CustomItem item = getItem(position);

        if (item != null) {
            TextView tvName = v.findViewById(R.id.itemName);
            TextView tvQty = v.findViewById(R.id.quantityOrdered);
            TextView tvQtyReceived = v.findViewById(R.id.quantityReceived);
            tvName.setText(item.getDescription());
            tvQty.setText("Quantity Ordered: " + item.getQuantity());
            if(isRetrieval){
                tvQtyReceived.setText("Quantity Offered: " + item.getQuantityOffered());
                showButton(v, position);
            }
            else if(isDisbursementDept){
                tvQtyReceived.setText("Received From Store: " + item.getQuantityReceived());
                showButton(v, position);
            }
            else if(isRequisition){ tvQtyReceived.setVisibility(View.GONE);}
            else tvQtyReceived.setText("Quantity Received: " + item.getQuantityReceived());

        }
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showButton(View v, int position){
        Button btnView = v.findViewById(R.id.btnView);
        Button btnUpdate = v.findViewById(R.id.btnUpdate);
        if(chosenId == position){
            btnView.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);

        }else {
            btnView.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
        }
    }
}
