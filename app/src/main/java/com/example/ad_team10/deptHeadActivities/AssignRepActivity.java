//Author: Phung Khanh Chi

package com.example.ad_team10.deptHeadActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomEmployeeAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomDeptEmployee;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignRepActivity extends AppCompatActivity {
    RestService restService;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        Intent callerIntent = getIntent();
        final int departmentIdId = callerIntent.getIntExtra("departmentId", 0);
        loadEmployeeList(departmentIdId);
    }


    protected void loadEmployeeList(int departmentId){
        Call<List<CustomDeptEmployee>> call =
                restService.getService().getEmployeeList(departmentId);
        call.enqueue(new Callback<List<CustomDeptEmployee>>() {
            @Override
            public void onResponse(Call<List<CustomDeptEmployee>> call, Response<List<CustomDeptEmployee>> response) {
                final List<CustomDeptEmployee> employees = response.body();
                    final CustomEmployeeAdapter adapter = new CustomEmployeeAdapter(AssignRepActivity.this,
                            R.layout.custom_employee, employees);
                    listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        adapter.chosenId = position;
                        listView.invalidateViews();
                        Button btnAssign = view.findViewById(R.id.btnAssign);
                        btnAssign.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assignRep(employees.get(position));
                            }
                        });

                    }
                });
            }

            @Override
            public void onFailure(Call<List<CustomDeptEmployee>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }


    private void assignRep(CustomDeptEmployee employee){
        Call<Void> call = restService.getService().assignRepresentative(employee);
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
}
