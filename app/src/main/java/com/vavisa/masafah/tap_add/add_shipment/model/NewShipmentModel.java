package com.vavisa.masafah.tap_add.add_shipment.model;

import com.google.gson.annotations.SerializedName;

public class NewShipmentModel {

    @SerializedName("category_id")
    private Integer category_id;
    @SerializedName("quantity")
    private Integer quantity;

    private String cat_name;

    public NewShipmentModel(String cat_name,Integer category_id, Integer quantity) {
        this.cat_name = cat_name;
        this.category_id = category_id;
        this.quantity = quantity;
    }


    public String getCat_name() {
        return cat_name;
    }
    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
