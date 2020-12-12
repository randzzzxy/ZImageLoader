package com.example.library.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import androidx.annotation.NonNull;

/**
 * @author ZhongXinyu
 * @作用
 */

public class MemoryCache implements BaseCache{
    private LruCache<String, Bitmap> lruCache;
    //直接使用jdk提供的实现类

    public MemoryCache() {
        //限制缓存大小
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;
        lruCache = new LruCache<String,Bitmap>((int) maxMemory){
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    @Override
    public void put(String key, Bitmap value) {
        lruCache.put(key,value);
    }

    @Override
    public Bitmap get(String key) {
        if(key==null||"".equals(key)){
            return null;
        }
        System.out.println(lruCache);
        Bitmap bitmap = lruCache.get(key);
        return bitmap;
    }

    @Override
    public void remove(String key) {

    }
}
