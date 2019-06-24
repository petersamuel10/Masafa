package com.vavisa.masafah.tap_add.companies;

import com.vavisa.masafah.base.BaseView;

import java.util.ArrayList;

public interface CompaniesView extends BaseView {

    void companies(ArrayList<CompanyModel> companyList);
}
