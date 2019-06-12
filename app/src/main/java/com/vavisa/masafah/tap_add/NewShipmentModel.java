package com.vavisa.masafah.tap_add;

public class NewShipmentModel {

    String shipment_category;
    int quantity;

    public NewShipmentModel(String shipment_category, int quantity) {
        this.shipment_category = shipment_category;
        this.quantity = quantity;
    }

    public String getShipment_category() {
        return shipment_category;
    }

    public void setShipment_category(String shipment_category) {
        this.shipment_category = shipment_category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
