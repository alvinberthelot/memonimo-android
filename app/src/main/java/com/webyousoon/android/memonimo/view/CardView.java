package com.webyousoon.android.memonimo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CardView extends View {

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(0);
        paint.setColor(0xff101010);

        // Dessin d'un circle
        canvas.drawOval(new RectF(0, 0, 60, 60), paint);
    }
}
