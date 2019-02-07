package com.example.sasha.project2proper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddItemsActivity extends AppCompatActivity {

    Button addItem;
    EditText itemName;
    EditText itemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        addItem = findViewById(R.id.addButton);
        itemName = findViewById(R.id.nameItemET);
        itemQuantity = findViewById(R.id.itemQuantityET);


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create the intent and add the item to it
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                String itemToAdd =itemName.getText().toString() + " " + itemQuantity.getText().toString();
                i.putExtra("itemToAdd", itemToAdd);

                // setresult to be sent to the main activity
                setResult(Activity.RESULT_OK,i);

                // calendar objects and time for Shared Preferences storage
                Calendar myCal = Calendar.getInstance();
                Date now = myCal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a, MM-dd-yyyy");
                String myDateString = sdf.format(now);

                // wrtitng to Shared Preferences
                SharedPreferences prefs = getSharedPreferences("dataLog_file", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                String sharedPrefsInput = itemToAdd +" logged at: "+  myDateString;
                editor.putString("sharedPrefsInput", sharedPrefsInput);
                editor.commit();

                // finishing the activity
                finish();

            }
        });
    }
}
