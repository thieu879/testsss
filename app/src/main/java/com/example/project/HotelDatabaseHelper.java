package com.example.project;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class HotelDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hotel_manager.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_HOTEL = "hotel";

    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_ADDRESS = "address";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_ROOM_COUNT = "room_count";

    public HotelDatabaseHelper(MainActivity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_HOTEL + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT,"
                + COL_ADDRESS + " TEXT,"
                + COL_DESCRIPTION + " TEXT,"
                + COL_ROOM_COUNT + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTEL);
        onCreate(db);
    }

    public long insertHotel(HotelModel hotel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, hotel.getName());
        values.put(COL_ADDRESS, hotel.getAddress());
        values.put(COL_DESCRIPTION, hotel.getDescription());
        values.put(COL_ROOM_COUNT, hotel.getRoomCount());
        long id = db.insert(TABLE_HOTEL, null, values);
        db.close();
        return id;
    }

    public List<HotelModel> getAllHotels() {
        List<HotelModel> hotelList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_HOTEL + " ORDER BY " + COL_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HotelModel hotel = new HotelModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ROOM_COUNT))
                );
                hotelList.add(hotel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hotelList;
    }

    public int updateHotel(HotelModel hotel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, hotel.getName());
        values.put(COL_ADDRESS, hotel.getAddress());
        values.put(COL_DESCRIPTION, hotel.getDescription());
        values.put(COL_ROOM_COUNT, hotel.getRoomCount());
        int rows = db.update(TABLE_HOTEL, values, COL_ID + " = ?", new String[]{String.valueOf(hotel.getId())});
        db.close();
        return rows;
    }

    public int deleteHotel(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_HOTEL, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public HotelModel getHotelById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HOTEL, null, COL_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            HotelModel hotel = new HotelModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ROOM_COUNT))
            );
            cursor.close();
            db.close();
            return hotel;
        }
        if (cursor != null) cursor.close();
        db.close();
        return null;
    }
}

