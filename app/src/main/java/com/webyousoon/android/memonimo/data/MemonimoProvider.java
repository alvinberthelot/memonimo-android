package com.webyousoon.android.memonimo.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.webyousoon.android.memonimo.data.MemonimoContract.GameEntry;
import com.webyousoon.android.memonimo.data.MemonimoContract.CardEntry;
import com.webyousoon.android.memonimo.data.MemonimoContract.TurnEntry;
import com.webyousoon.android.memonimo.data.MemonimoContract.GameCardEntry;


/**
 * Created by hackorder on 14/04/2015.
 */
public class MemonimoProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MemonimoDbHelper mMemonimoDbHelper;

    static final int CODE_GAME = 100;
//    static final int WEATHER_WITH_LOCATION = 101;
//    static final int WEATHER_WITH_LOCATION_AND_DATE = 102;
    static final int CODE_CARD = 200;
    static final int CODE_TURN = 300;
    static final int CODE_GAME_CARD = 400;


    @Override
    public boolean onCreate() {
        mMemonimoDbHelper = new MemonimoDbHelper(getContext());
        return true;
    }

    /*
        Students: Here's where you'll code the getType function that uses the UriMatcher.  You can
        test this by uncommenting testGetType in TestProvider.

     */
    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
//            case WEATHER_WITH_LOCATION_AND_DATE:
//                return MemonimoContract.WeatherEntry.CONTENT_ITEM_TYPE;
//            case WEATHER_WITH_LOCATION:
//                return MemonimoContract.WeatherEntry.CONTENT_TYPE;
            case CODE_GAME:
                return GameEntry.CONTENT_TYPE;
            case CODE_CARD:
                return CardEntry.CONTENT_TYPE;
            case CODE_TURN:
                return TurnEntry.CONTENT_TYPE;
            case CODE_GAME_CARD:
                return GameCardEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    /*
        Méthode effectuant toutes les opérations nécessaires pour mettre à jour la base
     */
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        // Récupération de la base SQLite
        final SQLiteDatabase db = mMemonimoDbHelper.getWritableDatabase();

        int returnCount = 0;
        //
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CODE_GAME:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
//                        normalizeDate(value);
                        long _id = db.insert(GameEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);

                return returnCount;

            case CODE_CARD:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
//                        normalizeDate(value);
                        long _id = db.insert(CardEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);

                return returnCount;

            case CODE_TURN:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
//                        normalizeDate(value);
                        long _id = db.insert(TurnEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);

                return returnCount;

            case CODE_GAME_CARD:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
//                        normalizeDate(value);
                        long _id = db.insert(GameCardEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);

                return returnCount;

            default:
                return super.bulkInsert(uri, values);
        }
    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mMemonimoDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case CODE_GAME: {
//                normalizeDate(values);
                long _id = db.insert(GameEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = GameEntry.buildGameUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CODE_CARD: {
//                normalizeDate(values);
                long _id = db.insert(CardEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = CardEntry.buildCardUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CODE_TURN: {
//                normalizeDate(values);
                long _id = db.insert(TurnEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = TurnEntry.buildTurnUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CODE_GAME_CARD: {
//                normalizeDate(values);
                long _id = db.insert(GameCardEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = GameCardEntry.buildGameCardUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        db.close();

        return returnUri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor cursor;


        // Récupération de la base SQLite
        final SQLiteDatabase db = mMemonimoDbHelper.getReadableDatabase();

        switch (sUriMatcher.match(uri)) {
//            // "weather/*/*"
//            case WEATHER_WITH_LOCATION_AND_DATE:
//            {
//                retCursor = getWeatherByLocationSettingAndDate(uri, projection, sortOrder);
//                break;
//            }
//            // "weather/*"
//            case WEATHER_WITH_LOCATION: {
//                retCursor = getWeatherByLocationSetting(uri, projection, sortOrder);
//                break;
//            }



            case CODE_GAME: {
                // Requête de récupération de données via la table
                cursor = db.query(
                        GameEntry.TABLE_NAME, // Table
                        projection, // Colonnes interogées
                        selection, // Colonnes pour la condition WHERE
                        selectionArgs, // Valeurs pour la condition WHERE
                        null, // Colonnes pour le GROUP BY
                        null, // Colonnes pour le filtre
                        sortOrder // Tri
                );
                break;
            }
            case CODE_CARD: {
                // Requête de récupération de données via la table
                cursor = db.query(
                        CardEntry.TABLE_NAME, // Table
                        projection, // Colonnes interogées
                        selection, // Colonnes pour la condition WHERE
                        selectionArgs, // Valeurs pour la condition WHERE
                        null, // Colonnes pour le GROUP BY
                        null, // Colonnes pour le filtre
                        sortOrder // Tri
                );
                break;
            }
            case CODE_TURN: {
                // Requête de récupération de données via la table
                cursor = db.query(
                        TurnEntry.TABLE_NAME, // Table
                        projection, // Colonnes interogées
                        selection, // Colonnes pour la condition WHERE
                        selectionArgs, // Valeurs pour la condition WHERE
                        null, // Colonnes pour le GROUP BY
                        null, // Colonnes pour le filtre
                        sortOrder // Tri
                );
                break;
            }
            case CODE_GAME_CARD: {
                // Requête de récupération de données via la table
                cursor = db.query(
                        GameCardEntry.TABLE_NAME, // Table
                        projection, // Colonnes interogées
                        selection, // Colonnes pour la condition WHERE
                        selectionArgs, // Valeurs pour la condition WHERE
                        null, // Colonnes pour le GROUP BY
                        null, // Colonnes pour le filtre
                        sortOrder // Tri
                );
                break;
            }


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // Récupération de la base SQLite
        final SQLiteDatabase db = mMemonimoDbHelper.getReadableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case CODE_GAME:
//                normalizeDate(values);
                rowsUpdated = db.update(
                        GameEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case CODE_CARD:
//                normalizeDate(values);
                rowsUpdated = db.update(
                        CardEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case CODE_TURN:
//                normalizeDate(values);
                rowsUpdated = db.update(
                        TurnEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case CODE_GAME_CARD:
//                normalizeDate(values);
                rowsUpdated = db.update(
                        GameCardEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // Récupération de la base SQLite
        final SQLiteDatabase db = mMemonimoDbHelper.getReadableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";

        switch (match) {
            case CODE_GAME:
                rowsDeleted = db.delete(GameEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_CARD:
                rowsDeleted = db.delete(CardEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_TURN:
                rowsDeleted = db.delete(TurnEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_GAME_CARD:
                rowsDeleted = db.delete(GameCardEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    /*
        Students: Here is where you need to create the UriMatcher. This UriMatcher will
        match each URI to the WEATHER, WEATHER_WITH_LOCATION, WEATHER_WITH_LOCATION_AND_DATE,
        and LOCATION integer constants defined above.  You can test this by uncommenting the
        testUriMatcher test within TestUriMatcher.
     */
    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MemonimoContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MemonimoContract.PATH_GAME, CODE_GAME);
//        matcher.addURI(authority, MemonimoContract.PATH_WEATHER + "/*", WEATHER_WITH_LOCATION);
//        matcher.addURI(authority, MemonimoContract.PATH_WEATHER + "/*/#", WEATHER_WITH_LOCATION_AND_DATE);

        matcher.addURI(authority, MemonimoContract.PATH_CARD, CODE_CARD);

        matcher.addURI(authority, MemonimoContract.PATH_TURN, CODE_TURN);

        matcher.addURI(authority, MemonimoContract.PATH_GAME_CARD, CODE_GAME_CARD);

        return matcher;
    }
}
