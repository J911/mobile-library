package me.j911.term_project.mobile_library.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import me.j911.term_project.mobile_library.helper.DBHelper;
import me.j911.term_project.mobile_library.interfaces.IRequest;

public class RequestController implements IRequest {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public String dbName = "account.db";
    public int dbVersion = 1;
    public String tag = "SQLite";

    public RequestController(Context context) {
        dbHelper = new DBHelper(context, dbName, null, dbVersion);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(tag, "database error");
        }
    }

    @Override
    public int getReservedStdId() {
        return 0;
    }

    @Override
    public int getStdLike() {
        return 0;
    }

    @Override
    public boolean addLike() {
        return false;
    }

}
