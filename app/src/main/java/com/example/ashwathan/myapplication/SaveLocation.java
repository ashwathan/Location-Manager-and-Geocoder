package com.example.ashwathan.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

/**
 * Created by Ashwath A N on 11-Nov-15.
 */
public class SaveLocation {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_MONTH = "month";
    public static final String KEY_YEAR = "year";
    public static final String KEY_LOC = "loc";

    private static final String DATABASE_NAME = "LOCdb";
    private static final String TABLE = "LOCATION";
    private static final int DB_VER = 1;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;




    private static class DbHelper extends SQLiteOpenHelper
    {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DB_VER);
       }



        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + TABLE +
                            " (" +
                            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            KEY_DATE + " INTEGER NOT NULL," +
                            KEY_MONTH + " INTEGER NOT NULL," +
                            KEY_YEAR + " INTEGER NOT NULL," +
                            KEY_LOC + " TEXT NOT NULL);"
            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(db);
        }
    }

    public SaveLocation(Context c)
    {
        ourContext=c;
    }

    public SaveLocation open() throws SQLException
    {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }
    public void createEntry(int date,int month,int year,String add){
        ContentValues cv = new ContentValues();
        cv.put(KEY_DATE,date);
        cv.put(KEY_MONTH,month);
        cv.put(KEY_YEAR,year);
        cv.put(KEY_LOC,add);
        ourDatabase.insert(TABLE, null, cv);
    }
    public void close()
    {
        ourHelper.close();
    }
    public String getData(int day,int month, int year) {
        String query = "SELECT * FROM "+ TABLE + " WHERE "+KEY_DATE + "= " + day + " AND "+ KEY_MONTH +"= "+ month +" AND " + KEY_YEAR + "= " + year;
        String[] columns = new String[] {KEY_ROWID,KEY_DATE, KEY_MONTH, KEY_YEAR, KEY_LOC };
        //Cursor c = ourDatabase.query(TABLE,columns,KEY_DATE + "=11",null,null,null,null);
        String result="";
        try{
            Cursor c =ourDatabase.rawQuery(query, null);
            if(c!=null){
                c.moveToFirst();
                result = "You were in " + c.getString(4);
                return result;
        }

        }
        catch(Exception e)
        {
            return "You have not saved any location on " + day + "/" + (month+1) +"/" + year;
        }
        return null;
    }
}
