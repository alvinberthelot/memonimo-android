package com.webyousoon.android.memonimo;

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

    long mIdgame;

    static RestartDialogFragment newInstance(long _idGame) {
        RestartDialogFragment f = new RestartDialogFragment();

        Bundle args = new Bundle();
        args.putLong(ARGUMENT_ID_GAME, _idGame);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIdgame = getArguments().getLong(ARGUMENT_ID_GAME);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.question_new_game)
                .setNegativeButton(R.string.action_go_back_home, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        MemonimoProvider.removeGame (getActivity().getContentResolver(), mIdgame);

                        Intent intent = new Intent(getActivity(), StartActivity.class);
                        startActivity(intent);
                    }
                })
                .setPositiveButton(R.string.action_new_game, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(), GameActivity.class);
                        startActivity(intent);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
