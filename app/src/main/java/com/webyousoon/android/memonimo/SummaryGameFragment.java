package com.webyousoon.android.memonimo;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.webyousoon.android.memonimo.data.DbUtilities;
import com.webyousoon.android.memonimo.data.MemonimoContract.GameEntry;
import com.webyousoon.android.memonimo.model.Game;


public class SummaryGameFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = SummaryGameFragment.class.getSimpleName();

    public static final String BUNDLE_ARG_URI_GAME = "bundle_arg_uri_game";
    private static final String INSTANCE_STATE_ID_GAME = "instance_state_id_game";


    private Uri mUri;

    private static final int LOADER = 0;

    private static final String[] SUMMARY_GAME_COLUMNS = {
            GameEntry.TABLE_NAME + "." + GameEntry._ID,
            GameEntry.TABLE_NAME + "." + GameEntry.COLUMN_FINISHED,
            GameEntry.TABLE_NAME + "." + GameEntry.COLUMN_DIFFICULTY,
            GameEntry.TABLE_NAME + "." + GameEntry.COLUMN_NUM_ATTEMPT
//            GameEntry.TABLE_NAME + "." + GameEntry._ID,
//            GameEntry.TABLE_NAME + "." + GameEntry._ID,
//            GameEntry.TABLE_NAME + "." + GameEntry._ID,
            // This works because the WeatherProvider returns location data joined with
            // weather data, even though they're stored in two different tables.
//            WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING
    };

    // Index des valeurs via SUMMARY_GAME_COLUMNS
    public static final int COL_GAME_ID = 0;
    public static final int COL_GAME_FINISHED = 1;
    public static final int COL_GAME_DIFFICULTY = 2;
    public static final int COL_GAME_NUM_ATTEMPT = 3;
//    public static final int COL_WEATHER_DESC = 2;
//    public static final int COL_WEATHER_MAX_TEMP = 3;
//    public static final int COL_WEATHER_MIN_TEMP = 4;
//    public static final int COL_WEATHER_HUMIDITY = 5;
//    public static final int COL_WEATHER_PRESSURE = 6;
//    public static final int COL_WEATHER_WIND_SPEED = 7;
//    public static final int COL_WEATHER_DEGREES = 8;
//    public static final int COL_WEATHER_CONDITION_ID = 9;

    // Views
    private TextView mTextNumGame;
    private TextView mTextNumFamilyFound;
    private TextView mTextNumAttempt;
    private TextView mTextDifficulty;
    private ImageView mImgDifficulty;


    private Game mGame;

    public SummaryGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(SummaryGameFragment.BUNDLE_ARG_URI_GAME);
        }

        View rootView = inflater.inflate(R.layout.fragment_summary_game, container, false);
        mTextNumGame = (TextView) rootView.findViewById(R.id.txt_numGame);
        mTextNumFamilyFound = (TextView) rootView.findViewById(R.id.txt_numFamilyFound);
        mTextNumAttempt = (TextView) rootView.findViewById(R.id.txt_numAttempt);
        mTextDifficulty = (TextView) rootView.findViewById(R.id.txt_difficulty);
        mImgDifficulty = (ImageView) rootView.findViewById(R.id.img_difficulty);



//        // Récupération de l'identifiant de la partie envoyée par l'activitée
//        long idGame = getArguments().getLong(GameActivity.BUNDLE_GAME_ID);
//        // Récupération de la partie via le Provider
//        mGame = MemonimoProvider.restoreGame(getActivity().getContentResolver(), idGame);
//
//        TextView idGameView = (TextView) rootView.findViewById(R.id.idGame);
//        idGameView.setText("PARTIE #" + mGame.getId());
//
//        mTextNumGame = (TextView) rootView.findViewById(R.id.numFamily);
//        mTextNumGame.setText("Familles trouvées : " + mGame.getNumFamilyFound() + " / " + mGame.getNumFamily());

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    null,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor _cursor) {
        if (_cursor != null && _cursor.moveToFirst()) {
            updateFragment(_cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }

    private void updateFragment(Cursor _cursor) {

        mTextNumGame.setText("PARTIE #" + _cursor.getLong(COL_GAME_ID));

        boolean isFinished = DbUtilities.getBooleanValue(_cursor.getString(COL_GAME_FINISHED));
        mTextDifficulty.setText("FINIE : " + isFinished);
    }
}
