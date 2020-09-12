package com.trongphu.myapplication.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trongphu.myapplication.db.EngineerSQLiteHelper;


public class EngineerProvider extends ContentProvider {
    private static final String AUTHORITY = "com.ddona.hello.provider.EngineerProvider";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/engineer");
    private static final UriMatcher URI_MATCHER;

    private static final int URI_TABLE = 1001;

    private static final int URI_ID = 1002;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, "engineer", URI_TABLE);// code table (trỏ đến table )
        URI_MATCHER.addURI(AUTHORITY, "engineer/#", URI_ID);//code trỏ đến ID (# là một số bất kỳ) sẽ trỏ đến ID như vậy
    }

    private EngineerSQLiteHelper sqLiteHelper;

    @Override
    public boolean onCreate() {
        sqLiteHelper = new EngineerSQLiteHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] profections
            , @Nullable String selections
            , @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(EngineerSQLiteHelper.ENGINEER_TABLE);
        if (URI_MATCHER.match(uri) == URI_ID) {
            String id = uri.getPathSegments().get(1);
            builder.appendWhere("id=" + id);
        }
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cursor = builder.query(db, profections, selections, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (URI_MATCHER.match(uri) != URI_TABLE) {
            throw new IllegalArgumentException(" i don't know you...please try with another! You too stupid ^^");
        }
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        long rowId = db.insert(EngineerSQLiteHelper.ENGINEER_TABLE, null, contentValues);
        if (rowId > 0) {
            Uri result = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(result, null);
            return result;
        }
        throw new IllegalArgumentException("Fail to insert: " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
