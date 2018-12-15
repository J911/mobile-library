package me.j911.term_project.mobile_library.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import me.j911.term_project.mobile_library.entites.Seat;
import me.j911.term_project.mobile_library.helper.DBHelper;
import me.j911.term_project.mobile_library.interfaces.IReserveController;
import me.j911.term_project.mobile_library.interfaces.ISeat;

public class ReserveController implements IReserveController {
    private static ReserveController instance;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public String dbName = "reserve.db";
    public int dbVersion = 1;
    public String tag = "SQLite";

    public ReserveController(Context context) {
        dbHelper = new DBHelper(context, dbName, null, dbVersion);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(tag, "database error");
        }
    }

    @Override
    public boolean unreserveSeat(String seatId) {
        String UNRESERVE_SEAT = "UPDATE RESERVE SET reserved = 0 WHERE seat_id = '"+seatId+"'";
        db.execSQL(UNRESERVE_SEAT);
        return true;
    }

    @Override
    public Seat getReservedInfoById(String seatId) {
        String GET_ALL_RESERVED = "SELECT * FROM RESERVE WHERE seat_id = '"+seatId+"'";
        Cursor cursor = db.rawQuery(GET_ALL_RESERVED, null);
        if (!cursor.moveToNext()) return null;
        int accountId = cursor.getInt(2);
        boolean isReserve = cursor.getInt(3) != 0;
        Seat seat = new Seat(seatId, accountId, isReserve);

        return seat;
    }

    @Override
    public Seat[] getAllReservedInfo() {
        String GET_ALL_RESERVED_INFO = "SELECT * FROM RESERVE";
        Cursor cursor = db.rawQuery(GET_ALL_RESERVED_INFO, null);
        Seat[] seats = new Seat[cursor.getCount()];
        while (cursor.moveToNext()) {
            String seatId = cursor.getString(1);
            int accountId = cursor.getInt(2);
            boolean isReserve = cursor.getInt(3) != 0;
            seats[cursor.getPosition()] = new Seat(seatId, accountId, isReserve);
        }
        return seats;
    }

    @Override
    public boolean reserveSeat(String seatId, int accountId) {
        String UNRESERVE_SEAT = "UPDATE RESERVE SET reserved = 1, account_id = "+ accountId +" WHERE seat_id = '"+seatId+"'";
        db.execSQL(UNRESERVE_SEAT);
        return true;
    }

    public static ReserveController getInstance(Context context) {
        if (instance == null) {
            instance = new ReserveController(context);
        }

        return instance;
    }
}


