package com.example.grevocabtutor.grevocabularytutor.Deck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grevocabtutor.grevocabularytutor.Databasehelper.DatabaseHelper;
import com.example.grevocabtutor.grevocabularytutor.Deck.Deck;
import com.example.grevocabtutor.grevocabularytutor.R;
import com.example.grevocabtutor.grevocabularytutor.learn.LearnDeck;
import com.example.grevocabtutor.grevocabularytutor.main.MyApplication;
import com.example.grevocabtutor.grevocabularytutor.practice.PracticeDeck;
import java.util.ArrayList;


public class DeckList extends Activity {
    private ListView listView;
    private ArrayList<Deck> deckArrayList = new ArrayList<Deck>();
    private boolean learningMode;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = getIntent();
        MyApplication app = (MyApplication) getApplicationContext();
        dbHelper = app.getData();
        learningMode = myIntent.getBooleanExtra("learningMode",true);
        setContentView(R.layout.activity_deck_list);
        for(int i=1;i<=70;i++) {
            deckArrayList.add(new Deck(i));
        }
        DeckAdapter arrayAdapter = new DeckAdapter(this,deckArrayList );
        listView = (ListView)findViewById(R.id.decklist_view);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                if(learningMode){
                    intent = new Intent(getApplicationContext(), LearnDeck.class);
                }
                else{
                    intent = new Intent(getApplicationContext(),PracticeDeck.class);
                }
                intent.putExtra("deck_no",i);
                Log.d("TAG",Integer.toString(i));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deck_list, menu);
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
}
