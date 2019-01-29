package com.example.ysdm_bg_app;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class E32ContentProvider extends ContentProvider {
    private static final String TAG = E32ContentProvider.class.getSimpleName();
    private Uri contentUri = Uri.parse("content://com.example.ysdm_bg_app.provider/table_e32");
    private SQLiteDatabase db;

    public E32ContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType() is called");
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert(" + uri.toString() + ", " + values.toString() + ")");
        Uri muri = null;
        long id = db.insert("table_e32", null, values);
        if (id > 0) {
            muri = ContentUris.withAppendedId(contentUri, id);
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
        db = helper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
//        Log.d(TAG, "query(" + uri.toString() + ", " + projection.toString() + ", " + selection.toString() + ")");
        Log.d(TAG, "query(" + uri.toString() + ")");
        Cursor c = db.query("table_e32", projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
