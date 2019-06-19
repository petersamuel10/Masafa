package com.vavisa.masafah.tap_profile.TermsAndCondition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseFragment;

public class TermsAndConditions extends BaseFragment implements TermsView {

    private TextView terms_txt;
    private TermsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);

        terms_txt = view.findViewById(R.id.terms_txt);

        presenter = new TermsPresenter();
        presenter.attachView(this);

        presenter.getTerms();

        return view;
    }

    @Override
    public void Terms(String term_str) {

        terms_txt.setText(term_str);
    }
}
