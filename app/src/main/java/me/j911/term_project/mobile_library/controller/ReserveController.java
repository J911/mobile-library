package me.j911.term_project.mobile_library.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import me.j911.term_project.mobile_library.entites.Request;
import me.j911.term_project.mobile_library.helper.DBHelper;
import me.j911.term_project.mobile_library.interfaces.IRequest;
import me.j911.term_project.mobile_library.interfaces.IRequestController;

public class ReserveController {
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

    public static ReserveController getInstance(Context context) {
        if (instance == null) {
            instance = new ReserveController(context);
        }

        return instance;
    }
}


