package com.example.ad_team10.storeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomItemUpdateAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomDepartment;
import com.example.ad_team10.models.CustomDisbursementList;
import com.example.ad_team10.models.CustomItem;
import com.example.ad_team10.models.CustomRequisition;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDisbursementDetailActivity extends AppCompatActivity {
    RestService restService;
    ListView listView;
    Button btnUpdate;
    Gson gson;
    CustomItemUpdateAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        gson = new Gson();
        Intent callerIntent = getIntent();
        final String jsonDepartment = callerIntent.getStringExtra("department");
        if(jsonDepartment != null) {
            final CustomDepartment department = getDepartment();
            loadDisbursementDetail(department.getDepartmentID());
            btnUpdate = findViewById(R.id.update);
            btnUpdate.setVisibility(View.VISIBLE);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(department.getDepartmentID());
                }
            });
        }
    }

    protected CustomDepartment getDepartment(){
        if(jsonDepartment() != null) {
            return gson.fromJson(jsonDepartment(), CustomDepartment.class);
        }else return null;
    }

    protected String jsonDepartment(){
        Intent callerIntent = getIntent();
        return callerIntent.getStringExtra("department");
    }

    protected void loadDisbursementDetail(int departmentId){
        Call<List<CustomItem>> call =
                restService.getService().getDisbursementList(departmentId);
        call.enqueue(new Callback<List<CustomItem>>() {
            @Override
            public void onResponse(Call<List<CustomItem>> call, Response<List<CustomItem>> response) {
                List<CustomItem> items = response.body();
//                List<CustomItem> pendingItems = new ArrayList<>();
//                for(int i=0; i<items.size(); i++){
//                    if(items.get(i).getQuantityReceived() < items.get(i).getQuantity()) pendingItems.add(items.get(i));
//                }
                adapter = new CustomItemUpdateAdapter(UpdateDisbursementDetailActivity.this,
                        R.layout.custom_item_update, items);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CustomItem>> call, Throwable t) {
                System.out.println(t.getCause());
                System.out.println(t.getMessage());
            }
        });
    }

    private void update(int departmentId) {
        HashMap<Integer, Integer> quantityReceived = adapter.quantityReceived;
        CustomItem[] items = new CustomItem[adapter.getCount()];

        for (int i = 0; i < adapter.getCount(); i++) {
            CustomItem item = adapter.getItem(i);
            int qtyReceived = quantityReceived.get(item.getItemID());
            item.setQuantityReceived(qtyReceived);
            items[i] = item;
        }

        CustomDisbursementList disbursementList = new CustomDisbursementList();
        disbursementList.setDepartmentID(departmentId);
        disbursementList.setCustomItems(items);

        Call<Void> call = restService.getService().updateDisbursementList(disbursementList);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Intent intent = new Intent();
                intent.putExtra("department", jsonDepartment());
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
