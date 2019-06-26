package com.vavisa.masafah.base;


import retrofit2.Response;

public interface BaseView {

    void showErrorConnection();

    void hideErrorConnection();

    void showMissingData(Response response);

    void hideMissingData();

    void showProgress();

    void hideProgress();

    void showMessage(String msg);
}
