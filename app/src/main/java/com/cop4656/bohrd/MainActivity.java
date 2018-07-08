package com.cop4656.bohrd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String SPEED_CHOICE = "pref_speedChoice";

    private boolean preferencesChanged = true; // did preferences change?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        SharedPreferences.Editor sharedPreferencesEditor =
                PreferenceManager.getDefaultSharedPreferences(this).edit();
        sharedPreferencesEditor.remove(SPEED_CHOICE).commit();

        PreferenceManager.setDefaultValues(this,R.xml.preferences,true);

        Log.i("compBut","In Method onCreate");
        // register listener for SharedPreferences changes
        PreferenceManager.getDefaultSharedPreferences(this).
                registerOnSharedPreferenceChangeListener(
                        preferencesChangeListener);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (preferencesChanged) {
            MainActivityFragment mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(
                    R.id.gameFragment);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String choice = sharedPreferences.getString(SPEED_CHOICE,null);
            int choiceNum = Integer.parseInt(choice);
            mainActivityFragment.setTimeChanger(choiceNum);
            mainActivityFragment.resetGame();
            mainActivityFragment.playAgain.setText(R.string.start_game);
            preferencesChanged = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent preferencesIntent = new Intent(this, SettingsActivity.class);
        startActivity(preferencesIntent);
        return super.onOptionsItemSelected(item);
    }

    // listener for changes to the app's SharedPreferences
    private SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                // called when the user changes the app's preferences
                @Override
                public void onSharedPreferenceChanged(
                        SharedPreferences sharedPreferences, String key) {
                    preferencesChanged = true; // user changed app setting
                    Log.i("Prefernce Change","User changed preferences");
                    //get references to the main fragment
                    MainActivityFragment gameFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(
                            R.id.gameFragment);
                    if (key.equals(SPEED_CHOICE)) //to enable potential additional options later
                    {
                        String choice = sharedPreferences.getString(SPEED_CHOICE,null);
                        int choiceNum = Integer.parseInt(choice);
                        gameFragment.setTimeChanger(choiceNum);
                        gameFragment.resetGame();
                    }

                    Toast.makeText(getApplicationContext(),R.string.changed_settings,Toast.LENGTH_SHORT).show();
                }
            };
}
