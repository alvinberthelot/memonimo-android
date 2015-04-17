package com.webyousoon.android.memonimo.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.webyousoon.android.memonimo.CardGame;
import com.webyousoon.android.memonimo.R;

import java.util.List;

/**
 * Created by hackorder on 01/04/2015.
 */
public class GridMemoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<CardGame> mCardGameList;

    public GridMemoryAdapter(Context c, List<CardGame> _cardGameList) {
        mContext = c;
        mCardGameList = _cardGameList;
    }

    public int getCount() {
        return mCardGameList.size();
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
//            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }


        Resources resources = mContext.getResources();
        imageView.setImageDrawable(getCardDrawable(mCardGameList.get(position), resources));
//        imageView.setImageResource(getCardDrawable(mCardGameList.get(position)));
        return imageView;
    }

    public static LayerDrawable getCardDrawable(CardGame _cardGame, Resources _resources ) {

        Drawable[] layers = new Drawable[2];
        layers[0] = _resources.getDrawable(getInsideDrawable(_cardGame));
        layers[1] = _resources.getDrawable(getBorderDrawable(_cardGame));
        return new LayerDrawable(layers);
    }

    public static int getBorderDrawable(CardGame _cardGame) {
        if (_cardGame.isFoundPlayer1()) {
            return R.drawable.card_player1;
        } else if (_cardGame.isFoundPlayer2()) {
            return R.drawable.card_player2;
        } else {
            return R.drawable.card_neutral;
        }
    }

    public static int getInsideDrawable(CardGame _cardGame) {
        if (_cardGame.isCardFound() || _cardGame.isAttempt()) {
            return getAnimalDrawable(_cardGame.getAnimalGame());
        } else {
            return R.drawable.card_hidden;
        }
    }

    private static int getAnimalDrawable(CardGame.AnimalGame _animalGame) {
        switch (_animalGame) {
            case ALLIGATOR: return R.drawable.card_alligator;
            case BEAVER: return R.drawable.card_beaver;
            case BIRDY: return R.drawable.card_birdy;
            case BUFFALO: return R.drawable.card_buffalo;
            case CAT: return R.drawable.card_cat;
            case COW: return R.drawable.card_cow;
            case ELEPHANT: return R.drawable.card_elephant;
            case FISH: return R.drawable.card_fish;
            case FOX: return R.drawable.card_fox;
            case GIRAFFE: return R.drawable.card_giraffe;
            case GOOSE: return R.drawable.card_goose;
            case HORSE: return R.drawable.card_horse;
            case IGUANA: return R.drawable.card_iguana;
            case JELLYFISH: return R.drawable.card_jellyfish;
            case KOALA: return R.drawable.card_koala;
            case LION: return R.drawable.card_lion;
            case MONKEY: return R.drawable.card_monkey;
            case PHEASANT: return R.drawable.card_pheasant;
            case PIG: return R.drawable.card_pig;
            case RABBIT: return R.drawable.card_rabbit;
            case SHEEP: return R.drawable.card_sheep;
            case TURTLE: return R.drawable.card_turtle;
            case WOLF: return R.drawable.card_wolf;
            case WHALE: return R.drawable.card_whale;
            // TODO exception
            default: return 0;
        }
    }
}
