package com.webyousoon.android.memonimo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class StartActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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

    /*
        Pour arriver sur la vue d'une nouvelle partie
     */
    public void startGame(View _view, int _numFamily) {
        Intent intent = new Intent(this, GameActivity.class)
                // Envoi du nombre de familles via l'Intent
                .putExtra(MemonimoUtilities.INTENT_EXTRA_NUM_FAMILY, _numFamily);
        startActivity(intent);
    }
    public void startTestGame(View _view) {
        startGame(_view, MemonimoUtilities.NUM_FAMILY_TEST_MODE);
    }
    public void startEasyGame(View _view) {
        startGame(_view, MemonimoUtilities.NUM_FAMILY_EASY_MODE);
    }
    public void startNormalGame(View _view) {
        startGame(_view, MemonimoUtilities.NUM_FAMILY_NORMAL_MODE);
    }
    public void startHardGame(View _view) {
        startGame(_view, MemonimoUtilities.NUM_FAMILY_HARD_MODE);
    }

    /*
        Pour arriver sur la vue listant les parties non terminées
     */
    public void findGame(View _view) {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }
}
