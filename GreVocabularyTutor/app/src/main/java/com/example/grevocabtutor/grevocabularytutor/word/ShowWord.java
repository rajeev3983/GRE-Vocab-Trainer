package com.example.grevocabtutor.grevocabularytutor.word;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.grevocabtutor.grevocabularytutor.Databasehelper.DatabaseHelper;
import com.example.grevocabtutor.grevocabularytutor.main.MyApplication;
import com.example.grevocabtutor.grevocabularytutor.R;


public class ShowWord extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_word);
        MyApplication app = (MyApplication) getApplicationContext();
        DatabaseHelper dbhelper = app.getData();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        Word temp =  dbhelper.getWordById(id);
        TextView wordText = (TextView)findViewById(R.id.word);
        TextView meaningText = (TextView)findViewById(R.id.textView2);
        TextView exampleText = (TextView)findViewById(R.id.textView4);
        wordText.setText(temp.word);
        meaningText.setText(temp.meaning);
        exampleText.setText(temp.example);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_word, menu);
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
