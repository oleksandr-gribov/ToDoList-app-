package com.example.sasha.project2proper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable {
    ListView mylistView;
    Button addActivity;
    Button removeItem;
    Button dataLog;
    ArrayList<String> allItems = new ArrayList<>();
    ArrayList<String> sharedPrefsItems = new ArrayList<>();
    Boolean remove;

    ArrayAdapter<String> myAdapter;
    ArrayAdapter<String> dataLogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiliaze xml objects
        addActivity = findViewById(R.id.addButton);
        removeItem = findViewById(R.id.removeButton);
        dataLog = findViewById(R.id.dataLogButton);
        mylistView = findViewById(R.id.listView);


        // action for the "ADD" button
        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // start the second activity and expect a result back from it
                Intent i = new Intent(getApplicationContext(), AddItemsActivity.class);
                startActivityForResult(i,1);
            }
        });
        // action for the "REMOVE" button
        // sets the boolean value 'remove' tp true
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove = true;
            }
        });

        // action for the "DATALOG" button
        // when pressed, changes the listview adapter to display the items in shared preferences
        // storage
        dataLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylistView.setAdapter(dataLogAdapter);
            }
        });

        // adapter for the items in the shared preferences list
        dataLogAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sharedPrefsItems);


        // adapter for the all items list
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allItems);
        mylistView.setAdapter(myAdapter);

        // sets actions for clicked on items in the list
        mylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // only performed if the boolean value 'remove' is true
                if (remove){
                    // removes items both from allItems list and Shared Preferences
                    allItems.remove(position);
                    sharedPrefsItems.remove(position);
                    myAdapter.notifyDataSetChanged();
                    dataLogAdapter.notifyDataSetChanged();
                    remove = false;
                }
            }
        });

    }
    // receives the data back from second activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK){
                String receivedItem = data.getStringExtra("itemToAdd");
                allItems.add(receivedItem);
                myAdapter.notifyDataSetChanged();

                // writes the values from the shared preferences to the shared preferences array
                SharedPreferences prefs = getSharedPreferences("dataLog_file",
                        MODE_PRIVATE);
                String prefsString = prefs.getString("sharedPrefsInput",
                        "no value found");
                sharedPrefsItems.add(prefsString);

            }
        // check in case of error
        } else {
            System.out.println("Received error");
        }
    }
}






