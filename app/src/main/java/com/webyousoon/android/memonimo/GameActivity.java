package com.webyousoon.android.memonimo;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.webyousoon.android.memonimo.adapters.GridGameAdapter;
import com.webyousoon.android.memonimo.adapters.GridMemoryAdapter;
import com.webyousoon.android.memonimo.data.MemonimoContract;
import com.webyousoon.android.memonimo.data.ProviderUtilities;
import com.webyousoon.android.memonimo.model.Game;
import com.webyousoon.android.memonimo.model.GameCard;

import java.util.ArrayList;
import java.util.List;


public class GameActivity extends ActionBarActivity {

    private static final String LOG_TAG = GameActivity.class.getSimpleName();

    private static final String INSTANCE_STATE_ID_GAME = "instance_state_id_game";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


        private final String LOG_TAG = PlaceholderFragment.class.getSimpleName();

        private SharedPreferences mPreferences;

        private Game mGame;

//        private long mIdGame = -1;





//        private boolean mFirstChoicePlayer = false;
//        private CardGame mCardChoosen = null;
        private int mFirstPositionChoosen = -1;
        private int mSecondPositionChoosen = -1;
        private List<Integer> mPositionFoundList = new ArrayList<Integer>();

        public PlaceholderFragment() {


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
                saveGame();
            } else {
                // Restauration de la partie
                restoreGame(idGame);
            }


            TextView idGameView = (TextView) rootView.findViewById(R.id.idGame);
            idGameView.setText("PARTIE #" + mGame.getId());


            GridView gridGameView = (GridView) rootView.findViewById(R.id.gridMemory);


            final GridGameAdapter gridGameAdapter = new GridGameAdapter(
                    getActivity(),
                    mGame.getGameCardList()
            );
//            final GridGameAdapter gridGameAdapter = new GridGameAdapter(
//                    getActivity(),
//                    cursor,
//                    0
//            );

            gridGameView.setAdapter(gridGameAdapter);

            gridGameView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    // dans le cas d'un précédent tour de jeu
                    if (mFirstPositionChoosen != -1 && mSecondPositionChoosen != -1) {
                        // on modifie l'état des cartes pour qu'elles ne soient plus affichées,
                        // si celles-ci n'ont pas été trouvées
                        mGame.getGameCardList().get(mFirstPositionChoosen).setAttempt(false);
                        mGame.getGameCardList().get(mSecondPositionChoosen).setAttempt(false);
                        //
                        mFirstPositionChoosen = -1;
                        mSecondPositionChoosen = -1;
                    }

