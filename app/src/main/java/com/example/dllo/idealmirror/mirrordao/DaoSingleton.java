package com.example.dllo.idealmirror.mirrordao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;

import com.example.dllo.idealmirror.base.BaseApplication;

/**
 * Created by dllo on 16/4/7.
 */
public class DaoSingleton {
    private static final String DATABASE_NAME = "allmirror.db";
    private volatile static DaoSingleton instance;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Context context;
    private DaoMaster.DevOpenHelper helper;
    private AllMirrorCacheDao allMirrorCacheDao;
    private GoodListCacheDao goodListDao;
    private PlainMirrorDao plainMirrorDao;
    private StoryMirrorDao storyMirrorDao;

    private DaoSingleton() {
        context = BaseApplication.getContext();
    }

    public static DaoSingleton getInstance() {
        if (instance == null) {
            synchronized (DaoSingleton.class) {
                if (instance == null) {
                    instance = new DaoSingleton();
                }
            }
        }
        return instance;
    }

    public DaoMaster.DevOpenHelper getHelper() {
        if (helper == null) {
            helper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
        }
        return helper;
    }

    private SQLiteDatabase getDb() {
        if (db == null) {
            db = getHelper().getWritableDatabase();
        }
        return db;
    }

    private DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(getDb());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }
    public AllMirrorCacheDao getAllMirrorCacheDao() {
        if (allMirrorCacheDao == null) {
            allMirrorCacheDao = getDaoSession().getAllMirrorCacheDao();
        }
        return allMirrorCacheDao;
    }

    public GoodListCacheDao getGoodListCacheDao(){
        if (goodListDao ==null) {
            goodListDao = getDaoSession().getGoodListCacheDao();
        }
        return goodListDao;
    }
    public PlainMirrorDao getPlainMirrorDao(){
        if (plainMirrorDao ==null){
            plainMirrorDao = getDaoSession().getPlainMirrorDao();
        }
        return plainMirrorDao;
    }

   public StoryMirrorDao getStoryMirrorDao(){
       if (storyMirrorDao ==null){
           storyMirrorDao = getDaoSession().getStoryMirrorDao();
       }
       return storyMirrorDao;
   }
}


