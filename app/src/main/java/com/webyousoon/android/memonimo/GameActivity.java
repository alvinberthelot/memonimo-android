package com.webyousoon.android.memonimo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.webyousoon.android.memonimo.data.MemonimoProvider;
import com.webyousoon.android.memonimo.model.Game;

public class GameActivity extends ActionBarActivity implements GameFragment.OnGameListener {

    private static final String LOG_TAG = GameActivity.class.getSimpleName();
    private static final String INSTANCE_STATE_ID_GAME = "instance_state_id_game";
    private static final String FRAGMENT_TAG_SUMMARY_GAME = "FRA_TAG_SUM_GAM";

    public static final String BUNDLE_GAME_ID = "bundle_game_id";

    private boolean mIsTabletLayout;

//    private Game mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Obtention de l'identifiant de la partie
        // Soit par récupération
        // Soit par création d'une nouvelle partie
        long idGame = getIdGame();

        Bundle bundle = new Bundle();
        bundle.putLong(BUNDLE_GAME_ID, idGame);




        // Déclaration dynamique du fragment affichant la partie
        GameFragment gameFragment = new GameFragment();
        gameFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_main, gameFragment)
                .commit();


        // On vérifie la présence ou non du fragment affichant le résumé en mode tablette
        if (findViewById(R.id.container_annex) != null) {
            mIsTabletLayout = true;
            if (savedInstanceState == null) {

                SummaryGameFragment summaryGameFragment = new SummaryGameFragment();
                summaryGameFragment.setArguments(bundle);


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_annex,
                                summaryGameFragment,
                                FRAGMENT_TAG_SUMMARY_GAME)
                        .commit();
            }
        }


    }

    private long getIdGame() {
        long idGame = -1;
        // Initialisation à défaut
        int numFamily = -1;

        Game game;

//        // Récupération de l'id de la partie si celui-ci a été sauvé dans l'état
//        if (savedInstanceState != null) {
//            idGame = savedInstanceState.getLong(INSTANCE_STATE_ID_GAME);
//        }

        // Récupération des informations liées à l'Intent
        Intent intent  = this.getIntent();
        if (intent != null) {
            // Récupération de l'id de la partie si celui-ci a été passé par Intent
            if (intent.hasExtra(MemonimoUtilities.INTENT_EXTRA_ID_GAME)) {
                idGame = intent.getLongExtra(MemonimoUtilities.INTENT_EXTRA_ID_GAME, -1);
            }
            // Récupération de l'id de la partie si celui-ci a été passé par Intent
            if (intent.hasExtra(MemonimoUtilities.INTENT_EXTRA_NUM_FAMILY)) {
                numFamily = intent.getIntExtra(MemonimoUtilities.INTENT_EXTRA_NUM_FAMILY, -1);
            }
        }


        if (idGame == -1) {
            // Initialisation d'une nouvelle partie d'un point de vue du modèle
            game = new Game(numFamily);
            // Persistance de la partie
            idGame = MemonimoProvider.saveGame(this.getContentResolver(), game);
            game.setId(idGame);
        } else {
            // Restauration de la partie
            game = MemonimoProvider.restoreGame(this.getContentResolver(), idGame);
        }

        return game.getId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Correspondance pour l'accès à la configuration
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGameChanged(Game _game) {

        Log.v(LOG_TAG, ".onGameChanged()");


        SummaryGameFragment summaryGameFragment =
                (SummaryGameFragment) getSupportFragmentManager().findFragmentById(R.id.container_annex);

        // On vérifie la présence ou non du fragment affichant le résumé en mode tablette
        if (summaryGameFragment != null) {
            // Si présent on met à jour le fragment
            summaryGameFragment.updateSummaryView(_game);
        }

//        if (mIsTabletLayout) {
//
//
//
//
//
//            Bundle args = new Bundle();
//            args.putParcelable(GameFragment.ARGUMENT_ID_GAME, _game);
//
//            SummaryGameFragment summaryGameFragment = new SummaryGameFragment();
//            summaryGameFragment.setArguments(args);
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container_summary_game,
//                            summaryGameFragment,
//                            FRAGMENT_TAG_SUMMARY_GAME)
//                    .commit();
//        }
    }
}
