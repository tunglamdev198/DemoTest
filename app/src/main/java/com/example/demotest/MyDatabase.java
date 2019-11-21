package com.example.demotest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    public static final int VERSION = 1;

    public static final String TABLE_NAME = "ThiSinh";
    public static final String DATABASE_NAME = "Quanlidiem";

    public static final String ID = "_id";

    public static final String SBD = "sbd";
    public static final String HO_TEN = "ho_ten";
    public static final String DIEM_TOAN = "diem_toan";
    public static final String DIEM_LI = "diem_li";
    public static final String DIEM_HOA = "diem_hoa";

    private SQLiteDatabase database;
    private Context context;
    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SBD + " TEXT, " +
                HO_TEN + " TEXT, " +
                DIEM_TOAN + " REAL, "+
                DIEM_LI + " REAL, "+
                DIEM_HOA + " REAL )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public long insert(ThiSinh thiSinh){
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SBD,thiSinh.getSoBaoDanh());
        values.put(HO_TEN,thiSinh.getHoTen());
        values.put(DIEM_TOAN,thiSinh.getDiemToan());
        values.put(DIEM_LI,thiSinh.getDiemLi());
        values.put(DIEM_HOA,thiSinh.getDiemHoa());
        return database.insert(TABLE_NAME,null,values);
    }

    public long update(ThiSinh thiSinh,int id){
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SBD,thiSinh.getSoBaoDanh());
        values.put(HO_TEN,thiSinh.getHoTen());
        values.put(DIEM_TOAN,thiSinh.getDiemToan());
        values.put(DIEM_LI,thiSinh.getDiemLi());
        values.put(DIEM_HOA,thiSinh.getDiemHoa());
        return database.update(TABLE_NAME,values,ID + " = " + id,null);
    }

    public long delete(int id){
        database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,ID + " = " + id,null);
    }

    public List<ThiSinh> getAll() {
        database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);

        List<ThiSinh> thiSinhs = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String sbd = cursor.getString(1);
            String ten = cursor.getString(2);
            double diemToan = cursor.getDouble(3);
            double diemLi = cursor.getDouble(4);
            double diemHoa = cursor.getDouble(5);

            ThiSinh thiSinh = new ThiSinh(sbd,ten,diemToan,diemLi,diemHoa);
            thiSinhs.add(thiSinh);
            cursor.moveToNext();
        }

        return thiSinhs;
    }

    public void openDB(){
        database = this.getWritableDatabase();
    }

    public void closeDB(){
        if(database == null || database.isOpen()){
            database.close();
        }
    }


}
