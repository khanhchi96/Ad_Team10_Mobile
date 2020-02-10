package com.example.ad_team10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {
    Button btnDeptLogin;
    Button btnStoreLogin;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDeptLogin = findViewById(R.id.btnDeptLogin);
        btnStoreLogin = findViewById(R.id.btnStoreLogin);
        btnDeptLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity("Department");
            }
        });

        btnStoreLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity("Store");
            }
        });

        sharedPreferences = getSharedPreferences("user_credentials", MODE_PRIVATE);
        if(sharedPreferences.contains("userType") &&
                sharedPreferences.getString("userType", null) != null){
            startLoginActivity(sharedPreferences.getString("userType", null));
        }
    }

    protected void startLoginActivity(String userType){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("userType", userType);
        startActivity(intent);
    }
}
