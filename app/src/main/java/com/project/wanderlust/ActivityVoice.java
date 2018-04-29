package com.project.wanderlust;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityVoice extends ActionBarMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Check to see if a recognition activity is present
        // if running on AVD virtual device it will give this message. The mic
        // required only works on an actual android device
        PackageManager pm = getPackageManager();
        List activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0)
        {
        }
        else
        {
            Toast.makeText(this,"Recognizer not present", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void startSpeechRecognition(View v)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Operate Wanderlust Using Voice Commands");
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 2 && resultCode == RESULT_OK)
        {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (matches.contains("Send") && matches.contains("Invite"))
            {
                startActivity(new Intent(this, ActivityExtra.class));
                finish();
            }

            if (matches.contains("Close") && matches.contains("App"))
            {
                System.exit(0);
            }
        }
    }
}