package com.webyousoon.android.memonimo.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hackorder on 17/04/2015.
 */
public class Game {


    private static final int CARD_NO_CHOSEN = -1;

    private long mId;
    private boolean mFinished = false;
    private int mFirstPositionChosen = CARD_NO_CHOSEN;
    private int mSecondPositionChosen = CARD_NO_CHOSEN;
    private List<GameCard> mGameCardList;

    public Game(long _id, boolean _finished, int _firstPositionChosen, int _secondPositionChosen) {
        this.mId = _id;
        this.mFinished = _finished;
        this.mFirstPositionChosen = _firstPositionChosen;
        this.mSecondPositionChosen = _secondPositionChosen;
        this.mGameCardList = new ArrayList<GameCard>();
    }

    public Game (int _numFamily) {
        this.mId = CARD_NO_CHOSEN;
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

    public int getFirstPositionChosen() {
        return mFirstPositionChosen;
    }

    public void setFirstPositionChosen(int _firstPositionChosen) {
        this.mFirstPositionChosen = _firstPositionChosen;
    }

    public int getSecondPositionChosen() {
        return mSecondPositionChosen;
    }

    public void setSecondPositionChosen(int _secondPositionChosen) {
        this.mSecondPositionChosen = _secondPositionChosen;
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

    public boolean isAFoundCard(int _position) {
        return mGameCardList.get(_position).isCardFound();
    }

    public void setAttemptFirstPositionChosen() {
        getFirstCardChosen().setAttempt(true);
    }

    public void setAttemptSecondPositionChosen() {
        getSecondCardChosen().setAttempt(true);
    }

    public void chooseFirstCard(int _position) {
        setFirstPositionChosen(_position);
        setAttemptFirstPositionChosen();
    }

    public void chooseSecondCard(int _position) {
        setSecondPositionChosen(_position);
        setAttemptSecondPositionChosen();
    }

    public boolean isFirstCardChosen() {
        return getFirstPositionChosen() != CARD_NO_CHOSEN;
    }

    public boolean isSecondCardChosen() {
        return getSecondPositionChosen() != CARD_NO_CHOSEN;
    }

    public boolean isFamilyFound() {
        return getFirstCardChosen().getAnimalGame() == getSecondCardChosen().getAnimalGame();
    }

    public void checkFamilyFound() {
        if (isFamilyFound()) {
            getFirstCardChosen().setCardFound(true);
            getSecondCardChosen().setCardFound(true);
            getFirstCardChosen().setFoundPlayer1(true);
            getSecondCardChosen().setFoundPlayer1(true);
        }
    }

    public boolean isCardAlreadyChosen(int _position) {
        return mFirstPositionChosen == _position;
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

    public boolean isNextTurn() {
        return isFirstCardChosen() && isSecondCardChosen();
    }

    private GameCard getFirstCardChosen() {
        return mGameCardList.get(mFirstPositionChosen);
    }

    private GameCard getSecondCardChosen() {
        return mGameCardList.get(mSecondPositionChosen);
    }

    public void initNewTurn() {
        //
        getFirstCardChosen().setAttempt(false);
        getSecondCardChosen().setAttempt(false);
        //
        mFirstPositionChosen = -1;
        mSecondPositionChosen = -1;
    }
}
