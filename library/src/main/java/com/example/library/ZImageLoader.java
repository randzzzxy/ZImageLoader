package com.example.library;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

/**
 * @author ZhongXinyu
 * @作用
 */

public class ZImageLoader {
    public static BitMapRequest with(FragmentActivity activity){
        RequestManager.getInstance().setLifeCycle(activity);
        return new BitMapRequest(activity);
    }


}
