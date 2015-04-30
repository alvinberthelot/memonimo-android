package com.webyousoon.android.memonimo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.webyousoon.android.memonimo.adapters.ListSummaryGameAdapter;
import com.webyousoon.android.memonimo.data.MemonimoContract;


public class GameListFragment extends Fragment {

    private final String LOG_TAG = GameListFragment.class.getSimpleName();


    /**
     * Interface pour indiquer à l'activité parente qu'un nouvel item a été sélectionné
     */
    public interface Callback {
        public void onItemSelected(Uri gameUri);
    }

    public GameListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);
        ListView summaryGameListView = (ListView) rootView.findViewById(R.id.lv_summary_game);

        // Récupération des données via le Content Provider
        Cursor cursor = getActivity().getContentResolver().query(
                MemonimoContract.GameEntry.CONTENT_URI, // URI
                null, // Colonnes interogées
                null, // Colonnes pour la condition WHERE
                null, // Valeurs pour la condition WHERE
                null // Tri
        );

        // Initialisation de l'Adapter avec le curseur
        final ListSummaryGameAdapter listSummaryGameAdapter = new ListSummaryGameAdapter(
                getActivity(),
                cursor,
                0
        );
        // Affectation de l'Adapter à la ListView
        summaryGameListView.setAdapter(listSummaryGameAdapter);


        summaryGameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Récupération de l'identifiant de la partie
                long idGame = listSummaryGameAdapter.getIdItem(position);

                ((Callback) getActivity())
                        .onItemSelected(MemonimoContract.GameEntry.buildGameUri(idGame));
            }
        });

        return rootView;
    }
}
