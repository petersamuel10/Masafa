package com.vavisa.masafah.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.AddShipmentActivity;
import com.vavisa.masafah.tap_my_shipment.MyShipmentsFragment;
import com.vavisa.masafah.tap_profile.ProfileFragment;

public class MainActivity extends BaseActivity {

  public static BottomNavigationView navigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    FloatingActionButton addShipment = findViewById(R.id.add_shipment);

    navigationView = findViewById(R.id.bottom_navigation);

    navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    addShipment.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, AddShipmentActivity.class));
          }
        });

    Fragment myShipments = new MyShipmentsFragment();
    switchFragment(myShipments, "myShipments");
  }

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

          Fragment fragment = null;

          int backStackEntryAt;

          switch (menuItem.getItemId()) {
            case R.id.navigation_my_shipments:
              backStackEntryAt = getFragmentBackStackEntryAt("myShipments");

              if (backStackEntryAt >= 0) {
                getSupportFragmentManager()
                    .popBackStack(backStackEntryAt + 1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
              } else {
                if (!"myShipments"
                    .equals(
                        getSupportFragmentManager()
                            .getBackStackEntryAt(
                                getSupportFragmentManager().getBackStackEntryCount() - 1)
                            .getName())) {

                  getSupportFragmentManager()
                      .popBackStack(2, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                  fragment = new MyShipmentsFragment();
                  switchFragment(fragment, "myShipments");
                }
              }

              return true;

            case R.id.navigation_add_shipment:
              startActivity(new Intent(MainActivity.this, AddShipmentActivity.class));
              break;

            case R.id.navigation_profile:
              backStackEntryAt = getFragmentBackStackEntryAt("profile");

              if (backStackEntryAt > 0) {
                getSupportFragmentManager()
                    .popBackStack(backStackEntryAt + 1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
              } else {
                if (!"profile"
                    .equals(
                        getSupportFragmentManager()
                            .getBackStackEntryAt(
                                getSupportFragmentManager().getBackStackEntryCount() - 1)
                            .getName())) {
                  fragment = new ProfileFragment();
                  switchFragment(fragment, "profile");
                }
              }

              return true;
          }

          return false;
        }
      };

  private void switchFragment(Fragment fragment, String fragmentName) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.frame_layout, fragment, fragmentName);
    fragmentTransaction.addToBackStack(fragmentName);
    fragmentTransaction.commit();
  }

  @Override
  public void onBackPressed() {

    if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
      getSupportFragmentManager().popBackStack();
    } else {
      finish();
    }

    // super.onBackPressed();
  }

  private int getFragmentBackStackEntryAt(String fragmentTagName) {
    for (int entry = 0; entry < getSupportFragmentManager().getBackStackEntryCount(); entry++) {
      if (fragmentTagName.equals(
          getSupportFragmentManager().getBackStackEntryAt(entry).getName())) {
        return entry;
      }
    }
    return -1;
  }
}
