//Author: Phung Khanh Chi

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
import com.example.ad_team10.models.CustomRetrievalList;
import com.example.ad_team10.models.CustomRetrievalListDetail;

import java.util.List;

public class CustomRetrievalAdapter extends ArrayAdapter<CustomRetrievalListDetail> {
    public boolean showButton = false;
    public int chosenId = -1;
    public CustomRetrievalAdapter(Context context, int resource, List<CustomRetrievalListDetail> details) {
        super(context, resource, details);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_item, parent, false);
        }

        CustomRetrievalListDetail detail = getItem(position);

        if (detail != null) {
            TextView tvName = v.findViewById(R.id.itemName);
            TextView tvQty = v.findViewById(R.id.quantityOrdered);
            TextView tvQtyReceived = v.findViewById(R.id.quantityReceived);

            tvName.setText(detail.getDepartmentName());
            tvQty.setText("Quantity Requested: " + detail.getQuantity());
            tvQtyReceived.setText("Quantity Offered: " + detail.getQuantityOffered());
        }
        return v;
    }
}
