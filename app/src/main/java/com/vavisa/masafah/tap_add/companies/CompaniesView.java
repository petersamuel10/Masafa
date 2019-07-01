package com.vavisa.masafah.tap_add.companies;

import com.vavisa.masafah.base.BaseView;
import com.vavisa.masafah.tap_my_shipment.company_details.CompanyModel;

import java.util.ArrayList;

public interface CompaniesView extends BaseView {

    void companies(ArrayList<CompanyModel> companyList);
}
