package com.example.library.cache;

import android.graphics.Bitmap;

public interface BaseCache {
    /**
     * 存入缓存
     */
    void put(String key, Bitmap value);
    /**
     * 取出缓存
     */
    Bitmap get(String key);
    /**
     * 移除缓存
     */
    void remove(String key);
}
