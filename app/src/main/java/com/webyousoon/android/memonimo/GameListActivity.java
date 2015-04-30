package com.webyousoon.android.memonimo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class GameListActivity extends ActionBarActivity implements GameListFragment.Callback {

    private final String LOG_TAG = GameListActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG_SUMMARY_GAME = "FRA_TAG_SUM_GAM";

    private boolean mIsTabletLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_main, new GameListFragment())
                    .commit();
        }


        // On vérifie la présence ou non du fragment affichant le résumé en mode tablette
        if (findViewById(R.id.container_annex) != null) {
            mIsTabletLayout = true;
            if (savedInstanceState == null) {

                SummaryGameFragment summaryGameFragment = new SummaryGameFragment();
//                summaryGameFragment.setArguments(bundle);


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_annex,
                                summaryGameFragment,
                                FRAGMENT_TAG_SUMMARY_GAME)
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game_list, menu);
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
    public void onItemSelected(Uri _gameUri) {
        if (mIsTabletLayout) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(SummaryGameFragment.BUNDLE_ARG_URI_GAME, _gameUri);

            SummaryGameFragment fragment = new SummaryGameFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_annex, fragment, FRAGMENT_TAG_SUMMARY_GAME)
                    .commit();
        } else {
            // Envoi de l'id via l'Intent
            Intent intent = new Intent(this, GameActivity.class).setData(_gameUri);
            startActivity(intent);
        }
    }
}
