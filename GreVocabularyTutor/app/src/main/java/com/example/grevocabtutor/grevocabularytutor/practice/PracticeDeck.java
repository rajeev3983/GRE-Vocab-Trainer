package com.example.grevocabtutor.grevocabularytutor.practice;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.grevocabtutor.grevocabularytutor.Databasehelper.DatabaseHelper;
import com.example.grevocabtutor.grevocabularytutor.Deck.Deck;
import com.example.grevocabtutor.grevocabularytutor.main.MyApplication;
import com.example.grevocabtutor.grevocabularytutor.R;
import com.example.grevocabtutor.grevocabularytutor.word.Word;

import java.util.ArrayList;
import java.util.Random;


public class PracticeDeck extends ActionBarActivity {
    private int deckNumber;
    private ArrayList<Word> wordArrayList = new ArrayList<Word>();
    private DatabaseHelper dbHelper;
    private int numWords;
    private int currentWord;
    private int[] learningLevel;
    private Random rGenerator = new Random();
    private int correctChoice;
    private RadioGroup rg;
    private int mySum;
    private int unanswered;
    private int novice;
    private int intermediate;
    private int expert;
    private int  startScore = 26;
    ArrayList<Word> choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_deck);
        initialize();
    }

    private void initialize() {
        deckNumber = getIntent().getIntExtra("deck_no", 0);
        MyApplication app = (MyApplication) getApplicationContext();
        dbHelper = app.getData();
        wordArrayList = dbHelper.getWordsFromDeck(deckNumber);
        Deck deck = dbHelper.getDeckInfo(deckNumber);
        unanswered = deck.getUnanswered();
        novice = deck.getNovice();
        intermediate = deck.getIntermediate();
        expert = deck.getExpert();
        numWords = wordArrayList.size();
        numWords = wordArrayList.size();
        learningLevel = new int[numWords];
        mySum = 0;
        for(int i=0;i<numWords;i++) {
            learningLevel[i] = wordArrayList.get(i).level;
            mySum+=learningLevel[i];
        }
        generateNext();
        nextWord();
        rg = (RadioGroup)findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("Radio ", Integer.toString(checkedId));
                if(checkedId!=-1) {
                    group.clearCheck();
                    int temp = R.id.radioButton0+correctChoice;
                    if(temp==checkedId)
                        knowOnClick();
                    else
                        dknowOnClick();
                    generateNext();
                    nextWord();
                }
            }
        });
    }

    public void knowOnClick(){
        learningLevel[currentWord] = updateKnowScore(learningLevel[currentWord]);
        dbHelper.setLevelById(wordArrayList.get(currentWord).id, learningLevel[currentWord]);
        dbHelper.updateDeck(deckNumber, unanswered, novice, intermediate, expert);
    }

    private int updateKnowScore(int i) {
        if( i == 1 )
            return i;
        else if(i==startScore) {
            i -= 6;
            intermediate+=1;
            unanswered -=1;
            return i;
        }
        else if( i==5){
            i=1;
            intermediate -= 1;
            expert -=1;
            return i;
        }
        i-=5;
        return i;
    }

    public void dknowOnClick(){
        learningLevel[currentWord] = updateDontKnowScore(learningLevel[currentWord]);
        dbHelper.setLevelById(wordArrayList.get(currentWord).id, learningLevel[currentWord]);
        dbHelper.updateDeck(deckNumber, unanswered, novice, intermediate, expert);

    }

    private int updateDontKnowScore(int i) {
        if (i==startScore-1)
            return i;
        else if( i==startScore-6){
            i = startScore -1;
            intermediate -=1;
            novice +=1;
            return i;
        }
        else if( i==startScore){
            novice += 1;
            unanswered -= 1;
            i -= 1;
            return i;
        }
        else if( i==1){
            i+=5;
            expert -= 1;
            intermediate += 1;
        }
        return 0;
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
        choice = new ArrayList<Word>();
        ArrayList<Word> words = new ArrayList<>(wordArrayList);
        swap(words,currentWord,numWords-1);
        for(int i=0;i<3;i++){
            int rNum = rGenerator.nextInt(numWords-i-1);
            swap(words,rNum,numWords-i-1);
        }
        for(int i=0;i<4;i++){
            choice.add(words.get(numWords - i - 1));
        }
        correctChoice = rGenerator.nextInt(4);
        swap(choice,0,correctChoice);
        render();
    }
    public void render(){
        TextView tx = (TextView)findViewById(R.id.radio_question);
        tx.setText(choice.get(correctChoice).word);
        RadioButton rb;

        rb= (RadioButton)findViewById(R.id.radioButton0);
        rb.setText(choice.get(0).meaning);
        rb = (RadioButton)findViewById(R.id.radioButton2);
        rb.setText(choice.get(1).meaning);
        rb = (RadioButton)findViewById(R.id.radioButton3);
        rb.setText(choice.get(2).meaning);
        rb = (RadioButton)findViewById(R.id.radioButton4);
        rb.setText(choice.get(3).meaning);
    }

    private void swap(ArrayList<Word> words, int currentWord, int i) {
        Word temp = words.get(currentWord);
        words.set(currentWord,words.get(i));
        words.set(i,temp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practice_deck, menu);
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
