package com.example.ashwathan.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;


public class MainActivity extends Activity implements LocationListener{
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String add = " ";
    String provider;
    Button b;
    Button b2;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLat = (TextView) findViewById(R.id.textView);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        b=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean did = true;
                try {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int date = calendar.get(Calendar.DAY_OF_MONTH);
                    txtLat = (TextView) findViewById(R.id.textView);
                    txtLat.setText(add);
                    SaveLocation entry = new SaveLocation(MainActivity.this);
                    entry.open();
                    if(add.equals(" "))
                    {
                        Toast.makeText(getApplicationContext(), "Null value address", Toast.LENGTH_LONG).show();
                    }
                    else {
                        entry.createEntry(date, month, year, add);
                        Toast.makeText(getApplicationContext(), "succesfull", Toast.LENGTH_SHORT).show();
                    }
                    entry.close();
                }
                catch (Exception e)
                {
                    did = false;
                    Toast.makeText(getApplicationContext(), "Unsuccesfull", Toast.LENGTH_LONG).show();

                }

            }


        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(), MainActivity2Activity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onLocationChanged(Location location) {

        double lt = location.getLatitude();
        double lon = location.getLongitude();
        List<Address> addresses;
        Geocoder geo = new Geocoder(this, Locale.getDefault());
        try {

            addresses = geo.getFromLocation(lt,lon,1);
            add = addresses.get(0).getAddressLine(0);
            txtLat = (TextView) findViewById(R.id.textView);
            txtLat.setText(" " + add);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }
}
