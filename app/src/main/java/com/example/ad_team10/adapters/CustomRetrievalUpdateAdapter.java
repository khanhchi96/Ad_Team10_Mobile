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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.ad_team10.R;
import com.example.ad_team10.filter.InputFilterMinMax;
import com.example.ad_team10.models.CustomItem;
import com.example.ad_team10.models.CustomRetrievalList;
import com.example.ad_team10.models.CustomRetrievalListDetail;

import java.util.HashMap;
import java.util.List;

public class CustomRetrievalUpdateAdapter extends ArrayAdapter<CustomRetrievalListDetail> {

    public CustomRetrievalUpdateAdapter(Context context, int resource, List<CustomRetrievalListDetail> details) {
        super(context, resource, details);
        for(int i=0; i<details.size(); i++){
            quantityOffered.put(details.get(i).getDepartmentID(), details.get(i).getQuantityOffered());
        }
    }

    public HashMap<Integer, Integer> quantityOffered = new HashMap<>();
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_item_update, parent, false);
        }

        final CustomRetrievalListDetail detail = getItem(position);

        if (detail != null) {
            int qtyOffered = quantityOffered.get(detail.getDepartmentID());
            TextView tvId = v.findViewById(R.id.id);
            TextView tvName = v.findViewById(R.id.name);
            TextView tvQtyOrdered = v.findViewById(R.id.qtyOrdered);
            final EditText etQtyOffered = v.findViewById(R.id.qtyReceived);
            CheckBox cbQtyOfferedFull = v.findViewById(R.id.qtyReceivedFull);

            tvId.setText(Integer.toString(detail.getDepartmentID()));
            tvName.setText(detail.getDepartmentName());
            tvQtyOrdered.setText(Integer.toString(detail.getQuantity()));
            etQtyOffered.setFilters(new InputFilter[]{ new InputFilterMinMax("1", Integer.toString(detail.getQuantity()))});

            if(qtyOffered == detail.getQuantity()) cbQtyOfferedFull.setChecked(true);
            else if(qtyOffered > 0 && etQtyOffered.getText().toString().length() == 0) {
                etQtyOffered.setText(Integer.toString(qtyOffered));
            }


            if(cbQtyOfferedFull.isChecked()) etQtyOffered.setEnabled(false);
            else etQtyOffered.setEnabled(true);
            cbQtyOfferedFull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        etQtyOffered.setEnabled(false);
                        etQtyOffered.setFocusable(false);
                        quantityOffered.replace(detail.getDepartmentID(), detail.getQuantity());
                    }else {
                        etQtyOffered.setEnabled(true);
                        etQtyOffered.setFocusableInTouchMode(true);
                        if(etQtyOffered.getText().toString().length() == 0) quantityOffered.replace(detail.getDepartmentID(), 0);
                        else quantityOffered.replace(detail.getDepartmentID(), Integer.parseInt(etQtyOffered.getText().toString()));

                    }
                }
            });

            etQtyOffered.addTextChangedListener(new TextWatcher() {
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
                        quantityOffered.replace(detail.getDepartmentID(), Integer.parseInt(s.toString()));
                    }else{
                        quantityOffered.replace(detail.getDepartmentID(), detail.getQuantityOffered());
                    }
                }
            });
        }
        return v;
    }
}
