package com.vavisa.masafah.tap_add.invoice;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressIDs {

    @SerializedName("address_from")
    private Integer addressFrom;
    @SerializedName("address_to")
    private List<Integer> addressTo;

    public AddressIDs(Integer addressFrom, List<Integer> addressTo) {
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
    }

    public Integer getAddressFrom() {
        return addressFrom;
    }
    public void setAddressFrom(Integer addressFrom) {
        this.addressFrom = addressFrom;
    }

    public List<Integer> getAddressTo() {
        return addressTo;
    }
    public void setAddressTo(List<Integer> addressTo) {
        this.addressTo = addressTo;
    }
}
