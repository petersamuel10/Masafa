package com.vavisa.masafah.tap_add.add_shipment;

import com.vavisa.masafah.base.BaseView;
import com.vavisa.masafah.tap_add.add_shipment.model.CategoryModel;

import java.util.ArrayList;

public interface AddShipmentView extends BaseView {

    void categories(ArrayList<CategoryModel> categoryList);
}
