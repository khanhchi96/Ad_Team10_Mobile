package com.example.ad_team10.storeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomItemUpdateAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomItem;
import com.example.ad_team10.models.CustomPurchaseOrder;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateOrderDetailActivity extends AppCompatActivity {
    RestService restService;
    ListView listView;
    Button btnUpdate;
    CustomItemUpdateAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        Intent callerIntent = getIntent();
        final int orderId = callerIntent.getIntExtra("orderId", 0);
            loadOrderDetail(orderId);
            btnUpdate = findViewById(R.id.update);
            btnUpdate.setVisibility(View.VISIBLE);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(orderId);
                }
            });

    }


    protected void loadOrderDetail(int orderId){
        Call<CustomPurchaseOrder> call =
                restService.getService().getPurchaseOrderDetail(orderId);
        call.enqueue(new Callback<CustomPurchaseOrder>() {
            @Override
            public void onResponse(Call<CustomPurchaseOrder> call, Response<CustomPurchaseOrder> response) {
                CustomPurchaseOrder order = response.body();
                List<CustomItem> items = Arrays.asList(order.getCustomItems());
                if(items != null) {
                    adapter = new CustomItemUpdateAdapter(UpdateOrderDetailActivity.this,
                            R.layout.custom_item_update, items);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CustomPurchaseOrder> call, Throwable t) {
                System.out.println(t.getCause());
                System.out.println(t.getMessage());
            }
        });
    }



    private void update(final int orderId){
        HashMap<Integer, Integer> quantityReceived = adapter.quantityReceived;
        CustomItem[] items = new CustomItem[adapter.getCount()];

        for (int i = 0; i < adapter.getCount(); i++) {
            CustomItem item = adapter.getItem(i);
            int qtyReceived = quantityReceived.get(item.getItemID());
            item.setQuantityReceived(qtyReceived);
            items[i] = item;
        }

        CustomPurchaseOrder purchaseOrder = new CustomPurchaseOrder();
        purchaseOrder.setOrderID(orderId);
        purchaseOrder.setCustomItems(items);

        Call<Void> call = restService.getService().updatePurchaseOrder(purchaseOrder);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Intent intent = new Intent();
                intent.putExtra("orderId", orderId);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
