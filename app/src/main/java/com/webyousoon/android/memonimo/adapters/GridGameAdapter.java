package com.webyousoon.android.memonimo.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.webyousoon.android.memonimo.data.ProviderUtilities;
import com.webyousoon.android.memonimo.model.GameCard;
import com.webyousoon.android.memonimo.R;
import com.webyousoon.android.memonimo.data.MemonimoContract;

import java.util.List;

/**
 * Created by hackorder on 17/04/2015.
 */
public class GridGameAdapter extends BaseAdapter {

    private Context mContext;
    private List<GameCard> mGameCardList;

    private static class CardGameViewHolder {
        public final ImageView animalView;

        public CardGameViewHolder(View _view) {
            animalView = (ImageView) _view.findViewById(R.id.gi_game_animal);
        }
    }


    public GridGameAdapter(Context context, List<GameCard> _gameCardList) {
        this.mContext = context;
        this.mGameCardList = _gameCardList;
    }

    @Override
    public int getCount() {
        return mGameCardList.size();
    }

    @Override
    public GameCard getItem(int _position) {
        return mGameCardList.get(_position);
    }

    @Override
    public long getItemId(int _position) {
//        ((GameCard) getItem(_position)).
        return 0;
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {

        // Récupération du layout approprié
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item_game, _parent, false);

        CardGameViewHolder viewHolder = new CardGameViewHolder(view);
        view.setTag(viewHolder);

        GameCard gameCard = getItem(_position);

//        String idAnimal = "" + gameCard.getAnimalGame().getCode();
//        viewHolder.animalidView.setText(idAnimal);

        viewHolder.animalView.setImageDrawable(getCardDrawable(gameCard, mContext.getResources()));

        return view;


//        ImageView imageView;
//
//
//
//
//
//        if (convertView == null) {
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
////            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
////            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//
//        Resources resources = mContext.getResources();
//        imageView.setImageDrawable(getCardDrawable(mCardGameList.get(position), resources));
////        imageView.setImageResource(getCardDrawable(mCardGameList.get(position)));
//        return imageView;
    }

//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//
//        //
//        CardGameViewHolder viewHolder = (CardGameViewHolder) view.getTag();
//
//        String idAnimal = cursor.getString(cursor.getColumnIndex(MemonimoContract.GameCardEntry.COLUMN_ID_CARD));
//
////        viewHolder.animalidView.setText("tutu");
//        viewHolder.animalidView.setText(idAnimal);
//
////        viewHolder.positionView.setText(cursor.getColumnIndex(MemonimoContract.GameCardEntry.COLUMN_POSITION));
////
//
//        GameCard gameCard = ProviderUtilities.convertGameCardCursorToGameCardModel(cursor);
//
//        viewHolder.animalView.setImageDrawable(getCardDrawable(gameCard, mContext.getResources()));
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//
//        // Récupération du layout approprié
//        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_game, parent, false);
//
//        CardGameViewHolder viewHolder = new CardGameViewHolder(view);
//        view.setTag(viewHolder);
//
//        return view;
//    }

//    private ImageView getCardView() {
//        ImageView imageView;
//
//        if (convertView == null) {
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
////            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
////            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//
//        Resources resources = mContext.getResources();
//        imageView.setImageDrawable(getCardDrawable(mCardGameList.get(position), resources));
////        imageView.setImageResource(getCardDrawable(mCardGameList.get(position)));
//        return imageView;
//    }

    public static LayerDrawable getCardDrawable(GameCard _cardGame, Resources _resources ) {

        Drawable[] layers = new Drawable[2];
        layers[0] = _resources.getDrawable(getInsideDrawable(_cardGame));
        layers[1] = _resources.getDrawable(getBorderDrawable(_cardGame));
        return new LayerDrawable(layers);
    }

    public static int getBorderDrawable(GameCard _cardGame) {
        if (_cardGame.isFoundPlayer1()) {
            return R.drawable.card_player1;
        } else if (_cardGame.isFoundPlayer2()) {
            return R.drawable.card_player2;
        } else {
            return R.drawable.card_neutral;
        }
    }

    public static int getInsideDrawable(GameCard _cardGame) {
        if (_cardGame.isCardFound() || _cardGame.isAttempt()) {
            return getAnimalDrawable(_cardGame.getAnimalGame());
        } else {
            return R.drawable.card_hidden;
        }
    }

    private static int getAnimalDrawable(GameCard.AnimalGame _animalGame) {
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
