package com.example.library;

import android.graphics.Bitmap;

/**
 * 图片请求接口
 */
public interface RequestListener {
    //请求成功
    void onSuccess(Bitmap bitmap);
    //请求失败
    void onFaild();
}
