package Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import Models.CounterItem;
import utils.OpenHelper;

/**
 * Created by vivek on 10/05/16.
 */
public class CountersTable {

    public static long InsertCounter(Context context, CounterItem counterItem)
    {
        OpenHelper h = new OpenHelper(context);
        SQLiteDatabase db = h.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("Id", counterItem.mCounterId);
        c.put("CounterName", counterItem.mCounterName);
        c.put("MenuVersion", "0");
        long id = db.insert("Counter", null, c);

        return id;
    }
}
