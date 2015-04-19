package com.webyousoon.android.memonimo.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.webyousoon.android.memonimo.model.Game;
import com.webyousoon.android.memonimo.model.GameCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hackorder on 17/04/2015.
 */
public class ProviderUtilities {

    private static final String LOG_TAG = ProviderUtilities.class.getSimpleName();

    private static final String VALUE_FALSE = "0";
    private static final String VALUE_TRUE = "1";


    public static boolean getBooleanValue(String _value) {

        boolean booleanReturned = false;

        if (null != _value && _value.equalsIgnoreCase(VALUE_TRUE)) {
            booleanReturned = true;
        }

        return booleanReturned;
    }

    public static ContentValues convertGameModelToGameValues(Game _game) {

        ContentValues value = new ContentValues();
        value.put(MemonimoContract.GameEntry.COLUMN_FINISHED, _game.isFinished());
        value.put(MemonimoContract.GameEntry.COLUMN_FIRST_POSITION_CHOOSEN, _game.getFirstPositionChosen());
        value.put(MemonimoContract.GameEntry.COLUMN_SECOND_POSITION_CHOOSEN, _game.getSecondPositionChosen());

        return value;
    }

    public static Game convertGameCursorToGameModel(Cursor _cursor) {

        Game game = new Game(
            _cursor.getLong(_cursor.getColumnIndex(MemonimoContract.GameEntry._ID)),
            getBooleanValue(_cursor.getString(_cursor.getColumnIndex(MemonimoContract.GameEntry.COLUMN_FINISHED))),
            _cursor.getInt(_cursor.getColumnIndex(MemonimoContract.GameEntry.COLUMN_FIRST_POSITION_CHOOSEN)),
            _cursor.getInt(_cursor.getColumnIndex(MemonimoContract.GameEntry.COLUMN_SECOND_POSITION_CHOOSEN))
        );

        return game;
    }

    public static ContentValues[] convertGameModelToGameCardValues(Game _game) {

        List<ContentValues> values = new ArrayList<ContentValues>();

        int index = 0;

        for (GameCard cardGame : _game.getGameCardList()) {
            ContentValues value = new ContentValues();
            value.put(MemonimoContract.GameCardEntry.COLUMN_POSITION, index);
            value.put(MemonimoContract.GameCardEntry.COLUMN_ID_GAME, _game.getId());
            value.put(MemonimoContract.GameCardEntry.COLUMN_ID_CARD, cardGame.getAnimalGame().getCode());
            value.put(MemonimoContract.GameCardEntry.COLUMN_FOUND, cardGame.isCardFound());
            value.put(MemonimoContract.GameCardEntry.COLUMN_ATTEMPT, cardGame.isAttempt());

            values.add(value);

            index ++;
        }

        return values.toArray(new ContentValues[values.size()]);
    }

    public static GameCard convertGameCardCursorToGameCardModel(Cursor _cursor) {

        GameCard gameCard = new GameCard(
                _cursor.getInt(_cursor.getColumnIndex(MemonimoContract.GameCardEntry.COLUMN_ID_CARD)),
                getBooleanValue(_cursor.getString(_cursor.getColumnIndex(MemonimoContract.GameCardEntry.COLUMN_FOUND))),
                getBooleanValue(_cursor.getString(_cursor.getColumnIndex(MemonimoContract.GameCardEntry.COLUMN_PLAYER))),
                getBooleanValue(_cursor.getString(_cursor.getColumnIndex(MemonimoContract.GameCardEntry.COLUMN_PLAYER))),
                getBooleanValue(_cursor.getString(_cursor.getColumnIndex(MemonimoContract.GameCardEntry.COLUMN_ATTEMPT)))
        );



        return gameCard;
    }
}