package com.webyousoon.android.memonimo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.webyousoon.android.memonimo.MemonimoUtilities;
import com.webyousoon.android.memonimo.R;
import com.webyousoon.android.memonimo.data.MemonimoContract;

public class ListGameAdapter extends CursorAdapter {


    private static class SummaryGameViewHolder {
        public final ImageView mImageViewPattern;
        public final ImageView mImageViewDifficulty;
        public final TextView mTextViewTitle;

        public SummaryGameViewHolder(View view) {
            mImageViewPattern = (ImageView) view.findViewById(R.id.li_summary_game_pattern);
            mImageViewDifficulty = (ImageView) view.findViewById(R.id.li_summary_game_difficulty);
            mTextViewTitle = (TextView) view.findViewById(R.id.li_summary_game_title);
        }
    }


    public ListGameAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //
        SummaryGameViewHolder viewHolder = (SummaryGameViewHolder) view.getTag();

        String imgEncoded = cursor.getString(cursor.getColumnIndex(MemonimoContract.GameEntry.COLUMN_PATTERN));
        if (null != imgEncoded) {
            Bitmap bitmap = MemonimoUtilities.decodeBase64(imgEncoded);
            BitmapDrawable backgroundDrawable = new BitmapDrawable(bitmap);
            backgroundDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            viewHolder.mImageViewPattern.setImageDrawable(backgroundDrawable);
        }

        String title = "Partie #" + cursor.getLong(cursor.getColumnIndex(MemonimoContract.GameEntry._ID));
        viewHolder.mTextViewTitle.setTypeface(MemonimoUtilities.getCustomFont(context.getAssets()));
        viewHolder.mTextViewTitle.setText(title);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        // Récupération du layout approprié
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_game, parent, false);

        SummaryGameViewHolder viewHolder = new SummaryGameViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    public long getIdItem(int _position) {
        Cursor cursor = (Cursor) getItem(_position);
        return cursor.getLong(cursor.getColumnIndex(MemonimoContract.GameEntry._ID));
    }
}
