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


//    private static final int CARD_ALLIGATOR_ID = 1;
//    private static final int CARD_BEAVER_ID = 2;
//    private static final int CARD_BIRDY_ID = 3;
//    private static final int CARD_BUFFALO_ID = 4;
//    private static final int CARD_CAT_ID = 5;
//    private static final int CARD_COW_ID = 6;
//    private static final int CARD_ELEPHANT_ID = 7;
//    private static final int CARD_FISH_ID = 8;
//    private static final int CARD_FOX_ID = 9;
//    private static final int CARD_GIRAFFE_ID = 10;
//    private static final int CARD_GOOSE_ID = 11;
//    private static final int CARD_HORSE_ID = 12;
//    private static final int CARD_IGUANA_ID = 13;
//    private static final int CARD_JELLYFISH_ID = 14;
//    private static final int CARD_KOALA_ID = 15;
//    private static final int CARD_LION_ID = 16;
//    private static final int CARD_MONKEY_ID = 17;
//    private static final int CARD_PHEASANT_ID = 18;
//    private static final int CARD_PIG_ID = 19;
//    private static final int CARD_RABBIT_ID = 20;
//    private static final int CARD_SHEEP_ID = 21;
//    private static final int CARD_TURTLE_ID = 22;
//    private static final int CARD_WOLF_ID = 23;
//    private static final int CARD_WHALE_ID = 24;

//    int mId;
    private AnimalGame mAnimalGame;
    private boolean mCardFound = false;
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
}
