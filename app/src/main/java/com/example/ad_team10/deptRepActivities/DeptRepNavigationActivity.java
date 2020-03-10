//Author: Phung Khanh Chi

package com.example.ad_team10.deptRepActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomMembershipUser;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeptRepNavigationActivity extends AppCompatActivity implements View.OnClickListener{
    TextView txtInfo;
    Button btnEmployeeReq;
    Button btnLogout;
    SharedPreferences sharedPreferences;
    CustomMembershipUser user;
    int departmentId;
    RestService restService;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_rep_navigation);

        txtInfo = findViewById(R.id.txtInfo);
        btnEmployeeReq = findViewById(R.id.btnDisbursement);
        btnLogout = findViewById(R.id.btnLogout);
        restService = new RestService();
        sharedPreferences = getSharedPreferences("user_credentials", MODE_PRIVATE);
        Intent callerIntent = getIntent();
        String jsonMember = callerIntent.getStringExtra("jsonMember");
        Gson gson = new Gson();
        user = gson.fromJson(jsonMember, CustomMembershipUser.class);

        txtInfo.setText("Hi " + user.toString());
        getDepartmentId(user.getUserID());
        btnLogout.setOnClickListener(this);
        btnEmployeeReq.setOnClickListener(this);
    }

    private void getDepartmentId(int deptUserId){
        Call<Integer> call = restService.getService().getDepartmentIdFromUserId(deptUserId);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                departmentId = response.body();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnDisbursement:
                intent = new Intent(DeptRepNavigationActivity.this,
                        ViewDisbursementForDeptActivity.class);
                intent.putExtra("departmentId", departmentId);
                startActivity(intent);
                break;

            case R.id.btnLogout:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.remove("password");
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
