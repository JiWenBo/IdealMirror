package com.example.dllo.idealmirror.mirrordao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.dllo.idealmirror.mirrordao.AllMirrorCache;
import com.example.dllo.idealmirror.mirrordao.GoodListCache;
import com.example.dllo.idealmirror.mirrordao.PlainMirror;
import com.example.dllo.idealmirror.mirrordao.StoryMirror;

import com.example.dllo.idealmirror.mirrordao.AllMirrorCacheDao;
import com.example.dllo.idealmirror.mirrordao.GoodListCacheDao;
import com.example.dllo.idealmirror.mirrordao.PlainMirrorDao;
import com.example.dllo.idealmirror.mirrordao.StoryMirrorDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig allMirrorCacheDaoConfig;
    private final DaoConfig goodListCacheDaoConfig;
    private final DaoConfig plainMirrorDaoConfig;
    private final DaoConfig storyMirrorDaoConfig;

    private final AllMirrorCacheDao allMirrorCacheDao;
    private final GoodListCacheDao goodListCacheDao;
    private final PlainMirrorDao plainMirrorDao;
    private final StoryMirrorDao storyMirrorDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        allMirrorCacheDaoConfig = daoConfigMap.get(AllMirrorCacheDao.class).clone();
        allMirrorCacheDaoConfig.initIdentityScope(type);

        goodListCacheDaoConfig = daoConfigMap.get(GoodListCacheDao.class).clone();
        goodListCacheDaoConfig.initIdentityScope(type);

        plainMirrorDaoConfig = daoConfigMap.get(PlainMirrorDao.class).clone();
        plainMirrorDaoConfig.initIdentityScope(type);

        storyMirrorDaoConfig = daoConfigMap.get(StoryMirrorDao.class).clone();
        storyMirrorDaoConfig.initIdentityScope(type);

        allMirrorCacheDao = new AllMirrorCacheDao(allMirrorCacheDaoConfig, this);
        goodListCacheDao = new GoodListCacheDao(goodListCacheDaoConfig, this);
        plainMirrorDao = new PlainMirrorDao(plainMirrorDaoConfig, this);
        storyMirrorDao = new StoryMirrorDao(storyMirrorDaoConfig, this);

        registerDao(AllMirrorCache.class, allMirrorCacheDao);
        registerDao(GoodListCache.class, goodListCacheDao);
        registerDao(PlainMirror.class, plainMirrorDao);
        registerDao(StoryMirror.class, storyMirrorDao);
    }
    
    public void clear() {
        allMirrorCacheDaoConfig.getIdentityScope().clear();
        goodListCacheDaoConfig.getIdentityScope().clear();
        plainMirrorDaoConfig.getIdentityScope().clear();
        storyMirrorDaoConfig.getIdentityScope().clear();
    }

    public AllMirrorCacheDao getAllMirrorCacheDao() {
        return allMirrorCacheDao;
    }

    public GoodListCacheDao getGoodListCacheDao() {
        return goodListCacheDao;
    }

    public PlainMirrorDao getPlainMirrorDao() {
        return plainMirrorDao;
    }

    public StoryMirrorDao getStoryMirrorDao() {
        return storyMirrorDao;
    }

}
