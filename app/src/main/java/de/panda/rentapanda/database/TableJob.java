package de.panda.rentapanda.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author Marcin
 */
public class TableJob {
    public static final String TABLE_NAME = "jobs";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CUSTOMER_NAME = "customer_name";
    public static final String COLUMN_DISTANCE = "distance";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_EXTRAS = "extras";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_ORDER_TIME = "order_time";
    public static final String COLUMN_PAYMENT = "payment";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_RECURRENCY = "recurrency";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LON = "lon";
    public static final String COLUMN_CODE = "postal_code";
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_STATUS = "status";

    private static final String TABLE_CREATE = "create table " + TABLE_NAME
            + "("
            + COLUMN_ID + " text unique, "
            + COLUMN_CUSTOMER_NAME + " text, "
            + COLUMN_DISTANCE + " text, "
            + COLUMN_DATE + " long, "
            + COLUMN_EXTRAS + " text, "
            + COLUMN_DURATION + " int, "
            + COLUMN_ORDER_TIME + " text, "
            + COLUMN_PAYMENT + " text, "
            + COLUMN_PRICE + " text, "
            + COLUMN_RECURRENCY + " int, "
            + COLUMN_CITY + " text, "
            + COLUMN_LAT + " text, "
            + COLUMN_LON + " text, "
            + COLUMN_CODE + " int, "
            + COLUMN_STREET + " text, "
            + COLUMN_STATUS + " text"
            + ")";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);
    }

    public static void onUpdate(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}
