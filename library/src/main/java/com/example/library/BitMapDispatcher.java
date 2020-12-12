package com.example.library;

/**
 * @author ZhongXinyu
 * @作用
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;


import com.example.library.cache.CacheUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 从请求队列获取对象，请求图片线程
 */
public class BitMapDispatcher implements Runnable {
    //请求队列
    private LinkedBlockingQueue<BitMapRequest> requestQueue;
    //切换主线程
    private Handler handler = new Handler(Looper.getMainLooper());
    //缓存
    CacheUtils cacheUtils = new CacheUtils();

    public BitMapDispatcher(LinkedBlockingQueue<BitMapRequest> queue){
        this.requestQueue = queue;
    }

    //进行网络请求图片操作
    @Override
    public void run() {
        try {
            BitMapRequest request = requestQueue.take();
            showLoadingImage(request);
            //请求图片
            Bitmap bitmap = requestBitMap(request);
            if(request.getListener() != null){
                if(bitmap == null){
                    request.getListener().onFaild();
                }else{
                    request.getListener().onSuccess(bitmap);
                }
            }
            showImageView(request,bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //显示获取到的图片
    private void showImageView(BitMapRequest request, Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if(bitmap != null && imageView != null && request.getMd5().equals(imageView.getTag())){
            handler.post(new Runnable() {
                @Override
                public void run() {
                        imageView.setImageBitmap(bitmap);
                    }

            });
        }

    }

    //将图片获取到内存
    private Bitmap requestBitMap(BitMapRequest request) {
        Bitmap bitmap = null;
        bitmap = cacheUtils.get(request.getMd5());
        if(bitmap != null){
            return bitmap;
        }
        bitmap = getFromNet(request);
        if(bitmap != null){
            cacheUtils.put(request.getMd5(),bitmap);
        }
        return bitmap;
    }

    private Bitmap getFromNet(BitMapRequest request) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            URL url = new URL(request.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 显示占位图片
     */
    private void showLoadingImage(BitMapRequest request) {
        if(request.getId() > 0 && request.getImageView() != null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    request.getImageView().setImageResource(request.getId());
                }
            });
        }

    }
}
