package com.example.grevocabtutor.grevocabularytutor.learn;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.grevocabtutor.grevocabularytutor.Databasehelper.DatabaseHelper;
import com.example.grevocabtutor.grevocabularytutor.Deck.Deck;
import com.example.grevocabtutor.grevocabularytutor.main.MyApplication;
import com.example.grevocabtutor.grevocabularytutor.R;
import com.example.grevocabtutor.grevocabularytutor.word.Word;

import java.util.ArrayList;
import java.util.Random;


public class LearnDeck extends Activity {
    private int deckNumber;
    private ArrayList<Word> wordArrayList = new ArrayList<Word>();
    private DatabaseHelper dbHelper;
    private int[] learningLevel;
    private int numWords;
    private int currentWord;
    private TextView practiceWord;
    private TextView practiceMeaning;
    private TextView practiceExample;
    private int mySum;
    private int unanswered;
    private int novice;
    private int intermediate;
    private int expert;
    private Random rGenerator = new Random();
    private int startScore = 26;
    private int learnlimit = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_deck);
        practiceWord =(TextView)findViewById(R.id.practice_word);
        practiceMeaning = (TextView)findViewById(R.id.practice_meaning);
        practiceExample =(TextView)findViewById(R.id.practice_example);
        deckNumber = getIntent().getIntExtra("deck_no", 0);
        System.out.println(deckNumber);
        MyApplication app = (MyApplication) getApplicationContext();
        dbHelper = app.getData();
        wordArrayList = dbHelper.getWordsFromDeck(deckNumber);
        Deck deck = dbHelper.getDeckInfo(deckNumber);
        unanswered = deck.getUnanswered();
        novice = deck.getNovice();
        intermediate = deck.getIntermediate();
        expert = deck.getExpert();
        numWords = wordArrayList.size();
        learningLevel = new int[numWords];
        mySum = 0;
        for(int i=0;i<numWords;i++) {
            learningLevel[i] = wordArrayList.get(i).level;
            mySum+=learningLevel[i];
        }
        generateNext();
        nextWord();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_learn_deck, menu);
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

    public void knowOnClick(View v){
        learningLevel[currentWord] = updateKnowScore(learningLevel[currentWord]);
        dbHelper.setLevelById(wordArrayList.get(currentWord).id,learningLevel[currentWord]);
        dbHelper.updateDeck(deckNumber,unanswered,novice,intermediate,expert);
        generateNext();
        nextWord();
    }

    private int updateKnowScore(int i) {
        if( i <= learnlimit)
            return i;
        if( i == startScore ){
            i-=6;
            unanswered -=1;
            intermediate+=1;
            return i;
        }
        if(i == (startScore-1) ){
            novice-=1;
            intermediate+=1;
        }
        i-=5;
        return i;
    }

    public void dknowOnClick(View v){
        learningLevel[currentWord] = updateDontKnowScore(learningLevel[currentWord]);
        dbHelper.setLevelById(wordArrayList.get(currentWord).id,learningLevel[currentWord]);
        dbHelper.updateDeck(deckNumber,unanswered,novice,intermediate,expert);
        generateNext();;
        nextWord();
    }

    private int updateDontKnowScore(int i) {
        if( i==(startScore-1))
            return  i;
        if(i==startScore){
            i-=1;
            unanswered-=1;
            novice+=1;
            return i;
        }
        i+=5;
        return i;
    }

    public void generateNext(){
        int rNum =rGenerator.nextInt(mySum+1);
        int sumTillHere=0;
        for(int i=0;i<numWords;i++){
            sumTillHere+=learningLevel[i];
            if(sumTillHere>=rNum) {
                currentWord = i;
                return;
            }
        }
    }

    public void nextWord(){
        practiceWord.setText(wordArrayList.get(currentWord).word);
        practiceMeaning.setText(wordArrayList.get(currentWord).meaning);
        practiceExample.setText(wordArrayList.get(currentWord).example);
    }
}
