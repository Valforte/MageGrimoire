package com.gabrielvalforte.grimoire;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class DB extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "data/data/com.gabrielvalforte.grimorio/databases/";
    private static String DB_NAME = "grimoire";
    private static String TABLE_MAGIAS = "magia_view";

    private final Context context;
    private SQLiteDatabase db;

    // constructor
    public DB(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    public static int getApplicationVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException ex) {} catch(Exception e){}
        return 0;
    }
    public static String getApplicationVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException ex) {} catch(Exception e){}
        return "";
    }

    // Creates a empty database on the system and rewrites it with your own database.
    public void create() throws IOException{
        boolean dbExist = checkDataBase();

        if(dbExist){
            if (getApplicationVersionCode(context) != getDBVersion()){
                this.getReadableDatabase();
                try {
                    List<Integer> favs = backupFavs();
                    copyDataBase();
                    restoreFavs(favs);
                } catch (IOException e) {
                    throw new Error("Error copying database");
                }
            }
        }else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public static int getDBVersion() {
        String query = "SELECT ver FROM db WHERE app = 1";
        SQLiteDatabase db = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            return Integer.parseInt(cursor.getString(0));
        }
        cursor.close();
        return 0;
    }

    private static void setDBVersion(int version) {
        String query = "UPDATE db SET ver = " + version + " WHERE app = 1";
        SQLiteDatabase db = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
        db.execSQL(query);
    }

    // Check if the database exist to avoid re-copy the data
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            // database don't exist yet.
            e.printStackTrace();
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null;
    }

    // copy your assets db to the new system DB
    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        setDBVersion(getApplicationVersionCode(context));
    }

    //Open the database
    public boolean open() {
        try {
            String myPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            return true;
        } catch(SQLException sqle) {
            db = null;
            return false;
        }
    }

    @Override
    public synchronized void close() {
        if(db != null)
            db.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //
    // PUBLIC METHODS TO ACCESS DB CONTENT
    //

    private List<Integer> backupFavs() {
        List<Integer> favs = null;
        try {
            String query = "SELECT ID FROM " + TABLE_MAGIAS + " WHERE FAVORITO = 1";
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = db.rawQuery(query, null);
            favs = new LinkedList<Integer>();
            if (cursor.moveToFirst()) {
                do {
                    favs.add(Integer.parseInt(cursor.getString(0)));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            //erro no SQL
        }
        return favs;
    }

    private boolean restoreFavs(List<Integer> favs) {
        String query = "";
        SQLiteDatabase db = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
        for (int fav : favs) {
            query = "UPDATE magia SET fav = 1 WHERE id = " + fav;
            db.execSQL(query);
        }
        return true;
    }

    // Get locations
    public List<Magia> getMagias(String arcana) {
        List<Magia> magias = null;
        try {
            String query = "";
            if (arcana.equals("Favoritos")){
                query = "SELECT * FROM " + TABLE_MAGIAS + " WHERE FAVORITO = 1";
            } else{
                query = "SELECT * FROM " + TABLE_MAGIAS + " WHERE ARCANO = \"" + arcana + "\"";
            }
            SQLiteDatabase db = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = db.rawQuery(query, null);
            // go over each row, build elements and add it to list
            magias = new LinkedList<Magia>();
            if (cursor.moveToFirst()) {
                do {
                    Magia magia  = new Magia();
                    magia.setId(Integer.parseInt(cursor.getString(0)));
                    magia.setNivel(Integer.parseInt(cursor.getString(2)));
                    magia.setNome(cursor.getString(3));
                    magia.setPratica(cursor.getString(4));
                    magia.setAcao(cursor.getString(5));
                    magia.setDuracao(cursor.getString(6));
                    magia.setAspecto(cursor.getString(7));
                    magia.setCusto(cursor.getString(8));
                    magia.setDescri(cursor.getString(9));
                    magia.setClassico(cursor.getString(10));
                    magia.setOrdemCl(cursor.getString(11));
                    magia.setAtributoCl(cursor.getString(12));
                    magia.setHabilidadeCl(cursor.getString(13));
                    magia.setArcanoCl(cursor.getString(14));
                    magia.setDescriCl(cursor.getString(15));
                    magia.setNivelAd(cursor.getString(16));
                    magia.setPag(Integer.parseInt(cursor.getString(17)));
                    magia.setFav(Integer.parseInt(cursor.getString(18)) == 1);
                    magias.add(magia);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch(Exception e) {
            // sql error
        }
        return magias;
    }

    public Magia getMagia(int id) {
        Magia magia  = new Magia();
        try {
            String query  = "SELECT * FROM " + TABLE_MAGIAS + " WHERE id = \"" + id + "\"";
            SQLiteDatabase db = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            // go over each row, build elements and add it to list
            if (cursor.moveToFirst()) {
                magia.setId(Integer.parseInt(cursor.getString(0)));
                magia.setNivel(Integer.parseInt(cursor.getString(2)));
                magia.setNome(cursor.getString(3));
                magia.setPratica(cursor.getString(4));
                magia.setAcao(cursor.getString(5));
                magia.setDuracao(cursor.getString(6));
                magia.setAspecto(cursor.getString(7));
                magia.setCusto(cursor.getString(8));
                magia.setDescri(cursor.getString(9));
                magia.setClassico(cursor.getString(10));
                magia.setOrdemCl(cursor.getString(11));
                magia.setAtributoCl(cursor.getString(12));
                magia.setHabilidadeCl(cursor.getString(13));
                magia.setArcanoCl(cursor.getString(14));
                magia.setDescriCl(cursor.getString(15));
                magia.setNivelAd(cursor.getString(16));
                magia.setPag(Integer.parseInt(cursor.getString(17)));
                magia.setFav(Integer.parseInt(cursor.getString(18)) == 1);
            }
            cursor.close();
        } catch(Exception e) {
            // sql error
        }
        return magia;
    }

    public void ChangeFav(int id, boolean isFav){
        String query = "UPDATE magia SET fav = " + (!isFav ? "1" : "0") + " WHERE id = " + id;
        SQLiteDatabase db = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
        db.execSQL(query);
    }
}
