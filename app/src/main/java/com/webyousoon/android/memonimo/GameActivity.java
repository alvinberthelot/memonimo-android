package com.webyousoon.android.memonimo;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        private List<CardGame> mCardGameList = CardGame.getRandomList(8);

//        private boolean mFirstChoicePlayer = false;
//        private CardGame mCardChoosen = null;
        private int mFirstPositionChoosen = -1;
        private int mSecondPositionChoosen = -1;
        private List<Integer> mPositionFoundList = new ArrayList<Integer>();

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_game, container, false);

//            TextView testMemory = (TextView) rootView.findViewById(R.id.testMemoryView);
//            testMemory.setText("Coucou");


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

                    gridMemoryAdapter.notifyDataSetChanged();
                }
            });


            return rootView;
        }
    }
}
