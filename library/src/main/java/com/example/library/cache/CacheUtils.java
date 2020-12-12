package com.example.library.cache;

import android.graphics.Bitmap;

/**
 * @author ZhongXinyu
 * @作用
 */

public class CacheUtils implements BaseCache{
    MemoryCache memoryCache;
    DiskCache diskCache;

    public CacheUtils() {
        this.memoryCache = new MemoryCache();
        this.diskCache = new DiskCache();
    }

    @Override
    public void put(String key, Bitmap value) {
        memoryCache.put(key,value);
        diskCache.put(key,value);
    }

    @Override
    public Bitmap get(String key) {
        Bitmap bitmap = null;
        bitmap = memoryCache.get(key);
        if(bitmap != null){
            return bitmap;
        }
        bitmap = diskCache.get(key);
        if(bitmap != null){
            return bitmap;
        }
        return null;
    }

    @Override
    public void remove(String key) {

    }
}
