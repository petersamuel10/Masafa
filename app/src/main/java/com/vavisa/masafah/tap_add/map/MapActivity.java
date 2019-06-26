package com.vavisa.masafah.tap_add.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.add_address.AddAddressActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends BaseActivity
        implements OnMapReadyCallback, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Button selectButton;
    private String city, area, street, building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pickup_location);

        Toolbar toolbar = findViewById(R.id.add_shipment_toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.pickup_location_toolbar_title);
        toolbarTitle.setText("Pickup location");
        setTitle("");

        selectButton = findViewById(R.id.select_button);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        selectButton.setOnClickListener(
                v -> {
                    Intent intent = new Intent(MapActivity.this, AddAddressActivity.class);
                    intent.putExtra("city", city);
                    intent.putExtra("area", area);
                    intent.putExtra("street", street);
                    intent.putExtra("building", building);
                    startActivity(intent);
                    finish();
                });
    }

    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            final Task location = fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(
                    new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Location currentLocation = (Location) task.getResult();
                                moveCamera(
                                        new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15f);
                            } else {

                            }
                        }
                    });
        } catch (SecurityException e) {
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        if (ActivityCompat.checkSelfPermission(
                MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            // Write you code here if permission already given.
            getDeviceLocation();
            map.setMyLocationEnabled(true);
            map.setOnMyLocationClickListener(this);
            map.setOnMapClickListener(this);
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        map.addMarker(
                new MarkerOptions()
                        .position(new LatLng(location.getLatitude(), location.getLongitude()))
                        .title("My position"));
        CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);

        map.moveCamera(center);
        map.animateCamera(zoom);
    }

    @Override
    public void onMapClick(LatLng latLng) {

        map.clear();
        map.addMarker(
                new MarkerOptions()
                        .position(new LatLng(latLng.latitude, latLng.longitude))
                        .title("My position"));
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            city = addresses.get(0).getLocality();
            area = addresses.get(0).getSubLocality();
            street = addresses.get(0).getThoroughfare();
            building = addresses.get(0).getFeatureName();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
