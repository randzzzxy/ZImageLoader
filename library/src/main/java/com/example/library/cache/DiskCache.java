package com.example.library.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author ZhongXinyu
 * @作用
 */

public class DiskCache implements BaseCache {
    private static final String CACHE_PATH= "/storage/emulated/0/Android/data/com.example.zimageloader/cache/";
    @Override
    public void put(String key, Bitmap value) {
        try {
            File file=new File(CACHE_PATH,key);
            //通过得到文件的父文件,判断父文件是否存在
            File parentFile = file.getParentFile();
            if (!parentFile.exists()){
                parentFile.mkdirs();
            }
            Log.d("1366", parentFile.getAbsolutePath());
            Log.d("1366", ""+parentFile.exists());
            //把图片保存至本地
            value.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Bitmap get(String key) {
        File file=new File(CACHE_PATH,key);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(String key) {

    }
}
