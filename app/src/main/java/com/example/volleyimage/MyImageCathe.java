package com.example.volleyimage;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class MyImageCathe implements ImageCache {
    //android.util.LruCache下的包，非v4包
    LruCache<String, Bitmap> cache;

    public MyImageCathe(Context context) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int max = manager.getMemoryClass();
        //设置缓存大小
        cache = new LruCache<String, Bitmap>(max / 8);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }

}
