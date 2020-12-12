package com.example.library;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;


import com.example.library.utils.MD5Utils;

import java.lang.ref.SoftReference;

/**
 * 资源请求
 *
 */
public class BitMapRequest {
    //图片路径
    private String url;
    //控件引用
    private SoftReference<ImageView> imageView;
    //占位图片
    private int id;
    //回调接口
    private RequestListener listener;
    //请求标识
    /**
     * 作为缓存的key
     * 避免图片错位
     */
    private String md5;

    public BitMapRequest(){
    }

    public BitMapRequest load(String url){
        this.url = url;
        this.md5 = MD5Utils.MD5Lower(url);
        return this;
    }

    public BitMapRequest loading(int id){
        this.id = id;
        return this;
    }

    public BitMapRequest listen(RequestListener listener){
        this.listener = listener;
        return this;
    }

    public void into(ImageView imageView){
        imageView.setTag(md5);
        this.imageView = new SoftReference<>(imageView);
        RequestManager.getInstance().addBitMapRequest(this);
    }

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public int getId() {
        return id;
    }

    public RequestListener getListener() {
        return listener;
    }

    public String getMd5() {
        return md5;
    }
}
