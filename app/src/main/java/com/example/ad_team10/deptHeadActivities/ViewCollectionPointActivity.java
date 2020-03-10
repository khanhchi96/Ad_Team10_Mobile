//Author: Phung Khanh Chi

package com.example.ad_team10.deptHeadActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CollectionPoint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCollectionPointActivity extends AppCompatActivity {
    private int ON_CHANGE_RETURN = 1;
    ListView listView;
    RestService restService;
    TextView collectionPointName;
    Button btnChangeCollectionPoint;
    int departmentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_collection_point);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        collectionPointName = findViewById(R.id.collectionPointName);
        btnChangeCollectionPoint = findViewById(R.id.btnChangeCollectionPoint);
        Intent callerIntent = getIntent();
        departmentId = callerIntent.getIntExtra("departmentId", 0);
        getCollectionPoint(departmentId);
    }


    private void getCollectionPoint(int id){
        final Call<CollectionPoint> call = restService.getService().getCollectionPoint(id);
        call.enqueue(new Callback<CollectionPoint>() {
            @Override
            public void onResponse(Call<CollectionPoint> call, Response<CollectionPoint> response) {
                final CollectionPoint collectionPoint = response.body();
                if(collectionPoint != null) {
                       collectionPointName.setText(collectionPoint.getCollectionPointName());
                    }

                    btnChangeCollectionPoint.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ViewCollectionPointActivity.this, ChangeCollectionPointActivity.class);
                            intent.putExtra("departmentId", departmentId);
                            startActivityForResult(intent, ON_CHANGE_RETURN);
                        }
                    });
            }

            @Override
            public void onFailure(Call<CollectionPoint> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ON_CHANGE_RETURN) {
            if (resultCode == RESULT_OK) {
                getCollectionPoint(departmentId);
            }
        }
    }
}
