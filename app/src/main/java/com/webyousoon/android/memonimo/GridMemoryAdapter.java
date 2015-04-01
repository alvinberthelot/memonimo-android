package com.webyousoon.android.memonimo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by hackorder on 01/04/2015.
 */
public class GridMemoryAdapter extends BaseAdapter {

    private Context mContext;

    public GridMemoryAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.carte_alligator,
            R.drawable.carte_poussin,
            R.drawable.carte_vache,
            R.drawable.carte_alligator,
            R.drawable.carte_poussin,
            R.drawable.carte_vache,
            R.drawable.carte_alligator,
            R.drawable.carte_poussin,
            R.drawable.carte_vache,
            R.drawable.carte_alligator,
            R.drawable.carte_poussin,
            R.drawable.carte_vache
    };
}
