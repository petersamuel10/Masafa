package com.vavisa.masafah.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vavisa.masafah.R;

public class MapActivity extends BaseActivity
    implements OnMapReadyCallback, GoogleMap.OnMyLocationClickListener {

  private GoogleMap map;
  private static final String TAG = MapActivity.class.getName();
  private FusedLocationProviderClient fusedLocationProviderClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_pickup_location);

    Toolbar toolbar = findViewById(R.id.add_shipment_toolbar);
    setSupportActionBar(toolbar);
    TextView toolbarTitle = toolbar.findViewById(R.id.pickup_location_toolbar_title);
    toolbarTitle.setText("Pickup location");
    setTitle("");

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }

  /*private void getDeviceLocation() {
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
  }*/

  /* private void moveCamera(LatLng latLng, float zoom) {
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
  }*/

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
          MapActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

    } else {
      // Write you code here if permission already given.
      // getDeviceLocation();
      map.setMyLocationEnabled(true);

      // map.setOnMyLocationClickListener(this);
    }

    // Add a marker in Sydney, Australia, and move the camera.
    // LatLng sydney = new LatLng(-34, 151);
    // googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    // googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
  }

  @Override
  public void onMyLocationClick(@NonNull Location location) {
    map.addMarker(
        new MarkerOptions()
            .position(new LatLng(location.getLatitude(), location.getLongitude()))
            .title("My position"));
    /* CameraUpdate center =
        CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
    CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);

    map.moveCamera(center);
    map.animateCamera(zoom);*/
  }
}
