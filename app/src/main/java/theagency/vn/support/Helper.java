package theagency.vn.support;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import theagency.vn.burgerstein.R;

/**
 * Created by Administrator PC on 3/4/2015.
 */
public class Helper {
    private Context context;
    private static Helper _ins;

    public final int PROCESS_DELAY = 500;

    public final int COMMUNICATOR_GOTO_SYMTOM    = 10000;
    public final int COMMUNICATOR_GOTO_NAHRSTOFF = 11111;
    public final int COMMUNICATOR_GOTO_SENDEMAIL = 12222;
    public final int COMMUNICATOR_GOTO_CONTACTINFO = 13333;

    public final int ACTION_AFTERVIDEO = 12222220;
    public final int ACTION_BUTTON_UBER = 12222221;
    public final int ACTION_BUTTON_EMGAGEMENT = 12222222;
    public final int ACTION_BUTTON_SYMTOM_NARSTOFF = 12222223;
    public final int ACTION_BUTTON_NARSTOFF = 12223;
    public final int ACTION_BUTTON_SYMTOM = 12423;
    public final int ACTION_BUTTON_CONTACTINFO = 12222224;
    public final int ACTION_BUTTON_LINK = 12222225;
    public final int ACTION_BUTTON_HOME = 12222220;


    SQLiteDatabase databaseSQLite;
    public float density;
    public float sDensity;
    public float appWidth;
    public float appHeight;
    public float dpWidth;
    public float dpHeight;
    public boolean isZoomView;
    public boolean isFristTimeGoToSymptom;
    public boolean isFristTimeGoToNahrstoff;
    public String[] arrSymptom;
    public String[] arrSymptomID;
    public String[] arrNahrstoffID;
    public String[] arrWirkung;
    public String[] arrDoseing;
    public String[] arrNahrstoff;
    public String[] arrNahrstoffItem;

    public Typeface FONT_REGULAR,FONT_BOLD,FONT_BLACK,FONT_BLACKIT,FONT_BOLDIT,FONT_EXTRALIGHT,
            FONT_EXTRALIGHTIT,FONT_IT,FONT_LIGHT,FONT_LIGHTIT,FONT_SEMIBOLD,FONT_SEMIBOLDIT;

    public String[] getArrSymptom() {
        return arrSymptom;
    }

    public String[] getArrSymptomID() {
        return arrSymptomID;
    }

    public String[] getArrNahrstoffID() {
        return arrNahrstoffID;
    }

    public String[] getArrWirkung() {
        return arrWirkung;
    }

    public String[] getArrDoseing() {
        return arrDoseing;
    }

    public String[] getArrNahrstoff() {
        return arrNahrstoff;
    }

    public String[] getArrNahrstoffItem() {
        return arrNahrstoffItem;
    }

    public Helper (Context _context){
        this.context = _context;
        getScreenActivity();
        initializeFonts(_context);
        isZoomView=false;
        isFristTimeGoToSymptom=true;
        isFristTimeGoToNahrstoff=true;
        copyDataBase();
        arrDoseing = loadProductDoseingData();
        arrWirkung = loadProductWirkungData();
        arrNahrstoff = loadNahrstoffData();
        arrNahrstoffItem = loadNahrstoffDataItem();
        arrNahrstoffID = loadProductNahrstoffIDData();
        arrSymptom = loadSymptomData();
        arrSymptomID = loadProductSymptomIDData();
    }


    public static Helper shareIns(Context _context){
        if(Helper._ins==null){
            Helper._ins = new Helper(_context);

        }
        return Helper._ins;
    }

    /**
     *
     * @param d
     * @return
     */


    public float DpToPixel(double d) {
        return (float) (d * this.density);
    }
    public float PixelToDp(int pixel){
        return (pixel / this.density);

    }
    public float DpToSp(int dp){
        float pix = dp*this.density;
        return (pix / this.sDensity);
    }
    public float PxToSp(int px){

        return (px / this.sDensity);
    }


