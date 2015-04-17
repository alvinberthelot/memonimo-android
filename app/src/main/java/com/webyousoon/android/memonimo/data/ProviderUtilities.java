package com.webyousoon.android.memonimo.data;

import android.content.ContentValues;

import com.webyousoon.android.memonimo.CardGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hackorder on 17/04/2015.
 */
public class ProviderUtilities {

    public static ContentValues[] convertGameCard(List<CardGame> _cardGameList) {

        List<ContentValues> values = new ArrayList<ContentValues>();

        int index = 0;

        for (CardGame cardGame : _cardGameList) {
            ContentValues value = new ContentValues();
            value.put(MemonimoContract.GameCardEntry.COLUMN_POSITION, index);
//            value.put(MemonimoContract.GameCardEntry., index);
//            testValues.put(MemonimoContract.GameCardEntry.COLUMN_ID_GAME, _idGame);
//            testValues.put(MemonimoContract.GameCardEntry.COLUMN_ID_CARD, cardGame.getAnimalGame());

            values.add(value);

            index ++;
        }

        return values.toArray(new ContentValues[values.size()]);
    }
}
