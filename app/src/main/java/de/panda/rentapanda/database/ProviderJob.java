package de.panda.rentapanda.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Marcin
 */
public class ProviderJob extends ContentProvider {
    public static final int JOBS = 1;
    public static final int JOB_ID = 2;

    public static final String AUTHORITY = "de.panda.rentapanda.job.provider";
    public static final String BASE_PATH = "jobs";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String[] available = {TableJob.COLUMN_STATUS, TableJob.COLUMN_STREET, TableJob.COLUMN_CODE, TableJob.COLUMN_LON, TableJob.COLUMN_CITY,
            TableJob.COLUMN_CUSTOMER_NAME, TableJob.COLUMN_DATE, TableJob.COLUMN_DISTANCE, TableJob.COLUMN_DURATION, TableJob.COLUMN_EXTRAS,
            TableJob.COLUMN_ID, TableJob.COLUMN_LAT, TableJob.COLUMN_PAYMENT, TableJob.COLUMN_PRICE, TableJob.COLUMN_RECURRENCY, TableJob.COLUMN_ORDER_TIME};

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, JOBS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", JOB_ID);
    }

    private DatabaseHelper database;

    @Override
    public boolean onCreate() {
        Log.e("test", "oncreate");
        database = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        checkColumns(projection);

        queryBuilder.setTables(TableJob.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case JOBS:
                break;
            case JOB_ID:
                queryBuilder.appendWhere(TableJob.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = database.getWritableDatabase();
        long id;

        switch (uriMatcher.match(uri)) {
            case JOBS:
            case JOB_ID:
                id = db.insertWithOnConflict(TableJob.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsDeleted;

        switch (uriMatcher.match(uri)) {
            case JOBS:
                rowsDeleted = db.delete(TableJob.TABLE_NAME, selection, selectionArgs);
                break;
            case JOB_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(TableJob.TABLE_NAME, TableJob.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(TableJob.TABLE_NAME, TableJob.COLUMN_ID + "=" + id + " AND " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsUpdated;

        switch (uriMatcher.match(uri)) {
            case JOBS:
                rowsUpdated = db.update(TableJob.TABLE_NAME, values, selection, selectionArgs);
                break;
            case JOB_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(TableJob.TABLE_NAME, values, TableJob.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = db.update(TableJob.TABLE_NAME, values, TableJob.COLUMN_ID + "=" + id + " AND " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = database.getWritableDatabase();

        int count = 0;

        switch (uriMatcher.match(uri)) {
            case JOBS:
            case JOB_ID:
                db.beginTransaction();
                for (ContentValues value : values) {
                    db.insertWithOnConflict(TableJob.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                    count++;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    /**
     * Checks if specified columns are available in the database.
     * If {@code projection} contains unavailable columns method will throw {@code IllegalArgumentException}.
     *
     * @param projection Projections columns.
     */
    private void checkColumns(String[] projection) {
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
