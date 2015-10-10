package com.example.grevocabtutor.grevocabularytutor.Databasehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.grevocabtutor.grevocabularytutor.Deck.Deck;
import com.example.grevocabtutor.grevocabularytutor.word.Word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by rajeev on 5/6/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private Context mycontext;
    private String DB_PATH;// = mycontext.getApplicationContext().getPackageName()+"/databases/";
    private static String DB_NAME = "test.db";//the extension may be .sqlite or .db
    public SQLiteDatabase myDataBase;
    public void onCreate(SQLiteDatabase db) {}
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}

    public DatabaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        this.mycontext = context;
        DB_PATH = "/data/data/"
                + mycontext.getApplicationContext().getPackageName()
                + "/databases/";

        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println("Database exists");
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }

    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {
//SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }

        return checkdb;
    }

    private void copydatabase() throws IOException {

//Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open(DB_NAME);

// Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

//Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream("/data/data/"
                +"com.example.grevocabtutor.grevocabularytutor"
                +"/databases/test.db");

// transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

//Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();

    }

    public void opendatabase() throws SQLException {
//Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);

    }


    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    public ArrayList<String> getAllWords()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from vocab", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("word")));
            res.moveToNext();
        }
        return array_list;
    }
    public Word getWordById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from vocab where id= " + Integer.toString(id), null);
        Word myWord = new Word();
        res.moveToFirst();
        myWord.word=res.getString(res.getColumnIndex("word"));
        myWord.meaning=res.getString(res.getColumnIndex("meaning"));
        myWord.example=res.getString(res.getColumnIndex("example"));
        myWord.id = res.getInt(res.getColumnIndex("id"));
        myWord.level= res.getInt(res.getColumnIndex("level"));
        return  myWord;
    }

    public ArrayList<Word> getWordsFromDeck(int deckNum){
        Log.d("Deck Number",Integer.toString(deckNum));
        ArrayList<Word> array_list = new ArrayList<Word>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from vocab where deck= " + Integer.toString(deckNum), null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Word temp = new Word();
            temp.word = res.getString(res.getColumnIndex("word"));
            temp.meaning = res.getString(res.getColumnIndex("meaning"));
            temp.example = res.getString(res.getColumnIndex("example"));
            temp.id = res.getInt(res.getColumnIndex("id"));
            temp.level= res.getInt(res.getColumnIndex("level"));
            array_list.add(temp);
            res.moveToNext();
        }
        return array_list;
    }

    public void setLevelById(int id,int level){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE vocab SET level = "+Integer.toString(level)+" WHERE id = "+Integer.toString(id));
    }

    public Deck getDeckInfo(int deckNumber){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from deck_info where id= " + Integer.toString(deckNumber), null);
        Deck deck = new Deck(deckNumber);
        System.out.println("reached");
        res.moveToFirst();
        System.out.println(res.getInt(res.getColumnIndex("size")));
        deck.setSize(res.getInt(res.getColumnIndex("size")));
        deck.setNovice(res.getInt(res.getColumnIndex("novice")));
        deck.setIntermediate(res.getInt(res.getColumnIndex("intermediate")));
        deck.setExpert(res.getInt(res.getColumnIndex("expert")));
        deck.setUnanswered(res.getInt(res.getColumnIndex("unanswered")));
        return deck;
    }

    public void updateDeck(int id, int unanswered, int novice, int intermediate, int expert) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE deck_info SET unanswered = "+Integer.toString(unanswered)+", novice = "
                + Integer.toString(novice)+", intermediate = " + Integer.toString(intermediate)
                +", expert = "+Integer.toString(expert)+" WHERE id = "+Integer.toString(id));
    }
}