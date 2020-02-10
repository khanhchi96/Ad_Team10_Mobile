package com.example.ad_team10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ad_team10.deptHeadActivities.DeptHeadNavigationActivity;
import com.example.ad_team10.deptRepActivities.DeptRepNavigationActivity;
import com.example.ad_team10.models.DepartmentRole;
import com.example.ad_team10.storeActivities.StoreNavigationActivity;
import com.example.ad_team10.clients.*;
import com.example.ad_team10.models.CustomMembershipUser;
import com.example.ad_team10.viewModels.LoginView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity  {
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    Button btnBack;
    TextView error;
    SharedPreferences sharedPreferences;
    RestService restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        error = findViewById(R.id.error);
        restService = new RestService();
        sharedPreferences = getSharedPreferences("user_credentials", MODE_PRIVATE);
        if(sharedPreferences.contains("username") && sharedPreferences.contains("password")&& sharedPreferences.contains("userType")){
            String username = sharedPreferences.getString("username", null);
            String password = sharedPreferences.getString("password", null);
            String userType = sharedPreferences.getString("userType", null);
            if(username != null && password != null && userType != null) {
                authenticateUser(username, password, userType);
            }
        }

        Intent callerIntent = getIntent();
        final String userType = (String) callerIntent.getSerializableExtra("userType");
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                error.setText("");
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                authenticateUser(username, password, userType);

            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected  void writeToSharedPreferences(CustomMembershipUser membershipUser, String userType){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonMember = gson.toJson(membershipUser);
        editor.clear();
        editor.putString("username", membershipUser.getUsername());
        editor.putString("password", membershipUser.getPassword());
        editor.putString("userType", userType);
        editor.apply();
        if(userType.equals("Department")){
            if (membershipUser.getRole().equals(DepartmentRole.REPRESENTATIVE.toString()))
                startDeptRepNavigationActivity(jsonMember);
            if (membershipUser.getRole().equals(DepartmentRole.HEAD.toString()))
                startDeptHeadNavigationActivity(jsonMember);
        }
        if(userType.equals("Store")) startStoreNavigationActivity(jsonMember);
    }

    protected void startStoreNavigationActivity(String jsonMember){
        Intent intent = new Intent(LoginActivity.this, StoreNavigationActivity.class);
        intent.putExtra("jsonMember", jsonMember);
        startActivity(intent);
    }

    protected void startDeptRepNavigationActivity(String jsonMember){
        Intent intent = new Intent(LoginActivity.this, DeptRepNavigationActivity.class);
        intent.putExtra("jsonMember", jsonMember);
        startActivity(intent);
    }

    protected void startDeptHeadNavigationActivity(String jsonMember){
        Intent intent = new Intent(LoginActivity.this, DeptHeadNavigationActivity.class);
        intent.putExtra("jsonMember", jsonMember);
        startActivity(intent);
    }

    private void authenticateUser(final String username, final String password, final String userType) {
        final LoginView login = new LoginView(username, password, userType);
        Call<CustomMembershipUser> call1 = restService.getService().isValidUser(login);
        call1.enqueue(new Callback<CustomMembershipUser>() {
            @Override
            public void onResponse(Call<CustomMembershipUser> call, Response<CustomMembershipUser> response) {
                CustomMembershipUser user = response.body();
                if(response.code() == 200 && user != null){
                    writeToSharedPreferences(user, userType);
                }
                if(response.code() == 404){
                    error.setText("Invalid username or password");
                }
                if(response.code() == 401){
                    error.setText("You don't have the authority to access this application");
                }
            }

            @Override
            public void onFailure(Call<CustomMembershipUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }


}
