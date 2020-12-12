package com.example.library;

import android.content.Context;

/**
 * @author ZhongXinyu
 * @作用
 */

public class ZImageLoader {
    public static BitMapRequest with(Context context){
        return new BitMapRequest(context);
    }


}
