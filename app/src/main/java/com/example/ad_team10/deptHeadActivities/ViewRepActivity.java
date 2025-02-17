//Author: Phung Khanh Chi

package com.example.ad_team10.deptHeadActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ad_team10.R;
import com.example.ad_team10.clients.RestService;
import com.example.ad_team10.models.CustomDeptEmployee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRepActivity extends AppCompatActivity {
    private int ON_ASSIGN_RETURN = 1;
    ListView listView;
    RestService restService;
    TextView repName;
    TextView repEmail;
    TextView repPhone;
    Button btnAssignRep;
    int departmentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigning_rep);
        restService = new RestService();
        listView = findViewById(R.id.listView);
        repName = findViewById(R.id.repName);
        repEmail = findViewById(R.id.repEmail);
        repPhone = findViewById(R.id.repPhone);
        btnAssignRep = findViewById(R.id.btnAssignRep);
        Intent callerIntent = getIntent();
        departmentId = callerIntent.getIntExtra("departmentId", 0);
        loadCurrentRep(departmentId);
    }


    private void loadCurrentRep(int id){
        final Call<CustomDeptEmployee> orders = restService.getService().getCurrentRep(id);
        orders.enqueue(new Callback<CustomDeptEmployee>() {
            @Override
            public void onResponse(Call<CustomDeptEmployee> call, Response<CustomDeptEmployee> response) {
                final CustomDeptEmployee rep = response.body();
                if(rep != null) {
                    if (rep.getDeptEmployeeName() == null && rep.getEmail() == null && rep.getPhone() == null) {
                        repName.setText("Your department currently has no representative");
                    }else {
                        repName.setText(rep.getDeptEmployeeName());
                        repEmail.setText("Email: " + rep.getEmail());
                        repPhone.setText("Mobile" + rep.getPhone());
                    }

                    btnAssignRep.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ViewRepActivity.this, AssignRepActivity.class);
                            intent.putExtra("departmentId", departmentId);
                            startActivityForResult(intent, ON_ASSIGN_RETURN);
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<CustomDeptEmployee> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ON_ASSIGN_RETURN) {
            if (resultCode == RESULT_OK) {
                loadCurrentRep(departmentId);
            }
        }
    }
}
