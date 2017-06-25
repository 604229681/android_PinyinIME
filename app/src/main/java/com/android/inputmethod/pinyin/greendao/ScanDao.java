package com.android.inputmethod.pinyin.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.android.inputmethod.pinyin.greendao.entity.Scan;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SCAN".
*/
public class ScanDao extends AbstractDao<Scan, Long> {

    public static final String TABLENAME = "SCAN";

    /**
     * Properties of entity Scan.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Code = new Property(1, String.class, "code", false, "CODE");
        public final static Property Time = new Property(2, long.class, "time", false, "TIME");
        public final static Property BackOne = new Property(3, String.class, "backOne", false, "BACK_ONE");
        public final static Property BackTwo = new Property(4, String.class, "backTwo", false, "BACK_TWO");
        public final static Property BackThree = new Property(5, String.class, "backThree", false, "BACK_THREE");
    }


    public ScanDao(DaoConfig config) {
        super(config);
    }
    
    public ScanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SCAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"CODE\" TEXT UNIQUE ," + // 1: code
                "\"TIME\" INTEGER NOT NULL ," + // 2: time
                "\"BACK_ONE\" TEXT," + // 3: backOne
                "\"BACK_TWO\" TEXT," + // 4: backTwo
                "\"BACK_THREE\" TEXT);"); // 5: backThree
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SCAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Scan entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(2, code);
        }
        stmt.bindLong(3, entity.getTime());
 
        String backOne = entity.getBackOne();
        if (backOne != null) {
            stmt.bindString(4, backOne);
        }
 
        String backTwo = entity.getBackTwo();
        if (backTwo != null) {
            stmt.bindString(5, backTwo);
        }
 
        String backThree = entity.getBackThree();
        if (backThree != null) {
            stmt.bindString(6, backThree);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Scan entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(2, code);
        }
        stmt.bindLong(3, entity.getTime());
 
        String backOne = entity.getBackOne();
        if (backOne != null) {
            stmt.bindString(4, backOne);
        }
 
        String backTwo = entity.getBackTwo();
        if (backTwo != null) {
            stmt.bindString(5, backTwo);
        }
 
        String backThree = entity.getBackThree();
        if (backThree != null) {
            stmt.bindString(6, backThree);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public Scan readEntity(Cursor cursor, int offset) {
        Scan entity = new Scan( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // code
            cursor.getLong(offset + 2), // time
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // backOne
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // backTwo
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // backThree
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Scan entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTime(cursor.getLong(offset + 2));
        entity.setBackOne(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setBackTwo(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setBackThree(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Scan entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Scan entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Scan entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}