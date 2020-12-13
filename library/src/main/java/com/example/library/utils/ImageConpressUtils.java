package com.example.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author ZhongXinyu
 * @作用
 */

public class ImageConpressUtils {
    /**
     * 采样率优化
     * @return
     */
    public  static Bitmap decodeSampleBitmapFromStream(URL url, int reqWidth, int reqHeight){
        if(reqHeight == 0 || reqWidth == 0){
            return null;
        }
        InputStream is = null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
            BitmapFactory.Options options= new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            BitmapFactory.decodeStream(is,null,options);
            is.close();
            connection.disconnect();
            connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
            //计算采样率
            options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);

            options.inJustDecodeBounds=false;
            Bitmap bitmap = BitmapFactory.decodeStream(is,null,options);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public  static Bitmap decodeSampleBitmapFromFile(File file, int reqWidth, int reqHeight){
        final BitmapFactory.Options options= new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(file.getAbsolutePath(),options);

        //计算采样率
        options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds=false;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
        return bitmap;
    }

    /**
     * 获取采样率
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //获取图片的宽和高
        int width = options.outWidth;
        int height=options.outHeight;
        int inSampleSize=1;

        if (height>reqHeight ||  width>reqWidth){
            final int halfHeight=height/2;
            final int halfWidth=width/2;

            //计算最大的采样率，采样率为2的指数
            while ((halfHeight/inSampleSize)>=reqHeight && (halfWidth/inSampleSize)>=reqWidth){
                inSampleSize *=2;
            }
        }

        return inSampleSize;
    }
}
