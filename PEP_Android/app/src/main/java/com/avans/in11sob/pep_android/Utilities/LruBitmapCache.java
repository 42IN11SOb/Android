package com.avans.in11sob.pep_android.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;

import com.android.volley.toolbox.ImageLoader.ImageCache;


/**
 * Created by Mark on 30-5-2016.
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageCache
{
    private LruBitmapCache(final int maxSize)
    {
        super(maxSize);
    }

    public LruBitmapCache(@NonNull final Context ctx)
    {
        this(getCacheSize(ctx));
    }

    @Override
    protected int sizeOf(String key, Bitmap value)
    {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url)
    {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap)
    {
        put(url, bitmap);
    }

    /**
     * @param context to get the resources from
     *
     * @return a cache size equal to approximately three screens worth of images.
     */
    private static int getCacheSize(@NonNull final Context context)
    {
        final DisplayMetrics displayMetrics = context.getResources().
                getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 3;
    }
}