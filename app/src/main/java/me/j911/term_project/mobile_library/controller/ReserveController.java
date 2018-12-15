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
        return false;
    }

    @Override
    public ISeat getReservedInfoById(String seatId) {
        return null;
    }

    @Override
    public ISeat[] getAllReservedInfo() {
        String GET_ALL_RESERVED_INFO = "SELECT * FROM RESERVE";
        Cursor cursor = db.rawQuery(GET_ALL_RESERVED_INFO, null);
        Seat[] seat = new Seat[cursor.getCount()];
        while (cursor.moveToNext()) {
            String seatId = cursor.getString(1);
            int accountId = cursor.getInt(2);
            boolean isReserve = cursor.getInt(3) != 0;
            seat[cursor.getPosition()] = new Seat(seatId, accountId, isReserve);
        }
        return seat;
    }

    @Override
    public boolean reserveSeat(String seatId) {
        return false;
    }

    public static ReserveController getInstance(Context context) {
        if (instance == null) {
            instance = new ReserveController(context);
        }

        return instance;
    }
}


