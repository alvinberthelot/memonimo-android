package com.webyousoon.android.memonimo.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by hackorder on 14/04/2015.
 */
public class MemonimoContract {

    // "Content authority" permet de s'adresser au Content Provider
    public static final String CONTENT_AUTHORITY = "com.webyousoon.android.memonimo.app";

    // URI du Content Provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Chemin d'accès aux données
    public static final String PATH_GAME = "game";
    public static final String PATH_TURN = "turn";
    public static final String PATH_GAME_CARD = "game_card";
    public static final String PATH_CARD = "card";

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static final class GameEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GAME).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAME;

        // Nom de la table
        public static final String TABLE_NAME = "game";
        // Colonnes de la table
        public static final String COLUMN_FINISHED = "finished";

        public static Uri buildGameUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class CardEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CARD).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CARD;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CARD;

        // Nom de la table
        public static final String TABLE_NAME = "card";
        // Colonnes de la table
        public static final String COLUMN_LABEL = "label";

        public static Uri buildCardUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class TurnEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TURN).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TURN;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TURN;

        // Nom de la table
        public static final String TABLE_NAME = "turn";
        // Colonnes de la table
        public static final String COLUMN_INDEX_TURN = "index_turn";
        public static final String COLUMN_FIRST_POSITION_CHOOSEN = "first_position_choosen";
        public static final String COLUMN_SECOND_POSITION_CHOOSEN = "second_position_choosen";
        public static final String COLUMN_ID_GAME = "id_game";

        public static Uri buildTurnUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class GameCardEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GAME_CARD).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAME_CARD;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAME_CARD;

        // Nom de la table
        public static final String TABLE_NAME = "gamecard";
        // Colonnes de la table
        public static final String COLUMN_POSITION = "position";
        public static final String COLUMN_ID_GAME = "id_game";
        public static final String COLUMN_ID_CARD = "id_card";

        public static Uri buildGameCardUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
