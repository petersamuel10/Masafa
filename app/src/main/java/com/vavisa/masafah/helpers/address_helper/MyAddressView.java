package com.vavisa.masafah.helpers.address_helper;

import com.vavisa.masafah.base.BaseView;
import com.vavisa.masafah.tap_add.add_address.AddressModel;

import java.util.ArrayList;

public interface MyAddressView extends BaseView {

    void address(ArrayList<AddressModel> addressList);

    void handleClickAction(AddressModel addressModel);

}
