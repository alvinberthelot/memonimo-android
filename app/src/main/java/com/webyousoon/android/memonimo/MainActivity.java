package com.webyousoon.android.memonimo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

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
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        Pour arriver sur la vue d'une nouvelle partie
     */
    public void startGame(View _view, int _numFamily) {
        Intent intent = new Intent(this, GameActivity.class)
                // Envoi du nombre de familles via l'Intent
                .putExtra(MemonimoUtilities.INTENT_EXTRA_NUM_FAMILY, _numFamily);
        startActivity(intent);
    }
    public void startEasyGame(View _view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String numFamily = preferences.getString(
                getString(R.string.pref_num_family_easy_key),
                getString(R.string.pref_num_family_easy_default));
        startGame(_view, Integer.parseInt(numFamily));
    }
    public void startNormalGame(View _view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String numFamily = preferences.getString(
                getString(R.string.pref_num_family_normal_key),
                getString(R.string.pref_num_family_normal_default));
        startGame(_view, Integer.parseInt(numFamily));
    }
    public void startHardGame(View _view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String numFamily = preferences.getString(
                getString(R.string.pref_num_family_hard_key),
                getString(R.string.pref_num_family_hard_default));
        startGame(_view, Integer.parseInt(numFamily));
    }

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
