package com.webyousoon.android.memonimo;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class StartFragment extends Fragment {

    private final String LOG_TAG = StartFragment.class.getSimpleName();

    Button mBtnNewEasyGame;
    Button mBtnNewNormalGame;
    Button mBtnNewHardGame;
    Button mBtnNewCustomGame;
    Button mBtnGameUnfinished;
    Button mBtnOverviewSettings;

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_start_game, container, false);

        mBtnNewEasyGame = MemonimoUtilities.buildButtonWithFont(rootView, R.id.btn_new_easy_game, getActivity().getAssets());
        mBtnNewNormalGame = MemonimoUtilities.buildButtonWithFont(rootView, R.id.btn_new_normal_game, getActivity().getAssets());
        mBtnNewHardGame = MemonimoUtilities.buildButtonWithFont(rootView, R.id.btn_new_hard_game, getActivity().getAssets());
        mBtnNewCustomGame = MemonimoUtilities.buildButtonWithFont(rootView, R.id.btn_new_custom_game, getActivity().getAssets());
        mBtnGameUnfinished = MemonimoUtilities.buildButtonWithFont(rootView, R.id.btn_game_unfinished, getActivity().getAssets());

        // Traitement des cas smartphone et tablette (fragments différents)
        if (rootView.findViewById(R.id.btn_overview_settings) != null) {
            mBtnOverviewSettings = MemonimoUtilities.buildButtonWithFont(rootView, R.id.btn_overview_settings, getActivity().getAssets());
        }


        return rootView;
    }
}
