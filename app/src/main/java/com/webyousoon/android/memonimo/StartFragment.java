package com.webyousoon.android.memonimo;

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

    private Button mBtnNewEasyGame;
    private Button mBtnNewNormalGame;
    private Button mBtnNewHardGame;
    private Button mBtnNewCustomGame;
    private Button mBtnGameUnfinished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_start_game, container, false);

        mBtnNewEasyGame = MemonimoUtilities.buildButtonWithFont(rootView, R.id.btn_new_easy_game, getActivity().getAssets());
        mBtnNewNormalGame = MemonimoUtilities.buildButtonWithFont(rootView, R.id.btn_new_normal_game, getActivity().getAssets());
        mBtnNewHardGame = MemonimoUtilities.buildButtonWithFont(rootView, R.id.btn_new_hard_game, getActivity().getAssets());
        mBtnNewCustomGame = MemonimoUtilities.buildButtonWithFont(rootView, R.id.btn_new_custom_game, getActivity().getAssets());

        // Traitement des cas smartphone et tablette (fragments différents)
        if (rootView.findViewById(R.id.btn_game_unfinished) != null) {
            mBtnGameUnfinished = MemonimoUtilities.buildButtonWithFont(rootView, R.id.btn_game_unfinished, getActivity().getAssets());
        }

        return rootView;
    }
}
