package me.j911.term_project.mobile_library.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import me.j911.term_project.mobile_library.entites.Request;
import me.j911.term_project.mobile_library.helper.DBHelper;
import me.j911.term_project.mobile_library.interfaces.IRequest;
import me.j911.term_project.mobile_library.interfaces.IRequestController;

public class RequestController implements IRequestController {

    private static RequestController instance;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public String dbName = "request.db";
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
    public boolean addRequest(String seatId, String title, String contents) {
        return false;
    }

    @Override
    public boolean isLiked(int requestId) {
        return false;
    }

    @Override
    public boolean like(int requestId) {
        return false;
    }

    @Override
    public boolean unlike(int requestId) {
        return false;
    }

    @Override
    public IRequest getRequestById(int requestId) {
        return new Request();
    }

    @Override
    public IRequest[] getAllRequest() {
        Request[] requests = new Request[10];
        return requests;
    }

    public static RequestController getInstance(Context context) {
        if (instance == null) {
            instance = new RequestController(context);
        }

        return instance;
    }
}


