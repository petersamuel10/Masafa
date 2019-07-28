package com.vavisa.masafah.tap_add.add_address;

import android.widget.ArrayAdapter;

import com.vavisa.masafah.base.BaseView;
import com.vavisa.masafah.helpers.OTP.CountryModel;

import java.util.ArrayList;

public interface AddAddressView extends BaseView {

    void addNewAddress(AddressModel address);
    void getAddressDetails(AddressModel address);
    void getEditAddress(AddressModel addressModel);
    void displayGovernorate(ArrayList<CountryModel> governorateList);
    void displayCities(ArrayList<CountryModel> citiesList);

}
