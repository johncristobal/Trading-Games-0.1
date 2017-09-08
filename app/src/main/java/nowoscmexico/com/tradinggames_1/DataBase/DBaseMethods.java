package nowoscmexico.com.tradinggames_1.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by moon on 30/10/16.
 */
public class DBaseMethods {

    public static modelBase base;
    //================================AsynTask to connect DB============================================
    public static class ThreadDBInsert extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            //Aqui va el nombre de la tala
            String val = strings[0];
            Log.w("Here",val);

            switch (val){

                case modelBase.FeedEntryUsuario.TABLE_NAME:
                    // Gets the data repository in write mode
                    SQLiteDatabase db2 = base.getWritableDatabase();

                    // Create a new map of values, where column names are the keys
                    ContentValues values2 = new ContentValues();
                    //values.put(modelBase.FeedEntryArticle.COLUMN_ID, strings[6]);
                    values2.put(modelBase.FeedEntryUsuario.COLUMN_NAME, strings[1]);
                    values2.put(modelBase.FeedEntryUsuario.COLUMN_CORREO, strings[2]);
                    //values2.put(modelBase.FeedEntryUsuario.COLUMN_UBI, strings[3]);
                    values2.put(modelBase.FeedEntryUsuario.COLUMN_TEL, strings[3]);
                    values2.put(modelBase.FeedEntryUsuario.COLUMN_PASS, strings[4]);

                    // Insert the new row, returning the primary key value of the new row
                    //Just change name table and the values....
                    long newRowId2 = db2.insert(val, null, values2);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return "" + newRowId2;

                case modelBase.FeedEntryArticle.TABLE_NAME:
                    // Gets the data repository in write mode
                    SQLiteDatabase db = base.getWritableDatabase();

                    // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    //values.put(modelBase.FeedEntryArticle.COLUMN_ID, strings[6]);
                    values.put(modelBase.FeedEntryArticle.COLUMN_TITULO, strings[1]);
                    values.put(modelBase.FeedEntryArticle.COLUMN_DESCRIPCION, strings[2]);
                    values.put(modelBase.FeedEntryArticle.COLUMN_CATE, strings[3]);
                    values.put(modelBase.FeedEntryArticle.COLUMN_FOTO, strings[4]);
                    values.put(modelBase.FeedEntryArticle.COLUMN_TIME, strings[5]);
                    values.put(modelBase.FeedEntryArticle.COLUMN_FKUser, strings[6]);

                    // Insert the new row, returning the primary key value of the new row
                    //Just change name table and the values....
                    long newRowId = db.insert(val, null, values);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return "" + newRowId;

                default:
                    break;
            }

            return "";
        }
    }

//================================AsynTask to update DB============================================
    public static class ThreadDBUpdate extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            //Aqui va el nombre de la tala
            /*String val = strings[0];
            Log.w("Here",val);

            switch (val){
                case modelBase.FeedEntryProyectos.TABLE_NAME:
                    // Gets the data repository in write mode
                    SQLiteDatabase db = base.getWritableDatabase();

                    // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    values.put(modelBase.FeedEntryProyectos.COLUMN_STATUS, strings[2]);
                    values.put(modelBase.FeedEntryProyectos.COLUMN_SENT, strings[3]);

                    //update sent
                    long newRowId = db.update(val, values,modelBase.FeedEntryProyectos.COLUMN_ID +" = "+strings[1],null);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return "" + newRowId;

                default:
                    break;
            }*/

            return "";
        }
    }

//================================AsynTask to readAll DB============================================
    public static class ThreadDBRead extends AsyncTask<String, Void, ArrayList<Object>> {

        @Override
        protected ArrayList<Object> doInBackground(String... strings) {

            String val = strings[0];
            Log.w("Here",val);

            switch(val){
                //proyectos...
                case modelBase.FeedEntryArticle.TABLE_NAME:
                    ArrayList<Object> proyectos = new ArrayList<>();
                    String query = "select * from "+ modelBase.FeedEntryArticle.TABLE_NAME;
                    SQLiteDatabase db = base.getWritableDatabase();

                    Cursor cursor = db.rawQuery(query,null);
                    if (cursor.moveToFirst()){
                        do {
                            ArticuloDao fond = new ArticuloDao();
                            //fond.setId(Integer.parseInt(cursor.getString(0)));
                            fond.setId(cursor.getString(0));
                            fond.setTitulo(cursor.getString(1));
                            fond.setDescripcion(cursor.getString(2));
                            fond.setCategoria(cursor.getString(3));
                            fond.setFoto(cursor.getString(4));
                            fond.setTimeup(cursor.getString(5));

                            proyectos.add(fond);
                        }while(cursor.moveToNext());
                    }

                    return proyectos;

                default:
                    break;
            }

            return null;
        }
    }

    public static class ThreadDBDelete extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String val = strings[0];
            Log.w("Here",val);

            switch (val){
                case modelBase.FeedEntryArticle.TABLE_NAME:

                SQLiteDatabase db = base.getWritableDatabase();
                int res = db.delete(modelBase.FeedEntryArticle.TABLE_NAME, modelBase.FeedEntryArticle._ID + "= ?",new String[]{strings[1]}); //Aqui va id de strings

                db.close();
                return ""+res;

                default:
                    return "";
            }

        }
    }

    public static class ThreadDBReadone extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... strings) {

            return null;
            /*String val = strings[0];
            Log.w("Here",val);

            //Personalizar para que retorne las columnas que quiera....
            //Usar variable strings...
            SQLiteDatabase db = base.getReadableDatabase();
            Cursor cursor = db.query(
                    modelBase.FeedEntryProyectos.TABLE_NAME,
                    new String[] {modelBase.FeedEntryProyectos._ID, modelBase.FeedEntryProyectos.COLUMN_NAME},
                    modelBase.FeedEntryProyectos._ID + "= ?",
                    new String[]{strings[1]},
                    null,
                    null,
                    null,
                    null);

            if(cursor != null)
            {
                cursor.moveToFirst();

                ProyectosDao fonda = new ProyectosDao();
                fonda.setId(cursor.getInt(0));
                fonda.setName((cursor.getString(1)));

                return fonda;
            }
            else{
                return null;
            }*/
        }
    }
}
