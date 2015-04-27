package com.webyousoon.android.memonimo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.webyousoon.android.memonimo.adapters.GridGameAdapter;
import com.webyousoon.android.memonimo.data.MemonimoProvider;
import com.webyousoon.android.memonimo.model.Game;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class StartFragment extends Fragment {

    private final String LOG_TAG = StartFragment.class.getSimpleName();

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_start_game, container, false);
        return rootView;
    }

//    /*
//        Pour arriver sur la vue d'une nouvelle partie
//     */
//    public void startGame(View _view, int _numFamily) {
//        Intent intent = new Intent(getActivity(), GameActivity.class)
//                // Envoi du nombre de familles via l'Intent
//                .putExtra(MemonimoUtilities.INTENT_EXTRA_NUM_FAMILY, _numFamily);
//        startActivity(intent);
//    }
//    public void startTestGame(View _view) {
//        startGame(_view, MemonimoUtilities.NUM_FAMILY_TEST_MODE);
//    }
//    public void startEasyGame(View _view) {
//        startGame(_view, MemonimoUtilities.NUM_FAMILY_EASY_MODE);
//    }
//    public void startNormalGame(View _view) {
//        startGame(_view, MemonimoUtilities.NUM_FAMILY_NORMAL_MODE);
//    }
//    public void startHardGame(View _view) {
//        startGame(_view, MemonimoUtilities.NUM_FAMILY_HARD_MODE);
//    }
//
//    /*
//        Pour arriver sur la vue listant les parties non terminées
//     */
//    public void findGame(View _view) {
//        Intent intent = new Intent(getActivity(), GameListActivity.class);
//        startActivity(intent);
//    }


}
