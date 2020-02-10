package com.example.ad_team10.storeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.adapters.CustomPurchaseOrderAdapter;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomPurchaseOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPurchaseOrderActivity extends AppCompatActivity {
    private final int ON_UPDATE_RETURN = 1;
    private final int ON_VIEW_RETURN = 0;
    ListView listView;
    RestService restService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        loadOrder();
    }


    private void loadOrder(){
        final Call<List<CustomPurchaseOrder>> orders = restService.getService().getPurchaseOrder();
        orders.enqueue(new Callback<List<CustomPurchaseOrder>>() {
            @Override
            public void onResponse(Call<List<CustomPurchaseOrder>> call, Response<List<CustomPurchaseOrder>> response) {
                final List<CustomPurchaseOrder> purchaseOrders = response.body();
                final CustomPurchaseOrderAdapter adapter = new CustomPurchaseOrderAdapter(
                        ViewPurchaseOrderActivity.this, R.layout.custom_purchase_order, purchaseOrders);
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
                                goToDetailPage(purchaseOrders.get(position).getOrderID());
                            }
                        });

                        Button btnUpdate = view.findViewById(R.id.btnUpdate);
                        btnUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToUpdatePage(purchaseOrders.get(position).getOrderID());
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<List<CustomPurchaseOrder>> call, Throwable t) {
                System.out.println(t.getCause());
                System.out.println(t.getMessage());
            }
        });
    }

    private void goToDetailPage(int id){
        Intent intent = new Intent(ViewPurchaseOrderActivity.this, ViewOrderDetailActivity.class);
        intent.putExtra("orderId", id);
        startActivityForResult(intent, ON_VIEW_RETURN);
    }

    private void goToUpdatePage(int id){
        Intent intent = new Intent(ViewPurchaseOrderActivity.this, UpdateOrderDetailActivity.class);
        intent.putExtra("orderId", id);
        startActivityForResult(intent, ON_UPDATE_RETURN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ON_UPDATE_RETURN || requestCode == ON_VIEW_RETURN) {
            if (resultCode == RESULT_OK) {
                int orderId = intent.getIntExtra("orderId", 0);
                loadOrder();
                goToDetailPage(orderId);
            }
        }
    }
}
