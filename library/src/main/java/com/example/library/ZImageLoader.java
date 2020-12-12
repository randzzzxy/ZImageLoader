package com.example.library;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

/**
 * @author ZhongXinyu
 * @作用
 */

public class ZImageLoader {
    public static BitMapRequest with(Lifecycle lifecycle){
        RequestManager.getInstance().setLifeCycle(lifecycle);
        return new BitMapRequest();
    }


}
