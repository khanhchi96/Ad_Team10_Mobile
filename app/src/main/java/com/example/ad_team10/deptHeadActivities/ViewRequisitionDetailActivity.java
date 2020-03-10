//Author: Phung Khanh Chi

package com.example.ad_team10.deptHeadActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomItemAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomItem;
import com.example.ad_team10.models.CustomRequisition;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRequisitionDetailActivity extends AppCompatActivity {
    RestService restService;
    ListView listView;
    Button btnApprove;
    Button btnReject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_requisition);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        btnApprove = findViewById(R.id.approve);
        btnReject = findViewById(R.id.reject);
        Intent callerIntent = getIntent();
        final int requisitionId = callerIntent.getIntExtra("requisitionId", 0);
        loadRequisitionDetail(requisitionId);
        System.out.println(requisitionId);
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approve(requisitionId);
            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reject(requisitionId);
            }
        });
    }


    protected void loadRequisitionDetail(int requisitionId){
        Call<CustomRequisition> call =
                restService.getService().getRequisitionDetail(requisitionId);
        call.enqueue(new Callback<CustomRequisition>() {
            @Override
            public void onResponse(Call<CustomRequisition> call, Response<CustomRequisition> response) {
                CustomRequisition requisition = response.body();
                List<CustomItem> items = Arrays.asList(requisition.getCustomItems());
                if(items != null) {
                    CustomItemAdapter adapter = new CustomItemAdapter(ViewRequisitionDetailActivity.this,
                            R.layout.custom_item, items);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CustomRequisition> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }


    private void approve(int requisitionId){
        Call<Void> call = restService.getService().approveRequisition(requisitionId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Intent intent = new Intent();
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

    private void reject(int requisitionId){
        Call<Void> call = restService.getService().rejectRequisition(requisitionId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
