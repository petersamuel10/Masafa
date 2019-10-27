package com.vavisa.masafah.tap_add.invoice;

import com.vavisa.masafah.base.BaseView;

public interface InvoiceView extends BaseView {

    void handleAddShipment(String msg);
    void displayPrice(String price);
}
