package com.cop4656.bohrd;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AdvertisementActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        TextView link = (TextView) findViewById(R.id.linkView);
        link.setMovementMethod(LinkMovementMethod.getInstance());

        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //when the close button is clicked, destroy the activity
                //this will bring the user to the main game
                finish();
                //this will not come up again unless the app is re-started
            }
        });
    }
}
