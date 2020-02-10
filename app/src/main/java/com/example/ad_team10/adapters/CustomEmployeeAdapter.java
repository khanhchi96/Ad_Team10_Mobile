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
import com.example.ad_team10.models.CustomDeptEmployee;
import com.example.ad_team10.models.CustomItem;

import java.util.List;

public class CustomEmployeeAdapter extends ArrayAdapter<CustomDeptEmployee> {
    public int chosenId = -1;
    public CustomEmployeeAdapter(Context context, int resource, List<CustomDeptEmployee> employees) {
        super(context, resource, employees);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_employee, parent, false);
        }

        CustomDeptEmployee employee = getItem(position);

        if (employee != null) {
            TextView tvName = v.findViewById(R.id.name);
            TextView tvDesignation = v.findViewById(R.id.designation);
            TextView tvEmail = v.findViewById(R.id.email);
            TextView tvPhone = v.findViewById(R.id.phone);

            tvName.setText(employee.getDeptEmployeeName());
            tvDesignation.setText(employee.getDesignation());
            tvEmail.setText(employee.getEmail());
            tvPhone.setText(employee.getPhone());

            Button btnAssign = v.findViewById(R.id.btnAssign);
                if(chosenId == position){
                    btnAssign.setVisibility(View.VISIBLE);
                }else {
                    btnAssign.setVisibility(View.GONE);
                }

        }
        return v;
    }
}
