package com.example.alicee_pc.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alicee-pc on 7/17/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="BinDetails";
    public static final String TABLE_NAME="Bin_Details";
    public static final String COL_1="B_NAME";
    public static final String COL_2="B_CAPACITY";
    public static final String COL_3="B_ALERT";
    public static final String COL_4="MOB_NO";
    public static final String COL_5="MAILID";
    public static final String COL_6="WARNING";
    public static final String COL_7="CAUTION";
    public static final String COL_8="MACH_NAME";
    public static final String COL_9="MACH_ID";
    public static final String COL_10="UNIT";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE_NAME+"(B_NAME TEXT,B_CAPACITY INTEGER,B_ALERT INTEGER,MOB_NO INTEGER,MAILID TEXT,WARNING INTEGER,CAUTION INTEGER,MACH_NAME TEXT,MACH_ID TEXT PRIMARY KEY" +
                ",UNIT TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String b_name,String b_capacity,String b_alert,String mob_no,String mailid,String warning,String caution,String mach_name,String mach_id,String unit){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,b_name);
        contentValues.put(COL_2,b_capacity);
        contentValues.put(COL_3,b_alert);
        contentValues.put(COL_4,mob_no);
        contentValues.put(COL_5,mailid);
        contentValues.put(COL_6,warning);
        contentValues.put(COL_7,caution);
        contentValues.put(COL_8,mach_name);
        contentValues.put(COL_9,mach_id);
        contentValues.put(COL_10,unit);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return  false;
        else
            return true;

    }
    public Cursor getAllData(){

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return  res;

    }

    public  Cursor getData(String mname){

        SQLiteDatabase db=this.getWritableDatabase();
       /* Cursor res=db.rawQuery("select * from "+TABLE_NAME+" where "+COL_8+"=mname",null);
        return  res;
*/

       String[] args={mname};
        Cursor c=db.query(DatabaseHelper.TABLE_NAME,null,DatabaseHelper.COL_8+"=?",args,null,null,null);
        return  c;

}
/*public Integer DeleteData(String mname){
    SQLiteDatabase db=this.getWritableDatabase();
    return db.delete(TABLE_NAME,"MACH_NAME = ?",new String[] {mname});

}*/
public Integer deleteData(String macid){
    SQLiteDatabase db=this.getWritableDatabase();
    return db.delete(TABLE_NAME,"MACH_ID = ?",new String[] {macid});
}


    }




