package com.webyousoon.android.memonimo.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hackorder on 17/04/2015.
 */
public class Game {


    private long mId;
    private boolean mFinished;
    private List<GameCard> mGameCardList;

    public Game (long _id) {
        this.mId = _id;
        this.mGameCardList = new ArrayList<GameCard>();
    }

    public Game (int _numFamily) {
        this.mId = -1;
        this.mGameCardList = GameCard.getRandomList(_numFamily);
    }


    public long getId() {
        return mId;
    }

    public void setId(long _id) {
        this.mId = _id;
    }

    public boolean isFinished() {
        return mFinished;
    }

    public void setFinished(boolean _finished) {
        this.mFinished = _finished;
    }

    public List<GameCard> getGameCardList() {
        return mGameCardList;
    }

    public void setmGameCardList(List<GameCard> _gameCardList) {
        this.mGameCardList = _gameCardList;
    }

    public void addGameCard(GameCard _gamGameCard) {
        this.mGameCardList.add(_gamGameCard);
    }

    public boolean isAllCardsFound() {

        boolean allCardsFound = true;

        for (GameCard gameCard : mGameCardList) {
            if (!gameCard.isCardFound()) {
                allCardsFound = false;
                break;
            }
        }

        return allCardsFound;
    }
}
