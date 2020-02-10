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
import com.example.ad_team10.models.CustomItem;
import com.example.ad_team10.models.CustomRequisition;

import org.w3c.dom.Text;

import java.util.List;

public class CustomRequisitionAdapter extends ArrayAdapter<CustomRequisition> {
    public boolean isByItem = false;
    public CustomRequisitionAdapter(Context context, int resource, List<CustomRequisition> requisitions) {
        super(context, resource, requisitions);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_requisition, parent, false);
        }

        CustomRequisition requisition = getItem(position);

        if (requisition != null) {
            TextView tvName = v.findViewById(R.id.employeeName);
            TextView tvDate = v.findViewById(R.id.requisitionDate);
            TextView tvQty = v.findViewById(R.id.quantityOrdered);
            TextView tvQtyReceived = v.findViewById(R.id.quantityReceived);
            if(!isByItem){
                tvQty.setVisibility(View.GONE);
                tvQtyReceived.setVisibility(View.GONE);
            }else {
                tvQty.setText(Integer.toString(requisition.getCustomItems()[0].getQuantity()));
                tvQtyReceived.setText(Integer.toString(requisition.getCustomItems()[0].getQuantityReceived()));
            }

            tvName.setText(requisition.getEmployeeName());
            tvDate.setText(requisition.getRequisitionDate());
        }
        return v;
    }
}
