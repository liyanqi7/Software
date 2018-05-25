package com.example.lyq.software.ui.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lyq on 2018/5/15.
 */

public class SearchSqliteOpenHelper extends SQLiteOpenHelper {

    /**
     * 在添加表或者修改表时没有更新版本，在做了上述操作后数据库版本要加一，每次做了修改都要加一。
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public SearchSqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

//    public SearchSqliteOpenHelper(Context context){
//        super(context,"search.db",null,1);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table sh(_id integer primary key autoincrement,value varchar(60))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
