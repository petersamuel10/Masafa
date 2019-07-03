package com.vavisa.masafah.tap_profile.shipment_history;

import com.vavisa.masafah.base.BaseView;
import com.vavisa.masafah.tap_my_shipment.my_shipments.ShipmentModel;

import java.util.ArrayList;

public interface ShipmentHistoryView extends BaseView {

    void displayShipment(ArrayList<ShipmentModel> shipmentList);
}