    public void getScreenActivity() {
        Display display = ((WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        this.density = this.context.getResources().getDisplayMetrics().density;
        this.sDensity = this.context.getResources().getDisplayMetrics().scaledDensity;
        float _density = this.density;
        this.appWidth = outMetrics.widthPixels;
        this.appHeight = outMetrics.heightPixels;
        this.dpWidth = (int) (outMetrics.widthPixels / _density);
        this.dpHeight = (int) (outMetrics.heightPixels / _density);
        Log.w("LTH dpWidth:", String.valueOf(dpWidth));
        Log.w("LTH dpHeight:", String.valueOf(dpHeight));
        Log.w("LTH appWidth:", String.valueOf(appWidth));
        Log.w("LTH appHeight:", String.valueOf(appHeight));

    }



    private void initializeFonts(final Context context) {
        FONT_REGULAR = Typeface.createFromAsset(context.getAssets(), "SourceSansPro-Regular.otf");
        FONT_BOLD = Typeface.createFromAsset(context.getAssets(), "SourceSansPro-Bold.otf");
        FONT_BLACK = Typeface.createFromAsset(context.getAssets(), "SourceSansPro-Black.otf");
        FONT_BLACKIT = Typeface.createFromAsset(context.getAssets(), "SourceSansPro-BlackIt.otf");
        FONT_BOLDIT = Typeface.createFromAsset(context.getAssets(), "SourceSansPro-BoldIt.otf");
        FONT_EXTRALIGHT = Typeface.createFromAsset(context.getAssets(), "SourceSansPro-ExtraLight.otf");
        FONT_EXTRALIGHTIT = Typeface.createFromAsset(context.getAssets(), "SourceSansPro-ExtraLightIt.otf");
        FONT_IT = Typeface.createFromAsset(context.getAssets(), "SourceSansPro-It.otf");
        FONT_LIGHT = Typeface.createFromAsset(context.getAssets(), "SourceSansPro-Light.otf");
        FONT_LIGHTIT = Typeface.createFromAsset(context.getAssets(), "SourceSansPro-LightIt.otf");
        FONT_SEMIBOLD = Typeface.createFromAsset(context.getAssets(), "SourceSansPro-Semibold.otf");
        FONT_SEMIBOLDIT = Typeface.createFromAsset(context.getAssets(), "SourceSansPro-SemiboldIt.otf");
    }


    public float getAppWidth() {
        return appWidth;
    }
    public float getAppHeight() {
        return appHeight;
    }
    public float getDpWidth() {
        return dpWidth;
    }
    public float getDpHeight() {
        return dpHeight;
    }

    public String[] loadSymptomData(){
        File outputFile = context.getDatabasePath("burgerstein.db");
        String DB_PATH =outputFile.getPath();
        String[] arrSymptomDatabase = new String[36];
        databaseSQLite = SQLiteDatabase.openDatabase(DB_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);
        Cursor c=databaseSQLite.query("Symptom",
                null, null, null, null, null, null);
        c.moveToFirst();
        int i=0;
        while(c.isAfterLast()==false)
        {
            arrSymptomDatabase[i] = c.getString(1);
            i++;
            c.moveToNext();
        }
        c.close();
        databaseSQLite.close();
        return arrSymptomDatabase;
    }
    public String[] loadNahrstoffData(){
        File outputFile = context.getDatabasePath("burgerstein.db");
        String DB_PATH =outputFile.getPath();
        String[] arrNahrstoffDatabase = new String[47];
        databaseSQLite = SQLiteDatabase.openDatabase(DB_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);
        Cursor c=databaseSQLite.query("Nahrstoff",
                null, null, null, null, null, null);
        c.moveToFirst();
        int i=0;
        while(c.isAfterLast()==false)
        {
            arrNahrstoffDatabase[i] = c.getString(1);
            i++;
            c.moveToNext();
        }
        c.close();
        databaseSQLite.close();
        return arrNahrstoffDatabase;
    }
    public String[] loadNahrstoffDataItem(){
        File outputFile = context.getDatabasePath("burgerstein.db");
        String DB_PATH =outputFile.getPath();
        String[] arrNahrstoffDatabase = new String[43];
        databaseSQLite = SQLiteDatabase.openDatabase(DB_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);
        Cursor c=databaseSQLite.query("NahrstoffItem",
                null, null, null, null, null, null);
        c.moveToFirst();
        int i=0;
        while(c.isAfterLast()==false)
        {
            arrNahrstoffDatabase[i] = c.getString(1);
            i++;
            c.moveToNext();
        }
        c.close();
        databaseSQLite.close();
        return arrNahrstoffDatabase;
    }
    public String[] loadProductNahrstoffIDData(){
        File outputFile = context.getDatabasePath("burgerstein.db");
        String DB_PATH =outputFile.getPath();
        String[] arrProductNahrstoffIDDatabase = new String[123];
        databaseSQLite = SQLiteDatabase.openDatabase(DB_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);
        Cursor c=databaseSQLite.query("DetailProduct",
                null, null, null, null, null, null);
        c.moveToFirst();
        int i=0;
        while(c.isAfterLast()==false)
        {
            i++;
            arrProductNahrstoffIDDatabase[i] = String.valueOf(c.getInt(3));
            c.moveToNext();
        }
        c.close();
        databaseSQLite.close();
        return arrProductNahrstoffIDDatabase;
    }
    public String[] loadProductSymptomIDData(){
        File outputFile = context.getDatabasePath("burgerstein.db");
        String DB_PATH =outputFile.getPath();
        String[] ProductSymptomID = new String[123];
        databaseSQLite = SQLiteDatabase.openDatabase(DB_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);
        Cursor c=databaseSQLite.query("DetailProduct",
                null, null, null, null, null, null);
        c.moveToFirst();
        int i=0;
        while(c.isAfterLast()==false)
        {
            i++;
            ProductSymptomID[i] = String.valueOf(c.getInt(4));

            c.moveToNext();
        }
        c.close();
        databaseSQLite.close();
        return ProductSymptomID;
    }
    public String[] loadProductWirkungData(){
        File outputFile = context.getDatabasePath("burgerstein.db");
        String DB_PATH =outputFile.getPath();
        String[] ProductWirkung = new String[123];
        databaseSQLite = SQLiteDatabase.openDatabase(DB_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);
        Cursor c=databaseSQLite.query("DetailProduct",
                null, null, null, null, null, null);
        c.moveToFirst();
        int i=0;
        while(c.isAfterLast()==false)
        {
            i++;
            ProductWirkung[i] = String.valueOf(c.getString(1));
            c.moveToNext();
        }
        c.close();
        databaseSQLite.close();
        return ProductWirkung;
    }
    public String[] loadProductDoseingData(){
        File outputFile = context.getDatabasePath("burgerstein.db");
        String DB_PATH =outputFile.getPath();
        String[] ProductDoseing = new String[123];
        databaseSQLite = SQLiteDatabase.openDatabase(DB_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);
        Cursor c=databaseSQLite.query("DetailProduct",
                null, null, null, null, null, null);
        c.moveToFirst();
        int i=0;
        while(c.isAfterLast()==false)
        {
            i++;
            ProductDoseing[i] = String.valueOf(c.getString(2));
            c.moveToNext();
        }
        c.close();
        databaseSQLite.close();
        return ProductDoseing;
    }

    private void copyDataBase()
    {
        File outputFile = context.getDatabasePath("burgerstein");
        if (outputFile.exists()) {
            outputFile.delete();

        }

        outputFile = context.getDatabasePath(context.getResources().getString(R.string.database_name));
        outputFile.getParentFile().mkdirs();
        try {
            InputStream inputStream = context.getAssets().open(context.getResources().getString(R.string.database_name));
            OutputStream outputStream = new FileOutputStream(outputFile);
            // transfer bytes from the input stream into the output stream
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            // Close the streams
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            outputFile.renameTo(context.getDatabasePath("burgerstein.db"));
        } catch (Exception e) {
            if (outputFile.exists()) {
                outputFile.delete();
            }
            return;
        }
    }

}
