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

import java.util.HashMap;
import java.util.List;

public class CustomItemUpdateAdapter extends ArrayAdapter<CustomItem> {
    public CustomItemUpdateAdapter(Context context, int resource, List<CustomItem> items) {
        super(context, resource, items);
        for(int i=0; i<items.size(); i++){
            quantityReceived.put(items.get(i).getItemID(), items.get(i).getQuantityReceived());
        }
    }

    public HashMap<Integer, Integer> quantityReceived = new HashMap<>();
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_item_update, parent, false);
        }

        final CustomItem item = getItem(position);

        if (item != null) {
            int qtyReceived = quantityReceived.get(item.getItemID());
            TextView tvId = v.findViewById(R.id.id);
            TextView tvName = v.findViewById(R.id.name);
            TextView tvQtyOrdered = v.findViewById(R.id.qtyOrdered);
            TextView editType = v.findViewById(R.id.editType);

            editType.setText("Quantity received: ");
            final EditText etQtyReceived = v.findViewById(R.id.qtyReceived);
            CheckBox cbQtyReceivedFull = v.findViewById(R.id.qtyReceivedFull);
            etQtyReceived.setFilters(new InputFilter[]{ new InputFilterMinMax("1", Integer.toString(item.getQuantity()))});

            tvId.setText(Integer.toString(item.getItemID()));
            tvName.setText(item.getDescription());
            tvQtyOrdered.setText("Quantity ordered: " + item.getQuantity());
            if(qtyReceived == item.getQuantity()) cbQtyReceivedFull.setChecked(true);
            else if(qtyReceived > 0 && etQtyReceived.getText().toString().length() == 0) {
                etQtyReceived.setText(Integer.toString(qtyReceived));
            }

            if(cbQtyReceivedFull.isChecked()) etQtyReceived.setEnabled(false);
            else etQtyReceived.setEnabled(true);
            cbQtyReceivedFull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        etQtyReceived.setEnabled(false);
                        etQtyReceived.setFocusable(false);
                        quantityReceived.put(item.getItemID(), item.getQuantity());
                    }else {
                        etQtyReceived.setEnabled(true);
                        etQtyReceived.setFocusableInTouchMode(true);
                        if(etQtyReceived.getText().toString().length() == 0) quantityReceived.put(item.getItemID(), 0);
                        else quantityReceived.put(item.getItemID(), Integer.parseInt(etQtyReceived.getText().toString()));
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

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void afterTextChanged(Editable s) {
                    if(s.toString().length() > 0){
                        quantityReceived.put(item.getItemID(), Integer.parseInt(s.toString()));
                    }else{
                        quantityReceived.put(item.getItemID(), item.getQuantityReceived());
                    }
                }
            });
        }
        return v;
    }
}
