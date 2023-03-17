package com.example.onlineshopping.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.onlineshopping.R;

import java.util.List;
import java.util.Locale;

public class Get_location extends AppCompatActivity implements LocationListener {
    Button button, btm;
    TextView address;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        button = findViewById(R.id.getadd);
        address = findViewById(R.id.add);
        btm = findViewById(R.id.tes);

        if (ActivityCompat.checkSelfPermission(Get_location.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Get_location.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        btm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_location();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Get_location.this , Geolocation.class);
                startActivity(intent);

            }
        });
    }

    private void get_location() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 5, Get_location.this);

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(Get_location.this , Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude() , location.getLongitude() , 1);
            String adress = addresses.get(0).getAddressLine(0);
            address.setText(adress);
      }catch (Exception e){
            e.printStackTrace();
        }
    }
}
