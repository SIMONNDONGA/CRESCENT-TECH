package com.example.crescenttechticketing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntegratedClass extends Activity {
    Button admin, tech, client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intergrated_layout);

        admin = (Button) findViewById(R.id.adminbutton);
        tech = (Button) findViewById(R.id.techbutton);
        client = (Button) findViewById(R.id.clientbutton);

        admin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent admin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(admin);

            }
        });
        tech.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent tech = new Intent(getApplicationContext(), LoginTech.class);
                startActivity(tech);

            }
        });
        client.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent client = new Intent(getApplicationContext(), LoginClient.class);
                startActivity(client);
            }
        });
    }


}
