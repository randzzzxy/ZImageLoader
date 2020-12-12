package com.example.library;

/**
 * @author ZhongXinyu
 * @作用
 */

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 管理请求队列
 */
public class RequestManager implements LifecycleObserver {
    /**
     * 单例模式
     */
    private volatile static RequestManager instance;
    /**
     * 请求队列
     */
    private LinkedBlockingQueue<BitMapRequest> requestQueue;

    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPool;

    /**
     * 获取活动对象
     */
    FragmentActivity activity;


    private RequestManager(){
        createThradPool();
    }


    public void setLifeCycle(FragmentActivity activity){
        this.activity = activity;
        activity.getLifecycle().addObserver(this);
    }

    private void createThradPool() {
        requestQueue = new LinkedBlockingQueue<>();
        int core = Runtime.getRuntime().availableProcessors();
        threadPool = new ThreadPoolExecutor(core * 2,Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

    /**
     * 双重校验锁
     */
    public static RequestManager getInstance() {
        if(instance == null){
            synchronized (RequestManager.class){
                if(instance == null){
                    instance = new RequestManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加请求到请求队列
     * @param bitMapRequest 请求
     */
    public void addBitMapRequest(BitMapRequest bitMapRequest){
        if(!requestQueue.contains(bitMapRequest)){
            requestQueue.add(bitMapRequest);
            BitMapDispatcher dispatcher = new BitMapDispatcher(requestQueue);
            threadPool.execute(dispatcher);
        }
    }

    /**
     *当活动销毁时回收资源
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        threadPool.shutdownNow();
        requestQueue.clear();
        activity = null;
    }
}
