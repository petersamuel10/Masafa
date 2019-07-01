package com.vavisa.masafah.tap_add.add_address;

import com.vavisa.masafah.base.BaseView;

public interface AddAddressView extends BaseView {

    void addNewAddress(AddressModel address);

    void getAddressDetails(AddressModel address);

    void getEditAddress(AddressModel addressModel);
}
