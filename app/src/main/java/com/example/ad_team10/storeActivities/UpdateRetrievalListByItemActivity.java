//Author: Phung Khanh Chi

package com.example.ad_team10.storeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomRetrievalUpdateAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomItem;
import com.example.ad_team10.models.CustomRetrievalList;
import com.example.ad_team10.models.CustomRetrievalListDetail;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateRetrievalListByItemActivity extends AppCompatActivity {
    RestService restService;
    ListView listView;
    Button btnUpdate;
    Gson gson;
    CustomRetrievalUpdateAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        gson = new Gson();
        if(getItem() != null) {
            final CustomItem item = getItem();
            loadOrderDetail(item.getItemID());
            btnUpdate = findViewById(R.id.update);
            btnUpdate.setVisibility(View.VISIBLE);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(item.getItemID());
                }
            });
        }
    }

    protected CustomItem getItem(){
        if(jsonItem() != null) {
            return gson.fromJson(jsonItem(), CustomItem.class);
        }else return null;
    }

    protected String jsonItem(){
        Intent callerIntent = getIntent();
        return callerIntent.getStringExtra("item");
    }
    protected void loadOrderDetail(int itemId){
        Call<List<CustomRetrievalListDetail>> call =
                restService.getService().getRetrievalListByItem(itemId);
        call.enqueue(new Callback<List<CustomRetrievalListDetail>>() {
            @Override
            public void onResponse(Call<List<CustomRetrievalListDetail>> call, Response<List<CustomRetrievalListDetail>> response) {
                List<CustomRetrievalListDetail> details = response.body();
                adapter = new CustomRetrievalUpdateAdapter(UpdateRetrievalListByItemActivity.this,
                        R.layout.custom_item_update, details);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CustomRetrievalListDetail>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    private void update(int itemId){
        HashMap<Integer, Integer> quantityOffered = adapter.quantityOffered;
        CustomRetrievalListDetail[] details = new CustomRetrievalListDetail[adapter.getCount()];

        for (int i = 0; i < adapter.getCount(); i++) {
            CustomRetrievalListDetail detail = adapter.getItem(i);
            int qtyOffered = quantityOffered.get(detail.getDepartmentID());
            detail.setQuantityOffered(qtyOffered);
            details[i] = detail;
        }

        CustomRetrievalList customRetrievalList = new CustomRetrievalList();
        customRetrievalList.setItemID(itemId);
        customRetrievalList.setRetrievalListDetails(details);

        Call<Void> call = restService.getService().updateRetrievalListByItem(customRetrievalList);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Intent intent = new Intent();
                intent.putExtra("item", jsonItem());
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
