package com.example.grevocabtutor.grevocabularytutor.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.grevocabtutor.grevocabularytutor.Databasehelper.DatabaseHelper;
import com.example.grevocabtutor.grevocabularytutor.Deck.DeckList;
import com.example.grevocabtutor.grevocabularytutor.R;
import com.example.grevocabtutor.grevocabularytutor.word.ShowWordlist;

import java.io.IOException;


public class MainActivity extends Activity {
    private DatabaseHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            dbhelper = new DatabaseHelper(this);
            MyApplication app = (MyApplication) getApplicationContext();
            app.setData(dbhelper);
        }
        catch (IOException ex){
            Log.d("TAG","Could not handle database");
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

    public void learnOnClick(View v){
        Intent myIntent = new Intent(this, DeckList.class);
        myIntent.putExtra("learningMode",true);
        startActivity(myIntent);
    }

    public void practiceOnClick(View v){
        Intent myIntent = new Intent(this,DeckList.class);
        myIntent.putExtra("learningMode",false);
        startActivity(myIntent);
    }

    public void wordlistOnClick(View v){
        Intent myIntent = new Intent(this, ShowWordlist.class);
        startActivity(myIntent);
    }


}
