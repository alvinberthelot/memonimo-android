package com.webyousoon.android.memonimo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.webyousoon.android.memonimo.data.MemonimoContract.GameEntry;
import com.webyousoon.android.memonimo.data.MemonimoContract.CardEntry;
import com.webyousoon.android.memonimo.data.MemonimoContract.TurnEntry;
import com.webyousoon.android.memonimo.data.MemonimoContract.GameCardEntry;
import com.webyousoon.android.memonimo.data.MemonimoContract.PatternEntry;


/**
 * Created by hackorder on 14/04/2015.
 */
public class MemonimoDbHelper extends SQLiteOpenHelper {

    // Version de la base de données
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "memonimo.db";

    public MemonimoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_GAME_TABLE = "CREATE TABLE " + GameEntry.TABLE_NAME + " (" +
                GameEntry._ID + " INTEGER PRIMARY KEY," +
                GameEntry.COLUMN_FINISHED + " INTEGER NOT NULL, " +
                GameEntry.COLUMN_FIRST_POSITION_CHOOSEN + " INTEGER NOT NULL, " +
                GameEntry.COLUMN_SECOND_POSITION_CHOOSEN + " INTEGER NOT NULL, " +
                GameEntry.COLUMN_NUM_ATTEMPT + " INTEGER NOT NULL, " +
                GameEntry.COLUMN_DIFFICULTY + " INTEGER NOT NULL " +
                " );";

        final String SQL_CREATE_PATTERN_TABLE = "CREATE TABLE " + PatternEntry.TABLE_NAME + " (" +
                PatternEntry._ID + " INTEGER PRIMARY KEY," +
                PatternEntry.COLUMN_IMG_ENCODED + " TEXT NOT NULL, " +
                PatternEntry.COLUMN_ID_GAME + " INTEGER, " +
                // Clé étrangère vers la table "game"
                " FOREIGN KEY (" + PatternEntry.COLUMN_ID_GAME + ") REFERENCES " +
                GameEntry.TABLE_NAME + " (" + GameEntry._ID + ") " +
                " );";

        final String SQL_CREATE_CARD_TABLE = "CREATE TABLE " + CardEntry.TABLE_NAME + " (" +
                CardEntry._ID + " INTEGER PRIMARY KEY," +
                CardEntry.COLUMN_LABEL + " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_TURN_TABLE = "CREATE TABLE " + TurnEntry.TABLE_NAME + " (" +
                TurnEntry._ID + " INTEGER PRIMARY KEY," +
                TurnEntry.COLUMN_ID_GAME + " INTEGER NOT NULL, " +
                TurnEntry.COLUMN_INDEX_TURN + " INTEGER NOT NULL, " +
                TurnEntry.COLUMN_FIRST_POSITION_CHOOSEN + " INTEGER, " +
                TurnEntry.COLUMN_SECOND_POSITION_CHOOSEN + " INTEGER, " +
                // Clé étrangère vers la table "game"
                " FOREIGN KEY (" + TurnEntry.COLUMN_ID_GAME + ") REFERENCES " +
                GameEntry.TABLE_NAME + " (" + GameEntry._ID + ") " +
                " );";

        final String SQL_CREATE_GAME_CARD_TABLE = "CREATE TABLE " + GameCardEntry.TABLE_NAME + " (" +
                GameCardEntry._ID + " INTEGER PRIMARY KEY," +
                GameCardEntry.COLUMN_ID_GAME + " INTEGER NOT NULL, " +
                GameCardEntry.COLUMN_ID_CARD + " INTEGER NOT NULL, " +
                GameCardEntry.COLUMN_POSITION + " INTEGER NOT NULL, " +
                GameCardEntry.COLUMN_FOUND + " INTEGER NOT NULL, " +
                GameCardEntry.COLUMN_PLAYER + " INTEGER, " +
                GameCardEntry.COLUMN_ATTEMPT + " INTEGER NOT NULL, " +
                // Clé étrangère vers la table "game"
                " FOREIGN KEY (" + GameCardEntry.COLUMN_ID_GAME + ") REFERENCES " +
                GameEntry.TABLE_NAME + " (" + GameEntry._ID + "), " +
                // Clé étrangère vers la table "card"
                " FOREIGN KEY (" + GameCardEntry.COLUMN_ID_GAME + ") REFERENCES " +
                CardEntry.TABLE_NAME + " (" + CardEntry._ID + ") " +
                " );";

        // Création des tables
        sqLiteDatabase.execSQL(SQL_CREATE_GAME_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CARD_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TURN_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GAME_CARD_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PATTERN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Suppression des tables si changement de version
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GameEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CardEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TurnEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GameCardEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PatternEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
