package com.vavisa.masafah.tap_my_shipment.shipment_details;

import com.vavisa.masafah.base.BaseView;
import com.vavisa.masafah.tap_my_shipment.my_shipments.model.ShipmentModel;

public interface ShipmentDetailsViews extends BaseView {

    void displayShipmentDetails(ShipmentModel body);
}
