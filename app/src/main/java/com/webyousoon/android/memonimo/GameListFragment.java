package com.webyousoon.android.memonimo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.webyousoon.android.memonimo.adapters.ListGameAdapter;
import com.webyousoon.android.memonimo.data.MemonimoContract;


public class GameListFragment extends Fragment {

    private final String LOG_TAG = GameListFragment.class.getSimpleName();

    private TextView mTextViewTitle;
    private ListView mListViewGame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);

        mTextViewTitle = (TextView) rootView.findViewById(R.id.tv_title);
        mTextViewTitle.setTypeface(MemonimoUtilities.getCustomFont(getActivity().getAssets()));

        mListViewGame = (ListView) rootView.findViewById(R.id.lv_game);

        showGameList();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        showGameList();
    }

    private void showGameList() {
        // Récupération des données
        Cursor cursor = getActivity().getContentResolver().query(
                MemonimoContract.GameEntry.CONTENT_URI, // URI
                null, // Colonnes interogées
                null, // Colonnes pour la condition WHERE
                null, // Valeurs pour la condition WHERE
                null // Tri
        );

        // Initialisation de l'Adapter avec le curseur
        final ListGameAdapter listSummaryGameAdapter = new ListGameAdapter(
                getActivity(),
                cursor,
                0
        );
        // Affectation de l'Adapter à la ListView
        mListViewGame.setAdapter(listSummaryGameAdapter);


        mListViewGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Récupération de l'identifiant de la partie
                long idGame = listSummaryGameAdapter.getIdItem(position);

                Intent intent = new Intent(getActivity(), GameActivity.class)
                        // Envoi de l'identifiant de la partie
                        .putExtra(MemonimoUtilities.INTENT_EXTRA_ID_GAME, idGame);
                startActivity(intent);
            }
        });

    }
}
