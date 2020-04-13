package com.example.taskand;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.RenderScript;

public class SQLiteDataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tasks.db";
    public static final String TABLE_NAME= "tasks_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Nom";
    public static final String COL_3 = "Description";
    public static final String COL_4 =  "Date_Debut";
    public static final String COL_5 =  "Date_FIN";
    public static final String COL_6 = "Priorite";
    public static final String COL_7 = "Avancement";

    public SQLiteDataBase(Context context){
        super(context, DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Nom Text, Description Text, Date_Debut Text, Date_FIN Text, Priorite INTEGER, Avanvement INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String Nom, String Description, String Date_Debut, long priorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, Nom);
        contentValues.put(COL_3, Description);
        contentValues.put(COL_4, Date_Debut);
        contentValues.put(COL_6, priorite);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    }

