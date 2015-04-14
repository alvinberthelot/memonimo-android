package com.webyousoon.android.memonimo.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.webyousoon.android.memonimo.data.MemonimoContract.GameEntry;
import com.webyousoon.android.memonimo.data.MemonimoContract.CardEntry;
import com.webyousoon.android.memonimo.data.MemonimoContract.TurnEntry;
import com.webyousoon.android.memonimo.data.MemonimoContract.GameCardEntry;

import java.util.Map;
import java.util.Set;

/**
 * Created by hackorder on 14/04/2015.
 */
public class TestUtilities extends AndroidTestCase {

    private static final String TEST_GAME_FINISHED = "1";

    private static final String TEST_CARD_DOG = "Dog";

    private static final String TEST_TURN_INDEX_TURN = "4";
    private static final String TEST_TURN_FIRST_POSITION_CHOOSEN = "3";
    private static final String TEST_TURN_SECOND_POSITION_CHOOSEN = "5";

    private static final String TEST_GAME_CARD_POSITION = "7";

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    /*
        Création d'un jeu de données pour tester la table "game"
     */
    static ContentValues createGameValues() {
        ContentValues testValues = new ContentValues();
        testValues.put(GameEntry.COLUMN_FINISHED, TEST_GAME_FINISHED);

        return testValues;
    }

    /*
        Création d'un jeu de données pour tester la table "card"
     */
    static ContentValues createCardValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(CardEntry.COLUMN_LABEL, TEST_CARD_DOG);
//        testValues.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, "North Pole");
//        testValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, 64.7488);
//        testValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LONG, -147.353);

        return testValues;
    }

    /*
        Création d'un jeu de données pour tester la table "turn"
     */
    static ContentValues createTurnValues(long _idGame) {
        ContentValues testValues = new ContentValues();
        testValues.put(TurnEntry.COLUMN_INDEX_TURN, TEST_TURN_INDEX_TURN);
        testValues.put(TurnEntry.COLUMN_FIRST_POSITION_CHOOSEN, TEST_TURN_FIRST_POSITION_CHOOSEN);
        testValues.put(TurnEntry.COLUMN_SECOND_POSITION_CHOOSEN, TEST_TURN_SECOND_POSITION_CHOOSEN);
        testValues.put(TurnEntry.COLUMN_ID_GAME, _idGame);

        return testValues;
    }

    /*
        Création d'un jeu de données pour tester la table "game_card"
     */
    static ContentValues createGameCardValues(long _idGame, long _idCard) {
        ContentValues testValues = new ContentValues();
        testValues.put(GameCardEntry.COLUMN_POSITION, TEST_GAME_CARD_POSITION);
        testValues.put(GameCardEntry.COLUMN_ID_GAME, _idGame);
        testValues.put(GameCardEntry.COLUMN_ID_CARD, _idCard);

        return testValues;
    }
}
