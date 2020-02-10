package com.example.ad_team10.deptHeadActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomRequisitionAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomRequisition;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPendingRequisitionActivity extends AppCompatActivity {
    private int ON_ACTION_RETURN = 1;
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
        loadPendingRequisition(departmentId);
    }


    private void loadPendingRequisition(int id){
        final Call<List<CustomRequisition>> orders = restService.getService().getPendingRequisition(id);
        orders.enqueue(new Callback<List<CustomRequisition>>() {
            @Override
            public void onResponse(Call<List<CustomRequisition>> call, Response<List<CustomRequisition>> response) {
                final List<CustomRequisition> pendingList = response.body();

                    final CustomRequisitionAdapter adapter = new CustomRequisitionAdapter(
                            ViewPendingRequisitionActivity.this, R.layout.custom_requisition, pendingList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            System.out.println(pendingList.get(position).getRequisitionID());
                            goToDetailPage(pendingList.get(position).getRequisitionID());
                        }
                    });
                TextView message = findViewById(R.id.message);
                if(listView.getAdapter().getCount() == 0) {
                    message.setText("You have no pending requisition request");
                }
            }

            @Override
            public void onFailure(Call<List<CustomRequisition>> call, Throwable t) {
                System.out.println(t.getCause());
                System.out.println(t.getMessage());
            }
        });
    }

    private void goToDetailPage(int id){
        Intent intent = new Intent(ViewPendingRequisitionActivity.this, ViewRequisitionDetailActivity.class);
        intent.putExtra("requisitionId", id);
        startActivityForResult(intent, ON_ACTION_RETURN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ON_ACTION_RETURN) {
            if (resultCode == RESULT_OK) {
                loadPendingRequisition(departmentId);
            }
        }
    }
}
