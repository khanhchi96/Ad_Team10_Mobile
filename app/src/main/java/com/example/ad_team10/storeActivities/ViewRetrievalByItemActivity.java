package com.example.ad_team10.storeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomRetrievalAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomItem;
import com.example.ad_team10.models.CustomRetrievalListDetail;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRetrievalByItemActivity extends AppCompatActivity {
    final int ON_UPDATE_RETURN = 1;
    RestService restService;
    ListView listView;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        Intent callerIntent = getIntent();
        final String jsonItem = callerIntent.getStringExtra("item");
        if(jsonItem != null){
            parseGson(jsonItem);
            btnUpdate = findViewById(R.id.update);
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ViewRetrievalByItemActivity.this, UpdateRetrievalListByItemActivity.class);
                        intent.putExtra("item", jsonItem);
                        startActivityForResult(intent, ON_UPDATE_RETURN);
                    }
                });

        }
    }

    protected void parseGson(String jsonItem){
        Gson gson = new Gson();
        final CustomItem item = gson.fromJson(jsonItem, CustomItem.class);
        loadRetrievalDetail(item.getItemID());
    }
    protected void loadRetrievalDetail(int itemId){
        System.out.println(itemId);
        Call<List<CustomRetrievalListDetail>> call =
                restService.getService().getRetrievalListByItem(itemId);
        call.enqueue(new Callback<List<CustomRetrievalListDetail>>() {
            @Override
            public void onResponse(Call<List<CustomRetrievalListDetail>> call, Response<List<CustomRetrievalListDetail>> response) {
                List<CustomRetrievalListDetail> details = response.body();
                CustomRetrievalAdapter adapter = new CustomRetrievalAdapter(ViewRetrievalByItemActivity.this,
                        R.layout.custom_item, details);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CustomRetrievalListDetail>> call, Throwable t) {
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
                intent2.putExtra("item", intent.getStringExtra("item"));
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
