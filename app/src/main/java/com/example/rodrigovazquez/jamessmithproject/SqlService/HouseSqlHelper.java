/**
 * @author
 * @version
 * @see
 */

package com.example.rodrigovazquez.jamessmithproject.SqlService;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//
public class HouseSqlHelper extends SQLiteOpenHelper {

    //
    private static  final String sqlCreate = "CREATE TABLE Houses (HomeId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Description TEXT, Address TEXT, Price NUM, Active INTEGER)";

    /**
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public HouseSqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Elimina la tabla
        db.execSQL("DROP TABLE IF EXISTS Houses");
        //Crea la nueva tabla
        db.execSQL(sqlCreate);
    }
}
