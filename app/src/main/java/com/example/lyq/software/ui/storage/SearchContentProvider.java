package com.example.lyq.software.ui.storage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by lyq on 2018/5/15.
 */

public class SearchContentProvider extends ContentProvider{

    private SearchSqliteOpenHelper searchSqliteOpenHelper;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        searchSqliteOpenHelper = new SearchSqliteOpenHelper(getContext(),"search.db",null,1);
        db = searchSqliteOpenHelper.getReadableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = db.query("sh",projection,selection,selectionArgs,sortOrder,null,null);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        db.insert("sh",null,values);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        db.delete("sh",selection,selectionArgs);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
