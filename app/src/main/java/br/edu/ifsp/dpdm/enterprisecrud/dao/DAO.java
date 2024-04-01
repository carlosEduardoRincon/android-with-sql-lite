package br.edu.ifsp.dpdm.enterprisecrud.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DAO <T extends Object> extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sgcp.sqlite3";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_FUNCIONARIO = "funcionario";

    protected Context context;
    protected String[] campos;
    protected String tableName;

    private static final String CREATE_TABLE_FUNCIONARIO = "CREATE TABLE funcionario ( "
            + " id integer primary key autoincrement NOT NULL,"
            + " idade integer,"
            + " nome varchar(75),"
            + " sexo VARCHAR( 1 ));";

    public DAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_FUNCIONARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_FUNCIONARIO);
        onCreate(db);
    }

    protected void closeDatabase(SQLiteDatabase db) {
        if(db.isOpen())
            db.close();
    }
}
