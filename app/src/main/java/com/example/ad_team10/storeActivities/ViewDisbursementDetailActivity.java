package com.example.ad_team10.storeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomItemAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomDepartment;
import com.example.ad_team10.models.CustomItem;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDisbursementDetailActivity extends AppCompatActivity {
    private int ON_UPDATE_RETURN = 1;
    ListView listView;
    RestService restService;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        Intent callerIntent = getIntent();
        final String jsonDepartment = callerIntent.getStringExtra("department");
        if(jsonDepartment != null){
            parseGson(jsonDepartment);
            btnUpdate = findViewById(R.id.update);
            btnUpdate.setVisibility(View.VISIBLE);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ViewDisbursementDetailActivity.this,UpdateDisbursementDetailActivity.class);
                    intent.putExtra("department", jsonDepartment);
                    startActivityForResult(intent, ON_UPDATE_RETURN);
                }
            });
        }
    }

    protected void parseGson(String jsonDepartment){
        Gson gson = new Gson();
        final CustomDepartment department = gson.fromJson(jsonDepartment, CustomDepartment.class);
        loadDisbursementList(department.getDepartmentID());
    }
    private void loadDisbursementList(int departmentId){
        final Call<List<CustomItem>> call = restService.getService().getDisbursementList(departmentId);
        call.enqueue(new Callback<List<CustomItem>>() {
            @Override
            public void onResponse(Call<List<CustomItem>> call, Response<List<CustomItem>> response) {
                final List<CustomItem> items = response.body();
                final CustomItemAdapter adapter = new CustomItemAdapter(
                        ViewDisbursementDetailActivity.this, R.layout.custom_item, items);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CustomItem>> call, Throwable t) {
                System.out.println(t.getCause());
                System.out.println(t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ON_UPDATE_RETURN) {
            if (resultCode == RESULT_OK) {
                Intent intent2 = new Intent();
                intent2.putExtra("orderId", intent.getStringExtra("department"));
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
