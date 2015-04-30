package com.webyousoon.android.memonimo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class MemonimoUtilities {

    public static final String INTENT_EXTRA_ID_GAME = "intent_extra_id_game";
    public static final String INTENT_EXTRA_MODE_GAME = "intent_extra_mode_game";

    /**
     * Fonction retournant un Bitmap à partir d'une image encodée en Base64
     * @param _input image en codée en Base64
     * @return
     */
    public static Bitmap decodeBase64(String _input)
    {
        byte[] decodedByte = Base64.decode(_input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}
