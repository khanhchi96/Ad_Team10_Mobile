//Author: Phung Khanh Chi

package com.example.ad_team10.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.ad_team10.R;
import com.example.ad_team10.filter.InputFilterMinMax;
import com.example.ad_team10.models.CustomItem;
import com.example.ad_team10.models.CustomRequisition;


import java.util.HashMap;
import java.util.List;

public class CustomRequisitionUpdateAdapter extends ArrayAdapter<CustomRequisition> {
    public CustomRequisitionUpdateAdapter(Context context, int resource, List<CustomRequisition> requisitions) {
        super(context, resource, requisitions);
        for(int i=0; i<requisitions.size(); i++){
            quantityReceived.put(requisitions.get(i).getRequisitionID(), requisitions.get(i).getCustomItems()[0].getQuantityReceived());
        }
    }


    public HashMap<Integer, Integer> quantityReceived = new HashMap<>();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_requisition_update, parent, false);
        }

        final CustomRequisition requisition = getItem(position);

        if (requisition != null) {
            int qtyReceived = quantityReceived.get(requisition.getRequisitionID());
            TextView tvName = v.findViewById(R.id.employeeName);
            TextView tvDate = v.findViewById(R.id.requisitionDate);
            TextView tvQty = v.findViewById(R.id.quantityOrdered);
            final EditText etQtyReceived = v.findViewById(R.id.qtyReceived);
            final CheckBox cbQtyReceivedFull = v.findViewById(R.id.qtyReceivedFull);
            final CustomItem item = requisition.getCustomItems()[0];

            tvName.setText(requisition.getEmployeeName());
            tvDate.setText("Requisition Date:" + requisition.getRequisitionDate() );
            tvQty.setText("Quantity Requested: " + item.getQuantity());
            etQtyReceived.setFilters(new InputFilter[]{ new InputFilterMinMax("1", Integer.toString(item.getQuantity()))});

            if(qtyReceived == item.getQuantity()) cbQtyReceivedFull.setChecked(true);
            else if(qtyReceived > 0 && etQtyReceived.getText().toString().length() == 0) {
                etQtyReceived.setText(Integer.toString(qtyReceived));
            }

            if(cbQtyReceivedFull.isChecked()) {
                etQtyReceived.setEnabled(false);
            }
            else {
                etQtyReceived.setEnabled(true);

            }
            cbQtyReceivedFull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        etQtyReceived.setEnabled(false);
                        etQtyReceived.setFocusable(false);
                        quantityReceived.replace(requisition.getRequisitionID(), item.getQuantity());
                    }else {
                        etQtyReceived.setEnabled(true);
                        etQtyReceived.setFocusableInTouchMode(true);
                        if(etQtyReceived.getText().toString().length() == 0) quantityReceived.replace(requisition.getRequisitionID(), 0);
                        else quantityReceived.replace(requisition.getRequisitionID(), Integer.parseInt(etQtyReceived.getText().toString()));
                    }
                }
            });

            etQtyReceived.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.toString().length() > 0){
                        quantityReceived.replace(requisition.getRequisitionID(), Integer.parseInt(s.toString()));
                    }else{
                        quantityReceived.replace(requisition.getRequisitionID(), item.getQuantityReceived());
                    }
                }
            });
        }
        return v;
    }
}
