//Author: Phung Khanh Chi

package com.example.ad_team10.deptRepActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomRequisitionAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomRequisition;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDisbursementItemActivity extends AppCompatActivity {
    final int ON_UPDATE_RETURN = 1;
    RestService restService;
    ListView listView;
    Button btnUpdate;
    int departmentId;
    int itemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        btnUpdate = findViewById(R.id.update);
        Intent callerIntent = getIntent();
        departmentId = callerIntent.getIntExtra("departmentId", 0);
        itemId = callerIntent.getIntExtra("itemId", 0);
        loadRequisitionListByItem(departmentId, itemId);
        btnUpdate.setVisibility(View.VISIBLE);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDisbursementItemActivity.this, UpdateDisbursementItemActivity.class);
                intent.putExtra("departmentId", departmentId);
                intent.putExtra("itemId", itemId);
                startActivityForResult(intent, ON_UPDATE_RETURN);
            }
        });
    }

    protected void loadRequisitionListByItem(int departmentId, final int itemId){
        Call<List<CustomRequisition>> call =
                restService.getService().getDisbursementDetailByItem(departmentId, itemId);
        call.enqueue(new Callback<List<CustomRequisition>>() {
            @Override
            public void onResponse(Call<List<CustomRequisition>> call, Response<List<CustomRequisition>> response) {
                List<CustomRequisition> requisitions = response.body();
                CustomRequisitionAdapter adapter = new CustomRequisitionAdapter(ViewDisbursementItemActivity.this,
                        R.layout.custom_requisition, requisitions);
                adapter.isByItem = true;
                listView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<CustomRequisition>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ON_UPDATE_RETURN) {
            if (resultCode == RESULT_OK) {
                loadRequisitionListByItem(departmentId, itemId);
                Intent intent2 = new Intent();
                intent2.putExtra("itemId", itemId);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
