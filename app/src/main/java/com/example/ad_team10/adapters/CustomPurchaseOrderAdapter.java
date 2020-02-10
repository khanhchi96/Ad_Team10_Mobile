package com.example.ad_team10.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ad_team10.R;
import com.example.ad_team10.models.CustomPurchaseOrder;

import java.util.List;

public class CustomPurchaseOrderAdapter extends ArrayAdapter<CustomPurchaseOrder> {
    public int chosenId = -1;

    public CustomPurchaseOrderAdapter(Context context, int resource, List<CustomPurchaseOrder> orders) {
        super(context, resource, orders);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_purchase_order, parent, false);
        }

        CustomPurchaseOrder order = getItem(position);

        if (order != null) {
            TextView tvId = v.findViewById(R.id.order_Id);
            TextView tvDate = v.findViewById(R.id.order_date);
            TextView tvStatus = v.findViewById(R.id.status);
            TextView tvSupplier = v.findViewById(R.id.supplier_name);

            tvId.setText(Integer.toString(order.getOrderID()));
            tvDate.setText(order.getOrderDate());
            tvStatus.setText(order.getStatus());
            tvSupplier.setText(order.getSupplierName());
            Button btnView = v.findViewById(R.id.btnView);
            Button btnUpdate = v.findViewById(R.id.btnUpdate);

            if(chosenId == position){
                btnView.setVisibility(View.VISIBLE);
                if(!order.getStatus().equals("Completed")) btnUpdate.setVisibility(View.VISIBLE);

            }else {
                btnView.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.GONE);
            }
        }
        return v;
    }
}
