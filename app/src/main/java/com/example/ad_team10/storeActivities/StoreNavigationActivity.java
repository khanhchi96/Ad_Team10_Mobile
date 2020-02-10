package com.example.ad_team10.storeActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.models.CustomMembershipUser;
import com.google.gson.Gson;

public class StoreNavigationActivity extends AppCompatActivity implements View.OnClickListener{
    TextView txtInfo;
    Button btnPurchaseOrder;
    Button btnDisbursement;
    Button btnRetrieval;
    Button btnLogout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_navigation);

        txtInfo = findViewById(R.id.txtInfo);
        btnPurchaseOrder = findViewById(R.id.btnPurchaseOrder);
        btnDisbursement = findViewById(R.id.btnDisbursement);
        btnRetrieval = findViewById(R.id.btnRetrieval);
        btnLogout = findViewById(R.id.btnLogout);
        sharedPreferences = getSharedPreferences("user_credentials", MODE_PRIVATE);
        Intent callerIntent = getIntent();
        String jsonMember = callerIntent.getStringExtra("jsonMember");
        Gson gson = new Gson();
        CustomMembershipUser user = gson.fromJson(jsonMember, CustomMembershipUser.class);

        txtInfo.setText("Hi " + user.toString());

        btnLogout.setOnClickListener(this);
        btnPurchaseOrder.setOnClickListener(this);
        btnDisbursement.setOnClickListener(this);
        btnRetrieval.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnPurchaseOrder:
                intent = new Intent(StoreNavigationActivity.this,
                        ViewPurchaseOrderActivity.class);
                startActivity(intent);
                break;

            case R.id.btnDisbursement:
                intent = new Intent(StoreNavigationActivity.this,
                        ViewDisbursementByDeptActivity.class);
                startActivity(intent);
                break;

            case R.id.btnRetrieval:
                intent = new Intent(StoreNavigationActivity.this,
                        ViewRetrievalListActivity.class);
                startActivity(intent);
                break;

            case R.id.btnLogout:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("user");
                editor.remove("userType");
                editor.clear();
                editor.apply();
                finish();
                break;

            default:
                 intent = null;
                 break;

        }
    }
}
