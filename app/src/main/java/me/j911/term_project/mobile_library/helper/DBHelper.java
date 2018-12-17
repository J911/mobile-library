package me.j911.term_project.mobile_library.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        initializeTables();
    }

    private void initializeTables() {
        db.execSQL("CREATE TABLE ACCOUNT (idx INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, name TEXT, password TEXT);");
        db.execSQL("CREATE TABLE RESERVE (idx INTEGER PRIMARY KEY AUTOINCREMENT, seat_id TEXT, account_id INTEGER, reserved TINYINT(1) DEFAULT 0);");
        db.execSQL("INSERT INTO RESERVE (seat_id, account_id) VALUES('A-1', '')");
        db.execSQL("INSERT INTO RESERVE (seat_id, account_id) VALUES('A-2', '')");
        db.execSQL("INSERT INTO RESERVE (seat_id, account_id) VALUES('A-3', '')");
        db.execSQL("INSERT INTO RESERVE (seat_id, account_id) VALUES('A-4', '')");
        db.execSQL("INSERT INTO RESERVE (seat_id, account_id) VALUES('A-5', '')");
        db.execSQL("INSERT INTO RESERVE (seat_id, account_id) VALUES('A-6', '')");
        db.execSQL("INSERT INTO RESERVE (seat_id, account_id) VALUES('A-7', '')");
        db.execSQL("INSERT INTO RESERVE (seat_id, account_id) VALUES('A-8', '')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

}
