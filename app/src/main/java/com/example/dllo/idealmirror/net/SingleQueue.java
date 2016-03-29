package com.example.dllo.idealmirror.net;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.dllo.idealmirror.base.BaseApplication;


/**
 * Created by dllo on 16/3/29.
 */
public class SingleQueue {
    private static SingleQueue singleQueue;
    private RequestQueue queue;//创建请求队列
    //第一步：构造方法私有化，没有对象可以new、
    private SingleQueue(){
        queue = Volley.newRequestQueue(BaseApplication.getContext());
    }
    //第三布，提供方法，将自己暴露出去
    public static SingleQueue getInstance(){
        if (singleQueue==null){//为了快，节约时间
            /*小括号里为一个类，判断里面有没有线程，所有的类都可以放到这里*/
            synchronized (SingleQueue.class){//线程锁，能保证大括号的内部只有一个线程
                if (singleQueue == null){
                    singleQueue = new SingleQueue();
                }

            }

        }
        return singleQueue;
    }
    public RequestQueue getQueue(){
        return queue;
    }
}

