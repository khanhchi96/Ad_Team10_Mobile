package com.example.ad_team10.storeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomDepartmentAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomDepartment;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDisbursementByDeptActivity extends AppCompatActivity {
    private final int ON_UPDATE_RETURN = 1;
    private final int ON_VIEW_RETURN = 0;
    RestService restService;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        loadDepartment();
    }

    private void loadDepartment(){
        final Call<List<CustomDepartment>> call = restService.getService().getDepartmentList();
        call.enqueue(new Callback<List<CustomDepartment>>() {
            @Override
            public void onResponse(Call<List<CustomDepartment>> call, Response<List<CustomDepartment>> response) {
                final List<CustomDepartment> departments = response.body();
                final CustomDepartmentAdapter adapter = new CustomDepartmentAdapter(
                        ViewDisbursementByDeptActivity.this, R.layout.custom_department, departments);
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
                                Gson gson = new Gson();
                                String jsonDepartment = gson.toJson(departments.get(position));
                                goToDetailPage(jsonDepartment);
                            }
                        });
                        Button btnUpdate = view.findViewById(R.id.btnUpdate);
                        btnUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Gson gson = new Gson();
                                String jsonDepartment = gson.toJson(departments.get(position));
                                goToUpdatePage(jsonDepartment);
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<List<CustomDepartment>> call, Throwable t) {
                System.out.println(t.getCause());
                System.out.println(t.getMessage());
            }
        });
    }


    private void goToDetailPage(String jsonDepartment){
        Intent intent = new Intent(ViewDisbursementByDeptActivity.this, ViewDisbursementDetailActivity.class);
        intent.putExtra("department", jsonDepartment);
        startActivityForResult(intent, ON_VIEW_RETURN);
    }

    private void goToUpdatePage(String jsonDepartment){
        Intent intent = new Intent(ViewDisbursementByDeptActivity.this, UpdateDisbursementDetailActivity.class);
        intent.putExtra("department", jsonDepartment);
        startActivityForResult(intent, ON_UPDATE_RETURN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ON_UPDATE_RETURN || requestCode == ON_VIEW_RETURN) {
            if (resultCode == RESULT_OK) {
                String jsonDepartment = intent.getStringExtra("department");
                loadDepartment();
                goToDetailPage(jsonDepartment);
            }
        }
    }
}
