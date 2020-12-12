package com.example.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.WindowManager;

import java.io.InputStream;

/**
 * @author ZhongXinyu
 * @作用
 */

public class ImageConpressUtils {
    /**
     * 采样率优化
     * @return
     */
    public  static Bitmap decodeSampleBitmapFromStream(InputStream is,int reqWidth,int reqHeight){
        final BitmapFactory.Options options= new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeStream(is,null,options);

        //计算采样率
        options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeStream(is,null,options);
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
