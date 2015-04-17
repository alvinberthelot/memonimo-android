package com.webyousoon.android.memonimo;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
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

import com.webyousoon.android.memonimo.adapters.GridMemoryAdapter;
import com.webyousoon.android.memonimo.data.MemonimoContract;

import java.util.ArrayList;
import java.util.List;


public class GameActivity extends ActionBarActivity {

    private final String LOG_TAG = GameActivity.class.getSimpleName();

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

        private List<CardGame> mCardGameList = CardGame.getRandomList(3);
        private long mIdGame = -1;
//        private List<CardGame> mCardGameList;




//        private boolean mFirstChoicePlayer = false;
//        private CardGame mCardChoosen = null;
        private int mFirstPositionChoosen = -1;
        private int mSecondPositionChoosen = -1;
        private List<Integer> mPositionFoundList = new ArrayList<Integer>();

        public PlaceholderFragment() {


        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            // Récupération de l'id de la partie si celle-ci a été passée
            Intent intent  = getActivity().getIntent();
            if (intent != null && intent.hasExtra(MemonimoUtilities.INTENT_EXTRA_ID_GAME)) {
                mIdGame = intent.getLongExtra(MemonimoUtilities.INTENT_EXTRA_ID_GAME, -1);
            }

            View rootView = inflater.inflate(R.layout.fragment_game, container, false);

            if (mIdGame == -1) {
//                mIdGame = getUnfinishedGame();
                
            }

            TextView idGameView = (TextView) rootView.findViewById(R.id.idGame);
            idGameView.setText("PARTIE #" + mIdGame);


            GridView gridMemory = (GridView) rootView.findViewById(R.id.gridMemory);
            final GridMemoryAdapter gridMemoryAdapter = new GridMemoryAdapter(getActivity(), mCardGameList);
            gridMemory.setAdapter(gridMemoryAdapter);

            gridMemory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    // dans le cas d'un précédent tour de jeu
                    if (mFirstPositionChoosen != -1 && mSecondPositionChoosen != -1) {
                        // on modifie l'état des cartes pour qu'elles ne soient plus affichées,
                        // si celles-ci n'ont pas été trouvées
                        mCardGameList.get(mFirstPositionChoosen).setAttempt(false);
                        mCardGameList.get(mSecondPositionChoosen).setAttempt(false);
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
                            mCardGameList.get(mFirstPositionChoosen).setAttempt(true);
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

                            if (mCardGameList.get(mFirstPositionChoosen).getAnimalGame() != mCardGameList.get(mSecondPositionChoosen).getAnimalGame()) {
                                mCardGameList.get(mSecondPositionChoosen).setAttempt(true);
                            } else {
                                mCardGameList.get(mFirstPositionChoosen).setCardFound(true);
                                mCardGameList.get(mSecondPositionChoosen).setCardFound(true);
                                mCardGameList.get(mFirstPositionChoosen).setFoundPlayer1(true);
                                mCardGameList.get(mSecondPositionChoosen).setFoundPlayer1(true);
                                mPositionFoundList.add(new Integer(mFirstPositionChoosen));
                                mPositionFoundList.add(new Integer(mSecondPositionChoosen));
                            }
                        }
                    }

                    if (mPositionFoundList.size() == mCardGameList.size()) {
                        //
                        finishGame();
                        //
                        Toast.makeText(
                                getActivity(),
                                "PARTIE TERMINÉE !!",
                                Toast.LENGTH_SHORT).show();


                    }


                    gridMemoryAdapter.notifyDataSetChanged();
                }
            });


            return rootView;
        }

        @Override
        public void onPause() {
            super.onPause();

            Log.v(LOG_TAG, "ALVIN onPause B");

            // Récupération des données via le Content Provider
            Cursor cursor = getActivity().getContentResolver().query(
                    MemonimoContract.GameEntry.CONTENT_URI, // URI
                    null, // Colonnes interogées
                    null, // Colonnes pour la condition WHERE
                    null, // Valeurs pour la condition WHERE
                    null // Tri
            );

            if (cursor.moveToFirst()) {
                Log.v(LOG_TAG, "Game #" + cursor.getLong(0) + " found into database");
            } else {
                Log.d(LOG_TAG, "No game into database");

                ContentValues gameValue = new ContentValues();
                gameValue.put(MemonimoContract.GameEntry.COLUMN_FINISHED, "0");

                // Insertion d'une partie via le Provider
                Uri uri = getActivity().getContentResolver().insert(
                        MemonimoContract.GameEntry.CONTENT_URI, gameValue);
                // Récupération de l'identifiant généré
                long idGenerated = ContentUris.parseId(uri);

                Log.d(LOG_TAG, "Game #" + idGenerated + " created into database");
            }

            cursor.close();

            Log.v(LOG_TAG, "ALVIN onPause E");
        }

        @Override
        public void onResume() {
            super.onResume();

            // Récupération des données via le Content Provider
            Cursor cursor = getActivity().getContentResolver().query(
                    MemonimoContract.GameEntry.CONTENT_URI, // URI
                    null, // Colonnes interogées
                    null, // Colonnes pour la condition WHERE
                    null, // Valeurs pour la condition WHERE
                    null // Tri
            );

            if (cursor.moveToFirst()) {
                Log.v(LOG_TAG, "Game #" + cursor.getLong(0) + " found into database");
            } else {
                Log.d(LOG_TAG, "OUPS pas normal");
            }

            cursor.close();


            Log.v(LOG_TAG, "ALVIN onResume E");
        }

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

        private void finishGame() {


            ContentValues gameValue = new ContentValues();
            gameValue.put(MemonimoContract.GameEntry.COLUMN_FINISHED, "1");

            // Insertion d'une partie via le Provider
            int numRowsUpdated = getActivity().getContentResolver().update(
                    MemonimoContract.GameEntry.CONTENT_URI,
                    gameValue,
                    MemonimoContract.GameEntry._ID + "=?",
                    new String[] {Long.toString(mIdGame)}
            );

            if (numRowsUpdated != 1) {
                Log.w(LOG_TAG, "SOUCI une seule ligen aurait du être modifiée");
            }

            Log.d(LOG_TAG, "Game #" + mIdGame + " updated as finish into database");
        }
    }
}
