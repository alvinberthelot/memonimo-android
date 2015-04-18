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
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private final String LOG_TAG = GameFragment.class.getSimpleName();

    private static final String INSTANCE_STATE_ID_GAME = "instance_state_id_game";

    private Game mGame;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        long idGame = -1;

        // Récupération de l'id de la partie si celui-ci a été sauvé dans l'état
        if (savedInstanceState != null) {
            idGame = savedInstanceState.getLong(INSTANCE_STATE_ID_GAME);
        }

        // Récupération de l'id de la partie si celui-ci a été passé par Intent
        Intent intent  = getActivity().getIntent();
        if (intent != null && intent.hasExtra(MemonimoUtilities.INTENT_EXTRA_ID_GAME)) {
            idGame = intent.getLongExtra(MemonimoUtilities.INTENT_EXTRA_ID_GAME, -1);
        }

        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        if (idGame == -1) {
            // Initialisation d'une nouvelle partie d'un point de vue du modèle
            mGame = new Game(3);
            // Persistance de la partie
            idGame = MemonimoProvider.saveGame(getActivity().getContentResolver(), mGame);
            mGame.setId(idGame);
        } else {
            // Restauration de la partie
            mGame = MemonimoProvider.restoreGame(getActivity().getContentResolver(), idGame);
        }


        TextView idGameView = (TextView) rootView.findViewById(R.id.idGame);
        idGameView.setText("PARTIE #" + mGame.getId());


        GridView gridGameView = (GridView) rootView.findViewById(R.id.gridMemory);


        final GridGameAdapter gridGameAdapter = new GridGameAdapter(
                getActivity(),
                mGame.getGameCardList()
        );

        gridGameView.setAdapter(gridGameAdapter);

        gridGameView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // dans le cas d'un précédent tour de jeu
                if (mGame.isNextTurn()) {
                    // on modifie l'état des cartes pour qu'elles ne soient plus affichées,
                    // si celles-ci n'ont pas été trouvées
                    mGame.initNewTurn();
                }

                // On vérifie si la première carte n'a pas déjà été choisie
                if (!mGame.isFirstCardChosen()) {
                    if (mGame.isAFoundCard(position)) {
                        //
                        Toast.makeText(
                                getActivity(),
                                "Carte déjà trouvée à la position N°" + position,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // on affecte la position sélectionnée
                        mGame.chooseFirstCard(position);
                    }
                }
                // deuxième coup du tour de jeu
                else {

                    if (mGame.isCardAlreadyChosen(position)) {
                        Toast.makeText(
                                getActivity(),
                                "Carte déjà sélectionnée à la position N°" + position,
                                Toast.LENGTH_SHORT).show();
                    } else if (mGame.isAFoundCard(position)) {
                        //
                        Toast.makeText(
                                getActivity(),
                                "Carte déjà trouvée à la position N°" + position,
                                Toast.LENGTH_SHORT).show();
                    } else {

                        mGame.chooseSecondCard(position);

                        mGame.checkFamilyFound();

                    }
                }

                if (mGame.isAllCardsFound()) {
                    //
                    mGame.setFinished(true);

                    // Affichage d'une fenêtre pour recommencer une partie ou retourner à l'accueil
                    RestartDialogFragment restartDialogFragment = RestartDialogFragment.newInstance(mGame.getId());
                    restartDialogFragment.show(getActivity().getSupportFragmentManager(), "restartDialogFragment");

                }

                long idGame = MemonimoProvider.saveGame(getActivity().getContentResolver(), mGame);
                mGame.setId(idGame);

                gridGameAdapter.notifyDataSetChanged();
            }
        });


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Sauvegarde de l'identifiant de la partie dans l'état
        savedInstanceState.putLong(INSTANCE_STATE_ID_GAME, mGame.getId());
        Log.d(LOG_TAG, ".onSaveInstanceState() : id --> " + mGame.getId() + " saved");
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.d(LOG_TAG, ".onPause()");
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d(LOG_TAG, ".onResume()");
    }
}
