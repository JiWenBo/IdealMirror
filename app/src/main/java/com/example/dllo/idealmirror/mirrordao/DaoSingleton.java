package com.example.dllo.idealmirror.mirrordao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;

import com.example.dllo.idealmirror.base.BaseApplication;

import java.util.List;

/**
 * Created by dllo on 16/4/7.
 * 操作数据库单例模式
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

    /**
     * 私有构造方法
     */
    private DaoSingleton() {
        context = BaseApplication.getContext();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    /**
     * 对外提供一个方法 可以获得DaoSingleton实例
     * @return
     */
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

    public AllMirrorCacheDao getAllMirrorCacheDao() {
        if (allMirrorCacheDao == null) {
            allMirrorCacheDao = daoSession.getAllMirrorCacheDao();
        }
        return allMirrorCacheDao;
    }

    public GoodListCacheDao getGoodListCacheDao() {
        if (goodListDao == null) {
            goodListDao = daoSession.getGoodListCacheDao();
        }
        return goodListDao;
    }

    public PlainMirrorDao getPlainMirrorDao() {
        if (plainMirrorDao == null) {
            plainMirrorDao = daoSession.getPlainMirrorDao();
        }
        return plainMirrorDao;
    }

    public StoryMirrorDao getStoryMirrorDao() {
        if (storyMirrorDao == null) {
            storyMirrorDao = daoSession.getStoryMirrorDao();
        }
        return storyMirrorDao;
    }

    /**
     * 插入菜单数据
     * @param goodListCache 菜单数据
     * @return
     */
    public long insert(GoodListCache goodListCache) {
        return daoSession.getGoodListCacheDao().insert(goodListCache);
    }

    /**
     * 插入菜单数据集合
     * @param goodListCacheList 菜单数据集合
     */
    public void insertGoodList(List<GoodListCache> goodListCacheList) {
        daoSession.getGoodListCacheDao().insertInTx(goodListCacheList);
    }

    /**
     * 根据position删除goodListCache数据
     * @param position key位置
     */
    public void deleteGoodListByKey(long position) {
        daoSession.getGoodListCacheDao().deleteByKey(position);
    }

    /**
     * 删除菜单所有的数据
     */
    public void deleteGoodListAll() {
        daoSession.getGoodListCacheDao().deleteAll();
    }

    /**
     * 查询菜单数据
     * @return 菜单集合
     */
    public List<GoodListCache> queryGoodList() {
        List<GoodListCache> goodListCacheList = daoSession.getGoodListCacheDao().queryBuilder().list();
        return goodListCacheList;
    }

    /**
     * 添加所有类别的数据
     * @param allMirrorCache 所有类别
     * @return
     */
    public long insert(AllMirrorCache allMirrorCache) {
        return daoSession.getAllMirrorCacheDao().insert(allMirrorCache);
    }

    /**
     * 添加所有类别的数据集合
     * @param allMirrorCacheList 所有类别数据集合
     */
    public void insertAllMirror(List<AllMirrorCache> allMirrorCacheList) {
        daoSession.getAllMirrorCacheDao().insertInTx(allMirrorCacheList);
    }

    /**
     * 根据position删除allMirrorCache数据
     * @param position key位置
     */
    public void deleteAllMirrorByKey(long position) {
        daoSession.getAllMirrorCacheDao().deleteByKey(position);
    }

    /**
     * 删除所有类别的所有数据
     */
    public void deleteAllMirrorAll() {
        daoSession.getAllMirrorCacheDao().deleteAll();
    }

    /**
     * 查询所有类别的数据
     * @return 所有类别的数据集合
     */
    public List<AllMirrorCache> queryAllMirror() {
        List<AllMirrorCache> allMirrorCacheList = daoSession.getAllMirrorCacheDao().queryBuilder().list();
        return allMirrorCacheList;
    }

    /**
     * 添加分类的数据
     * @param plainMirror 分类数据
     * @return
     */
    public long insert(PlainMirror plainMirror) {
        return daoSession.getPlainMirrorDao().insert(plainMirror);
    }

    /**
     * 添加分类的数据集合
     * @param plainMirrorList 分类数据的集合
     */
    public void insertPlainMirror(List<PlainMirror> plainMirrorList) {
        daoSession.getPlainMirrorDao().insertInTx(plainMirrorList);
    }

    /**
     * 根据position删除plainMirrorCache数据
     * @param position
     */
    public void deletePlainMirrorByKey(long position) {
        daoSession.getPlainMirrorDao().deleteByKey(position);
    }

    /**
     * 删除分类的所有数据
     */
    public void deletePlainMirrorAll() {
        daoSession.getPlainMirrorDao().deleteAll();
    }

    /**
     * 查询分类的数据集合
     * @return 分类的数据集合
     */
    public List<PlainMirror> queryPlainMirror() {
        List<PlainMirror> plainMirrorList = daoSession.getPlainMirrorDao().queryBuilder().list();
        return plainMirrorList;
    }

    /**
     * 添加分享的数据
     * @param storyMirror 分享的数据
     * @return
     */
    public long insert(StoryMirror storyMirror) {
        return daoSession.getStoryMirrorDao().insert(storyMirror);
    }

    /**
     * 添加分享的数据集合
     * @param storyMirrorList 分享的数据集合
     */
    public void insertStoryMirror(List<StoryMirror> storyMirrorList) {
        daoSession.getStoryMirrorDao().insertInTx(storyMirrorList);
    }

    /**
     * 根据position删除StoryMirrorCache数据
     * @param position
     */
    public void deleteStoryMirrorByKey(long position) {
        daoSession.getStoryMirrorDao().deleteByKey(position);
    }

    /**
     * 删除分享的所有数据
     */
    public void deleteStoryMirrorAll() {
        daoSession.getStoryMirrorDao().deleteAll();
    }

    /**
     * 查询分享的数据集合
     * @return 分享的数据集合
     */
    public List<StoryMirror> queryStoryMirror() {
        List<StoryMirror> storyMirrorList = daoSession.getStoryMirrorDao().queryBuilder().list();
        return storyMirrorList;
    }


}


