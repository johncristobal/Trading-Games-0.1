package nowoscmexico.com.tradinggames_1.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by john_vera on 26/12/16.
 *
 * Create the tables and all the queries to add,update,delete data....
 *
 */
public class modelBase extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public Context contex;
    //public static final int DATABASE_VERSION = 22;
    public static final String DATABASE_NAME = "TrendinGames.db";

    /* Inner class that defines the table contents */
    public static class FeedEntryUsuario implements BaseColumns {
        public static final String TABLE_NAME = "usuario";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_CORREO = "correo";
        public static final String COLUMN_TEL = "telefono";
        public static final String COLUMN_UBI = "ubicacion";
        public static final String COLUMN_PASS = "password";
        public static final String COLUMN_ACTIVO = "activo";
    }

    /* Inner class that defines the table contents */
    public static class FeedEntryArticle implements BaseColumns {
        public static final String TABLE_NAME = "article";
        public static final String COLUMN_TITULO = "titulo";
        public static final String COLUMN_DESCRIPCION = "descripcion";
        public static final String COLUMN_CATE = "categoria";
        public static final String COLUMN_FOTO = "rutaFoto";
        public static final String COLUMN_TIME = "tiempopublicacion";
        public static final String COLUMN_FKUser = "idUsuario";
    }

    public static class FeedEntryMatch implements BaseColumns {
        public static final String TABLE_NAME = "match";
        public static final String COLUMN_USER = "id_usuario";
        public static final String COLUMN_ARTICLE = "_id_article";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";

    private static final String SQL_CREATE_ENTRIESUsuario =
            "CREATE TABLE " + FeedEntryUsuario.TABLE_NAME + " (" +
                    FeedEntryUsuario._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    FeedEntryUsuario.COLUMN_NAME + TEXT_TYPE + "," +
                    FeedEntryUsuario.COLUMN_CORREO + TEXT_TYPE + "," +
                    FeedEntryUsuario.COLUMN_UBI + TEXT_TYPE + "," +
                    FeedEntryUsuario.COLUMN_PASS + TEXT_TYPE + "," +
                    FeedEntryUsuario.COLUMN_TEL + TEXT_TYPE + "," +
                    FeedEntryUsuario.COLUMN_ACTIVO + INT_TYPE + " )";

    private static final String SQL_CREATE_ENTRIESArticle =
            "CREATE TABLE " + FeedEntryArticle.TABLE_NAME + " (" +
                    FeedEntryArticle._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    FeedEntryArticle.COLUMN_TITULO + TEXT_TYPE + "," +
                    FeedEntryArticle.COLUMN_DESCRIPCION + TEXT_TYPE + "," +
                    FeedEntryArticle.COLUMN_CATE + TEXT_TYPE + "," +
                    FeedEntryArticle.COLUMN_FOTO + TEXT_TYPE + "," +
                    FeedEntryArticle.COLUMN_TIME + TEXT_TYPE + "," +
                    FeedEntryArticle.COLUMN_FKUser + INT_TYPE + " )";

    private static final String SQL_CREATE_ENTRIESMatch =
            "CREATE TABLE " + FeedEntryMatch.TABLE_NAME + " (" +
                    FeedEntryMatch._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    FeedEntryMatch.COLUMN_USER + INT_TYPE + "," +
                    FeedEntryMatch.COLUMN_ARTICLE + INT_TYPE +")";

    private static final String SQL_DELETE_ENTRIESUsuario =
            "DROP TABLE IF EXISTS " + FeedEntryUsuario.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIESArticle =
            "DROP TABLE IF EXISTS " + FeedEntryArticle.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIESMatch =
            "DROP TABLE IF EXISTS " + FeedEntryMatch.TABLE_NAME;

    public modelBase(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIESUsuario);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIESArticle);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIESMatch);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIESUsuario);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIESArticle);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIESMatch);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
