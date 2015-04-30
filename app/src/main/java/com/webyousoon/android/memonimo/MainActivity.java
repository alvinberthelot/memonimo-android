package com.webyousoon.android.memonimo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.webyousoon.android.memonimo.model.Game;


public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_start, new StartFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mShareActionProvider.setShareIntent(MemonimoUtilities.createShareIntent(getResources()));

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

    /*
        Pour arriver sur la vue d'une nouvelle partie
     */
    public void startGame(View _view, String _mode) {
        Intent intent = new Intent(this, GameActivity.class)
                // Envoi du nombre de familles via l'Intent
                .putExtra(MemonimoUtilities.INTENT_EXTRA_MODE_GAME, _mode);
        startActivity(intent);
    }
    public void startEasyGame(View _view) {
        startGame(_view, Game.Mode.EASY.toString());
    }
    public void startNormalGame(View _view) {
        startGame(_view, Game.Mode.NORMAL.toString());
    }
    public void startHardGame(View _view) { startGame(_view, Game.Mode.HARD.toString()); }
    public void startCustomGame(View _view) { startGame(_view, Game.Mode.CUSTOM.toString()); }

    /*
        Pour arriver sur la vue listant les parties non terminées
     */
    public void findGame(View _view) {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }

    /*
        Pour arriver sur la vue de configuration de l'application
    */
    public void goToSettings(View _view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