                    // premier coup du tour de jeu
                    if (mFirstPositionChoosen == -1) {
                        if (mPositionFoundList.contains(position)) {
                            //
                            Toast.makeText(
                                    getActivity(),
                                    "Carte déjà trouvée à la position N°" + position,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // on affecte la position sélectionnée
                            mFirstPositionChoosen = position;
                            mGame.getGameCardList().get(mFirstPositionChoosen).setAttempt(true);
                        }
                    }
                    // deuxième coup du tour de jeu
                    else {

                        if (mFirstPositionChoosen == position) {
                            Toast.makeText(
                                    getActivity(),
                                    "Carte déjà sélectionnée à la position N°" + position,
                                    Toast.LENGTH_SHORT).show();
                        } else if (mPositionFoundList.contains(position)) {
                            //
                            Toast.makeText(
                                    getActivity(),
                                    "Carte déjà trouvée à la position N°" + position,
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            mSecondPositionChoosen = position;

                            if (mGame.getGameCardList().get(mFirstPositionChoosen).getAnimalGame() != mGame.getGameCardList().get(mSecondPositionChoosen).getAnimalGame()) {
                                mGame.getGameCardList().get(mSecondPositionChoosen).setAttempt(true);
                            } else {
                                mGame.getGameCardList().get(mFirstPositionChoosen).setCardFound(true);
                                mGame.getGameCardList().get(mSecondPositionChoosen).setCardFound(true);
                                mGame.getGameCardList().get(mFirstPositionChoosen).setFoundPlayer1(true);
                                mGame.getGameCardList().get(mSecondPositionChoosen).setFoundPlayer1(true);
                                mPositionFoundList.add(new Integer(mFirstPositionChoosen));
                                mPositionFoundList.add(new Integer(mSecondPositionChoosen));
                            }
                        }
                    }

                    if (mGame.isAllCardsFound()) {
                        //
                        mGame.setFinished(true);
                        //
                        Toast.makeText(
                                getActivity(),
                                "PARTIE TERMINÉE !!",
                                Toast.LENGTH_SHORT).show();


                    }

                    saveGame();


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
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.d(LOG_TAG, ".onPause()");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d(LOG_TAG, ".onResume()");
        }


        private int saveGame() {

            Log.d(LOG_TAG, ".saveGame() : id -> " + mGame.getId());

            if (mGame.getId() == -1) {
                ContentValues gameValue = new ContentValues();
                gameValue.put(MemonimoContract.GameEntry.COLUMN_FINISHED, "0");

                // Insertion d'une partie via le Provider
                Uri uri = getActivity().getContentResolver().insert(
                        MemonimoContract.GameEntry.CONTENT_URI,
                        gameValue
                );
                // Récupération de l'identifiant généré
                mGame.setId(ContentUris.parseId(uri));
            } else {
                ContentValues gameValue = ProviderUtilities.convertGameModelToGameValues(mGame);

                // Insertion d'une partie via le Provider
                int numRowsUpdated = getActivity().getContentResolver().update(
                        MemonimoContract.GameEntry.CONTENT_URI,
                        gameValue,
                        MemonimoContract.GameEntry._ID + "=?",
                        new String[] {Long.toString(mGame.getId())}
                );

                if (numRowsUpdated != 1) {
                    Log.w(LOG_TAG, "SOUCI une seule ligne aurait du être modifiée");
                }

                Log.d(LOG_TAG, "Game #" + mGame.getId() + " updated as finish into database");
            }



            // Conversion pour le Provider
            ContentValues[] gameCards = ProviderUtilities.convertGameModelToGameCardValues(mGame);

            // Suppressions massives via le Provider
            getActivity().getContentResolver().delete(
                    MemonimoContract.GameCardEntry.CONTENT_URI,
                    MemonimoContract.GameCardEntry.COLUMN_ID_GAME + "=?",
                    new String[] {Long.toString(mGame.getId())}
            );

            // Insertions massives via le Provider
            int numRowsConcerned = getActivity().getContentResolver().bulkInsert(
                    MemonimoContract.GameCardEntry.CONTENT_URI,
                    gameCards
            );

            Log.v(LOG_TAG, numRowsConcerned + " rows concerned into database");

            return numRowsConcerned;
        }

        private void restoreGame(long _idGame) {

            Log.d(LOG_TAG, ".restoreGame() : id -> " + _idGame);

            mGame = new Game(_idGame);

            // Récupération des données via le Content Provider
            Cursor cursor = getActivity().getContentResolver().query(
                    MemonimoContract.GameCardEntry.CONTENT_URI, // URI
                    null, // Colonnes interogées
                    MemonimoContract.GameCardEntry.COLUMN_ID_GAME + "=?", // Colonnes pour la condition WHERE
                    new String[] {"" + _idGame}, // Valeurs pour la condition WHERE
                    null // Tri
            );
            // Récupération du modèle
            while(cursor.moveToNext()) {
                mGame.addGameCard(ProviderUtilities.convertGameCardCursorToGameCardModel(cursor));
            }


            Log.e(LOG_TAG, ".restoreGame() test : id -> " + _idGame);
        }

//        private long createGame(List<GameCard> _cardGameList) {
//            ContentValues gameValue = new ContentValues();
//            gameValue.put(MemonimoContract.GameEntry.COLUMN_FINISHED, "0");
//
//            // Insertion d'une partie via le Provider
//            Uri uri = getActivity().getContentResolver().insert(
//                    MemonimoContract.GameEntry.CONTENT_URI,
//                    gameValue
//            );
//            // Récupération de l'identifiant généré
//            long idGenerated = ContentUris.parseId(uri);
//
//
//            saveGame();
//
//
//            return idGenerated;
//        }

        private long getUnfinishedGame() {
            long idGame;

            // Récupération des données via le Content Provider
            Cursor cursor = getActivity().getContentResolver().query(
                    MemonimoContract.GameEntry.CONTENT_URI, // URI
                    null, // Colonnes interogées
                    MemonimoContract.GameEntry.COLUMN_FINISHED + "=?", // Colonnes pour la condition WHERE
                    new String[] {"0"}, // Valeurs pour la condition WHERE
                    null // Tri
            );

            if (cursor.moveToFirst()) {
                idGame = cursor.getLong(0);
                Log.v(LOG_TAG, "Game #" + idGame + " found into database");
            } else {
                Log.d(LOG_TAG, "No game into database");

                ContentValues gameValue = new ContentValues();
                gameValue.put(MemonimoContract.GameEntry.COLUMN_FINISHED, "0");

                // Insertion d'une partie via le Provider
                Uri uri = getActivity().getContentResolver().insert(
                        MemonimoContract.GameEntry.CONTENT_URI,
                        gameValue);
                // Récupération de l'identifiant généré
                idGame = ContentUris.parseId(uri);

                Log.d(LOG_TAG, "Game #" + idGame + " created into database");
            }

            cursor.close();

            return idGame;
        }

        private long getGame() {

            long idGame;

            // Récupération des données via le Content Provider
            Cursor cursor = getActivity().getContentResolver().query(
                    MemonimoContract.GameEntry.CONTENT_URI, // URI
                    null, // Colonnes interogées
                    null, // Colonnes pour la condition WHERE
                    null, // Valeurs pour la condition WHERE
                    null // Tri
            );

            if (cursor.moveToFirst()) {
                idGame = cursor.getLong(0);
                Log.v(LOG_TAG, "Game #" + idGame + " found into database");
            } else {
                Log.d(LOG_TAG, "No game into database");

                ContentValues gameValue = new ContentValues();
                gameValue.put(MemonimoContract.GameEntry.COLUMN_FINISHED, "0");

                // Insertion d'une partie via le Provider
                Uri uri = getActivity().getContentResolver().insert(
                        MemonimoContract.GameEntry.CONTENT_URI,
                        gameValue);
                // Récupération de l'identifiant généré
                idGame = ContentUris.parseId(uri);

                Log.d(LOG_TAG, "Game #" + idGame + " created into database");
            }

            cursor.close();

            return idGame;
        }

    }
}
