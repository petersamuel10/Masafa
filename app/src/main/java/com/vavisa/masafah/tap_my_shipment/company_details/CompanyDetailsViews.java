package com.vavisa.masafah.tap_my_shipment.company_details;

import com.vavisa.masafah.base.BaseView;
import com.vavisa.masafah.tap_my_shipment.my_shipments.ShipmentModel;

public interface CompanyDetailsViews extends BaseView {

    void displayCompanyDetails(CompanyModel companyModel);

    void rationResponse();
}
