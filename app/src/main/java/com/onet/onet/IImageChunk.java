/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.onet.onet;

import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import android.graphics.Rect;

class IImageChunk
{
    public final int NO_COLOR = 1;
    public final int TRANSPARENT_COLOR = 0;
    public int[] mColor;
    public int[] mDivX;
    public int[] mDivY;
    public Rect mPaddings;
    
    IImageChunk() {
        super();
        this.mPaddings = new Rect();
    }
    
    private static void checkDivCount(final int n) {
        if (n == 0 || (n & 0x1) != 0x0) {
            throw new RuntimeException("invalid nine-patch: " + n);
        }
    }
    
    public static IImageChunk deserialize(final byte[] array) {
        final ByteBuffer order = ByteBuffer.wrap(array).order(ByteOrder.nativeOrder());
        IImageChunk ninePatchChunk;
        if (order.get() == 0) {
            ninePatchChunk = null;
        }
        else {
            ninePatchChunk = new IImageChunk();
            ninePatchChunk.mDivX = new int[order.get()];
            ninePatchChunk.mDivY = new int[order.get()];
            ninePatchChunk.mColor = new int[order.get()];
            checkDivCount(ninePatchChunk.mDivX.length);
            checkDivCount(ninePatchChunk.mDivY.length);
            order.getInt();
            order.getInt();
            ninePatchChunk.mPaddings.left = order.getInt();
            ninePatchChunk.mPaddings.right = order.getInt();
            ninePatchChunk.mPaddings.top = order.getInt();
            ninePatchChunk.mPaddings.bottom = order.getInt();
            order.getInt();
            readIntArray(ninePatchChunk.mDivX, order);
            readIntArray(ninePatchChunk.mDivY, order);
            readIntArray(ninePatchChunk.mColor, order);
        }
        return ninePatchChunk;
    }
    
    private static void readIntArray(final int[] array, final ByteBuffer byteBuffer) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = byteBuffer.getInt();
        }
    }
}