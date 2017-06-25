package com.android.inputmethod.pinyin.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.android.inputmethod.pinyin.greendao.entity.Scan;

import com.android.inputmethod.pinyin.greendao.ScanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig scanDaoConfig;

    private final ScanDao scanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        scanDaoConfig = daoConfigMap.get(ScanDao.class).clone();
        scanDaoConfig.initIdentityScope(type);

        scanDao = new ScanDao(scanDaoConfig, this);

        registerDao(Scan.class, scanDao);
    }
    
    public void clear() {
        scanDaoConfig.clearIdentityScope();
    }

    public ScanDao getScanDao() {
        return scanDao;
    }

}
