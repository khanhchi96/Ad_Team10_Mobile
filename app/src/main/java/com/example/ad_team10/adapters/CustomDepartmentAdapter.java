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
import com.example.ad_team10.models.CustomDepartment;

import java.util.List;

public class CustomDepartmentAdapter extends ArrayAdapter<CustomDepartment> {
    public int chosenId = -1;

    public CustomDepartmentAdapter(Context context, int resource, List<CustomDepartment> departments) {
        super(context, resource, departments);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_department, parent, false);
        }

        CustomDepartment department = getItem(position);

        if (department != null) {
            TextView tvName = v.findViewById(R.id.deptName);
            TextView tvCollectionPoint = v.findViewById(R.id.collectionPoint);
            TextView tvRepName = v.findViewById(R.id.repName);
            TextView tvRepPhone = v.findViewById(R.id.repPhone);
            TextView tvRepEmail = v.findViewById(R.id.repEmail);

            tvName.setText(department.getDepartmentName());
            tvCollectionPoint.setText(department.getCollectionPoint().getCollectionPointName());
            tvRepName.setText(department.getRepresentative().getDeptEmployeeName());
            tvRepPhone.setText(department.getRepresentative().getPhone());
            tvRepEmail.setText(department.getRepresentative().getEmail());
        }
        Button btnView = v.findViewById(R.id.btnView);
        Button btnUpdate = v.findViewById(R.id.btnUpdate);
        if(chosenId == position){
            btnView.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);

        }else {
            btnView.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
        }
        return v;
    }
}
