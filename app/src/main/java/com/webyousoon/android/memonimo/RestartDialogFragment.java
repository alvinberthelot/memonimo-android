package com.webyousoon.android.memonimo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.webyousoon.android.memonimo.data.MemonimoProvider;

/**
 * Created by hackorder on 18/04/2015.
 */
public class RestartDialogFragment extends DialogFragment {

    private static final String LOG_TAG = RestartDialogFragment.class.getSimpleName();

    private static final String ARGUMENT_ID_GAME = "argument_id_game";
    private static final String ARGUMENT_NUM_FAMILY = "argument_num_family";

    long mIdgame;
    int mNumFamily;
    RestartDialogListener mRestartDialogListener;

    public interface RestartDialogListener {
        public void onDialogNegativeClick(DialogFragment dialogFragment);
        public void onDialogPositiveClick(DialogFragment dialogFragment, int numFamily);
    }

    static RestartDialogFragment newInstance(long _idGame, int _numFamily) {
        RestartDialogFragment f = new RestartDialogFragment();

        Bundle args = new Bundle();
        args.putLong(ARGUMENT_ID_GAME, _idGame);
        args.putInt(ARGUMENT_NUM_FAMILY, _numFamily);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Vérification que l'activité appelant ce fragment implements l'interface
        try {
            // Instanciation de l'interface
            mRestartDialogListener = (RestartDialogListener) activity;
        } catch (ClassCastException e) {
            // L'activité n'implemente pas l'interface
            throw new ClassCastException(activity.toString()
                    + " must implement " + RestartDialogFragment.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIdgame = getArguments().getLong(ARGUMENT_ID_GAME);
        mNumFamily = getArguments().getInt(ARGUMENT_NUM_FAMILY);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.question_new_game)
                .setNegativeButton(R.string.action_go_back_home, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Suppression de la partie
                        MemonimoProvider.removeGame(getActivity().getContentResolver(), mIdgame);
                        // Appel l'événement de l'activité
                        mRestartDialogListener.onDialogNegativeClick(RestartDialogFragment.this);
                    }
                })
                .setPositiveButton(R.string.action_new_game, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Suppression de la partie
                        MemonimoProvider.removeGame(getActivity().getContentResolver(), mIdgame);
                        //
                        mRestartDialogListener.onDialogPositiveClick(RestartDialogFragment.this, mNumFamily);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
