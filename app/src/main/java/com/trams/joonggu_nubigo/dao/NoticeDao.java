package com.trams.joonggu_nubigo.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import com.trams.joonggu_nubigo.dao.Notice;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NOTICE".
*/
public class NoticeDao extends AbstractDao<Notice, Long> {

    public static final String TABLENAME = "NOTICE";

    /**
     * Properties of entity Notice.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Content = new Property(2, String.class, "content", false, "CONTENT");
        public final static Property NoticeType = new Property(3, Integer.class, "noticeType", false, "NOTICE_TYPE");
        public final static Property CreateDate = new Property(4, java.util.Date.class, "createDate", false, "CREATE_DATE");
        public final static Property UpdateDate = new Property(5, java.util.Date.class, "updateDate", false, "UPDATE_DATE");
        public final static Property UserId = new Property(6, long.class, "userId", false, "USER_ID");
    };

    private DaoSession daoSession;


    public NoticeDao(DaoConfig config) {
        super(config);
    }
    
    public NoticeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NOTICE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"TITLE\" TEXT NOT NULL ," + // 1: title
                "\"CONTENT\" TEXT," + // 2: content
                "\"NOTICE_TYPE\" INTEGER," + // 3: noticeType
                "\"CREATE_DATE\" INTEGER," + // 4: createDate
                "\"UPDATE_DATE\" INTEGER," + // 5: updateDate
                "\"USER_ID\" INTEGER NOT NULL );"); // 6: userId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NOTICE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Notice entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindString(2, entity.getTitle());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }
 
        Integer noticeType = entity.getNoticeType();
        if (noticeType != null) {
            stmt.bindLong(4, noticeType);
        }
 
        java.util.Date createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindLong(5, createDate.getTime());
        }
 
        java.util.Date updateDate = entity.getUpdateDate();
        if (updateDate != null) {
            stmt.bindLong(6, updateDate.getTime());
        }
        stmt.bindLong(7, entity.getUserId());
    }

    @Override
    protected void attachEntity(Notice entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Notice readEntity(Cursor cursor, int offset) {
        Notice entity = new Notice( //
            cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // content
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // noticeType
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // createDate
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)), // updateDate
            cursor.getLong(offset + 6) // userId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Notice entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setTitle(cursor.getString(offset + 1));
        entity.setContent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNoticeType(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setCreateDate(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setUpdateDate(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
        entity.setUserId(cursor.getLong(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Notice entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Notice entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getUserDao().getAllColumns());
            builder.append(" FROM NOTICE T");
            builder.append(" LEFT JOIN USER T0 ON T.\"USER_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Notice loadCurrentDeep(Cursor cursor, boolean lock) {
        Notice entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        User users = loadCurrentOther(daoSession.getUserDao(), cursor, offset);
         if(users != null) {
            entity.setUsers(users);
        }

        return entity;    
    }

    public Notice loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Notice> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Notice> list = new ArrayList<Notice>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Notice> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Notice> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
