package com.example.ysdm_bg_app;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import com.example.e32contract.E32Contract;

class E32SQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = E32SQLiteHelper.class.getSimpleName();

    public E32SQLiteHelper(Context context) {
        super(context, E32Contract.E32_DB_NAME, null, E32Contract.E32_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(E32Contract.CREATE_TABLE_E32);
        db.execSQL(E32Contract.CREATE_TABLE_EXX);
        Log.d(TAG, "onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade(version from " + oldVersion + " to " + newVersion + ")");
        db.execSQL("DROP TABLE IF EXISTS " + E32Contract.TABLE_E32);
        db.execSQL("DROP TABLE IF EXISTS " + E32Contract.TABLE_EXX);
        onCreate(db);
    }
}

public class E32ContentProvider extends ContentProvider {
    private static final String TAG = E32ContentProvider.class.getSimpleName();

    private SQLiteDatabase db;

    static final UriMatcher uriMatcher;
    private static final int MATCHER_TABLE_E32 = 1;
    private static final int MATCHER_ITEM_E32 = 2;
    private static final int MATCHER_TABLE_EXX = 3;
    private static final int MATCHER_ITEM_EXX = 4;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(E32Contract.AUTHORITY, E32Contract.TABLE_E32, MATCHER_TABLE_E32);
        uriMatcher.addURI(E32Contract.AUTHORITY, E32Contract.ITEM_E32, MATCHER_ITEM_E32);
        uriMatcher.addURI(E32Contract.AUTHORITY, E32Contract.TABLE_EXX, MATCHER_TABLE_EXX);
        uriMatcher.addURI(E32Contract.AUTHORITY, E32Contract.ITEM_EXX, MATCHER_ITEM_EXX);
    }

    public E32ContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete(" + uri.toString() + ")");

        int count = 0;
        String table, sel;
        switch (uriMatcher.match(uri)) {
            case MATCHER_ITEM_E32:
                table = E32Contract.TABLE_E32;
                break;

            case MATCHER_ITEM_EXX:
                table = E32Contract.TABLE_EXX;
                break;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri.toString());
        }
        sel = BaseColumns._ID + " = " + uri.getPathSegments().get(1);
        sel += !TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "";
        count = db.delete(table, sel, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType(" + uri.toString() + ")");

        switch (uriMatcher.match(uri)) {
            case MATCHER_TABLE_E32:
                return E32Contract.MIME_TYPE_DIR_E32;

            case MATCHER_ITEM_E32:
                return E32Contract.MIME_TYPE_ITEM_E32;

            case MATCHER_TABLE_EXX:
                return E32Contract.MIME_TYPE_DIR_EXX;

            case MATCHER_ITEM_EXX:
                return E32Contract.MIME_TYPE_ITEM_EXX;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri.toString());
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert(" + uri.toString() + ", " + values.toString() + ")");
        Uri muri = null;
        long id = 0;
        switch (uriMatcher.match(uri)) {
            case MATCHER_TABLE_E32:
                id = db.insert(E32Contract.TABLE_E32, null, values);
                if (id > 0) {
                    muri = ContentUris.withAppendedId(E32Contract.URI_E32, id);
                }
                break;

            case MATCHER_TABLE_EXX:
                id = db.insert(E32Contract.TABLE_EXX, null, values);
                if (id > 0) {
                    muri = ContentUris.withAppendedId(E32Contract.URI_EXX, id);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri.toString());
        }

        if (muri != null) {
            Log.d(TAG, "inserted: " + muri.toString());
            getContext().getContentResolver().notifyChange(muri, null);
        }
        return muri;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate() is called");
        Context context = getContext();
        E32SQLiteHelper helper = new E32SQLiteHelper(context);

        /*
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = helper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query(" + uri.toString() + ")");

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String table;
        switch (uriMatcher.match(uri)) {
            case MATCHER_TABLE_E32:
                qb.setTables(E32Contract.TABLE_E32);
                break;

            case MATCHER_ITEM_E32:
                qb.setTables(E32Contract.TABLE_E32);
                qb.appendWhere(BaseColumns._ID + "=" + uri.getPathSegments().get(1));
                break;

            case MATCHER_TABLE_EXX:
                qb.setTables(E32Contract.TABLE_EXX);
                break;

            case MATCHER_ITEM_EXX:
                qb.setTables(E32Contract.TABLE_EXX);
                qb.appendWhere(BaseColumns._ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri.toString());
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case MATCHER_TABLE_E32:
                count = db.update(E32Contract.TABLE_E32, values, selection, selectionArgs);
                break;

            case MATCHER_ITEM_E32:
                count = db.update(E32Contract.TABLE_E32, values, BaseColumns._ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND(" +selection + ')' : ""), selectionArgs);
                break;

            case MATCHER_TABLE_EXX:
                count = db.update(E32Contract.TABLE_EXX, values, selection, selectionArgs);
                break;

            case MATCHER_ITEM_EXX:
                count = db.update(E32Contract.TABLE_EXX, values, BaseColumns._ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND(" +selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
