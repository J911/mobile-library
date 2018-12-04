package me.j911.term_project.mobile_library.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import me.j911.term_project.mobile_library.helper.DBHelper;
import me.j911.term_project.mobile_library.interfaces.IAccountController;

public class AccountController implements IAccountController {

    private static AccountController instance;

    private boolean isLoggedIn;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public String dbName = "account.db";
    public int dbVersion = 1;
    public String tag = "SQLite";

    public AccountController(Context context) {
        isLoggedIn = false;
        dbHelper = new DBHelper(context, dbName, null, dbVersion);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(tag, "database error");
        }
    }

    @Override
    public int signIn(int stdId, String stdPassword) {
        String VALID_CHECK_SQL = "SELECT * FROM ACCOUNT WHERE id = " + stdId + ";";
        Cursor cursor = db.rawQuery(VALID_CHECK_SQL, null);
        if (!cursor.moveToNext()) return 404;
        String password = cursor.getString(3);
        if (!stdPassword.equals(password)) return 403;
        return 200;
    }

    @Override
    public int signUp(String stdName, int stdId, String stdPassword) {
        String CONFLICT_CHECK_SQL = "SELECT * FROM ACCOUNT WHERE id = " + stdId + ";";
        Cursor cursor = db.rawQuery(CONFLICT_CHECK_SQL, null);
        if (cursor.moveToNext()) return 409;
        String INSERT_ACCOUNT_SQL = "INSERT INTO ACCOUNT (id, name, password) VALUES("+stdId+", '"+ stdName +"', '"+ stdPassword +"');";
        db.execSQL(INSERT_ACCOUNT_SQL);
        return 201;
    }

    @Override
    public int signOut() {
        return 0;
    }

    @Override
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static AccountController getInstance (Context context) {
        if (instance == null) {
            instance = new AccountController(context);
        }
        return instance;
    }


}
