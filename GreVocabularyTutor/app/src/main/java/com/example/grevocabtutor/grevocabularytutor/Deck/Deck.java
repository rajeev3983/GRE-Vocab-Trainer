package com.example.grevocabtutor.grevocabularytutor.Deck;

/**
 * Created by rajeev on 5/6/15.
 */
public class Deck {
    private int deckid;
    private String deckName;
    private int size;
    private int unanswered;
    private int novice;

    public int getIntermediate() {
        return intermediate;
    }

    public void setIntermediate(int intermediate) {
        this.intermediate = intermediate;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getUnanswered() {
        return unanswered;
    }

    public void setUnanswered(int unanswered) {
        this.unanswered = unanswered;
    }

    public int getNovice() {
        return novice;
    }

    public void setNovice(int novice) {
        this.novice = novice;
    }

    public int getExpert() {
        return expert;
    }

    public void setExpert(int expert) {
        this.expert = expert;
    }

    private int intermediate;
    private int expert;
    public Deck(int id){
        deckid=id;
        deckName="Deck "+Integer.toString(id);
    }

    @Override
    public String toString() {
        return deckName;
    }
}
