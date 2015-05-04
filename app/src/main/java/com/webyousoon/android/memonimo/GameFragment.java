package com.webyousoon.android.memonimo;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.webyousoon.android.memonimo.adapters.GridGameAdapter;
import com.webyousoon.android.memonimo.data.MemonimoProvider;
import com.webyousoon.android.memonimo.model.BackgroundPattern;
import com.webyousoon.android.memonimo.model.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private final String LOG_TAG = GameFragment.class.getSimpleName();

    private static final String INSTANCE_STATE_ID_GAME = "instance_state_id_game";


    private ShareActionProvider mShareActionProvider;

    OnGameListener mGameCallback;

    private Game mGame;

//    private List<BackgroundPattern> mBackgroundPatternList;

    // Views
    private View mRootView;

    public GameFragment() {
        // Required empty public constructor
    }

    // Interface pour indiquer à l'activité parente que l'état de la partie a changé
    public interface OnGameListener {
        public void onGameChanged(Game game);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_game, container, false);



        startGame();


        return mRootView;
    }

    private void createGame() {

    }

    private void startGame() {
        // Récupération de l'identifiant de la partie envoyée par l'activitée
        long idGame = getArguments().getLong(GameActivity.BUNDLE_GAME_ID);
        // Récupération de la partie via le Provider
        mGame = MemonimoProvider.restoreGame(getActivity().getContentResolver(), idGame);



        BackgroundPattern backgroundPattern = MemonimoProvider.restoreBackgroundPatternByIdGame(
                getActivity().getContentResolver(), idGame);

        if (backgroundPattern == null) {
            // Récupération des patterns stockés en base
//            mBackgroundPatternList = MemonimoProvider.restoreAllPatternList(getActivity().getContentResolver());
//
//            if (mBackgroundPatternList == null || mBackgroundPatternList.size() == 0) {
////                RandomPatternTask randomPatternTask = new RandomPatternTask();
////                randomPatternTask.execute();
//            } else {
////                chooseBackground();
//            }

        } else {
            // Affectation du background
            mRootView.setBackgroundDrawable(backgroundPattern.getBackgroundDrawable());
        }


        GridView gridGameView = (GridView) mRootView.findViewById(R.id.gridMemory);


        final GridGameAdapter gridGameAdapter = new GridGameAdapter(
                getActivity(),
                mGame
        );

        gridGameView.setAdapter(gridGameAdapter);

        gridGameView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

                        finishChoice();

                        // Notification du changement d'état pour la vue
                        gridGameAdapter.notifyDataSetChanged();
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

                        if (mGame.isAllCardsFound()) {
                            //
                            mGame.setFinished(true);

                            // Affichage d'une fenêtre pour recommencer une partie ou retourner à l'accueil
                            RestartDialogFragment restartDialogFragment = RestartDialogFragment.newInstance(
                                    mGame.getId(),
                                    mGame.getMode().toString());
                            restartDialogFragment.show(getActivity().getSupportFragmentManager(), "restartDialogFragment");

                        }
                        // Notification du changement d'état pour la vue
                        gridGameAdapter.notifyDataSetChanged();

                    }
                }

                finishChoice();

            }
        });

    }

    private void finishChoice() {
        // Enregistrement de la partie en base via le Provider
        long idGame = MemonimoProvider.saveGame(getActivity().getContentResolver(), mGame);
        mGame.setId(idGame);
        // Notification du changement d'état de la partie à l'activité pour les autres fragments
        mGameCallback.onGameChanged(mGame);
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mGameCallback = (OnGameListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString()
                    + " must implement OnGameListener");
        }
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
