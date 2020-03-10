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
import com.example.ad_team10.models.CollectionPoint;

import java.util.List;

public class CustomCollectionPointAdapter extends ArrayAdapter<CollectionPoint> {
    public int chosenId = -1;
    public CustomCollectionPointAdapter(Context context, int resource, List<CollectionPoint> collectionPoints) {
        super(context, resource, collectionPoints);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.collection_point, parent, false);
        }

        CollectionPoint collectionPoint = getItem(position);

        if (collectionPoint != null) {
            TextView tvName = v.findViewById(R.id.collectionPointName);

            tvName.setText(collectionPoint.getCollectionPointName());
            Button btnChoose = v.findViewById(R.id.btnChoose);

            if(chosenId == position){
                btnChoose.setVisibility(View.VISIBLE);
            }else {
                btnChoose.setVisibility(View.GONE);
            }

        }
        return v;
    }
}
