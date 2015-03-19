package com.example.crescenttechticketing;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuClient extends ListActivity {
    String classes[] = {"createtask", "Alltasks", "email"};

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        String counter = classes[position];
        super.onListItemClick(l, v, position, id);
        try {
            Class myClass = Class.forName("com.example.crescenttechticketing." + counter);
            Intent myIntent = new Intent(MenuClient.this, myClass);
            startActivity(myIntent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(MenuClient.this, android.R.layout.simple_list_item_1, classes));
    }


}
