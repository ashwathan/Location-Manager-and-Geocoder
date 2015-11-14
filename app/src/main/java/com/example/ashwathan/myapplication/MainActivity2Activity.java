package com.example.ashwathan.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


public class MainActivity2Activity extends Activity {
    DatePicker date1;
    Button b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        TextView tv = (TextView)findViewById(R.id.textView2);
        date1 = (DatePicker)findViewById(R.id.datePicker);
        b3 = (Button)findViewById(R.id.button3);
        final SaveLocation info = new SaveLocation(this);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = date1.getYear();
                int month = date1.getMonth();
                int day = date1.getDayOfMonth();
                info.open();
                String data = info.getData(day, month, year);
                TextView tv = (TextView)findViewById(R.id.textView2);
                tv.setText(data);
                info.close();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
