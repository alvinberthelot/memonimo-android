package com.webyousoon.android.memonimo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webyousoon.android.memonimo.data.MemonimoProvider;
import com.webyousoon.android.memonimo.model.Game;


public class SummaryGameFragment extends Fragment {

    private final String LOG_TAG = SummaryGameFragment.class.getSimpleName();

    private static final String INSTANCE_STATE_ID_GAME = "instance_state_id_game";


    // Views
    private TextView mLabelTestView;


    private Game mGame;

    public SummaryGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_score, container, false);

        // Récupération de l'identifiant de la partie envoyée par l'activitée
        long idGame = getArguments().getLong(GameActivity.BUNDLE_GAME_ID);
        // Récupération de la partie via le Provider
        mGame = MemonimoProvider.restoreGame(getActivity().getContentResolver(), idGame);

        TextView idGameView = (TextView) rootView.findViewById(R.id.idGame);
        idGameView.setText("PARTIE #" + mGame.getId());

        mLabelTestView = (TextView) rootView.findViewById(R.id.numFamily);
        mLabelTestView.setText("Familles trouvées : " + mGame.getNumFamilyFound());

        return rootView;
    }

    public void updateSummaryView(Game _game) {
        mGame = _game;

        mLabelTestView.setText("Familles trouvées : " + mGame.getNumFamilyFound());

    }
}
