package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

    public OpenHelper(Context context) {
        super(context, "Mess.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Counter(Id INTEGER PRIMARY KEY , CounterName TEXT, MenuVersion INTEGER)");

        db.execSQL("CREATE TABLE Menu(MenuId INTEGER PRIMARY KEY AUTOINCREMENT, CounterId INTEGER, Day TEXT, StartTime DATETIME, EndTime DATETIME, MenuType TEXT, Cost DOUBLE, Version INTEGER )");

        db.execSQL("CREATE TABLE Item(Id INTEGER PRIMARY KEY AUTOINCREMENT, MenuId INTEGER, Name TEXT, Type TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
