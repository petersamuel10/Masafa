package com.vavisa.masafah.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vavisa.masafah.R;
import com.vavisa.masafah.util.dialogs.ConnectionMessage;
import com.vavisa.masafah.util.dialogs.FailedMessage;
import com.vavisa.masafah.util.dialogs.ProgressDialog;

import retrofit2.Response;

public class BaseFragment extends Fragment implements BaseView {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base, container, false);
        return v;
    }

    public void switchFragment(
            FragmentManager fragmentManager, Fragment fragment, String fragmentName) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, fragmentName);
        fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }

    public int getFragmentBackStackEntryAt(
            FragmentManager fragmentManager, String fragmentTagName) {
        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            if (fragmentTagName.equals(fragmentManager.getBackStackEntryAt(entry).getName())) {
                return entry;
            }
        }
        return -1;
    }

    @Override
    public void showErrorConnection() {
        ConnectionMessage.getInstance().show(getActivity());
    }

    @Override
    public void hideErrorConnection() {
        ConnectionMessage.getInstance().dismiss();
    }

    @Override
    public void showMissingData(Response response) {
        FailedMessage.getInstance().show(getActivity(), response);

    }

    @Override
    public void hideMissingData() {
        FailedMessage.getInstance().dismiss();
    }

    @Override
    public void showProgress() {
        ProgressDialog.getInstance().show(getActivity());
    }

    @Override
    public void hideProgress() {
        ProgressDialog.getInstance().dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

    }

}