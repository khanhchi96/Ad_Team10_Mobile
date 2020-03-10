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
import com.example.ad_team10.adapters.CustomCollectionPointAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CollectionPoint;
import com.example.ad_team10.models.CustomDepartment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeCollectionPointActivity extends AppCompatActivity {
    RestService restService;
    ListView listView;
    int departmentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        Intent callerIntent = getIntent();
        departmentId = callerIntent.getIntExtra("departmentId", 0);
        loadCollectionPointList(departmentId);
    }


    protected void loadCollectionPointList(int departmentId){
        Call<List<CollectionPoint>> call =
                restService.getService().getCollectionPointList(departmentId);
        call.enqueue(new Callback<List<CollectionPoint>>() {
            @Override
            public void onResponse(Call<List<CollectionPoint>> call, Response<List<CollectionPoint>> response) {
                final List<CollectionPoint> collectionPoints = response.body();
                final CustomCollectionPointAdapter adapter = new CustomCollectionPointAdapter(ChangeCollectionPointActivity.this,
                        R.layout.collection_point, collectionPoints);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        adapter.chosenId = position;
                        listView.invalidateViews();
                        Button btnChoose = view.findViewById(R.id.btnChoose);
                        btnChoose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                changeCollectionPoint(collectionPoints.get(position));
                            }
                        });

                    }
                });
            }

            @Override
            public void onFailure(Call<List<CollectionPoint>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }


    private void changeCollectionPoint(CollectionPoint collectionPoint){
        CustomDepartment customDepartment = new CustomDepartment();
        customDepartment.setDepartmentID(departmentId);
        customDepartment.setCollectionPoint(collectionPoint);
        Call<Void> call = restService.getService().changeCollectionPoint(customDepartment);
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
