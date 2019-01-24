package com.vavisa.masafah.activities;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vavisa.masafah.R;
import com.vavisa.masafah.fragments.MyShipmentsFragment;

public class MainActivity extends AppCompatActivity {

  private BottomNavigationView navigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    navigationView = findViewById(R.id.bottom_navigation);

    navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    Fragment myShipments = new MyShipmentsFragment();
    switchFragment(myShipments);
  }

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

          Fragment fragment = null;

          switch (menuItem.getItemId()) {
            case R.id.navigation_shipments:
              // fragment = new HomeFragment();
              // switchFragment(fragment);
              return true;

            case R.id.navigation_profile:
              // fragment = new SellBookFragment();
              // switchFragment(fragment);
              return true;
          }

          return false;
        }
      };

  private void switchFragment(Fragment fragment) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.frame_layout, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }
}
