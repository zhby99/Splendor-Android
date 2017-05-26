package com.example.boyinzhang.splendorandroid.View;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by boyinzhang on 5/25/17.
 */

public class ViewUtils {
    private Bitmap createBitmap(Bitmap src, Bitmap watermark) {

        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        //create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src, 0, 0, null);
        cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);//在src的右下角画入水印
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return newb;
    }
}
