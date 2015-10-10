package com.example.grevocabtutor.grevocabularytutor.Deck;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.grevocabtutor.grevocabularytutor.Databasehelper.DatabaseHelper;
import com.example.grevocabtutor.grevocabularytutor.R;
import com.example.grevocabtutor.grevocabularytutor.main.MyApplication;

import java.util.ArrayList;

/**
 * Created by rajeev on 2/8/15.
 */
public class DeckAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private ArrayList<Deck> deckList;
    private DatabaseHelper dbHelper;
    public DeckAdapter(Activity activity, ArrayList<Deck> objects) {
        super(activity, R.layout.decklist_adapter, objects);
        inflater = activity.getWindow().getLayoutInflater();
        deckList=objects;
        MyApplication app = (MyApplication)activity.getApplicationContext();
        dbHelper = app.getData();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.decklist_adapter, parent, false);
        TextView deckName =(TextView)v.findViewById(R.id.deck_name);
        deckName.setText(deckList.get(position).toString());
        Deck deck = dbHelper.getDeckInfo(position);
        TextView et;
        et = (TextView) v.findViewById(R.id.expert_num);
        et.setText(Integer.toString(deck.getExpert())+"\nExpert");
        et = (TextView) v.findViewById(R.id.intermidiate_num);
        et.setText(Integer.toString(deck.getIntermediate())+"\nIntermediate");
        et = (TextView) v.findViewById(R.id.novice_num);
        et.setText(Integer.toString(deck.getNovice())+"\nNovice");
        et = (TextView) v.findViewById(R.id.unanswered_num);
        et.setText(Integer.toString(deck.getUnanswered())+"\nUnanswered");
        return v;

//        ViewHolder holder = null;
//        LayoutInflater inflater = getLayoutInflater();
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.row, null, false);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        }
//        else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        holder.getUpperText().setText(dataSource[position]);
//        holder.getLowerText().setText(dataSource[position]);
//
//        return convertView;
    }
}
