package com.example.ad_team10.deptRepActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomItemAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDisbursementForDeptActivity extends AppCompatActivity {
    private int ON_UPDATE_RETURN = 1;
    private int ON_VIEW_RETURN = 0;
    ListView listView;
    RestService restService;
    int departmentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        Intent callerIntent = getIntent();
        departmentId = callerIntent.getIntExtra("departmentId", 0);
        loadDisbursementList(departmentId);
    }

    private void loadDisbursementList(int departmentId){
        final Call<List<CustomItem>> call = restService.getService().getDisbursementList(departmentId);
        call.enqueue(new Callback<List<CustomItem>>() {
            @Override
            public void onResponse(Call<List<CustomItem>> call, Response<List<CustomItem>> response) {
                final List<CustomItem> items = response.body();
                final CustomItemAdapter adapter = new CustomItemAdapter(
                        ViewDisbursementForDeptActivity.this, R.layout.custom_item, items);
                adapter.isDisbursementDept = true;
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        adapter.chosenId = position;
                        listView.invalidateViews();
                        Button btnView = view.findViewById(R.id.btnView);
                        btnView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToDetailPage(items.get(position).getItemID());
                            }
                        });

                        Button btnUpdate = view.findViewById(R.id.btnUpdate);
                        btnUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToUpdatePage(items.get(position).getItemID());
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<List<CustomItem>> call, Throwable t) {
                System.out.println(t.getCause());
                System.out.println(t.getMessage());
            }
        });
    }

    private void goToDetailPage(int itemId){
        Intent intent = new Intent(ViewDisbursementForDeptActivity.this, ViewDisbursementItemActivity.class);
        intent.putExtra("itemId", itemId);
        intent.putExtra("departmentId", departmentId);
        startActivityForResult(intent, ON_VIEW_RETURN);
    }

    private void goToUpdatePage(int itemId){
        Intent intent = new Intent(ViewDisbursementForDeptActivity.this, UpdateDisbursementItemActivity.class);
        intent.putExtra("itemId", itemId);
        intent.putExtra("departmentId", departmentId);
        startActivityForResult(intent, ON_UPDATE_RETURN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ON_UPDATE_RETURN) {
            if (resultCode == RESULT_OK) {
                int itemId = intent.getIntExtra("itemId", 0);
                loadDisbursementList(departmentId);
                goToDetailPage(itemId);
            }
        }
    }
}
