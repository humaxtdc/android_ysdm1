package com.example.e32contract;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public interface E32Contract {
    /*
     * DB E32
     */
    public static final String AUTHORITY = "com.example.ysdm_bg_app.provider";
    public static final int E32_DB_VERSION = 2;
    public static final String E32_DB_NAME = "E32.db";

    /*
     * table_e32
     */
    public static final String TABLE_E32 = "table_e32";
    public static final String ITEM_E32 = TABLE_E32 + "/#";
    public static Uri URI_E32 = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + TABLE_E32);

    public interface E32Columns extends BaseColumns {
        public static final String AGE = "age";
        public static final String NAME = "name";
    }

    public static final String CREATE_TABLE_E32 =
            "CREATE TABLE " + TABLE_E32 + "( " +
                    E32Columns.AGE + " INTEGER, " +
                    E32Columns.NAME + " TEXT);";
    public static final String MIME_TYPE_DIR_E32 = "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_E32;
    public static final String MIME_TYPE_ITEM_E32 = "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_E32;

    /*
     * table_exx
     */
    public static final String TABLE_EXX = "table_exx";
    public static final String ITEM_EXX = TABLE_EXX + "/#";
    public static Uri URI_EXX = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + TABLE_EXX);

    public interface EXXColumns extends BaseColumns {
        public static final String NAME = "name";
        public static final String LCN = "lcn";
        public static final String TSID = "tsid";
    }

    public static final String CREATE_TABLE_EXX =
            "CREATE TABLE " + TABLE_EXX + "( " +
                    EXXColumns.LCN + " INTEGER, " +
                    EXXColumns.TSID + " INTEGER, " +
                    EXXColumns.NAME + " TEXT);";
    public static final String MIME_TYPE_DIR_EXX = "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_EXX;
    public static final String MIME_TYPE_ITEM_EXX = "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_EXX;
}
