package com.example.grevocabtutor.grevocabularytutor.main;

import android.app.Application;

import com.example.grevocabtutor.grevocabularytutor.Databasehelper.DatabaseHelper;

/**
 * Created by rajeev on 5/6/15.
 */
public class MyApplication extends Application {
    private DatabaseHelper dbHelper;
    public DatabaseHelper getData() {return dbHelper;}
    public void setData(DatabaseHelper data) {this.dbHelper = data;}
}
