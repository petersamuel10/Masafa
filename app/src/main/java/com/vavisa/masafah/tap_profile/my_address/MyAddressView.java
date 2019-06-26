package com.vavisa.masafah.tap_profile.my_address;

import com.vavisa.masafah.base.BaseView;
import com.vavisa.masafah.tap_add.add_address.AddressModel;

import java.util.ArrayList;

public interface MyAddressView extends BaseView {

    void address(ArrayList<AddressModel> addressList);
}
