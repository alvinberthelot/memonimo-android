package com.webyousoon.android.memonimo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class OverviewFragment extends Fragment {

    private final String LOG_TAG = OverviewFragment.class.getSimpleName();

    Button mBtnOverviewSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);

        mBtnOverviewSettings = MemonimoUtilities.buildButtonWithFont(rootView, R.id.btn_overview_settings, getActivity().getAssets());

        return rootView;
    }
}
