package com.webyousoon.android.memonimo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by hackorder on 13/04/2015.
 */
public class CardGame implements Cloneable {

    public enum AnimalGame {
        ALLIGATOR, BEAVER, BIRDY, BUFFALO, CAT, COW, ELEPHANT, FISH, FOX, GIRAFFE, GOOSE, HORSE,
        IGUANA, JELLYFISH, KOALA, LION, MONKEY, PHEASANT, PIG, RABBIT, SHEEP, TURTLE, WOLF, WHALE;

        private static final List<AnimalGame> VALUES =
                Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();

        public static List<AnimalGame> getModifiableList() {
            return new ArrayList<AnimalGame>(Arrays.asList(values()));
        }
    }

//    int mId;
    private AnimalGame mAnimalGame;
    private boolean mCardFound = false;
    private boolean mFoundPlayer1 = false;
    private boolean mFoundPlayer2 = false;
    private boolean mAttempt = false;

    private CardGame(AnimalGame _animalGame) {
        this.mAnimalGame = _animalGame;
    }

    private static CardGame getRandomCardOnce(List<AnimalGame> _list) {
        Random random = new Random();
//        List<AnimalGame> list = AnimalGame.getModifiableList();

        int selection = random.nextInt(_list.size());
        AnimalGame animalSelected = (AnimalGame) _list.get(selection);
        _list.remove(selection);

        return new CardGame(animalSelected);
    }

    public static List<CardGame> getRandomList(int _size) {
        List <CardGame> cardGameList = new ArrayList<CardGame>();
        List<AnimalGame> animalGameList = AnimalGame.getModifiableList();

        // TODO
        // if _size > animalGameList.size();

        for (int i = 0; i < _size; i++) {
            CardGame cardGenerated = getRandomCardOnce(animalGameList);
            cardGameList.add(cardGenerated);
            try {
                cardGameList.add((CardGame) cardGenerated.clone());
            } catch (CloneNotSupportedException e) {

            }
        }

        Collections.shuffle(cardGameList);

        return cardGameList;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public AnimalGame getAnimalGame() {
        return mAnimalGame;
    }

    public void setAnimalGame(AnimalGame _animalGame) {
        this.mAnimalGame = _animalGame;
    }

    public boolean isCardFound() {
        return mCardFound;
    }

    public void setCardFound(boolean _cardFound) {
        this.mCardFound = _cardFound;
    }

    public boolean isAttempt() {
        return mAttempt;
    }

    public void setAttempt(boolean _attempt) {
        this.mAttempt = _attempt;
    }

    public boolean isFoundPlayer1() {
        return mFoundPlayer1;
    }

    public void setFoundPlayer1(boolean _foundPlayer1) {
        this.mFoundPlayer1 = _foundPlayer1;
    }

    public boolean isFoundPlayer2() {
        return mFoundPlayer2;
    }

    public void setFoundPlayer2(boolean _foundPlayer2) {
        this.mFoundPlayer2 = _foundPlayer2;
    }
}
