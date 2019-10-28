package com.vavisa.masafah.tap_my_shipment.my_shipments;

import com.vavisa.masafah.base.BaseView;
import com.vavisa.masafah.tap_my_shipment.my_shipments.model.ShipmentModel;

import java.util.ArrayList;
import java.util.HashMap;

public interface MyShipmentsView extends BaseView {

    void displayShipments(HashMap<String,ArrayList<ShipmentModel>> data);
    void deleteShipmentRes(int position);
}
