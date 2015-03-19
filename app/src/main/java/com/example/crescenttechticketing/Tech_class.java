package com.example.crescenttechticketing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tech_class extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tech_layout);
        Button createtask = (Button) findViewById(R.id.btn_createtasktech);
        Button alltasks = (Button) findViewById(R.id.btn_alltaskstech);
        Button email = (Button) findViewById(R.id.btn_emailtech);
        Button allclients = (Button) findViewById(R.id.btn_allclientstech);
        Button logout = (Button) findViewById(R.id.btn_logouttech);


        createtask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent create = new Intent(getApplicationContext(), createtask.class);
                startActivity(create);

            }
        });

        alltasks.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent tasks = new Intent(getApplicationContext(), Alltasks.class);
                startActivity(tasks);

            }
        });

        email.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent email = new Intent(getApplicationContext(), email.class);
                startActivity(email);

            }
        });

        allclients.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent clients = new Intent(getApplicationContext(), AllClientsTech.class);
                startActivity(clients);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                finish();

            }
        });
    }


}
