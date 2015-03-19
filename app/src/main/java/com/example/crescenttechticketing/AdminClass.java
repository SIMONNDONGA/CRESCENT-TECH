package com.example.crescenttechticketing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminClass extends Activity {
    UserFunctions userFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        Button btn_addclient = (Button) findViewById(R.id.btn_addclient);
        Button btn_addusers = (Button) findViewById(R.id.btn_adduser);
        Button btn_allclients = (Button) findViewById(R.id.btn_allclients);
        Button btn_alltasks = (Button) findViewById(R.id.btn_alltasks);
        Button btn_email = (Button) findViewById(R.id.btn_email);
        Button btn_logout = (Button) findViewById(R.id.btn_logout);
        Button btn_piechart = (Button) findViewById(R.id.btn_piechart);
        Button btn_taskcreate = (Button) findViewById(R.id.btn_createtask);


        btn_addclient.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent x = new Intent(getApplicationContext(), AddClients.class);
                startActivity(x);

            }
        });
        btn_addusers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent y = new Intent(getApplicationContext(), AddSystemUser.class);
                startActivity(y);

            }
        });
        btn_allclients.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent l = new Intent(getApplicationContext(), AllClients.class);
                startActivity(l);
            }
        });
        btn_alltasks.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent p = new Intent(getApplicationContext(), Alltasks.class);
                startActivity(p);
            }
        });
        btn_email.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent q = new Intent(getApplicationContext(), email.class);
                startActivity(q);

            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                finish();
            }
        });
        btn_piechart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent p = new Intent(getApplicationContext(), Graph.class);
                startActivity(p);
            }
        });
        btn_taskcreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent w = new Intent(getApplicationContext(), createtask.class);
                startActivity(w);
            }
        });
    }


}
