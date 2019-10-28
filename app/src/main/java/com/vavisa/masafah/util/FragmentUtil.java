package com.vavisa.masafah.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vavisa.masafah.R;

public class FragmentUtil {

    public static void switchFragment(
            FragmentManager fragmentManager, Fragment fragment, String fragmentName) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, fragmentName);
        fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }

    public static int getFragmentBackStackEntryAt(
            FragmentManager fragmentManager, String fragmentTagName) {
        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            if (fragmentTagName.equals(fragmentManager.getBackStackEntryAt(entry).getName())) {
                return entry;
            }
        }
        return -1;
    }
}
