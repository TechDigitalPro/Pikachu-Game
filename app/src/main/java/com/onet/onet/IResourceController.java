/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.onet.onet;

import android.graphics.drawable.NinePatchDrawable;
import android.graphics.NinePatch;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.IOException;
import android.util.Log;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.content.Context;

public class IResourceController {

    public static Bitmap getAssertBitmap(final Context context, final String s) {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(s));
        } catch (IOException ex) {
            Log.e("ResourceManager", "Assert\u4e2d" + s + "\u4e0d\u5b58\u5728");
            return null;
        }
    }

    public static Drawable getAssertDrawable(final Context context, final String s) {
        try {
            final Object o = new BitmapDrawable(BitmapFactory.decodeStream(context.getAssets().open(s)));
            return (Drawable) o;
        } catch (IOException ex) {
            Log.e("ResourceManager", "Assert\u4e2d" + s + "\u4e0d\u5b58\u5728");
            final Object o = null;
            return (Drawable) o;
        }
    }

    public static Drawable getAssertDrawable(final Context context, final String s, final float n, final float n2) {
        while (true) {
            try {
                Bitmap decodeStream = BitmapFactory.decodeStream(context.getAssets().open(s));
                if (decodeStream != null) {
                    final Matrix matrix = new Matrix();
                    matrix.postScale(n / decodeStream.getWidth(), n2 / decodeStream.getHeight());
                    final Bitmap bitmap = Bitmap.createBitmap(decodeStream, 0, 0, decodeStream.getWidth(), decodeStream.getHeight(), matrix, true);
                    final Object o = new BitmapDrawable(bitmap);
                    return (Drawable) o;
                }
            } catch (IOException ex) {
                Log.e("ResourceManager", "Assert\u4e2d" + s + "\u4e0d\u5b58\u5728");
            }
            final Object o = null;
            return (Drawable) o;
        }
    }

    public static Drawable getAssertNinePatch(final Context context, final String s) {
        Object o;
        try {
            final Bitmap decodeStream = BitmapFactory.decodeStream(context.getAssets().open(s));
            final byte[] ninePatchChunk = decodeStream.getNinePatchChunk();
            if (!NinePatch.isNinePatchChunk(ninePatchChunk)) {
                o = null;
            } else {
                o = new NinePatchDrawable(context.getResources(), decodeStream, ninePatchChunk, IImageChunk.deserialize(ninePatchChunk).mPaddings, (String) null);
            }
        } catch (IOException ex) {
            Log.e("ResourceManager", "Assert\u4e2d" + s + "\u4e0d\u5b58\u5728");
            o = null;
        }
        return (Drawable) o;
    }
}
