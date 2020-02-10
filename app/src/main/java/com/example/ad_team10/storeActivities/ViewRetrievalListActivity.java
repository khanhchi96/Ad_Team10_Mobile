package com.example.ad_team10.storeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomItemAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomItem;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRetrievalListActivity extends AppCompatActivity {
    private final int ON_UPDATE_RETURN = 1;
    private final int ON_VIEW_RETURN = 0;
    ListView listView;
    RestService restService;
    TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        message = findViewById(R.id.message);
        loadRetrievalList();

    }

    private void loadRetrievalList(){
        final Call<List<CustomItem>> call = restService.getService().getRetrievalList();
        call.enqueue(new Callback<List<CustomItem>>() {
            @Override
            public void onResponse(Call<List<CustomItem>> call, Response<List<CustomItem>> response) {
                if(response.code() == 404 || response.body().size() == 0){
                    message.setText("No custom_requisition is pending or incomplete");
                }else{
                    final List<CustomItem> items = response.body();
                    final CustomItemAdapter adapter = new CustomItemAdapter(
                            ViewRetrievalListActivity.this, R.layout.custom_item, items);
                    adapter.isRetrieval = true;
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
                                    String jsonItem = gson.toJson(items.get(position));
                                    goToDetailPage(jsonItem);
                                }
                            });

                            Button btnUpdate = view.findViewById(R.id.btnUpdate);
                            btnUpdate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Gson gson = new Gson();
                                    String jsonItem = gson.toJson(items.get(position));
                                    goToUpdatePage(jsonItem);

                                }
                            });
                        }
                    });
                }
            }


            @Override
            public void onFailure(Call<List<CustomItem>> call, Throwable t) {
                System.out.println(t.getCause());
                System.out.println(t.getMessage());
            }
        });
    }

    private void goToDetailPage(String jsonItem){
        Intent intent = new Intent(ViewRetrievalListActivity.this, ViewRetrievalByItemActivity.class);
        intent.putExtra("item", jsonItem);
        startActivityForResult(intent, ON_VIEW_RETURN);
    }

    private void goToUpdatePage(String jsonItem){
        Intent intent = new Intent(ViewRetrievalListActivity.this, UpdateRetrievalListByItemActivity.class);
        intent.putExtra("item", jsonItem);
        startActivityForResult(intent, ON_UPDATE_RETURN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ON_UPDATE_RETURN) {
            if (resultCode == RESULT_OK) {
                String jsonItem = intent.getStringExtra("item");
                loadRetrievalList();
                goToDetailPage(jsonItem);
            }
        }
    }

}
