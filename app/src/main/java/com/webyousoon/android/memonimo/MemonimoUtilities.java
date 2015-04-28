package com.webyousoon.android.memonimo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by hackorder on 17/04/2015.
 */
public class MemonimoUtilities {

    public static final String INTENT_EXTRA_ID_GAME = "intent_extra_id_game";
    public static final String INTENT_EXTRA_NUM_FAMILY = "intent_extra_num_family";


    public static Bitmap decodeBase64(String _input)
    {
        byte[] decodedByte = Base64.decode(_input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}
