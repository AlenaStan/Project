package com.github.alenastan.vkclient;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by lena on 03.12.2014.
 */
public class MyContentProvider extends ContentProvider {

    final String LOG_TAG = "myLogs";

    static final String DB_NAME = "dbVk";
    static final int DB_VERSION = 1;
    static final String FRIENDS_TABLE = "friends";

    static final String FRIEND_ID = "_id";
    static final String FRIEND_NAME = "name";

    static final String DB_CREATE = "create table " + FRIENDS_TABLE + "("
            + FRIEND_ID + " integer primary key autoincrement, "
            + FRIEND_NAME + " text "  + ");";

    static final String AUTHORITY = "com.github.alenastan.providers.FriendsPicker";
    static final String CONTACT_PATH = "friends";

    public static final Uri CONTACT_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + CONTACT_PATH);
    static final String CONTACT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + CONTACT_PATH;
    static final String CONTACT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + CONTACT_PATH;
    static final int URI_FRIENDS = 1;
    static final int URI_FRIENDS_ID = 2;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CONTACT_PATH, URI_FRIENDS);
        uriMatcher.addURI(AUTHORITY, CONTACT_PATH + "/#", URI_FRIENDS_ID);
    }

    DBHelper dbHelper;
    SQLiteDatabase db;

    public boolean onCreate() {
        Log.d(LOG_TAG, "onCreate");
        dbHelper = new DBHelper(getContext());
        return true;
    }
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(LOG_TAG, "query, " + uri.toString());

        switch (uriMatcher.match(uri)) {
            case URI_FRIENDS:
                Log.d(LOG_TAG, "URI_CONTACTS");

                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = FRIEND_NAME + " ASC";
                }
                break;
            case URI_FRIENDS_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id);

                if (TextUtils.isEmpty(selection)) {
                    selection = FRIEND_ID + " = " + id;
                } else {
                    selection = selection + " AND " + FRIEND_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(FRIENDS_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(),
                CONTACT_CONTENT_URI);
        return cursor;
    }

    public Uri insert(Uri uri, ContentValues values) {
        Log.d(LOG_TAG, "insert, " + uri.toString());
        if (uriMatcher.match(uri) != URI_FRIENDS)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(FRIENDS_TABLE, null, values);
        Uri resultUri = ContentUris.withAppendedId(CONTACT_CONTENT_URI, rowID);

        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "delete, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_FRIENDS:
                Log.d(LOG_TAG, "URI_CONTACTS");
                break;
            case URI_FRIENDS_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = FRIEND_ID + " = " + id;
                } else {
                    selection = selection + " AND " + FRIEND_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(FRIENDS_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d(LOG_TAG, "update, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_FRIENDS:
                Log.d(LOG_TAG, "URI_CONTACTS");

                break;
            case URI_FRIENDS_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = FRIEND_ID + " = " + id;
                } else {
                    selection = selection + " AND " + FRIEND_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.update(FRIENDS_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    public String getType(Uri uri) {
        Log.d(LOG_TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_FRIENDS:
                return CONTACT_CONTENT_TYPE;
            case URI_FRIENDS_ID:
                return CONTACT_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            ContentValues cv = new ContentValues();
            for (int i = 1; i <= 2; i++) {
                cv.put(FRIEND_NAME, "name " + i);
                db.insert(FRIENDS_TABLE, null, cv);
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }




}
