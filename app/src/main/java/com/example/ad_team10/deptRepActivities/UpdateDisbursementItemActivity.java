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
import com.example.ad_team10.adapters.CustomRequisitionUpdateAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomRequisition;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDisbursementItemActivity extends AppCompatActivity {
    RestService restService;
    ListView listView;
    Button btnUpdate;
    int departmentId;
    CustomRequisitionUpdateAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);

        Intent callerIntent = getIntent();
        departmentId = callerIntent.getIntExtra("departmentId", 0);
        final int itemId = callerIntent.getIntExtra("itemId", 0);
        loadRequisitionListByItem(departmentId, itemId);

    }


    protected void loadRequisitionListByItem(int departmentId, final int itemId){
        Call<List<CustomRequisition>> call =
                restService.getService().getDisbursementDetailByItem(departmentId, itemId);
        call.enqueue(new Callback<List<CustomRequisition>>() {
            @Override
            public void onResponse(Call<List<CustomRequisition>> call, Response<List<CustomRequisition>> response) {
                List<CustomRequisition> requisitions = response.body();
                System.out.println(requisitions.size());
                adapter = new CustomRequisitionUpdateAdapter(UpdateDisbursementItemActivity.this,
                        R.layout.custom_requisition_update, requisitions);
                listView.setAdapter(adapter);
                System.out.println(listView.getAdapter().getCount());
                btnUpdate = findViewById(R.id.update);
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        update(itemId);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<CustomRequisition>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }


    private void update(final int itemId) {
        HashMap<Integer, Integer> quantityDistributed = adapter.quantityReceived;
        CustomRequisition[] requisitions = new CustomRequisition[adapter.getCount()];

        for (int i = 0; i < adapter.getCount(); i++) {
            CustomRequisition requisition = adapter.getItem(i);
            int qtyDistributed = quantityDistributed.get(requisition.getRequisitionID());
            requisition.getCustomItems()[0].setQuantityReceived(qtyDistributed);
            requisitions[i] = requisition;
        }

        Call<Void> call = restService.getService().updateDisbursementDetailByItem(requisitions);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Intent intent = new Intent();
                intent.putExtra("itemId", itemId);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

}
