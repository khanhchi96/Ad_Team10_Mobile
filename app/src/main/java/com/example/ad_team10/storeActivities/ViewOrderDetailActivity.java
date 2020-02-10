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
import com.example.ad_team10.models.CustomItem;
import com.example.ad_team10.models.CustomPurchaseOrder;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrderDetailActivity extends AppCompatActivity {
    final int ON_UPDATE_RETURN = 1;
    final int ON_VIEW_RETURN = 0;
    RestService restService;
    ListView listView;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        btnUpdate = findViewById(R.id.update);
        Intent callerIntent = getIntent();
        int orderId = callerIntent.getIntExtra("orderId", 0);
        loadOrderDetail(orderId);
    }

    protected void loadOrderDetail(final int orderId){
        Call<CustomPurchaseOrder> call =
                restService.getService().getPurchaseOrderDetail(orderId);
        call.enqueue(new Callback<CustomPurchaseOrder>() {
            @Override
            public void onResponse(Call<CustomPurchaseOrder> call, Response<CustomPurchaseOrder> response) {
                final CustomPurchaseOrder order = response.body();
                List<CustomItem> items = Arrays.asList(order.getCustomItems());
                CustomItemAdapter adapter = new CustomItemAdapter(ViewOrderDetailActivity.this,
                        R.layout.custom_item, items);
                listView.setAdapter(adapter);
                if(!order.getStatus().equals("Completed")){
                    btnUpdate.setVisibility(View.VISIBLE);
                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ViewOrderDetailActivity.this, UpdateOrderDetailActivity.class);
                            intent.putExtra("orderId", order.getOrderID());
                            startActivityForResult(intent, ON_UPDATE_RETURN);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CustomPurchaseOrder> call, Throwable t) {
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
                intent2.putExtra("orderId", intent.getIntExtra("orderId", 0));
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
