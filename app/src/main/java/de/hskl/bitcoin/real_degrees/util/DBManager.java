package de.hskl.bitcoin.real_degrees.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {
    /* DB Schema siehe:
     * https://drive.google.com/file/d/1HoGD10KiCBq8vcCAKE2mdIRjDJq2kMJn/view?usp=sharing
     */

    // DataBase
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Certifier.db";

    // Table Language
    public static final String TABLE_LANGUAGE = "language";
    public static final String COLUMN_LANGUAGE_ID = "_id";
    public static final String COLUMN_LANGUAGE_NAME = "lang_name";

    // Table Promotion
    public static final String TABLE_PROMOTION = "promotion";
    public static final String COLUMN_PROMOTION_ROW_ID = COLUMN_LANGUAGE_ID;
    public static final String COLUMN_PROMOTION_ID = "promotion_id";
    public static final String COLUMN_PROMOTION_LANGUAGE_ID = "language_id";
    public static final String COLUMN_PROMOTION_VALUE = "value";

    // Table Category
    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_ROW_ID = "_id";
    public static final String COLUMN_CATEGORY_LANGUAGE_ID = COLUMN_PROMOTION_LANGUAGE_ID;
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_CATEGORY_NAME = "category_name";

    // Table State
    public static final String TABLE_STATE = "state";
    public static final String COLUMN_STATE_ROW_ID = "_id";
    public static final String COLUMN_STATE_LANGUAGE_ID = COLUMN_PROMOTION_LANGUAGE_ID;
    public static final String COLUMN_STATE_ID = "state_id";
    public static final String COLUMN_STATE_NAME = "state_name";
    public static final String COLUMN_STATE_PATH = "file_path";

    // Table Course
    public static final String TABLE_COURSE = "course";
    public static final String COLUMN_COURSE_ROW_ID = "_id";
    public static final String COLUMN_COURSE_LANGUAGE_ID = "language_id";
    public static final String COLUMN_COURSE_UNIVERSITY_ID = "university_id";
    public static final String COLUMN_COURSE_NAME = "course_name";

    // Table University
    public static final String TABLE_UNIVERSITY = "university";
    public static final String COLUMN_UNIVERSITY_ROW_ID = "_id";
    public static final String COLUMN_UNIVERSITY_LANGUAGE_ID = "language_id";
    public static final String COLUMN_UNIVERSITY_STATE_ID = COLUMN_STATE_ID;
    public static final String COLUMN_UNIVERSITY_CATEGORY_ID = COLUMN_CATEGORY_ID;
    public static final String COLUMN_UNIVERSITY_NAME = "university_name";
    public static final String COLUMN_UNIVERSITY_PROMOTION = "promotion_id";
    public static final String COLUMN_UNIVERSITY_START = "start_year";
    public static final String COLUMN_UNIVERSITY_STUDENTS = "students";
    public static final String COLUMN_UNIVERSITY_PUBLIC_KEY = "public_key";
    public static final String COLUMN_UNIVERSITY_PATH = "file_path";

    // Table Tested
    public static final String TABLE_TESTED = "tested";
    public static final String COLUMN_TESTED_ROW_ID = "_id";
    public static final String COLUMN_TESTED_UNIVERSITY_ID = COLUMN_COURSE_UNIVERSITY_ID;
    public static final String COLUMN_TESTED_COURSE_ID = "course_id";
    public static final String COLUMN_TESTED_NAME = "name";
    public static final String COLUMN_TESTED_SURNAME = "surname";
    public static final String COLUMN_TESTED_BIRTH = "birth_date";
    public static final String COLUMN_TESTED_GRADE = "grade";
    public static final String COLUMN_TESTED_PIN = "pin";
    public static final String COLUMN_TESTED_ADDRESS = "hash_adress";
    public static final String COLUMN_TESTED_MESSAGE = "message";
    public static final String COLUMN_TESTED_RECREATED_MESSAGE = "recreated";
    public static final String COLUMN_TESTED_VALID = "valid";

    // HELPER
    public static final String HELPER_DROP = "DROP TABLE IF EXISTS ";
    // Instance
    private static DBManager mInstance;

    public static synchronized DBManager getInstance(@NonNull Context context) {
        if (null == mInstance) {
            mInstance = new DBManager(context.getApplicationContext());
        }
        return mInstance;
    }

    private DBManager(@NonNull Context cxt) {
        super(cxt, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @SuppressLint("LogConditional")
    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        // DROP TABLE Statements


        // CREATE TABLE Statements
        db.execSQL(
                "CREATE TABLE " + TABLE_LANGUAGE +
                        " (" +
                        COLUMN_LANGUAGE_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_LANGUAGE_NAME + " TEXT NOT NULL " +
                        ")"
        );
        db.execSQL(
                "CREATE TABLE " + TABLE_PROMOTION +
                        " (" +
                        COLUMN_PROMOTION_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PROMOTION_ID + " INTEGER NOT NULL, " +
                        COLUMN_PROMOTION_LANGUAGE_ID + " INTEGER NOT NULL, " +
                        COLUMN_PROMOTION_VALUE + " TEXT NOT NULL" +
                        " )"
        );
        db.execSQL(
                "CREATE TABLE " + TABLE_CATEGORY +
                        " ( " +
                        COLUMN_CATEGORY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_CATEGORY_LANGUAGE_ID + " INTEGER NOT NULL, " +
                        COLUMN_CATEGORY_ID + " INTEGER NOT NULL, " +
                        COLUMN_CATEGORY_NAME + " TEXT NOT NULL " +
                        ")"
        );
        db.execSQL(
                "CREATE TABLE " + TABLE_COURSE +
                        " (" +
                        COLUMN_COURSE_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_COURSE_LANGUAGE_ID + " INTEGER NOT NULL, " +
                        COLUMN_COURSE_UNIVERSITY_ID + " INTEGER NOT NULL, " +
                        COLUMN_COURSE_NAME + " TEXT NOT NULL "
                        + ")"
        );
        db.execSQL(
                "CREATE TABLE " + TABLE_STATE +
                        " (" +
                        COLUMN_STATE_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_STATE_LANGUAGE_ID + " INTEGER NOT NULL, " +
                        COLUMN_STATE_ID + " INTEGER  NOT NULL, " +
                        COLUMN_STATE_NAME + " TEXT NOT NULL," +
                        COLUMN_STATE_PATH + " TEXT NOT NULL"
                        + ")"
        );
        db.execSQL(
                "CREATE TABLE " + TABLE_TESTED +
                        " (" +
                        COLUMN_TESTED_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TESTED_UNIVERSITY_ID + " INTEGER, " +
                        COLUMN_TESTED_COURSE_ID + " INTEGER, " +
                        COLUMN_TESTED_NAME + " TEXT, " +
                        COLUMN_TESTED_SURNAME + " TEXT, " +
                        COLUMN_TESTED_BIRTH + " TEXT, " +
                        COLUMN_TESTED_GRADE + " TEXT, " +
                        COLUMN_TESTED_PIN + " TEXT, " +
                        COLUMN_TESTED_ADDRESS + " TEXT , " +
                        COLUMN_TESTED_MESSAGE + " TEXT, " +
                        COLUMN_TESTED_RECREATED_MESSAGE + " TEXT, " +
                        COLUMN_TESTED_VALID + " INTEGER "
                        + ")"
        );
        db.execSQL(
                "CREATE TABLE " + TABLE_UNIVERSITY +
                        " ( " +
                        COLUMN_UNIVERSITY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_UNIVERSITY_LANGUAGE_ID + " INTEGER NOT NULL, " +
                        COLUMN_UNIVERSITY_STATE_ID + " INTEGER NOT NULL, " +
                        COLUMN_UNIVERSITY_CATEGORY_ID + " INTEGER  NOT NULL, " +
                        COLUMN_UNIVERSITY_NAME + " TEXT NOT NULL," +
                        COLUMN_UNIVERSITY_PROMOTION + " INTEGER NOT NULL, " +
                        COLUMN_UNIVERSITY_START + " INTEGER NOT NULL, " +
                        COLUMN_UNIVERSITY_STUDENTS + " INTEGER, " +
                        COLUMN_UNIVERSITY_PUBLIC_KEY + " TEXT, " +
                        COLUMN_UNIVERSITY_PATH + " TEXT "
                        + ")"
        );
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(HELPER_DROP + TABLE_LANGUAGE);
        db.execSQL(HELPER_DROP + TABLE_PROMOTION);
        db.execSQL(HELPER_DROP + TABLE_STATE);
        db.execSQL(HELPER_DROP + TABLE_COURSE);
        db.execSQL(HELPER_DROP + TABLE_CATEGORY);
        db.execSQL(HELPER_DROP + TABLE_UNIVERSITY);
        db.execSQL(HELPER_DROP + TABLE_TESTED);
        mInstance.onCreate(db);
    }

    public void insertLanguage(int _id, @NonNull String lang_name) {
        ContentValues newLine = new ContentValues();
        newLine.put(COLUMN_LANGUAGE_ID, _id);
        newLine.put(COLUMN_LANGUAGE_NAME, lang_name);

        SQLiteDatabase db = mInstance.getWritableDatabase();
        db.insert(TABLE_LANGUAGE, null, newLine);
        //  Log.d("DataBaseManager", "insertLanguage( ) called");
    }

    public void insertState(int lang_id, int state_id, @NonNull String state_name, @Nullable String path) {
        ContentValues newLine = new ContentValues();

        newLine.put(COLUMN_STATE_LANGUAGE_ID, lang_id);
        newLine.put(COLUMN_STATE_ID, state_id);
        newLine.put(COLUMN_STATE_NAME, state_name);
        newLine.put(COLUMN_STATE_PATH, path);

        SQLiteDatabase db = mInstance.getWritableDatabase();
        db.insert(TABLE_STATE, null, newLine);
        //  Log.d("DataBaseManager", "insertState( ) called");
    }

    public void insertCategory(int lang_id, int category_id, @NonNull String category_name) {
        ContentValues newLine = new ContentValues();

        newLine.put(COLUMN_CATEGORY_LANGUAGE_ID, lang_id);
        newLine.put(COLUMN_CATEGORY_ID, category_id);
        newLine.put(COLUMN_CATEGORY_NAME, category_name);

        SQLiteDatabase db = mInstance.getWritableDatabase();
        db.insert(TABLE_CATEGORY, null, newLine);
        // Log.d("DataBaseManager", "insertCategory( ) called");
    }

    public void deleteFromTested(int row_id) {
        SQLiteDatabase db = mInstance.getWritableDatabase();
        String where = COLUMN_TESTED_ROW_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(row_id)};

        db.delete(TABLE_TESTED, where, whereArg);
        //Log.d("DataBaseManager", "deleteFromTested( " + row_id + " ) called");

    }

    public void insertUniversity(int lang_id, int state_id, int category_id, @NonNull String name,
                                 int promotion, int start, int students, @Nullable String public_key,
                                 @Nullable String file_path) {
        ContentValues newLine = new ContentValues();

        newLine.put(COLUMN_UNIVERSITY_LANGUAGE_ID, lang_id);
        newLine.put(COLUMN_UNIVERSITY_STATE_ID, state_id);
        newLine.put(COLUMN_UNIVERSITY_CATEGORY_ID, category_id);
        newLine.put(COLUMN_UNIVERSITY_NAME, name);
        newLine.put(COLUMN_UNIVERSITY_PROMOTION, promotion);
        newLine.put(COLUMN_UNIVERSITY_START, start);
        newLine.put(COLUMN_UNIVERSITY_STUDENTS, students);
        newLine.put(COLUMN_UNIVERSITY_PUBLIC_KEY, public_key);
        newLine.put(COLUMN_UNIVERSITY_PATH, file_path);

        SQLiteDatabase db = mInstance.getWritableDatabase();
        db.insert(TABLE_UNIVERSITY, null, newLine);
        // Log.d("DataBaseManager", "insertUniversity( ) called");
    }

    public long insertFormIntoTested(int university_id, int course_id, @NonNull String name, @NonNull String surname,
                                     @NonNull String birth, @NonNull String grade, @NonNull String address) {
        ContentValues newLine = new ContentValues();

        newLine.put(COLUMN_TESTED_UNIVERSITY_ID, university_id);
        newLine.put(COLUMN_TESTED_COURSE_ID, course_id);
        newLine.put(COLUMN_TESTED_NAME, name);
        newLine.put(COLUMN_TESTED_SURNAME, surname);
        newLine.put(COLUMN_TESTED_BIRTH, birth);
        newLine.put(COLUMN_TESTED_GRADE, grade);
        newLine.put(COLUMN_TESTED_ADDRESS, address);

        SQLiteDatabase db = mInstance.getWritableDatabase();
        return db.insert(TABLE_TESTED, null, newLine);
        // Log.d("DataBaseManager", "insertTested( ) called");
    }

    public int getUniversityByName(@NonNull String university_name) {
        SQLiteDatabase db = mInstance.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " +
                        "u." + COLUMN_UNIVERSITY_ROW_ID +

                        " FROM " + TABLE_UNIVERSITY + " u" +

                        " WHERE u." + COLUMN_UNIVERSITY_NAME +
                        " LIKE '%" + university_name + "%'"
                , null);

        int result = 0;
        if (cursor.moveToFirst()) {
            result = cursor.getInt(cursor.getColumnIndex(DBManager.COLUMN_UNIVERSITY_ROW_ID));
        }
        //  Log.d("DBManager", "getUniversityByName( " + university_name + " ) called");
        cursor.close();
        return result;
    }


    public int getCourseByName(@NonNull String course_name, int university_id) {
        SQLiteDatabase db = mInstance.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " +
                        "c." + COLUMN_COURSE_ROW_ID +

                        " FROM " + TABLE_COURSE + " c" +

                        " WHERE c." + COLUMN_COURSE_UNIVERSITY_ID +
                        " = " + university_id +
                        " AND c." + COLUMN_COURSE_NAME + " LIKE '%" + course_name + "%'"
                , null);

        int result = 0;
        if (cursor.moveToFirst()) {
            result = cursor.getInt(cursor.getColumnIndex(DBManager.COLUMN_COURSE_ROW_ID));
        }
        //  Log.d("DBManager", "getCourseByName( " + course_name + " , " + university_id + " ) called");
        cursor.close();
        return result;
    }


    public void updateTested(@NonNull String pin, @NonNull String message, @NonNull String recreated, boolean result) {
        SQLiteDatabase db = mInstance.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(" +
                COLUMN_TESTED_ROW_ID + " ) AS max" +
                " FROM " + TABLE_TESTED, null);
        int tested_id = 0;
        if (cursor.moveToFirst()) {
            tested_id = cursor.getInt(cursor.getColumnIndex("max"));
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TESTED_PIN, pin);
        values.put(COLUMN_TESTED_MESSAGE, message);
        values.put(COLUMN_TESTED_RECREATED_MESSAGE, recreated);
        if (result) {
            values.put(COLUMN_TESTED_VALID, 1);
        } else {
            values.put(COLUMN_TESTED_VALID, 0);
        }


        String where = COLUMN_TESTED_ROW_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(tested_id)};
        db.update(TABLE_TESTED, values, where, whereArg);

        Log.d("DataBaseManager", "updateTested() called");
    }

    public void insertCourse(int language_id, int university_id, @NonNull String course_name) {
        ContentValues newLine = new ContentValues();

        newLine.put(COLUMN_COURSE_LANGUAGE_ID, language_id);
        newLine.put(COLUMN_COURSE_UNIVERSITY_ID, university_id);
        newLine.put(COLUMN_COURSE_NAME, course_name);

        SQLiteDatabase db = mInstance.getWritableDatabase();
        db.insert(TABLE_COURSE, null, newLine);
        Log.d("DataBaseManager", "insertCourse( ) called");

    }

    public void insertPromotion(int lang_id, int promotion_id, @NonNull String value) {
        ContentValues newLine = new ContentValues();

        newLine.put(COLUMN_PROMOTION_LANGUAGE_ID, lang_id);
        newLine.put(COLUMN_PROMOTION_ID, promotion_id);
        newLine.put(COLUMN_PROMOTION_VALUE, value);

        SQLiteDatabase db = mInstance.getWritableDatabase();
        db.insert(TABLE_PROMOTION, null, newLine);
        Log.d("DataBaseManager", "insertPromotion() called");
    }

    public int selectRowIDBySelection(int index) {
        SQLiteDatabase db = mInstance.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT u." + COLUMN_TESTED_ROW_ID + " AS _id " +
                " FROM " + TABLE_TESTED + " u  ", null);
        int row_id = 0;
        if (cursor.moveToPosition(index)) {
            row_id = cursor.getInt(cursor.getColumnIndex(DBManager.COLUMN_TESTED_ROW_ID));
        }
        cursor.close();
        //  Log.d("DataBaseManager", "selectRowIDBySelection( " + index + " ) called");
        return row_id;
    }

    public Cursor selectTested(int row_id) {
        int lang_id = Utilities.getLanguage();
        SQLiteDatabase db = mInstance.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT t." + COLUMN_TESTED_ROW_ID +
                        " , t." + COLUMN_TESTED_NAME +
                        " , t." + COLUMN_TESTED_SURNAME +
                        " , t." + COLUMN_TESTED_GRADE +
                        " , t." + COLUMN_TESTED_VALID +
                        " , t." + COLUMN_TESTED_ADDRESS +
                        " , t." + COLUMN_TESTED_BIRTH +
                        " , t." + COLUMN_TESTED_PIN +

                        " , uni." + COLUMN_UNIVERSITY_NAME +
                        " , uni." + COLUMN_UNIVERSITY_NAME +
                        " , uni." + COLUMN_UNIVERSITY_PATH + " AS path" +
                        " , uni." + COLUMN_UNIVERSITY_STUDENTS +
                        " , uni." + COLUMN_UNIVERSITY_START +
                        " , uni." + COLUMN_UNIVERSITY_PROMOTION +

                        " , pro." + COLUMN_PROMOTION_VALUE +

                        " , s." + COLUMN_STATE_NAME +
                        " , s." + COLUMN_STATE_PATH +

                        " , c." + COLUMN_COURSE_NAME +
                        " , ca." + COLUMN_CATEGORY_NAME +

                        " FROM " + TABLE_TESTED + " t " +
                        " JOIN " + TABLE_UNIVERSITY + " uni ON uni." +
                        COLUMN_UNIVERSITY_ROW_ID + " = t." + COLUMN_TESTED_UNIVERSITY_ID +
                        " JOIN " + TABLE_STATE + " s ON s." + COLUMN_STATE_ID + " = uni." + COLUMN_UNIVERSITY_STATE_ID +
                        " JOIN " + TABLE_COURSE + " c ON c." + COLUMN_COURSE_ROW_ID + " = t." + COLUMN_TESTED_COURSE_ID +
                        " JOIN " + TABLE_PROMOTION + " pro ON pro." + COLUMN_PROMOTION_ID + " = uni." + COLUMN_UNIVERSITY_PROMOTION +
                        " JOIN " + TABLE_CATEGORY + " ca ON ca." + COLUMN_CATEGORY_ID + " = uni." + COLUMN_UNIVERSITY_CATEGORY_ID +

                        " WHERE s." + COLUMN_STATE_LANGUAGE_ID + " = " + lang_id + " AND " +
                        " t." + COLUMN_TESTED_ROW_ID + " = " + row_id + " AND " +
                        " pro." + COLUMN_PROMOTION_LANGUAGE_ID + " = " + lang_id + " AND " +
                        " ca." + COLUMN_CATEGORY_LANGUAGE_ID + " =  " + lang_id, null);
        cursor.moveToFirst();
        // Log.d("DataBaseManager", "selectAllTested ( " + (language_id + 1) + " ) called");
        return cursor;
    }

    public Cursor selectAllTested(int language_id) {
        SQLiteDatabase db = mInstance.getReadableDatabase();
        Cursor cursor = db.rawQuery(

                "SELECT t." + COLUMN_TESTED_ROW_ID +
                        " , t." + COLUMN_TESTED_NAME +
                        " , t." + COLUMN_TESTED_SURNAME +
                        " , s." + COLUMN_STATE_NAME +
                        " , uni." + COLUMN_UNIVERSITY_NAME +
                        " , c." + COLUMN_COURSE_NAME +
                        " , t." + COLUMN_TESTED_GRADE +
                        " , t." + COLUMN_TESTED_VALID +
                        " , t." + COLUMN_TESTED_ADDRESS +

                        " FROM " + TABLE_TESTED + " t" +
                        " JOIN " + TABLE_UNIVERSITY + " uni ON uni." +
                        COLUMN_UNIVERSITY_ROW_ID + " = t." + COLUMN_TESTED_UNIVERSITY_ID +
                        " JOIN " + TABLE_STATE + " s ON s." + COLUMN_STATE_ID + " = uni." + COLUMN_UNIVERSITY_STATE_ID +
                        " JOIN " + TABLE_COURSE + " c ON c." + COLUMN_COURSE_ROW_ID + " = t." + COLUMN_TESTED_COURSE_ID +

                        " WHERE s." + COLUMN_STATE_LANGUAGE_ID + " = " + language_id, null);
        cursor.moveToFirst();
        // Log.d("DataBaseManager", "selectAllTested ( " + (language_id + 1) + " ) called");
        return cursor;
    }

    public Cursor getLastTested() {
        SQLiteDatabase db = mInstance.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT t." + COLUMN_TESTED_ROW_ID +
                        " , t." + COLUMN_TESTED_NAME +
                        " , t." + COLUMN_TESTED_SURNAME +
                        " , uni." + COLUMN_UNIVERSITY_NAME +
                        " , c." + COLUMN_COURSE_NAME +
                        " , t." + COLUMN_TESTED_GRADE +
                        " , t." + COLUMN_TESTED_BIRTH +
                        " , t." + COLUMN_TESTED_ADDRESS +
                        " , uni. " + COLUMN_UNIVERSITY_PUBLIC_KEY +

                        " FROM " + TABLE_TESTED + " t" +
                        " JOIN " + TABLE_UNIVERSITY + " uni ON uni." +
                        COLUMN_UNIVERSITY_ROW_ID + " = t." + COLUMN_TESTED_UNIVERSITY_ID +
                        " JOIN " + TABLE_STATE + " s ON s." + COLUMN_STATE_ID + " = uni." + COLUMN_UNIVERSITY_STATE_ID +
                        " JOIN " + TABLE_COURSE + " c ON c." + COLUMN_COURSE_ROW_ID + " = t." + COLUMN_TESTED_COURSE_ID +

                        " WHERE t." + COLUMN_TESTED_ROW_ID + " = (" +
                        " ( SELECT MAX(" + " tt." + COLUMN_TESTED_ROW_ID + ") " +
                        " FROM " + TABLE_TESTED + " tt ))", null);
        cursor.moveToFirst();
        return cursor;

    }

    /*
    @SuppressLint("LogConditional")
    public void deleteUser(int user_id) {
        SQLiteDatabase db = mInstance.getWritableDatabase();
        String where = COLUMN_USER_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(user_id)};

        db.delete(TABLE_USER, where, whereArg);
        Log.d("DataBaseManager", "deleteRace( " + user_id + " ) called");

    }

    @SuppressLint("LogConditional")
    public void deleteAvailableByUser(int user_id) {
        SQLiteDatabase db = mInstance.getWritableDatabase();
        String where = COLUMN_AVAILABLE_USER_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(user_id)};

        db.delete(TABLE_AVAILABLE, where, whereArg);

        mInstance
                .insertAvailable(user_id, 1);

        Log.d("DataBaseManager", "deleteFinished( " + user_id + " ) called");
    }

    @SuppressLint("LogConditional")
    public void updateRankInUser(int user_id, int new_rank) {
        SQLiteDatabase db = mInstance.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_RANK_ID, new_rank);
        String where = COLUMN_USER_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(user_id)};
        db.update(TABLE_USER, values, where, whereArg);
        Log.d("DataBaseManager", "updateRankInUser( " + user_id +
                ", " + new_rank + " ) called");
    }

    @NonNull
    @SuppressLint("LogConditional")
    public Cursor selectOnlyUnlockedShips(int user_id) {
        SQLiteDatabase db = mInstance.getReadableDatabase();
        String subSubQueryHelper = " SELECT u." + COLUMN_UNLOCKED_SHIP_ID +
                " FROM " + TABLE_UNLOCKED + " u " +
                " WHERE u." + COLUMN_UNLOCKED_USER_ID + " = " + user_id;

        String selectHelper = "SELECT s." + COLUMN_SHIP_ROW_ID + " AS _id" +
                " , s." + COLUMN_SHIP_SHIP_ID +
                " , s." + COLUMN_SHIP_NAME +
                " , s." + COLUMN_SHIP_DECKS +
                " , s." + COLUMN_SHIP_CREW +
                " , s." + COLUMN_SHIP_FILEPATH +
                " , u." + COLUMN_UNIVERSE_NAME + " AS universe" +
                " , s." + COLUMN_SHIP_LOCKED +

                " FROM " + TABLE_SHIP + " s JOIN " +
                TABLE_UNIVERSE + " u ON u." + COLUMN_UNIVERSE_ID + " = s." + COLUMN_SHIP_UNIVERSE_ID;

        String subQueryAvailable = selectHelper +
                " WHERE s." + COLUMN_SHIP_SHIP_ID + " IN (" + subSubQueryHelper + ")" +
                " AND s." + COLUMN_SHIP_LOCKED + " = 0";

        Cursor cursor = db.rawQuery(
                " SELECT sub._id" +
                        " , sub." + COLUMN_SHIP_LOCKED +
                        " , sub." + COLUMN_SHIP_FILEPATH +
                        " , sub." + COLUMN_SHIP_NAME +
                        " , sub." + "universe" +
                        " , sub." + COLUMN_SHIP_CREW +
                        " , sub." + COLUMN_SHIP_DECKS +
                        " , sub." + COLUMN_SHIP_SHIP_ID +


                        " FROM ( " + subQueryAvailable + " ) sub " +

                        " ORDER BY sub." + COLUMN_SHIP_SHIP_ID, null);
        cursor.moveToFirst();
        Log.d("DataBaseManager", "selectOnlyUnlockedShips ( " + user_id + " ) called");
        return cursor;
    }



    public Cursor showSelectedLevel(int mission_id, int lang_id) {
        SQLiteDatabase db = mInstance.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT p." + COLUMN_MISSION_MISSION_ID +
                        " , m." + COLUMN_MISSION_TITLE +
                        " , m." + COLUMN_MISSION_DIFFICULTY +
                        " , p." + COLUMN_PREVIEW_FILEPATH +

                        " FROM " + TABLE_MISSION + " m " +
                        " JOIN " + TABLE_PREVIEW + " p ON p." +
                        COLUMN_PREVIEW_MISSION_ID + " = m." +
                        COLUMN_MISSION_MISSION_ID +

                        " WHERE m." + COLUMN_MISSION_MISSION_ID + " = " + mission_id +
                        " AND m." + COLUMN_MISSION_LANGUAGE_ID + " = " + lang_id
                , null);
        cursor.moveToFirst();
        Log.d("DataBaseManager", "showSelectedLevel( " + mission_id + " , " + lang_id + " ) called");
        return cursor;
    }

    public int selectDashBoardRank(int user_id) {
        SQLiteDatabase db = mInstance.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT u." +
                        COLUMN_USER_RANK_ID + " as count " +
                        " FROM " + TABLE_USER + " u " +
                        " WHERE u." + COLUMN_USER_ID + " = " + user_id
                , null);

        float rank = 0;
        if (cursor.moveToFirst()) {
            rank = cursor.getFloat(cursor.getColumnIndex("count"));
        }
        cursor.close();

        if ((int) rank != 0) {
            return ((int) rank * 20);
        }
        return 0;
    }

    public boolean checkPlayableShip(int ship_id, int user_id) {
        SQLiteDatabase db = mInstance.getReadableDatabase();
        Cursor cursor = db.rawQuery("", null);
        boolean test = false;
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            test = true;
        }
        db.close();
        cursor.close();
        return test;
    }
    */
    public String repeatedScan(String hashaddress, String recreatedString) {
        SQLiteDatabase db = mInstance.getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery(
                "SELECT t." + COLUMN_TESTED_ROW_ID +
                        " , t." + COLUMN_TESTED_MESSAGE +

                        " FROM " + TABLE_TESTED + " t " +

                        " WHERE t." + COLUMN_TESTED_ADDRESS + " = '" + hashaddress +
                        "' AND t." + COLUMN_TESTED_RECREATED_MESSAGE + " = '" + recreatedString +
                        "' AND t." + COLUMN_TESTED_VALID + " = 1", null
        );
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(COLUMN_TESTED_MESSAGE));
        }
        cursor.close();
        return result;
    }

    public int getRawCountByTable(String table_name) {
        SQLiteDatabase db = mInstance.getReadableDatabase();
        int count = 0;
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) AS count" +
                        " FROM " + table_name, null
        );
        if (cursor.moveToFirst()) {
            count = cursor.getInt(cursor.getColumnIndex("count"));
        }
        cursor.close();
        return count;
    }

    public boolean isContentComplete() {
        return mInstance.getRawCountByTable(TABLE_UNIVERSITY) > 0;
    }

    public void fillWithContent() {
        // Table language
        mInstance.insertLanguage(0, "general");
        mInstance.insertLanguage(1, "de");
        mInstance.insertLanguage(2, "en");
        mInstance.insertLanguage(3, "fr");
        mInstance.insertLanguage(4, "sp");
        mInstance.insertLanguage(5, "it");

        // Table state
        mInstance.insertState(1, 0, "Bundesland", "Pfad");
        mInstance.insertState(1, 1, "Baden-Württemberg", "s_baden_wuerttemberg");
        mInstance.insertState(1, 2, "Bayern", "s_bayern");
        mInstance.insertState(1, 3, "Berlin", "s_berlin");
        mInstance.insertState(1, 4, "Brandenburg", "s_brandenburg");
        mInstance.insertState(1, 5, "Bremen", "s_bremen");
        mInstance.insertState(1, 6, "Hamburg", "s_hamburg");
        mInstance.insertState(1, 7, "Hessen", "s_hesse");
        mInstance.insertState(1, 8, "Mecklenburg-Vorpommern", "s_mecklenburg");
        mInstance.insertState(1, 9, "Niedersachsen", "s_lower_saxony");
        mInstance.insertState(1, 10, "Nordrhein-Westfalen", "s_north_rhine_westfalia");
        mInstance.insertState(1, 11, "Rheinland-Pfalz", "s_rhineland_palatinate");
        mInstance.insertState(1, 12, "Saarland", "s_saarland");
        mInstance.insertState(1, 13, "Sachsen", "s_saxony");
        mInstance.insertState(1, 14, "Sachsen-Anhalt", "s_sachsen_anhalt");
        mInstance.insertState(1, 15, "Schleswig-Holstein", "s_schleswig_holstein");
        mInstance.insertState(1, 16, "Thüringen", "s_thuringia");

        mInstance.insertState(2, 0, "federal state", "Pfad");
        mInstance.insertState(2, 1, "Baden-Württemberg", "s_baden_wuerttemberg");
        mInstance.insertState(2, 2, "Bavaria", "s_bayern");
        mInstance.insertState(2, 3, "Berlin", "s_berlin");
        mInstance.insertState(2, 4, "Brandenburg", "s_brandenburg");
        mInstance.insertState(2, 5, "Bremen", "s_bremen");
        mInstance.insertState(2, 6, "Hamburg", "s_hamburg");
        mInstance.insertState(2, 7, "Hesse", "s_hesse");
        mInstance.insertState(2, 8, "Lower Saxony", "s_mecklenburg");
        mInstance.insertState(2, 9, "Mecklenburg-Vorpommern", "s_lower_saxony");
        mInstance.insertState(2, 10, "North Rhine-Westphalia", "s_north_rhine_westfalia");
        mInstance.insertState(2, 11, "Rhineland-Palatinate", "s_rhineland_palatinate");
        mInstance.insertState(1, 12, "Saarland", "s_saarland");
        mInstance.insertState(2, 13, "Saxony", "s_saxony");
        mInstance.insertState(2, 14, "Saxony-Anhalt", "s_sachsen_anhalt");
        mInstance.insertState(2, 15, "Schleswig-Holstein", "s_schleswig_holstein");
        mInstance.insertState(2, 16, "Thuringia", "s_thuringia");

        mInstance.insertState(3, 0, "État fédéral", "Pfad");
        mInstance.insertState(3, 1, "Bade-Wurtemberg", "s_baden_wuerttemberg");
        mInstance.insertState(3, 2, "Basse-Saxe", "s_bayern");
        mInstance.insertState(3, 3, "Bavière", "s_berlin");
        mInstance.insertState(3, 4, "Berlin", "s_brandenburg");
        mInstance.insertState(3, 5, "Brandenburg", "s_bremen");
        mInstance.insertState(3, 6, "Brême", "s_hamburg");
        mInstance.insertState(3, 7, "Hambourg", "s_hesse");
        mInstance.insertState(3, 8, "Hesse", "s_mecklenburg");
        mInstance.insertState(3, 9, "Mecklembourg-Poméranie-Occidentale", "s_lower_saxony");
        mInstance.insertState(3, 10, "Rhénanie-du-Nord-Westphalie", "s_north_rhine_westfalia");
        mInstance.insertState(3, 11, "Rhénanie-Palatinat", "s_rhineland_palatinate");
        mInstance.insertState(3, 12, "Sarre", "s_saarland");
        mInstance.insertState(3, 13, "Saxe", "s_saxony");
        mInstance.insertState(3, 14, "Saxe-Anhalt", "s_sachsen_anhalt");
        mInstance.insertState(3, 15, "Schleswig-Holstein", "s_schleswig_holstein");
        mInstance.insertState(3, 16, "Thuringe", "s_thuringia");

        mInstance.insertState(4, 0, "estado federal", "Pfad");
        mInstance.insertState(4, 1, "Baden-Wurtemberg", "s_baden_wuerttemberg");
        mInstance.insertState(4, 2, "Baviera", "s_bayern");
        mInstance.insertState(4, 3, "Berlín", "s_berlin");
        mInstance.insertState(4, 4, "Brandeburgo", "s_brandenburg");
        mInstance.insertState(4, 5, "Ciudad libre hanseática de Bremen", "s_bremen");
        mInstance.insertState(4, 6, "Ciudad libre y hanseática de Hamburgo", "s_hamburg");
        mInstance.insertState(4, 7, "Hesse", "s_hesse");
        mInstance.insertState(4, 8, "Mecklemburgo-Pomerania Occidental", "s_mecklenburg");
        mInstance.insertState(4, 9, "Baja Sajonia", "s_lower_saxony");
        mInstance.insertState(4, 10, "Renania del Norte-Westfalia", "s_north_rhine_westfalia");
        mInstance.insertState(4, 11, "Renania-Palatinado", "s_rhineland_palatinate");
        mInstance.insertState(4, 12, "Sarre", "s_saarland");
        mInstance.insertState(4, 13, "Sajonia", "s_saxony");
        mInstance.insertState(4, 14, "Sajonia-Anhalt", "s_sachsen_anhalt");
        mInstance.insertState(4, 15, "Schleswig-Holstein", "s_schleswig_holstein");
        mInstance.insertState(4, 16, "Turingia", "s_thuringia");

        mInstance.insertState(5, 0, "stati federati ", "Pfad");
        mInstance.insertState(5, 1, "Baden-Württemberg", "s_baden_wuerttemberg");
        mInstance.insertState(5, 2, "Baviera", "s_bayern");
        mInstance.insertState(5, 3, "Berlino", "s_berlin");
        mInstance.insertState(5, 4, "Brandeburgo", "s_brandenburg");
        mInstance.insertState(5, 5, "Brema", "s_bremen");
        mInstance.insertState(5, 6, "Amburgo", "s_hamburg");
        mInstance.insertState(5, 7, "Assia", "s_hesse");
        mInstance.insertState(5, 8, "Meclemburgo-Pomerania Anteriore", "s_mecklenburg");
        mInstance.insertState(5, 9, "Bassa Sassonia", "s_lower_saxony");
        mInstance.insertState(5, 10, "Renania Settentrionale-Vestfalia", "s_north_rhine_westfalia");
        mInstance.insertState(5, 11, "Renania-Palatinato", "s_rhineland_palatinate");
        mInstance.insertState(5, 12, "Saarland", "s_saarland");
        mInstance.insertState(5, 13, "Sassonia", "s_saxony");
        mInstance.insertState(5, 14, "Sassonia-Anhalt", "s_sachsen_anhalt");
        mInstance.insertState(5, 15, "Schleswig-Holstein", "s_schleswig_holstein");
        mInstance.insertState(5, 16, "Turingia", "s_thuringia");

        // Table university
        mInstance.insertUniversity(1, 0, 0, "Name der Hochschule", 0, 0, 0, "uni publickey", "path");
        mInstance.insertUniversity(2, 0, 0, "Name of the college", 0, 0, 0, "uni publickey", "path");
        mInstance.insertUniversity(3, 0, 0, "Nom du collège", 0, 0, 0, "uni publickey", "path");
        mInstance.insertUniversity(4, 0, 0, "Nombre de la universidad", 0, 0, 0, "uni publickey", "path");
        mInstance.insertUniversity(5, 0, 0, "Nome del college", 0, 0, 0, "uni publickey", "path");
        mInstance.insertUniversity(0, 10, 2, "FH Aachen", 2, 1971, 13671, null, null);
        mInstance.insertUniversity(0, 10, 1, "RWTH Aachen", 1, 1870, 45944, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Aalen", 2, 1962, 4970, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Albstadt-Sigmaringen", 2, 1971, 2925, null, null);
        mInstance.insertUniversity(0, 10, 2, "Alanus Hochschule für Kunst und Gesellschaft", 1, 2002, 914, null, null);
        mInstance.insertUniversity(0, 15, 2, "Fachhochschule für Verwaltung und Dienstleistung", 2, 1975, 807, null, null);
        mInstance.insertUniversity(0, 2, 2, "Ostbayerische Technische Hochschule Amberg-Weiden", 2, 1994, 3500, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für angewandte Wissenschaften Ansbach", 2, 1996, 3100, null, null);
        mInstance.insertUniversity(0, 2, 2, "Technische Hochschule Aschaffenburg", 2, 1995, 2867, null, null);
        mInstance.insertUniversity(0, 14, 2, "Fachhochschule Polizei Sachsen-Anhalt", 2, 1997, 316, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für angewandte Wissenschaften Augsburg", 2, 1971, 5895, null, null);
        mInstance.insertUniversity(0, 2, 1, "Universität Augsburg", 1, 1970, 19403, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule der Bayerischen Wirtschaft", 2, 2012, 147, null, null);
        mInstance.insertUniversity(0, 7, 2, "Hochschule der Deutschen Gesetzlichen Unfallversicherung", 2, 1996, 151, null, null);
        mInstance.insertUniversity(0, 7, 2, "accadis Hochschule Bad Homburg", 2, 1990, 553, null, null);
        mInstance.insertUniversity(0, 16, 2, "IUBH Internationale Hochschule", 2, 1998, 27000, null, null);
        mInstance.insertUniversity(0, 10, 2, "Fachhochschule für Rechtspflege Nordrhein-Westfalen", 2, 1976, 685, null, null);
        mInstance.insertUniversity(0, 7, 2, "Diploma Hochschule", 2, 1994, 3499, null, null);
        mInstance.insertUniversity(0, 2, 1, "Otto-Friedrich-Universität Bamberg", 1, 1972, 12079, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für evangelische Kirchenmusik Bayreuth", 2, 2000, 25, null, null);
        mInstance.insertUniversity(0, 2, 1, "Universität Bayreuth", 1, 1975, 12931, null, null);
        mInstance.insertUniversity(0, 3, 2, "Akkon-Hochschule für Humanwissenschaften", 2, 2009, 800, null, null);
        mInstance.insertUniversity(0, 3, 2, "Alice Salomon Hochschule Berlin", 2, 1908, 3051, null, null);
        mInstance.insertUniversity(0, 3, 2, "Bard College Berlin", 2, 1999, 240, null, null);
        mInstance.insertUniversity(0, 3, 2, "Berlin International University of Applied Sciences", 2, 2014, 109, null, null);
        mInstance.insertUniversity(0, 3, 2, "bbw Hochschule", 2, 2007, 1045, null, null);
        mInstance.insertUniversity(0, 3, 2, "BSP Business School Berlin", 2, 2009, 346, null, null);
        mInstance.insertUniversity(0, 3, 2, "Beuth Hochschule für Technik Berlin", 2, 1823, 12519, null, null);
        mInstance.insertUniversity(0, 3, 2, "CODE University of Applied Sciences", 2, 2017, 230, null, null);
        mInstance.insertUniversity(0, 3, 2, "Dekra Hochschule für Medien", 2, 1997, 332, null, null);
        mInstance.insertUniversity(0, 3, 2, "design akademie berlin, SRH Hochschule für Kommunikation und Design", 2, 2007, 295, null, null);
        mInstance.insertUniversity(0, 3, 2, "ESCP Business School", 1, 1973, 736, null, null);
        mInstance.insertUniversity(0, 3, 2, "European School of Management and Technology", 1, 2002, 373, null, null);
        mInstance.insertUniversity(0, 3, 2, "Evangelische Hochschule Berlin", 2, 1903, 1372, null, null);
        mInstance.insertUniversity(0, 3, 2, "SRH Hochschule der populären Künste", 2, 2009, 269, null, null);
        mInstance.insertUniversity(0, 3, 2, "Hochschule für Medien, Kommunikation und Wirtschaft", 2, 2008, 334, null, null);
        mInstance.insertUniversity(0, 3, 2, "Hochschule für Technik und Wirtschaft Berlin", 2, 1994, 13922, null, null);
        mInstance.insertUniversity(0, 3, 2, "Hochschule für Wirtschaft, Technik und Kultur", 2, 2011, 440, null, null);
        mInstance.insertUniversity(0, 3, 2, "Hochschule für Wirtschaft und Recht Berlin", 2, 1971, 11400, null, null);
        mInstance.insertUniversity(0, 3, 1, "Freie Universität Berlin", 1, 1948, 36933, null, null);
        mInstance.insertUniversity(0, 3, 2, "German open Business School", 2, 2011, 444, null, null);
        mInstance.insertUniversity(0, 3, 2, "Hertie School", 1, 2003, 651, null, null);
        mInstance.insertUniversity(0, 3, 2, "Deutsche Hochschule für Gesundheit und Sport", 2, 2007, 888, null, null);
        mInstance.insertUniversity(0, 3, 2, "Hochschule für Musik Hanns Eisler Berlin", 2, 1950, 488, null, null);
        mInstance.insertUniversity(0, 3, 2, "Hochschule für Schauspielkunst Ernst Busch", 2, 1951, 221, null, null);
        mInstance.insertUniversity(0, 3, 1, "Humboldt-Universität zu Berlin", 1, 1810, 34929, null, null);
        mInstance.insertUniversity(0, 3, 2, "IB-Hochschule Berlin", 2, 2007, 518, null, null);
        mInstance.insertUniversity(0, 3, 2, "International Psychoanalytic University Berlin", 2, 2009, 700, null, null);
        mInstance.insertUniversity(0, 3, 2, "Katholische Hochschule für Sozialwesen Berlin", 2, 1991, 1416, null, null);
        mInstance.insertUniversity(0, 3, 2, "Kunsthochschule Berlin-Weißensee", 2, 1946, 802, null, null);
        mInstance.insertUniversity(0, 3, 2, "Mediadesign Hochschule", 2, 2004, 1279, null, null);
        mInstance.insertUniversity(0, 3, 2, "Psychologische Hochschule Berlin", 2, 2010, 458, null, null);
        mInstance.insertUniversity(0, 3, 2, "SRH Hochschule Berlin", 2, 2002, 518, null, null);
        mInstance.insertUniversity(0, 3, 2, "Steinbeis-Hochschule Berlin", 1, 1998, 8142, null, null);
        mInstance.insertUniversity(0, 3, 1, "Technische Universität Berlin", 1, 1770, 34708, null, null);
        mInstance.insertUniversity(0, 3, 2, "Touro College Berlin", 2, 2003, 122, null, null);
        mInstance.insertUniversity(0, 3, 1, "Universität der Künste Berlin", 1, 1696, 3447, null, null);
        mInstance.insertUniversity(0, 3, 2, "Quadriga Hochschule Berlin", 2, 2009, 75, null, null);
        mInstance.insertUniversity(0, 14, 2, "Hochschule Anhalt", 2, 1991, 4761, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Biberach", 2, 1964, 2370, null, null);
        mInstance.insertUniversity(0, 10, 2, "Fachhochschule Bielefeld", 2, 1971, 10085, null, null);
        mInstance.insertUniversity(0, 10, 2, "Fachhochschule der Diakonie", 2, 2006, 559, null, null);
        mInstance.insertUniversity(0, 10, 2, "Fachhochschule des Mittelstands", 2, 2000, 1584, null, null);
        mInstance.insertUniversity(0, 10, 1, "Universität Bielefeld", 1, 1969, 25239, null, null);
        mInstance.insertUniversity(0, 11, 2, "Technische Hochschule Bingen", 1, 1897, 2731, null, null);
        mInstance.insertUniversity(0, 10, 2, "EBZ Business School", 2, 2008, 569, null, null);
        mInstance.insertUniversity(0, 10, 2, "Evangelische Hochschule Rheinland-Westfalen-Lippe", 2, 1971, 2000, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule Bochum", 2, 1972, 8041, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule für Gesundheit", 2, 2009, 491, null, null);
        mInstance.insertUniversity(0, 10, 1, "Ruhr-Universität Bochum", 1, 1962, 43166, null, null);
        mInstance.insertUniversity(0, 10, 2, "Technische Hochschule Georg Agricola", 2, 1816, 2178, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule der Sparkassen-Finanzgruppe", 2, 2003, 919, null, null);
        mInstance.insertUniversity(0, 10, 1, "Rheinische Friedrich-Wilhelms-Universität Bonn", 1, 1818, 38481, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule Bonn-Rhein-Sieg", 2, 1995, 8000, null, null);
        mInstance.insertUniversity(0, 4, 2, "Technische Hochschule Brandenburg", 2, 1992, 2920, null, null);
        mInstance.insertUniversity(0, 4, 1, "Medizinische Hochschule Brandenburg Theodor Fontane", 1, 2014, 82, null, null);
        mInstance.insertUniversity(0, 9, 2, "Hochschule für Bildende Künste Braunschweig", 1, 1963, 1065, null, null);
        mInstance.insertUniversity(0, 9, 1, "Technische Universität Braunschweig", 1, 1745, 19823, null, null);
        mInstance.insertUniversity(0, 5, 2, "Hochschule Bremen", 2, 1982, 8917, null, null);
        mInstance.insertUniversity(0, 5, 2, "Apollon Hochschule der Gesundheitswirtschaft", 2, 2005, 1242, null, null);
        mInstance.insertUniversity(0, 5, 2, "Hochschule für internationale Wirtschaft und Logistik", 2, 2009, 68, null, null);
        mInstance.insertUniversity(0, 5, 2, "Hochschule für Künste Bremen", 2, 1873, 903, null, null);
        mInstance.insertUniversity(0, 5, 2, "Hochschule für Öffentliche Verwaltung Bremen", 2, 1979, 375, null, null);
        mInstance.insertUniversity(0, 5, 1, "Jacobs University Bremen", 1, 1999, 1466, null, null);
        mInstance.insertUniversity(0, 5, 1, "Universität Bremen", 1, 1971, 18488, null, null);
        mInstance.insertUniversity(0, 5, 2, "Hochschule Bremerhaven", 2, 1975, 3093, null, null);
        mInstance.insertUniversity(0, 10, 2, "Europäische Fachhochschule", 2, 2001, 1599, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule des Bundes für öffentliche Verwaltung", 2, 1978, 1085, null, null);
        mInstance.insertUniversity(0, 9, 2, "hochschule 21", 2, 1971, 714, null, null);
        mInstance.insertUniversity(0, 1, 2, "SRH Hochschule für Wirtschaft und Medien Calw", 2, 1972, 299, null, null);
        mInstance.insertUniversity(0, 13, 1, "Technische Universität Chemnitz", 1, 1836, 9914, null, null);
        mInstance.insertUniversity(0, 9, 1, "Technische Universität Clausthal", 1, 1775, 3882, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für angewandte Wissenschaften Coburg", 2, 1971, 4446, null, null);
        mInstance.insertUniversity(0, 4, 1, "Brandenburgische Technische Universität Cottbus-Senftenberg", 1, 2013, 6806, null, null);
        mInstance.insertUniversity(0, 7, 2, "Evangelische Hochschule Darmstadt", 2, 1971, 1603, null, null);
        mInstance.insertUniversity(0, 7, 2, "Hochschule Darmstadt", 1, 1971, 17062, null, null);
        mInstance.insertUniversity(0, 7, 1, "Technische Universität Darmstadt", 1, 1877, 25095, null, null);
        mInstance.insertUniversity(0, 2, 2, "Technische Hochschule Deggendorf", 2, 1994, 5760, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule für Musik Detmold", 1, 1946, 646, null, null);
        mInstance.insertUniversity(0, 10, 2, "Fachhochschule Dortmund", 2, 1971, 14318, null, null);
        mInstance.insertUniversity(0, 10, 2, "International School of Management", 2, 1990, 2190, null, null);
        mInstance.insertUniversity(0, 10, 1, "Technische Universität Dortmund", 1, 1968, 33706, null, null);
        mInstance.insertUniversity(0, 13, 1, "Dresden International University", 2, 2003, 2189, null, null);
        mInstance.insertUniversity(0, 13, 2, "Hochschule für Kirchenmusik Dresden", 2, 1949, 30, null, null);
        mInstance.insertUniversity(0, 13, 2, "Evangelische Hochschule Dresden", 2, 1991, 627, null, null);
        mInstance.insertUniversity(0, 13, 2, "Fachhochschule Dresden", 2, 2010, 113, null, null);
        mInstance.insertUniversity(0, 13, 2, "Hochschule für Bildende Künste Dresden", 1, 1764, 599, null, null);
        mInstance.insertUniversity(0, 13, 2, "Hochschule für Musik Carl Maria von Weber Dresden", 1, 1856, 554, null, null);
        mInstance.insertUniversity(0, 13, 2, "Hochschule für Technik und Wirtschaft Dresden", 2, 1992, 5272, null, null);
        mInstance.insertUniversity(0, 13, 2, "Palucca Hochschule für Tanz Dresden", 2, 1925, 164, null, null);
        mInstance.insertUniversity(0, 13, 1, "Technische Universität Dresden", 1, 1828, 31126, null, null);
        mInstance.insertUniversity(0, 10, 1, "Universität Duisburg-Essen", 1, 2003, 43029, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule Düsseldorf", 2, 1971, 8532, null, null);
        mInstance.insertUniversity(0, 10, 2, "Fliedner Fachhochschule Düsseldorf", 2, 2011, 268, null, null);
        mInstance.insertUniversity(0, 10, 1, "Heinrich-Heine-Universität Düsseldorf", 1, 1965, 37473, null, null);
        mInstance.insertUniversity(0, 10, 2, "IST-Hochschule für Management", 2, 2013, 1134, null, null);
        mInstance.insertUniversity(0, 10, 2, "Kunstakademie Düsseldorf", 1, 1773, 608, null, null);
        mInstance.insertUniversity(0, 10, 2, "Robert Schumann Hochschule Düsseldorf", 1, 1972, 890, null, null);
        mInstance.insertUniversity(0, 4, 2, "Hochschule für nachhaltige Entwicklung Eberswalde", 2, 1992, 2033, null, null);
        mInstance.insertUniversity(0, 11, 2, "Hochschule für Finanzen Rheinland-Pfalz", 2, 1981, 474, null, null);
        mInstance.insertUniversity(0, 2, 1, "Katholische Universität Eichstätt-Ingolstadt", 1, 1980, 4769, null, null);
        mInstance.insertUniversity(0, 15, 2, "Nordakademie", 2, 1992, 1327, null, null);
        mInstance.insertUniversity(0, 4, 2, "Theologische Hochschule Elstal", 2, 1880, 65, null, null);
        mInstance.insertUniversity(0, 9, 2, "Hochschule Emden/Leer", 2, 2009, 4708, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für angewandtes Management", 2, 2004, 3000, null, null);
        mInstance.insertUniversity(0, 16, 2, "Fachhochschule Erfurt", 2, 1991, 4589, null, null);
        mInstance.insertUniversity(0, 16, 1, "Universität Erfurt", 1, 1994, 5928, null, null);
        mInstance.insertUniversity(0, 2, 1, "Friedrich-Alexander-Universität Erlangen-Nürnberg", 1, 1742, 37575, null, null);
        mInstance.insertUniversity(0, 10, 2, "Folkwang Universität der Künste", 1, 1927, 1506, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule der bildenden Künste Essen", 2, 2013, 139, null, null);
        mInstance.insertUniversity(0, 10, 2, "FOM – Hochschule für Oekonomie und Management", 2, 1993, 55000, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Esslingen", 2, 1971, 6018, null, null);
        mInstance.insertUniversity(0, 7, 2, "Theologische Hochschule Ewersbach", 2, 1912, 67, null, null);
        mInstance.insertUniversity(0, 15, 2, "Hochschule Flensburg", 2, 1886, 4047, null, null);
        mInstance.insertUniversity(0, 15, 1, "Europa-Universität Flensburg", 1, 1994, 5938, null, null);
        mInstance.insertUniversity(0, 7, 2, "Frankfurt University of Applied Sciences", 1, 1971, 15626, null, null);
        mInstance.insertUniversity(0, 7, 1, "Frankfurt School of Finance & Management", 1, 1957, 2701, null, null);
        mInstance.insertUniversity(0, 7, 2, "Hochschule für Musik und Darstellende Kunst Frankfurt am Main", 1, 1938, 862, null, null);
        mInstance.insertUniversity(0, 7, 1, "Johann Wolfgang Goethe-Universität Frankfurt am Main", 1, 1914, 45604, null, null);
        mInstance.insertUniversity(0, 7, 2, "Philosophisch-Theologische Hochschule Sankt Georgen", 1, 1926, 335, null, null);
        mInstance.insertUniversity(0, 7, 2, "Provadis School of International Management and Technology", 2, 2002, 897, null, null);
        mInstance.insertUniversity(0, 7, 2, "Staatliche Hochschule für Bildende Künste – Städelschule", 2, 1829, 196, null, null);
        mInstance.insertUniversity(0, 4, 1, "Europa-Universität Viadrina", 1, 1992, 6004, null, null);
        mInstance.insertUniversity(0, 13, 1, "Technische Universität Bergakademie Freiberg", 1, 1765, 3830, null, null);
        mInstance.insertUniversity(0, 1, 1, "Albert-Ludwigs-Universität Freiburg", 1, 1457, 24028, null, null);
        mInstance.insertUniversity(0, 1, 2, "Evangelische Hochschule Freiburg", 2, 1918, 896, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Musik Freiburg", 1, 1946, 481, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Kunst, Design und Populäre Musik Freiburg", 2, 2010, 111, null, null);
        mInstance.insertUniversity(0, 1, 2, "Katholische Hochschule Freiburg", 2, 1971, 1716, null, null);
        mInstance.insertUniversity(0, 1, 3, "Pädagogische Hochschule Freiburg", 1, 1962, 4535, null, null);
        mInstance.insertUniversity(0, 14, 2, "Theologische Hochschule Friedensau", 2, 1899, 188, null, null);
        mInstance.insertUniversity(0, 1, 1, "Zeppelin Universität", 1, 2003, 903, null, null);
        mInstance.insertUniversity(0, 7, 2, "Hochschule Fulda", 1, 1974, 8495, null, null);
        mInstance.insertUniversity(0, 7, 2, "Theologische Fakultät Fulda", 1, 1734, 43, null, null);
        mInstance.insertUniversity(0, 2, 2, "Wilhelm Löhe Hochschule für angewandte Wissenschaften", 2, 2012, 103, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Furtwangen", 2, 1850, 5840, null, null);
        mInstance.insertUniversity(0, 7, 2, "Hochschule Geisenheim", 1, 2013, 1100, null, null);
        mInstance.insertUniversity(0, 10, 2, "Fachhochschule für öffentliche Verwaltung Nordrhein-Westfalen", 2, 1976, 6881, null, null);
        mInstance.insertUniversity(0, 10, 2, "Westfälische Hochschule Gelsenkirchen Bocholt Recklinghausen", 2, 1992, 8731, null, null);
        mInstance.insertUniversity(0, 16, 4, "Duale Hochschule Gera-Eisenach", 2, 2016, 1440, null, null);
        mInstance.insertUniversity(0, 16, 2, "SRH Hochschule für Gesundheit", 2, 2006, 486, null, null);
        mInstance.insertUniversity(0, 7, 2, "Freie Theologische Hochschule Gießen", 2, 1974, 209, null, null);
        mInstance.insertUniversity(0, 7, 1, "Justus-Liebig-Universität Gießen", 1, 1607, 27727, null, null);
        mInstance.insertUniversity(0, 7, 2, "Technische Hochschule Mittelhessen", 11, 1971, 18684, null, null);
        mInstance.insertUniversity(0, 16, 2, "Thüringer Fachhochschule für öffentliche Verwaltung", 2, 1994, 396, null, null);
        mInstance.insertUniversity(0, 9, 1, "Georg-August-Universität Göttingen", 1, 1734, 30153, null, null);
        mInstance.insertUniversity(0, 9, 2, "PFH Private Hochschule Göttingen", 2, 1994, 2013, null, null);
        mInstance.insertUniversity(0, 8, 1, "Universität Greifswald", 1, 1456, 10058, null, null);
        mInstance.insertUniversity(0, 8, 2, "Fachhochschule für öffentliche Verwaltung, Polizei und Rechtspflege", 2, 1991, 466, null, null);
        mInstance.insertUniversity(0, 11, 2, "Hochschule der Deutschen Bundesbank", 2, 1976, 317, null, null);
        mInstance.insertUniversity(0, 10, 1, "Fernuniversität in Hagen", 1, 1974, 76647, null, null);
        mInstance.insertUniversity(0, 14, 2, "Burg Giebichenstein Kunsthochschule Halle", 1, 1879, 1059, null, null);
        mInstance.insertUniversity(0, 14, 2, "Evangelische Hochschule für Kirchenmusik Halle", 2, 1993, 49, null, null);
        mInstance.insertUniversity(0, 14, 1, "Martin-Luther-Universität Halle-Wittenberg", 1, 1817, 20096, null, null);
        mInstance.insertUniversity(0, 6, 2, "Bucerius Law School", 1, 2000, 858, null, null);
        mInstance.insertUniversity(0, 6, 2, "Brand University of Applied Sciences", 2, 2010, 90, null, null);
        mInstance.insertUniversity(0, 6, 2, "EBC Hochschule", 2, 2008, 873, null, null);
        mInstance.insertUniversity(0, 6, 2, "Europäische Fernhochschule Hamburg", 2, 2003, 7025, null, null);
        mInstance.insertUniversity(0, 6, 2, "Evangelische Hochschule für Soziale Arbeit & Diakonie", 2, 1971, 454, null, null);
        mInstance.insertUniversity(0, 6, 1, "HafenCity Universität Hamburg", 1, 2006, 2316, null, null);
        mInstance.insertUniversity(0, 6, 2, "Hamburger Fern-Hochschule", 2, 1997, 9383, null, null);
        mInstance.insertUniversity(0, 6, 2, "HSBA Hamburg School of Business Administration", 2, 2004, 1000, null, null);
        mInstance.insertUniversity(0, 6, 2, "Hochschule für Angewandte Wissenschaften Hamburg", 2, 1970, 16746, null, null);
        mInstance.insertUniversity(0, 6, 2, "Hochschule für bildende Künste Hamburg", 1, 1767, 884, null, null);
        mInstance.insertUniversity(0, 6, 2, "Hochschule für Musik und Theater Hamburg", 1, 1950, 1252, null, null);
        mInstance.insertUniversity(0, 6, 2, "Norddeutsche Akademie für Finanzen und Steuerrecht", 2, 2007, 359, null, null);
        mInstance.insertUniversity(0, 6, 2, "Northern Institute of Technology Management", 1, 1998, 30, null, null);
        mInstance.insertUniversity(0, 6, 2, "International Business School of Service Management", 2, 2006, 74, null, null);
        mInstance.insertUniversity(0, 6, 2, "Kühne Logistics University", 1, 2010, 343, null, null);
        mInstance.insertUniversity(0, 6, 2, "MSH Medical School Hamburg", 2, 2009, 1078, null, null);
        mInstance.insertUniversity(0, 6, 1, "Technische Universität Hamburg", 1, 1978, 7615, null, null);
        mInstance.insertUniversity(0, 6, 1, "Universität Hamburg", 1, 1919, 43509, null, null);
        mInstance.insertUniversity(0, 9, 2, "Hochschule Weserbergland", 2, 2010, 453, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule Hamm-Lippstadt", 2, 2009, 4500, null, null);
        mInstance.insertUniversity(0, 10, 2, "SRH Hochschule Hamm", 2, 2004, 495, null, null);
        mInstance.insertUniversity(0, 9, 2, "Fachhochschule für die Wirtschaft Hannover", 2, 1996, 473, null, null);
        mInstance.insertUniversity(0, 9, 1, "Gottfried Wilhelm Leibniz Universität Hannover", 1, 1831, 30454, null, null);
        mInstance.insertUniversity(0, 9, 2, "Hochschule für Musik, Theater und Medien Hannover", 1, 1897, 1287, null, null);
        mInstance.insertUniversity(0, 9, 2, "Hochschule Hannover", 2, 1971, 10040, null, null);
        mInstance.insertUniversity(0, 9, 2, "Kommunale Hochschule für Verwaltung in Niedersachsen", 2, 2007, 806, null, null);
        mInstance.insertUniversity(0, 9, 2, "Leibniz-Akademie Hannover", 2, 1920, 311, null, null);
        mInstance.insertUniversity(0, 9, 1, "Medizinische Hochschule Hannover", 1, 1961, 3465, null, null);
        mInstance.insertUniversity(0, 9, 1, "Stiftung Tierärztliche Hochschule Hannover", 1, 1778, 2410, null, null);
        mInstance.insertUniversity(0, 15, 2, "Fachhochschule Westküste", 2, 1993, 1927, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Jüdische Studien Heidelberg", 1, 1979, 98, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Kirchenmusik Heidelberg", 2, 1931, 0, null, null);
        mInstance.insertUniversity(0, 1, 3, "Pädagogische Hochschule Heidelberg", 1, 1904, 4458, null, null);
        mInstance.insertUniversity(0, 1, 1, "Ruprecht-Karls-Universität Heidelberg", 1, 1386, 27818, null, null);
        mInstance.insertUniversity(0, 1, 2, "SRH Hochschule Heidelberg", 2, 1972, 2769, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Heilbronn", 2, 1961, 7917, null, null);
        mInstance.insertUniversity(0, 1, 2, "German Graduate School of Management and Law", 2, 2005, 128, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule für Kirchenmusik der Evangelischen Kirche von Westfalen", 2, 1948, 38, null, null);
        mInstance.insertUniversity(0, 9, 2, "Fachhochschule für Interkulturelle Theologie Hermannsburg", 2, 2012, 15, null, null);
        mInstance.insertUniversity(0, 9, 2, "Norddeutsche Hochschule für Rechtspflege", 2, 2007, 267, null, null);
        mInstance.insertUniversity(0, 9, 1, "Universität Hildesheim", 1, 1946, 8439, null, null);
        mInstance.insertUniversity(0, 9, 2, "Hochschule Hildesheim/Holzminden/Göttingen", 2, 1971, 5193, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für Angewandte Wissenschaften Hof", 2, 1994, 3041, null, null);
        mInstance.insertUniversity(0, 7, 2, "Hochschule Fresenius", 2, 1848, 5708, null, null);
        mInstance.insertUniversity(0, 16, 1, "Technische Universität Ilmenau", 1, 1894, 5425, null, null);
        mInstance.insertUniversity(0, 2, 2, "Technische Hochschule Ingolstadt", 2, 1994, 5800, null, null);
        mInstance.insertUniversity(0, 10, 2, "University of Applied Sciences Europe", 2, 2017, 2716, null, null);
        mInstance.insertUniversity(0, 10, 2, "Fachhochschule Südwestfalen", 2, 2002, 14017, null, null);
        mInstance.insertUniversity(0, 1, 2, "Naturwissenschaftlich-Technische Akademie Isny", 2, 1945, 194, null, null);
        mInstance.insertUniversity(0, 16, 2, "Ernst-Abbe-Hochschule Jena", 2, 1991, 4702, null, null);
        mInstance.insertUniversity(0, 16, 1, "Friedrich-Schiller-Universität Jena", 1, 1558, 17066, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Musik Karlsruhe", 1, 1812, 598, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Karlsruhe – Technik und Wirtschaft", 2, 1971, 7672, null, null);
        mInstance.insertUniversity(0, 1, 2, "Karlshochschule International University", 2, 2004, 636, null, null);
        mInstance.insertUniversity(0, 1, 1, "Karlsruher Institut für Technologie", 1, 1825, 23646, null, null);
        mInstance.insertUniversity(0, 1, 3, "Pädagogische Hochschule Karlsruhe", 1, 1962, 3742, null, null);
        mInstance.insertUniversity(0, 1, 2, "Staatliche Akademie der Bildenden Künste Karlsruhe", 1, 1854, 288, null, null);
        mInstance.insertUniversity(0, 1, 2, "Staatliche Hochschule für Gestaltung Karlsruhe", 1, 1992, 397, null, null);
        mInstance.insertUniversity(0, 7, 2, "CVJM-Hochschule", 2, 2009, 297, null, null);
        mInstance.insertUniversity(0, 7, 2, "Kunsthochschule Kassel", 2, 1777, 965, null, null);
        mInstance.insertUniversity(0, 7, 1, "Universität Kassel", 1, 1970, 25058, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Kehl", 2, 1971, 991, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für angewandte Wissenschaften Kempten", 2, 1977, 5888, null, null);
        mInstance.insertUniversity(0, 15, 1, "Christian-Albrechts-Universität zu Kiel", 1, 1665, 27101, null, null);
        mInstance.insertUniversity(0, 15, 2, "Fachhochschule Kiel", 2, 1969, 7268, null, null);
        mInstance.insertUniversity(0, 15, 2, "Muthesius Kunsthochschule", 1, 2005, 568, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule Rhein-Waal", 2, 2009, 7390, null, null);
        mInstance.insertUniversity(0, 11, 2, "Hochschule Koblenz", 2, 1996, 9713, null, null);
        mInstance.insertUniversity(0, 11, 1, "Universität Koblenz-Landau", 1, 1990, 17864, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Konstanz Technik, Wirtschaft und Gestaltung", 2, 1906, 4535, null, null);
        mInstance.insertUniversity(0, 1, 1, "Universität Konstanz", 1, 1966, 11002, null, null);
        mInstance.insertUniversity(0, 10, 2, "Cologne Business School", 2, 1993, 1123, null, null);
        mInstance.insertUniversity(0, 10, 1, "Deutsche Sporthochschule Köln", 1, 1947, 5794, null, null);
        mInstance.insertUniversity(0, 10, 2, "Technische Hochschule Köln", 2, 1971, 23600, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule für Musik und Tanz Köln", 1, 1850, 1568, null, null);
        mInstance.insertUniversity(0, 10, 2, "Katholische Hochschule Nordrhein-Westfalen", 2, 1971, 4030, null, null);
        mInstance.insertUniversity(0, 10, 2, "Kunsthochschule für Medien Köln", 1, 1990, 328, null, null);
        mInstance.insertUniversity(0, 10, 1, "Universität zu Köln", 1, 1919, 54105, null, null);
        mInstance.insertUniversity(0, 10, 2, "Rheinische Fachhochschule Köln", 2, 1971, 4742, null, null);
        mInstance.insertUniversity(0, 4, 2, "Fachhochschule für Finanzen Brandenburg", 2, 1991, 580, null, null);
        mInstance.insertUniversity(0, 1, 2, "Allensbach Hochschule", 2, 1996, 269, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für angewandte Wissenschaften Landshut", 2, 1978, 5311, null, null);
        mInstance.insertUniversity(0, 13, 1, "Handelshochschule Leipzig", 1, 1898, 657, null, null);
        mInstance.insertUniversity(0, 13, 2, "Hochschule für Grafik und Buchkunst Leipzig", 1, 1764, 568, null, null);
        mInstance.insertUniversity(0, 13, 2, "Hochschule für Musik und Theater „Felix Mendelssohn Bartholdy“ Leipzig", 1, 1843, 1036, null, null);
        mInstance.insertUniversity(0, 13, 2, "Hochschule für Technik, Wirtschaft und Kultur Leipzig", 2, 1992, 6277, null, null);
        mInstance.insertUniversity(0, 13, 2, "Hochschule für Telekommunikation Leipzig", 2, 1991, 1004, null, null);
        mInstance.insertUniversity(0, 13, 1, "Universität Leipzig", 1, 1409, 28727, null, null);
        mInstance.insertUniversity(0, 10, 2, "Technische Hochschule Ostwestfalen-Lippe", 2, 1971, 6361, null, null);
        mInstance.insertUniversity(0, 1, 2, "Internationale Hochschule Liebenzell", 2, 2011, 69, null, null);
        mInstance.insertUniversity(0, 1, 2, "Akademie für Darstellende Kunst Baden-Württemberg", 2, 2007, 55, null, null);
        mInstance.insertUniversity(0, 1, 2, "Filmakademie Baden-Württemberg", 2, 1991, 500, null, null);
        mInstance.insertUniversity(0, 1, 2, "Evangelische Hochschule Ludwigsburg", 2, 1971, 1028, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für öffentliche Verwaltung und Finanzen Ludwigsburg", 2, 1999, 1902, null, null);
        mInstance.insertUniversity(0, 1, 3, "Pädagogische Hochschule Ludwigsburg", 1, 1962, 5639, null, null);
        mInstance.insertUniversity(0, 11, 2, "Hochschule für Wirtschaft und Gesellschaft Ludwigshafen", 2, 1965, 4674, null, null);
        mInstance.insertUniversity(0, 15, 2, "Technische Hochschule Lübeck", 2, 1969, 5056, null, null);
        mInstance.insertUniversity(0, 9, 1, "Leuphana Universität Lüneburg", 1, 1946, 9595, null, null);
        mInstance.insertUniversity(0, 15, 2, "Musikhochschule Lübeck", 2, 1973, 369, null, null);
        mInstance.insertUniversity(0, 15, 1, "Universität zu Lübeck", 1, 1964, 5331, null, null);
        mInstance.insertUniversity(0, 14, 1, "Otto-von-Guericke-Universität Magdeburg", 1, 1993, 13797, null, null);
        mInstance.insertUniversity(0, 14, 2, "Hochschule Magdeburg-Stendal", 2, 1991, 5664, null, null);
        mInstance.insertUniversity(0, 11, 2, "Hochschule Mainz", 2, 1971, 5607, null, null);
        mInstance.insertUniversity(0, 11, 2, "Katholische Hochschule Mainz", 2, 1972, 1438, null, null);
        mInstance.insertUniversity(0, 11, 1, "Johannes Gutenberg-Universität Mainz", 1, 1946, 31064, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule der Bundesagentur für Arbeit", 2, 2006, 849, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule der Wirtschaft für Management Mannheim", 2, 2010, 238, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Musik und Darstellende Kunst Mannheim", 1, 1971, 594, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Mannheim", 2, 1898, 5106, null, null);
        mInstance.insertUniversity(0, 1, 2, "Popakademie Baden-Württemberg", 2, 2003, 250, null, null);
        mInstance.insertUniversity(0, 1, 1, "Universität Mannheim", 1, 1967, 12088, null, null);
        mInstance.insertUniversity(0, 7, 2, "Archivschule Marburg", 2, 1949, 49, null, null);
        mInstance.insertUniversity(0, 7, 2, "Evangelische Hochschule Tabor", 2, 1909, 87, null, null);
        mInstance.insertUniversity(0, 7, 1, "Philipps-Universität Marburg", 1, 1527, 23434, null, null);
        mInstance.insertUniversity(0, 11, 2, "Hochschule für öffentliche Verwaltung Rheinland-Pfalz", 2, 1981, 1692, null, null);
        mInstance.insertUniversity(0, 13, 2, "Fachhochschule für öffentliche Verwaltung und Rechtspflege Meißen", 2, 1992, 648, null, null);
        mInstance.insertUniversity(0, 14, 2, "Hochschule Merseburg", 2, 1992, 2727, null, null);
        mInstance.insertUniversity(0, 13, 2, "Hochschule Mittweida", 2, 1867, 7057, null, null);
        mInstance.insertUniversity(0, 13, 2, "Evangelische Hochschule Moritzburg", 2, 1872, 93, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule Ruhr West", 2, 2009, 4400, null, null);
        mInstance.insertUniversity(0, 2, 2, "Akademie der Bildenden Künste München", 1, 1808, 676, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für Angewandte Sprachen München", 2, 2006, 371, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für angewandte Wissenschaften München", 2, 1971, 17830, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für Fernsehen und Film München", 1, 1966, 381, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für Musik und Theater München", 1, 1846, 1033, null, null);
        mInstance.insertUniversity(0, 2, 1, "Hochschule für Philosophie München", 1, 1925, 336, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für Politik München", 1, 1950, 434, null, null);
        mInstance.insertUniversity(0, 2, 2, "Fachhochschule für öffentliche Verwaltung und Rechtspflege in Bayern", 2, 1974, 3824, null, null);
        mInstance.insertUniversity(0, 2, 2, "Katholische Stiftungshochschule München", 2, 1971, 2166, null, null);
        mInstance.insertUniversity(0, 2, 1, "Ludwig-Maximilians-Universität München", 1, 1472, 52425, null, null);
        mInstance.insertUniversity(0, 2, 2, "Macromedia Hochschule für Medien und Kommunikation", 2, 2007, 2008, null, null);
        mInstance.insertUniversity(0, 2, 2, "Munich Business School ", 2, 1991, 518, null, null);
        mInstance.insertUniversity(0, 2, 1, "Technische Universität München", 1, 1868, 41768, null, null);
        mInstance.insertUniversity(0, 2, 1, "Universität der Bundeswehr München", 1, 1973, 3453, null, null);
        mInstance.insertUniversity(0, 10, 1, "Deutsche Hochschule der Polizei ", 1, 2006, 420, null, null);
        mInstance.insertUniversity(0, 10, 2, "Fachhochschule Münster", 2, 1971, 15050, null, null);
        mInstance.insertUniversity(0, 10, 2, "Kunstakademie Münster", 1, 1971, 332, null, null);
        mInstance.insertUniversity(0, 10, 1, "Philosophisch-Theologische Hochschule Münster", 2, 1971, 62, null, null);
        mInstance.insertUniversity(0, 10, 1, "Westfälische Wilhelms-Universität", 1, 1780, 45022, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für angewandte Wissenschaften Neu-Ulm", 2, 1994, 3825, null, null);
        mInstance.insertUniversity(0, 8, 2, "Hochschule Neubrandenburg", 2, 1991, 2086, null, null);
        mInstance.insertUniversity(0, 2, 1, "Augustana-Hochschule Neuendettelsau", 1, 1947, 147, null, null);
        mInstance.insertUniversity(0, 10, 2, "Rheinische Fachhochschule Neuss für Internationale Wirtschaft", 2, 2009, 177, null, null);
        mInstance.insertUniversity(0, 10, 2, "Hochschule Niederrhein", 2, 1971, 14511, null, null);
        mInstance.insertUniversity(0, 16, 2, "Fachhochschule Nordhausen", 2, 1997, 2461, null, null);
        mInstance.insertUniversity(0, 10, 2, "Fachhochschule für Finanzen Nordrhein-Westfalen", 2, 1976, 1464, null, null);
        mInstance.insertUniversity(0, 2, 2, "Akademie der Bildenden Künste Nürnberg", 2, 1662, 317, null, null);
        mInstance.insertUniversity(0, 2, 2, "Evangelische Hochschule Nürnberg", 2, 1995, 1197, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für Musik Nürnberg", 1, 1998, 388, null, null);
        mInstance.insertUniversity(0, 2, 2, "Technische Hochschule Nürnberg Georg Simon Ohm", 2, 1823, 12860, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Kunsttherapie Nürtingen", 2, 1987, 289, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Wirtschaft und Umwelt Nürtingen-Geislingen", 2, 1949, 4577, null, null);
        mInstance.insertUniversity(0, 7, 2, "Lutherische Theologische Hochschule Oberursel", 2, 1948, 33, null, null);
        mInstance.insertUniversity(0, 7, 2, "Hochschule für Gestaltung Offenbach am Main", 1, 1832, 644, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Offenburg", 2, 1964, 4096, null, null);
        mInstance.insertUniversity(0, 9, 1, "Carl von Ossietzky Universität Oldenburg", 1, 1973, 15989, null, null);
        mInstance.insertUniversity(0, 4, 2, "Fachhochschule der Polizei des Landes Brandenburg", 2, 1998, 268, null, null);
        mInstance.insertUniversity(0, 9, 2, "Hochschule Osnabrück", 2, 1971, 13552, null, null);
        mInstance.insertUniversity(0, 9, 1, "Universität Osnabrück", 1, 1974, 13903, null, null);
        mInstance.insertUniversity(0, 9, 2, "Hochschule für Künste im Sozialen Ottersberg", 2, 1967, 431, null, null);
        mInstance.insertUniversity(0, 10, 2, "Fachhochschule der Wirtschaft", 2, 1992, 1874, null, null);
        mInstance.insertUniversity(0, 10, 1, "Theologische Fakultät Paderborn", 1, 1614, 92, null, null);
        mInstance.insertUniversity(0, 10, 1, "Universität Paderborn", 1, 1972, 20306, null, null);
        mInstance.insertUniversity(0, 2, 1, "Universität Passau", 1, 1978, 12264, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Pforzheim", 2, 1877, 5442, null, null);
        mInstance.insertUniversity(0, 4, 2, "Fachhochschule für Sport & Management Potsdam", 2, 2009, 119, null, null);
        mInstance.insertUniversity(0, 4, 2, "Fachhochschule Potsdam", 2, 1991, 3283, null, null);
        mInstance.insertUniversity(0, 4, 1, "Filmuniversität Babelsberg Konrad Wolf", 1, 1954, 863, null, null);
        mInstance.insertUniversity(0, 4, 1, "Universität Potsdam", 1, 1991, 21223, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Ravensburg-Weingarten", 2, 1964, 3621, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für Katholische Kirchenmusik und Musikpädagogik Regensburg", 2, 2001, 90, null, null);
        mInstance.insertUniversity(0, 2, 1, "Universität Regensburg", 1, 1962, 20585, null, null);
        mInstance.insertUniversity(0, 2, 2, "Ostbayerische Technische Hochschule Regensburg", 2, 1971, 11000, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule Reutlingen", 2, 1855, 5091, null, null);
        mInstance.insertUniversity(0, 1, 2, "Theologische Hochschule Reutlingen", 2, 1877, 59, null, null);
        mInstance.insertUniversity(0, 10, 2, "MamInstanceas Hochschule Rheine", 2, 2009, 255, null, null);
        mInstance.insertUniversity(0, 1, 2, "SRH Fernhochschule Riedlingen", 2, 1996, 5400, null, null);
        mInstance.insertUniversity(0, 2, 2, "Technische Hochschule Rosenheim", 2, 1971, 5900, null, null);
        mInstance.insertUniversity(0, 8, 1, "Hochschule für Musik und Theater Rostock", 1, 1994, 555, null, null);
        mInstance.insertUniversity(0, 8, 1, "Universität Rostock", 1, 1419, 12994, null, null);
        mInstance.insertUniversity(0, 7, 2, "Hessische Hochschule für Finanzen und Rechtspflege", 2, 1980, 879, null, null);
        mInstance.insertUniversity(0, 13, 2, "Hochschule der Sächsischen Polizei", 2, 1994, 374, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Forstwirtschaft Rottenburg", 2, 1954, 1000, null, null);
        mInstance.insertUniversity(0, 1, 2, "Katholische Hochschule für Kirchenmusik Rottenburg", 2, 1997, 40, null, null);
        mInstance.insertUniversity(0, 12, 2, "Deutsche Hochschule für Prävention und Gesundheitsmanagement", 2, 2002, 3421, null, null);
        mInstance.insertUniversity(0, 12, 2, "Fachhochschule für Verwaltung des Saarlandes", 2, 1980, 458, null, null);
        mInstance.insertUniversity(0, 12, 2, "Hochschule der Bildenden Künste Saar", 1, 1989, 397, null, null);
        mInstance.insertUniversity(0, 12, 2, "Hochschule für Musik Saar", 1, 1947, 445, null, null);
        mInstance.insertUniversity(0, 12, 2, "Hochschule für Technik und Wirtschaft des Saarlandes", 2, 1807, 5575, null, null);
        mInstance.insertUniversity(0, 16, 2, "Hochschule Schmalkalden", 2, 1991, 2836, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Gestaltung Schwäbisch Gmünd", 2, 1776, 642, null, null);
        mInstance.insertUniversity(0, 1, 2, "Fachhochschule Schwetzingen", 2, 1953, 436, null, null);
        mInstance.insertUniversity(0, 1, 3, "Pädagogische Hochschule Schwäbisch Gmünd", 1, 1825, 2712, null, null);
        mInstance.insertUniversity(0, 10, 1, "Universität Siegen", 1, 1972, 19114, null, null);
        mInstance.insertUniversity(0, 11, 1, "Deutsche Universität für Verwaltungswissenschaften Speyer", 1, 1947, 420, null, null);
        mInstance.insertUniversity(0, 10, 1, "Kölner Hochschule für Katholische Theologie (KHKT) – St. Augustin", 1, 1925, 104, null, null);
        mInstance.insertUniversity(0, 1, 2, "AKAD Bildungsgesellschaft", 2, 1959, 5207, null, null);
        mInstance.insertUniversity(0, 1, 4, "Duale Hochschule Baden-Württemberg", 2, 2009, 34242, null, null);
        mInstance.insertUniversity(0, 1, 2, "Freie Hochschule Stuttgart", 2, 1928, 300, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule der Medien", 2, 2001, 4085, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Musik und Darstellende Kunst Stuttgart", 1, 1857, 772, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Technik Stuttgart", 2, 1971, 3738, null, null);
        mInstance.insertUniversity(0, 1, 2, "Merz Akademie", 2, 1918, 250, null, null);
        mInstance.insertUniversity(0, 1, 2, "Staatliche Akademie der Bildenden Künste Stuttgart", 1, 1829, 840, null, null);
        mInstance.insertUniversity(0, 1, 2, "VWA Hochschule für berufsbegleitendes Studium Stuttgart", 2, 2013, 160, null, null);
        mInstance.insertUniversity(0, 1, 1, "Universität Hohenheim", 1, 1818, 8614, null, null);
        mInstance.insertUniversity(0, 1, 1, "Universität Stuttgart", 1, 1829, 24153, null, null);
        mInstance.insertUniversity(0, 11, 2, "Hochschule Trier", 2, 1996, 7357, null, null);
        mInstance.insertUniversity(0, 11, 1, "Theologische Fakultät Trier", 1, 1950, 183, null, null);
        mInstance.insertUniversity(0, 11, 1, "Universität Trier", 1, 1970, 12307, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Musik Trossingen", 1, 1944, 463, null, null);
        mInstance.insertUniversity(0, 1, 1, "Eberhard Karls Universität Tübingen", 1, 1477, 26842, null, null);
        mInstance.insertUniversity(0, 1, 2, "Evangelische Hochschule für Kirchenmusik Tübingen", 2, 1998, 23, null, null);
        mInstance.insertUniversity(0, 1, 2, "Technische Hochschule Ulm", 2, 1971, 4018, null, null);
        mInstance.insertUniversity(0, 1, 1, "Universität Ulm", 1, 1967, 10109, null, null);
        mInstance.insertUniversity(0, 11, 1, "Philosophisch-Theologische Hochschule Vallendar", 1, 1896, 398, null, null);
        mInstance.insertUniversity(0, 11, 1, "WHU – Otto Beisheim School of Management", 1, 1984, 1371, null, null);
        mInstance.insertUniversity(0, 9, 2, "Fachhochschule für Wirtschaft und Technik Vechta/Diepholz/Oldenburg", 2, 1998, 700, null, null);
        mInstance.insertUniversity(0, 9, 1, "Universität Vechta", 1, 1995, 4891, null, null);
        mInstance.insertUniversity(0, 1, 2, "Hochschule für Polizei Baden-Württemberg", 2, 1979, 1251, null, null);
        mInstance.insertUniversity(0, 15, 2, "Fachhochschule Wedel", 2, 1969, 957, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule Weihenstephan-Triesdorf", 2, 1971, 6407, null, null);
        mInstance.insertUniversity(0, 16, 1, "Bauhaus-Universität Weimar", 1, 1860, 3890, null, null);
        mInstance.insertUniversity(0, 16, 2, "Hochschule für Musik Franz Liszt Weimar", 1, 1872, 828, null, null);
        mInstance.insertUniversity(0, 1, 3, "Pädagogische Hochschule Weingarten", 1, 1949, 3233, null, null);
        mInstance.insertUniversity(0, 14, 2, "Hochschule Harz", 2, 1991, 3182, null, null);
        mInstance.insertUniversity(0, 7, 1, "EBS Universität für Wirtschaft und Recht", 1, 1971, 1931, null, null);
        mInstance.insertUniversity(0, 7, 2, "Hessische Hochschule für Polizei und Verwaltung", 2, 1980, 2692, null, null);
        mInstance.insertUniversity(0, 7, 2, "Hochschule RheinMain", 1, 1971, 13246, null, null);
        mInstance.insertUniversity(0, 7, 2, "Wilhelm Büchner Hochschule", 2, 1997, 5804, null, null);
        mInstance.insertUniversity(0, 4, 2, "Technische Hochschule Wildau", 2, 1991, 4152, null, null);
        mInstance.insertUniversity(0, 9, 2, "Jade Hochschule", 2, 2009, 6434, null, null);
        mInstance.insertUniversity(0, 8, 2, "Hochschule Wismar", 2, 1908, 8158, null, null);
        mInstance.insertUniversity(0, 10, 1, "Universität Witten/Herdecke", 1, 1982, 2617, null, null);
        mInstance.insertUniversity(0, 9, 2, "Ostfalia Hochschule für angewandte Wissenschaften", 2, 1971, 11042, null, null);
        mInstance.insertUniversity(0, 11, 2, "Hochschule Worms", 2, 1978, 3722, null, null);
        mInstance.insertUniversity(0, 10, 1, "Bergische Universität Wuppertal", 1, 1972, 22835, null, null);
        mInstance.insertUniversity(0, 10, 1, "Kirchliche Hochschule Wuppertal/Bethel", 1, 1905, 129, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für angewandte Wissenschaften Würzburg-Schweinfurt", 2, 1971, 9000, null, null);
        mInstance.insertUniversity(0, 2, 2, "Hochschule für Musik Würzburg", 1, 1797, 632, null, null);
        mInstance.insertUniversity(0, 2, 1, "Julius-Maximilians-Universität Würzburg", 1, 1582, 27552, null, null);
        mInstance.insertUniversity(0, 13, 2, "Hochschule Zittau/Görlitz", 2, 1992, 3342, null, null);
        mInstance.insertUniversity(0, 13, 2, "DPFA Hochschule Sachsen", 2, 2012, 27, null, null);
        mInstance.insertUniversity(0, 6, 2, "NBS Northern Business School – Hochschule für Management und Sicherheit", 2, 2013, 430, null, null);
        mInstance.insertUniversity(0, 13, 2, "Westsächsische Hochschule Zwickau", 2, 1992, 4880, null, null);
        mInstance.insertUniversity(0, 6, 1, "Helmut-Schmidt-Universität/Universität der Bundeswehr Hamburg", 1, 1972, 2809, null, null);
        mInstance.insertUniversity(0, 1, 2, "media Akademie - Hochschule Stuttgart", 2, 2015, 110, null, null);
        mInstance.insertUniversity(0, 4, 1, "Health and Medical University Potsdam", 2, 2020, 0, null, null);
        // UDS
        mInstance.insertUniversity(0, 12, 1, "Universität des Saarlandes", 1, 1948, 16389, null, "logo_uni_saarland");
        // HS KL
        mInstance.insertUniversity(0, 11, 2,
                "Hochschule Kaiserslautern", 2, 1971, 6107,
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1CAyngym4z5p/G+amel1" +
                        "ZGNwFjfKBXxAy17rT9WKnSNhpiC5ZTC/F3rrlLxKqBOD0jXUhvuW47oJkp1DQpOs" +
                        "reWEfP/CHEBvzIoG4Q5w1vZCCjBSQGw6jEvS/zcpdMDe490lk0pgJtbNGNizndCB" +
                        "ZsyyRwE5XmoDj9mKa4njWU7fCPYC1W03UWx0pGCMHj3RTuamzgtPOjS0Zw4XJsLG" +
                        "hu2aQ/QsQuLhOk87tFprv4O5ggzxY8Go5gO61nMhQnNu66dV+/1KGE9GFx38pv9b" +
                        "3HXZ4U3LIkvUjctEUc0hBW+jK4jXdsJWE+h6l7NJp42gkzBB2RUKVbMIz1LTFVEV" +
                        "YwIDAQAB", "logo_hs_kl");
        // TU KL
        mInstance.insertUniversity(0, 11, 1,
                "Technische Universität Kaiserslautern", 1, 1970, 14453,
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnMYNGEPpE7HslLtTVOAr" +
                        "kAW3YmTQCcUHSFKxmdiZFdS8d4ld8f9qnYUeKM26J4at2pArZ+A/rA8b0X8U9OU1" +
                        "TLL5X65AldlbeIubDMFxODcUQcI93NFV7eUlbGlV0u8k92ZBhQhCsGzK500c1COc" +
                        "xUNXSYO9Ccp2vo9rc7xqvD69I9klraIVOMlulgUBzMaiqZDFUZZH2G/T+xW+W6bs" +
                        "uW0QTJmWP4miKRE8cLnG0jE72v24SCchVl9RDtzoG3k1eKkTmtvVdTKYnha+6A9O" +
                        "IQUxtwP52JGdjR+NTq/6ef4zXCKMM6AZ86BmzRYJRDYtBYwZQzmrFvDuO069Nx6e" +
                        "bwIDAQAB", "logo_tu_kl");
        // HS Stralsund
        mInstance.insertUniversity(0, 8, 2,
                "Hochschule Stralsund", 2, 1991, 2453,
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA48aIHsxDt+wl+OE1LiMv" +
                        "DNrmFVWIJoCq3dh5B1Um0llynLlX45XAm7j0n0KrVlCd4lmRHyksOSetRQGsk7bn" +
                        "ylRfkYj5ExUUCMGl5F2EHMwtjsvD+nI6t4pbYghEvBV1ETC0X6mGNElnom9KauAd" +
                        "G7X5r5+1s7f/yzgG/Bp6k9EkHugZGG2dqqzt9SEzmlX4uRlAhTnInUGhaX9xqFet" +
                        "b4V/qfrfHCaKtWLpYRVC2YfgPjRohYdIIN2v7iJWnYYB6QRw0/2MvWZDzpNF8RpB" +
                        "PB09AXdURVewR6WnnxDmC4ETR37niRFep0hyTkdm2Ti83BXAK7/5pvq0nepVhFUJ" +
                        "jQIDAQAB", "logo_host");


        // Table promotion
        mInstance.insertPromotion(1, 0, "Promotionsrecht");
        mInstance.insertPromotion(1, 1, "Ja");
        mInstance.insertPromotion(1, 2, "Nein");

        mInstance.insertPromotion(2, 0, "right of promotion");
        mInstance.insertPromotion(2, 1, "Yes");
        mInstance.insertPromotion(2, 2, "No");

        mInstance.insertPromotion(3, 0, "droit de promotion");
        mInstance.insertPromotion(3, 1, "Oui");
        mInstance.insertPromotion(3, 2, "Non");

        mInstance.insertPromotion(4, 0, "derecho de promoción");
        mInstance.insertPromotion(4, 1, "Si");
        mInstance.insertPromotion(4, 2, "No");

        mInstance.insertPromotion(5, 0, "diritto di promozione");
        mInstance.insertPromotion(5, 1, "Sì");
        mInstance.insertPromotion(5, 2, "No");

        // Table course
        mInstance.insertCourse(1, 428, "Name des Studienganges");
        mInstance.insertCourse(2, 428, "Course of study");
        mInstance.insertCourse(3, 428, "Programme scolaire");
        mInstance.insertCourse(4, 428, "Curso de Estudio");
        mInstance.insertCourse(5, 428, "Corso di studio");
        // UDS
        mInstance.insertCourse(0, 428, "Altertumswissenschaften (B.A.)");
        mInstance.insertCourse(0, 428, "Archäologie, klassische");
        mInstance.insertCourse(0, 428, "Bachelor Plus MINT (B.Sc.)");
        mInstance.insertCourse(0, 428, "Betriebswirtschaftslehre (B.Sc.)");
        mInstance.insertCourse(0, 428, "Bildwissenschaften der Künste (B.A.)");
        mInstance.insertCourse(0, 428, "Bioinformatik (B.Sc.)");
        mInstance.insertCourse(0, 428, "Biologische Physik (siehe Biophysik)");
        mInstance.insertCourse(0, 428, "Biologie (Human- und Molekularbiologie) (B.Sc.)");
        mInstance.insertCourse(0, 428, "Biophysik (B.Sc.)");
        mInstance.insertCourse(0, 428, "Chemie (B.Sc.)");
        mInstance.insertCourse(0, 428, "Computer- und Kommunikationstechnik (B.Sc.)");
        mInstance.insertCourse(0, 428, "Computerlinguistik (B.Sc.)");
        mInstance.insertCourse(0, 428, "Cybersicherheit (B.Sc.)");
        mInstance.insertCourse(0, 428, "Data Science and Artificial Intelligence (B.Sc.)");
        mInstance.insertCourse(0, 428, "Deutsch als Fremd- und Zweitsprache (Aufbaustudiengang)");
        mInstance.insertCourse(0, 428, "Deutsch-Französische Studien: Grenzüberschreitende Kommunikation und Kooperation (B.A.)");
        mInstance.insertCourse(0, 428, "Digitale Betriebswirtschaftslehre (B.Sc.)");
        mInstance.insertCourse(0, 428, "Droit français et allemand (Licence/Bachelor)");
        mInstance.insertCourse(0, 428, "EEIGM: École Européenne d'Ingénieurs en Génie des Matériaux (B.Sc.)");
        mInstance.insertCourse(0, 428, "Eingebettete Systeme (B.Sc.)");
        mInstance.insertCourse(0, 428, "English: Linguistics, Literatures, and Cultures (B.A.)");
        mInstance.insertCourse(0, 428, "Europäische Literaturen und Medien im globalen Kontext (B.A.)");
        mInstance.insertCourse(0, 428, "Europaicum");
        mInstance.insertCourse(0, 428, "Europawissenschaften: Geographien Europas (B.A.)");
        mInstance.insertCourse(0, 428, "Europawissenschaften: Neu-Zeit-Geschichte (B.A.)");
        mInstance.insertCourse(0, 428, "Europawissenschaften: Vergleichende Literaturwissenschaft (B.A.)");
        mInstance.insertCourse(0, 428, "Evangelische Theologie (B.A.)");
        mInstance.insertCourse(0, 428, "Französische Kulturwissenschaft und Interkulturelle Kommunikation (B.A.)");
        mInstance.insertCourse(0, 428, "Germanistik (B.A.)");
        mInstance.insertCourse(0, 428, "Geschichte (B.A.)");
        mInstance.insertCourse(0, 428, "Geschichtswissenschaften (B.A.)");
        mInstance.insertCourse(0, 428, "Historisch orientierte Kulturwissenschaften (B.A.)");
        mInstance.insertCourse(0, 428, "Informatik (B.Sc.)");
        mInstance.insertCourse(0, 428, "Katholische Theologie (B.A.)");
        mInstance.insertCourse(0, 428, "Klassische Archäologie");
        mInstance.insertCourse(0, 428, "Klassische Philologie");
        mInstance.insertCourse(0, 428, "Komparatistik (siehe EuLit: Europäische Literaturen und Medien im globalen Kontext)");
        mInstance.insertCourse(0, 428, "Kulturwissenschaften (siehe Historisch orientierte Kulturwissenschaften)");
        mInstance.insertCourse(0, 428, "Kunst- und Bildwissenschaft (B.A.)");
        mInstance.insertCourse(0, 428, "Kunstgeschichte");
        mInstance.insertCourse(0, 428, "Language Science (B.A.)");
        mInstance.insertCourse(0, 428, "Lateinische Philologie (B.A.)");
        mInstance.insertCourse(0, 428, "Materialwissenschaft und Maschinenbau ATLANTIS (B.Sc.)");
        mInstance.insertCourse(0, 428, "Materialwissenschaft und Werkstofftechnik (B.Sc.)");
        mInstance.insertCourse(0, 428, "Mathematik (B.Sc.)");
        mInstance.insertCourse(0, 428, "Mathematik und Informatik (B.Sc.)");
        mInstance.insertCourse(0, 428, "Medieninformatik (B.Sc.)");
        mInstance.insertCourse(0, 428, "Musikmanagement (B.A.)");
        mInstance.insertCourse(0, 428, "Musikwissenschaft (B.A.)");
        mInstance.insertCourse(0, 428, "Optionalbereich (B.A.)");
        mInstance.insertCourse(0, 428, "Philosophie (B.A.)");
        mInstance.insertCourse(0, 428, "Phonetik (B.A./Bachelor-Ergänzungsfach)");
        mInstance.insertCourse(0, 428, "Physik (B.Sc.)");
        mInstance.insertCourse(0, 428, "Psychologie (B.Sc.)");
        mInstance.insertCourse(0, 428, "Quantum Engineering (B.Sc.)");
        mInstance.insertCourse(0, 428, "Quellenkundliche Grundwissenschaften (B.A./Bachelor-Ergänzungsfach)");
        mInstance.insertCourse(0, 428, "Romanistik (Französisch/Italienisch/Spanisch) (B.A.)");
        mInstance.insertCourse(0, 428, "Sportwissenschaft (B.A.)");
        mInstance.insertCourse(0, 428, "Sprachkompetenz 2. Fremdsprache (B.A./Bachelor-Ergänzungsfach)");
        mInstance.insertCourse(0, 428, "Sprachkompetenz Italienisch (B.A./Bachelor-Ergänzungsfach)");
        mInstance.insertCourse(0, 428, "Sprachkompetenz Spanisch (B.A./Bachelor-Ergänzungsfach)");
        mInstance.insertCourse(0, 428, "Systems Engineering (B.Sc.)");
        mInstance.insertCourse(0, 428, "Übersetzen (Language Science)");
        mInstance.insertCourse(0, 428, "Vor- und Frühgeschichte");
        mInstance.insertCourse(0, 428, "Wirtschaft und Recht (B.Sc.)");
        mInstance.insertCourse(0, 428, "Wirtschaftsinformatik (B.Sc.)");
        mInstance.insertCourse(0, 428, "Wirtschaftspädagogik (B.Sc.)");
        mInstance.insertCourse(0, 428, "Altertumswissenschaften (B.A.)");
        mInstance.insertCourse(0, 428, "Geschichte (B.A.)");
        mInstance.insertCourse(0, 428, "Geschichtswissenschaften (B.A.)");
        mInstance.insertCourse(0, 428, "Advanced Materials Science and Engineering AMASE (M.Sc.)");
        mInstance.insertCourse(0, 428, "Allgemeine und Vergleichende Literaturwissenschaft (M.A.)");
        mInstance.insertCourse(0, 428, "Altertumswissenschaften (M.A.)");
        mInstance.insertCourse(0, 428, "American Studies/British Studies/English Linguistics (M.A.)");
        mInstance.insertCourse(0, 428, "Angewandte Kulturwissenschaften (M.A.)");
        mInstance.insertCourse(0, 428, "Betriebswirtschaftslehre (M.Sc.)");
        mInstance.insertCourse(0, 428, "Bioinformatik (M.Sc.)");
        mInstance.insertCourse(0, 428, "Biophysik (M.Sc.)");
        mInstance.insertCourse(0, 428, "Biotechnologie (M.Sc.)");
        mInstance.insertCourse(0, 428, "Border Studies (M.A.)");
        mInstance.insertCourse(0, 428, "Chemie (M.Sc.)");
        mInstance.insertCourse(0, 428, "Data Science and Artificial Intelligence (M.Sc.)");
        mInstance.insertCourse(0, 428, "Deutsch-Französische Studien: Grenzüberschreitende Kommunikation und Kooperation (M.A.)");
        mInstance.insertCourse(0, 428, "Digitale Betriebswirtschaftslehre (M.Sc.)");
        mInstance.insertCourse(0, 428, "Ecole Européenne d'Ingénieurs en Génie des Matériaux EEIGM (M.Sc.)");
        mInstance.insertCourse(0, 428, "Educational Technology (M.Sc.)");
        mInstance.insertCourse(0, 428, "Embedded Systems (M.Sc.)");
        mInstance.insertCourse(0, 428, "Entrepreneurial Cybersecurity (M.Eng.)");
        mInstance.insertCourse(0, 428, "Europäische Kulturstudien (M.A.)");
        mInstance.insertCourse(0, 428, "Germanistik (M.A.)");
        mInstance.insertCourse(0, 428, "Geschichte (M.A.)");
        mInstance.insertCourse(0, 428, "Geschichtswissenschaften in europäischer Perspektive (M.A.)");
        mInstance.insertCourse(0, 428, "Gesundheitssport (M.Sc.)");
        mInstance.insertCourse(0, 428, "High-Performance Sport (M.Sc.)");
        mInstance.insertCourse(0, 428, "Historisch orientierte Kulturwissenschaften (M.A.)");
        mInstance.insertCourse(0, 428, "Human- und Molekularbiologie (M.Sc.)");
        mInstance.insertCourse(0, 428, "Human- und Molekularbiologie international (M.Sc.)");
        mInstance.insertCourse(0, 428, "Informatik/Computer Science (M.Sc.)");
        mInstance.insertCourse(0, 428, "Interkulturelle Kommunikation (M.A.)");
        mInstance.insertCourse(0, 428, "Italienische Sprache und Kultur (M.A.)");
        mInstance.insertCourse(0, 428, "Kunstgeschichte (M.A.)");
        mInstance.insertCourse(0, 428, "Lateinamerikanische Kultur- und Medienwissenschaft (M.A.)");
        mInstance.insertCourse(0, 428, "Language and Communication Technologies (M.Sc.)");
        mInstance.insertCourse(0, 428, "Language Science and Technology (M.Sc.)");
        mInstance.insertCourse(0, 428, "Linguistica e didattica dell'italiano nel contesto internazionale LIDIT (M.A.)");
        mInstance.insertCourse(0, 428, "Literatur-, Kultur- und Sprachgeschichte des deutschsprachigen Raums (M.A.)");
        mInstance.insertCourse(0, 428, "Materialchemie (M.Sc.)");
        mInstance.insertCourse(0, 428, "Materialwissenschaft und Werkstofftechnik (M.Sc.)");
        mInstance.insertCourse(0, 428, "Mathematik (M.Sc.)");
        mInstance.insertCourse(0, 428, "Mathematik und Informatik (M.Sc.)");
        mInstance.insertCourse(0, 428, "Medieninformatik (M.Sc.)");
        mInstance.insertCourse(0, 428, "Mikrotechnologie und Nanostrukturen (M.Sc.)");
        mInstance.insertCourse(0, 428, "Musikwissenschaft (M.A.)");
        mInstance.insertCourse(0, 428, "Musikwissenschaft international (M.A.)");
        mInstance.insertCourse(0, 428, "Philosophie (M.A.)");
        mInstance.insertCourse(0, 428, "Physik (M.Sc.)");
        mInstance.insertCourse(0, 428, "Physik international (M.Sc.)");
        mInstance.insertCourse(0, 428, "Praxisbezogene Literaturwissenschaft Französisch (M.A.)");
        mInstance.insertCourse(0, 428, "Psychologie (M.Sc.)");
        mInstance.insertCourse(0, 428, "Quantum Engineering (M.Sc.)");
        mInstance.insertCourse(0, 428, "Religion in Europa (M.A.)");
        mInstance.insertCourse(0, 428, "Romanistik (Französisch/Italienisch/Spanisch) (M.A.)");
        mInstance.insertCourse(0, 428, "Systems Engineering (M.Sc.)");
        mInstance.insertCourse(0, 428, "Theoretical Linguistics: Dynamics and Variation (M.A.)");
        mInstance.insertCourse(0, 428, "Translation Science and Technology (M.A.)");
        mInstance.insertCourse(0, 428, "Visual Computing (M.Sc.)");
        mInstance.insertCourse(0, 428, "Wirtschaft und Recht (M.Sc.)");
        mInstance.insertCourse(0, 428, "Wirtschaftsinformatik (M.Sc.)");
        mInstance.insertCourse(0, 428, "Wirtschaftspädagogik (M.Sc.)");
        mInstance.insertCourse(0, 428, "Bildende Kunst (Lehramt)");
        mInstance.insertCourse(0, 428, "Bildungswissenschaften (Lehramt)");
        mInstance.insertCourse(0, 428, "Biologie (Lehramt)");
        mInstance.insertCourse(0, 428, "Chemie (Lehramt)");
        mInstance.insertCourse(0, 428, "Deutsch (Lehramt)");
        mInstance.insertCourse(0, 428, "Englisch (Lehramt)");
        mInstance.insertCourse(0, 428, "Erdkunde (Lehramt)");
        mInstance.insertCourse(0, 428, "Erziehungswissenschaft (Lehramt)");
        mInstance.insertCourse(0, 428, "Evangelische Religion (Lehramt)");
        mInstance.insertCourse(0, 428, "Französisch (Lehramt)");
        mInstance.insertCourse(0, 428, "Geschichte (Lehramt)");
        mInstance.insertCourse(0, 428, "Informatik (Lehramt)");
        mInstance.insertCourse(0, 428, "Italienisch (Lehramt)");
        mInstance.insertCourse(0, 428, "Katholische Religion (Lehramt)");
        mInstance.insertCourse(0, 428, "Latein (Lehramt)");
        mInstance.insertCourse(0, 428, "Mathematik (Lehramt)");
        mInstance.insertCourse(0, 428, "Musik (Lehramt)");
        mInstance.insertCourse(0, 428, "Philosophie (Lehramt)");
        mInstance.insertCourse(0, 428, "Physik (Lehramt)");
        mInstance.insertCourse(0, 428, "Primarstufe (Lehramt)");
        mInstance.insertCourse(0, 428, "Spanisch (Lehramt)");
        mInstance.insertCourse(0, 428, "Sport (Lehramt)");
        mInstance.insertCourse(0, 428, "Studienfächer der Primarstufe (Lehramt)");
        mInstance.insertCourse(0, 428, "Technik (Lehramt)");
        // HS KL
        mInstance.insertCourse(0, 429, "Angewandte Informatik (B.Sc.)");
        mInstance.insertCourse(0, 429, "Applied Life Sciences (B.Sc.)");
        mInstance.insertCourse(0, 429, "Applied Life Sciences (M.Sc.)");
        mInstance.insertCourse(0, 429, "Digital Media Marketing (B.Sc.)");
        mInstance.insertCourse(0, 429, "Informatik (M.Sc.)");
        mInstance.insertCourse(0, 429, "IT Analyst (B.Sc.)");
        mInstance.insertCourse(0, 429, "Medieninformatik (B.Sc.)");
        mInstance.insertCourse(0, 429, "Medizin- und Biowissenschaften (B.Sc.)");
        mInstance.insertCourse(0, 429, "Medizininformatik (B.Sc.)");
        mInstance.insertCourse(0, 429, "Mikrosystem- und Nanotechnologie (M.Eng.)");
        mInstance.insertCourse(0, 429, "Mikrosystem- und Nanotechnologie (B.Eng.)");
        mInstance.insertCourse(0, 429, "Fernstudiengang Betriebwirtschaft (B.A.)");
        mInstance.insertCourse(0, 429, "Wirtschaftsinformatik (B.Sc.)");
        mInstance.insertCourse(0, 429, "Wirtschaft und Recht (B.A.)");
        mInstance.insertCourse(0, 429, "Mittelstandsökonomie (B.A.)");
        mInstance.insertCourse(0, 429, "Technische Betriebswirtschaft (B.Sc.)");
        mInstance.insertCourse(0, 429, "International Business Administration (B.A.)");
        mInstance.insertCourse(0, 429, "Industrial & Digital Management (B.Sc.)");
        mInstance.insertCourse(0, 429, "Finanzdienstleistungen (B.A.)");
        mInstance.insertCourse(0, 429, "Fernstudiengang Finanzberatung für Unternehmen und Privatkunden (B.A)");
        mInstance.insertCourse(0, 429, "Architektur (B.A.)");
        mInstance.insertCourse(0, 429, "Architektur (M.A.)");
        mInstance.insertCourse(0, 429, "Bauingenieurwesen (B.Eng.)");
        mInstance.insertCourse(0, 429, "Bauingenieurwesen Fachrichtung Baubetrieb (M.Eng.)");
        mInstance.insertCourse(0, 429, "Bauingenieurwesen Fachrich Infrastruktur (M.Eng.)");
        mInstance.insertCourse(0, 429, "Bauingenieurwesen Fachrichtung Konstruktiv (M.Eng.)");
        mInstance.insertCourse(0, 429, "Innenarchitektur (B.A.)");
        mInstance.insertCourse(0, 429, "Innenarchitektur (M.A.)");
        mInstance.insertCourse(0, 429, "TAS Bauschäden Baumängel und Instandsetzungsplanung (M.Eng.)");
        mInstance.insertCourse(0, 429, "TAS Grundstücksbewertung (M.Eng.)");
        mInstance.insertCourse(0, 429, "TAS Sicherheitstechnik (M.Eng.)");
        mInstance.insertCourse(0, 429, "TAS Vorbeugender Brandschutz (M.Eng.)");
        mInstance.insertCourse(0, 429, "Virtual Design (M.A.)");
        mInstance.insertCourse(0, 429, "Virtual Design (B.A.)");
        mInstance.insertCourse(0, 429, "Angewandte Chemie (B.Eng)");
        mInstance.insertCourse(0, 429, "Angewandte Pharmazie (B.Sc.)");
        mInstance.insertCourse(0, 429, "Angewandte Polymerchemie (M.Sc.)");
        mInstance.insertCourse(0, 429, "Industriepharmazie (B.Sc.)");
        mInstance.insertCourse(0, 429, "Leder und Textiltechnik (B.Eng.)");
        mInstance.insertCourse(0, 429, "Logistik (B.Sc.)");
        mInstance.insertCourse(0, 429, "Orthopädieschuhtechnik (B.Sc.)");
        mInstance.insertCourse(0, 429, "Product Refinement (M.Sc.)");
        mInstance.insertCourse(0, 429, "Refinement of Polymer and Composite Products (M.Sc.)");
        mInstance.insertCourse(0, 429, "Wirtschaftsingenieurwesen - Logistik & Produktionsmanagement (M.Sc)");
        mInstance.insertCourse(0, 429, "Automatisierungstechnik (B.Eng.)");
        mInstance.insertCourse(0, 429, "Digital Engineering (B.Eng.)");
        mInstance.insertCourse(0, 429, "Elektrotechnik (B.Eng.)");
        mInstance.insertCourse(0, 429, "Elektrotechnik und Informationstechnik (M.Eng.)");
        mInstance.insertCourse(0, 429, "Energie-Ingenieurwesen (B.Eng.)");
        mInstance.insertCourse(0, 429, "Energieeffiziente Systeme (B.Eng)");
        mInstance.insertCourse(0, 429, "Maschinenbau (B.Eng.)");
        mInstance.insertCourse(0, 429, "Maschinenbau (M.Eng.)");
        mInstance.insertCourse(0, 429, "Maschinenbau / Mechatronik (M.Eng.)");
        mInstance.insertCourse(0, 429, "Master Elektronik berufsbegleitend (M.Eng.)");
        mInstance.insertCourse(0, 429, "Master Prozesstechnik berufsbegleitend (M.Eng.)");
        mInstance.insertCourse(0, 429, "Mechatronik (B.Eng.)");
        mInstance.insertCourse(0, 429, "Mechatronik berufsbegleitend (B.Eng.)");
        mInstance.insertCourse(0, 429, "Prozessingenieurwesen (B.Eng.)");
        mInstance.insertCourse(0, 429, "Wirtschaftsingenieurwesen (B.Eng.)");
        mInstance.insertCourse(0, 429, "Industrial Engineering (B.Eng.)");
        // TU KL
        mInstance.insertCourse(0, 430, "Architektur (B.Sc.)");
        mInstance.insertCourse(0, 430, "Architektur (M.Sc.)");
        mInstance.insertCourse(0, 430, "Architektur (Dipl.)");
        mInstance.insertCourse(0, 430, "Bauingenieurwesen (B.Sc.)");
        mInstance.insertCourse(0, 430, "Bauingenieurwesen - Infrastruktur Wasser und Mobilität (M. Sc.)");
        mInstance.insertCourse(0, 430, "Bauingenieurwesen Konstruktiver Ingenieurbau (M.Sc.)");
        mInstance.insertCourse(0, 430, "Bautechnik  (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "Bautechnik (B.Ed.)");
        mInstance.insertCourse(0, 430, "Bautechnik (Master Lehramt Berufsbildende Schule)");
        mInstance.insertCourse(0, 430, "Holztechnik (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "Holztechnik (B. Ed.)");
        mInstance.insertCourse(0, 430, "Holztechnik (Master Lehramt Berufsbildende Schule)");
        mInstance.insertCourse(0, 430, "Immobilien und Facilities - Management und Technik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Immobilien und Facilities - Management und Technik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Biologie (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "Biologie (B. Ed.)");
        mInstance.insertCourse(0, 430, "Biologie (Master Lehramt Berufsbildende Schule)");
        mInstance.insertCourse(0, 430, "Biologie (Master Lehramt Gymnasium)");
        mInstance.insertCourse(0, 430, "Biologie (Master Lehramt Real+)");
        mInstance.insertCourse(0, 430, "Biologie (M.Sc.)");
        mInstance.insertCourse(0, 430, "Molekular Biologie (B.Sc.)");
        mInstance.insertCourse(0, 430, "Chemie (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "Chemie (B.Sc.)");
        mInstance.insertCourse(0, 430, "Chemie (B.Ed.)");
        mInstance.insertCourse(0, 430, "Chemie (M.Sc.)");
        mInstance.insertCourse(0, 430, "Chemie (Master Lehramt Berufsbildende Schule)");
        mInstance.insertCourse(0, 430, "Chemie (Master Lehramt Gymnasium)");
        mInstance.insertCourse(0, 430, "Chemie (Master Lehramt Real+)");
        mInstance.insertCourse(0, 430, "Chemie mit Schwerpunkt Wirtschaftswissenschaften (B.Sc.)");
        mInstance.insertCourse(0, 430, "Lebensmittelchemie (B.Sc.)");
        mInstance.insertCourse(0, 430, "Lebensmittelchemie (M.Sc.)");
        mInstance.insertCourse(0, 430, "Toxikologie (M.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftschemie (M.Sc.)");
        mInstance.insertCourse(0, 430, "Automation and Control (M.Sc.)");
        mInstance.insertCourse(0, 430, "Commercial Vehicle Technology (M.Sc.)");
        mInstance.insertCourse(0, 430, "Elektrotechnik (B.Ed.)");
        mInstance.insertCourse(0, 430, "Elektrotechnik (Master Lehramt Berufsbildende Schulen)");
        mInstance.insertCourse(0, 430, "Elektrotechnik (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "Elektrotechnik und Informationstechnik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Elektrotechnik  und Informationstechnik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Embedded Computing Systems (M.Sc.)");
        mInstance.insertCourse(0, 430, "Embedded Computing Systems (B.Sc.)");
        mInstance.insertCourse(0, 430, "Medien- und Kommunikationstechnik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Medien- und Kommunikationstechnik (M.Sc.)");
        mInstance.insertCourse(0, 430, "European Master on Software Engineering (M.Sc)");
        mInstance.insertCourse(0, 430, "Informatik (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "Informatik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Informatik (B.Ed.)");
        mInstance.insertCourse(0, 430, "Informatik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Informatik (Master Lehramt Berufsbildende Schulen)");
        mInstance.insertCourse(0, 430, "Informatik (Master Lehramt Gymnasium)");
        mInstance.insertCourse(0, 430, "Informatik (Master Lehramt Real+)");
        mInstance.insertCourse(0, 430, "Informationstechnik / Informatik (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "Informationstechnik / Informatik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Informationstechnik / Informatik (Master Lehramt Berufsbildende Schulen)");
        mInstance.insertCourse(0, 430, "Sozioinformatik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Sozioinformatik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Bio- und Chemieingenieurwissenschaften (B.Sc.)");
        mInstance.insertCourse(0, 430, "Bio- und Chemieingenieurwissenschaften (M.Sc.)");
        mInstance.insertCourse(0, 430, "Bioverfahrenstechnik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Computational Engineering (M.Sc.)");
        mInstance.insertCourse(0, 430, "Energie- und Verfahrenstechnik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Energie- und Verfahrenstechnik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Fahrzeugtechnik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Maschinenbau (B.Sc.)");
        mInstance.insertCourse(0, 430, "Maschinenbau mit angewandter Informatik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Maschinenbau mit BWL (B.Sc.)");
        mInstance.insertCourse(0, 430, "Maschinenbau mit BWL (M.Sc.)");
        mInstance.insertCourse(0, 430, "Maschinenbau mit Verfahrenstechnik (Diplom)");
        mInstance.insertCourse(0, 430, "Materialwissenschaften und Werkstofftechnik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Metalltechnik (B.Ed.)");
        mInstance.insertCourse(0, 430, "Metalltechnik (Master Lehramt Berufsbildende Schulen)");
        mInstance.insertCourse(0, 430, "Metalltechnik (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "Produktentwicklung im Maschinenbau (M.Sc.)");
        mInstance.insertCourse(0, 430, "Produktionstechnik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Finanz- und Versicherungsmathematik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Mathematics international (M.Sc.)");
        mInstance.insertCourse(0, 430, "Mathematik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Mathematik (B.Ed.)");
        mInstance.insertCourse(0, 430, "Mathematik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Mathematik (Master Lehramt Berugsbildende Schulen)");
        mInstance.insertCourse(0, 430, "Mathematik (Master Lehramt Gymnasium)");
        mInstance.insertCourse(0, 430, "Mathematik (Master Lehramt Real+)");
        mInstance.insertCourse(0, 430, "Mathematik (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "Project Studies in Advanced Technology (Zertifikat)");
        mInstance.insertCourse(0, 430, "Technomathematik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftsmathematik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftsmathematik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Advanced Quantum Pysics (M.Sc.)");
        mInstance.insertCourse(0, 430, "Biopyhsik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Biopyhsik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Physik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Physik (B.Ed.)");
        mInstance.insertCourse(0, 430, "Physik (Diplom)");
        mInstance.insertCourse(0, 430, "Physik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Physik (Master Lehramt Berufsbildende Schulen)");
        mInstance.insertCourse(0, 430, "Physik (Master Lehramt Gymnasium)");
        mInstance.insertCourse(0, 430, "Physik (Master Lehramt Real+)");
        mInstance.insertCourse(0, 430, "Physik (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "TechnoPhysik (B.Sc.)");
        mInstance.insertCourse(0, 430, "TechnoPhysik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Border Studies (M.A.)");
        mInstance.insertCourse(0, 430, "Geografie (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "Geografie (B.Ed.)");
        mInstance.insertCourse(0, 430, "Geografie (Master Lehramt Berufsbildende Schulen)");
        mInstance.insertCourse(0, 430, "Geografie (Master Lehramt Gymnasium)");
        mInstance.insertCourse(0, 430, "Geografie (Master Lehramt Real+)");
        mInstance.insertCourse(0, 430, "Raumplanung (B.Sc.)");
        mInstance.insertCourse(0, 430, "Stadt- und Regionalentwicklung (M.Sc.)");
        mInstance.insertCourse(0, 430, "Umweltplanung und Recht (M.Sc.)");
        mInstance.insertCourse(0, 430, "Cognitive Science (M.Sc.)");
        mInstance.insertCourse(0, 430, "Gesundheit (B.Ed.)");
        mInstance.insertCourse(0, 430, "Gesundheit (Master Lehramt Berufsbildende Schulen)");
        mInstance.insertCourse(0, 430, "Integrative Sozialwissenschaften (B.A.)");
        mInstance.insertCourse(0, 430, "Integrative Sozialwissenschaften (M.A.)");
        mInstance.insertCourse(0, 430, "Sozialkunde (B.Ed.)");
        mInstance.insertCourse(0, 430, "Sozialkunde (Master Lehramt Berufsbildende Schulen)");
        mInstance.insertCourse(0, 430, "Sozialkunde (Master Lehramt Gymnasium)");
        mInstance.insertCourse(0, 430, "Sozialkunde (Master Lehramt Real+)");
        mInstance.insertCourse(0, 430, "Sozialkunde (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "Sport (B.Ed.)");
        mInstance.insertCourse(0, 430, "Sport (Master Lehramt Berufsbildende Schulen)");
        mInstance.insertCourse(0, 430, "Sport (Master Lehramt Gymnasium)");
        mInstance.insertCourse(0, 430, "Sport (Master Lehramt Real+)");
        mInstance.insertCourse(0, 430, "Sport (Zertifikat Lehramt)");
        mInstance.insertCourse(0, 430, "Sportwissenschaften und Gesundheit (B.Sc.)");
        mInstance.insertCourse(0, 430, "Betriebswirtschaftslehre (M.Sc.)");
        mInstance.insertCourse(0, 430, "Betriebswirtschaftslehre (B.Sc.)");
        mInstance.insertCourse(0, 430, "Betriebswirtschaftslehre mit technischer Qualifikation (B.Sc.)");
        mInstance.insertCourse(0, 430, "Betriebswirtschaftslehre mit technischer Qualifikation (M.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftsingenieurwesen Studienrichtung Umwelt- und Verfahrenstechnik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftsingenieurwesen Studienrichtung Chemie (B.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftsingenieurwesen Studienrichtung Elektrotechnik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftsingenieurwesen Studienrichtung Informatik (B.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftsingenieurwesen Studienrichtung Maschinenbau (B.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftsingenieurwesen Studienrichtung Chemie (M.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftsingenieurwesen Studienrichtung Elektrotechnik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftsingenieurwesen Studienrichtung Informatik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftsingenieurwesen Studienrichtung Maschinenbau (M.Sc.)");
        mInstance.insertCourse(0, 430, "Wirtschaftsingenieurwesen Studienrichtung Umwelt- und Verfahrenstechnik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Aktualisierungskurs Strahlenschutz (Zertifikat)");
        mInstance.insertCourse(0, 430, "Auffrischungskurs für Brandschutzbeauftragte (Zertifikat)");
        mInstance.insertCourse(0, 430, "Baulicher Brandschutz (Zertifikat)");
        mInstance.insertCourse(0, 430, "Betriebswirtschaft und Management (M.A.)");
        mInstance.insertCourse(0, 430, "Branschutzbeauftragte/r (Zertifikat)");
        mInstance.insertCourse(0, 430, "Brandschutzplanung (M.Eng.)");
        mInstance.insertCourse(0, 430, "Digital Management (Zertifikat)");
        mInstance.insertCourse(0, 430, "Erwachsenenbildung (M.A.)");
        mInstance.insertCourse(0, 430, "Financial Engineering (M.Sc.)");
        mInstance.insertCourse(0, 430, "Lern und Entwicklungsauffälligkeiten im Kindesalter (Zertifikat)");
        mInstance.insertCourse(0, 430, "Management digitaler Bildungsprozesse (Zertifikat)");
        mInstance.insertCourse(0, 430, "Management von Gesundheits- und Sozialeinrichtungen (M.A.)");
        mInstance.insertCourse(0, 430, "Management von Kultur- und Non-Profit-Organisationen (M.A.)");
        mInstance.insertCourse(0, 430, "Master of Evaluation (M.A.)");
        mInstance.insertCourse(0, 430, "Medizinische Physik (M.Sc.)");
        mInstance.insertCourse(0, 430, "Medizinische Physik und Technik (Zertifikat)");
        mInstance.insertCourse(0, 430, "Nachhaltige Entwicklungszusammenarbeit (M.A.)");
        mInstance.insertCourse(0, 430, "Nanobiotechnology (Zertifikat)");
        mInstance.insertCourse(0, 430, "Organisation und Kommunikation (M.A.)");
        mInstance.insertCourse(0, 430, "Organisationsentwicklung (M.A.)");
        mInstance.insertCourse(0, 430, "Personalentwicklung (M.A.)");
        mInstance.insertCourse(0, 430, "Psychologie kindlicher Lern- und Entwicklungsauffälligkeiten (M.Sc.)");
        mInstance.insertCourse(0, 430, "Schulmanagement (M.A.)");
        mInstance.insertCourse(0, 430, "Software Engineering for Embedded Systems (M.Eng.)");
        mInstance.insertCourse(0, 430, "Steuerrecht für die Unternehmenspraxis (M.Sc.)");
        mInstance.insertCourse(0, 430, "Systemische Beratung (M.A.)");
        mInstance.insertCourse(0, 430, "Systemisches Management (Zertifikat)");
        mInstance.insertCourse(0, 430, "Technoethik (Zertifikat)");
        mInstance.insertCourse(0, 430, "Wirtschaftsrecht für die Unternehmenspraxis (M.A.)");
        // HS Stralsund
        mInstance.insertCourse(0, 431, "Betriebswirtschaftslehre (B.A.)");
        mInstance.insertCourse(0, 431, "Elektrotechnik (B.Sc.)");
        mInstance.insertCourse(0, 431, "International Management Studies in the Baltic Sea Region (BMS) (B.A)");
        mInstance.insertCourse(0, 431, "IT-Sicherheit und Mobile Systeme (B.Sc.)");
        mInstance.insertCourse(0, 431, "Leisure and Tourism Management (B.A.)");
        mInstance.insertCourse(0, 431, "Maschinenbau (B.Eng.)");
        mInstance.insertCourse(0, 431, "Maschinenbau, Dualer Studiengang (B.Eng.)");
        mInstance.insertCourse(0, 431, "Medizinisches Informationsmanagement/ eHealth (B.Sc.)");
        mInstance.insertCourse(0, 431, "Motorsport Engineering (B.Eng.)");
        mInstance.insertCourse(0, 431, "Produktionsmanagement (B.Eng.)");
        mInstance.insertCourse(0, 431, "Regenerative Energien (B.Sc.)");
        mInstance.insertCourse(0, 431, "Softwareentwicklung und Medieninformatik (B.Sc.)");
        mInstance.insertCourse(0, 431, "Wirtschaftsinformatik (B.Sc.)");
        mInstance.insertCourse(0, 431, "Wirtschaftsingenieurwesen (B.Eng.)");
        mInstance.insertCourse(0, 431, "Wirtschaftsingenieurwesen Elektrotechnik (B.Sc.)");
        mInstance.insertCourse(0, 431, "Wirtschaftsingenieurwesen, Frauenstudiengang (B.Eng.)");
        mInstance.insertCourse(0, 431, "Wirtschaftsingenieurwesen, Internationales (B.Eng.)");
        mInstance.insertCourse(0, 431, "Elektrotechnik (M.Sc.)");
        mInstance.insertCourse(0, 431, "Gesundheitsökonomie (M.Sc.)");
        mInstance.insertCourse(0, 431, "Informatik (M.Sc.)");
        mInstance.insertCourse(0, 431, "Management von kleinen und mittleren Unternehmen (KMU) (M.A.)");
        mInstance.insertCourse(0, 431, "Maschinenbau (M.Eng.)");
        mInstance.insertCourse(0, 431, "Medizintechnische Systeme (M.Sc.)");
        mInstance.insertCourse(0, 431, "Renewable Energy and E-Mobility (M.Eng.)");
        mInstance.insertCourse(0, 431, "Simulation and System Design (M.Eng.)");
        mInstance.insertCourse(0, 431, "Tourism Development Strategies (M.A.)");
        mInstance.insertCourse(0, 431, "Wirtschaftsinformatik (M.Sc.)");
        mInstance.insertCourse(0, 431, "Wirtschaftsingenieurwesen (M.Eng.)");
        mInstance.insertCourse(0, 431, "Ergänzungsstudium Wirtschaftsingenieurwesen");

        // Table category
        mInstance.insertCategory(1, 0, "Form der Hochschule");
        mInstance.insertCategory(1, 1, "Universität");
        mInstance.insertCategory(1, 2, "Fachhochschule");
        mInstance.insertCategory(1, 3, "Pädagogische Hochschule");
        mInstance.insertCategory(1, 4, "Duale Hochschule");

        mInstance.insertCategory(2, 0, "Form of university");
        mInstance.insertCategory(2, 1, "University");
        mInstance.insertCategory(2, 2, "University of Applied Sciences");
        mInstance.insertCategory(2, 3, "College of Education");
        mInstance.insertCategory(2, 4, "Cooperative State University");

        mInstance.insertCategory(3, 0, "Forme de collège");
        mInstance.insertCategory(3, 1, "Université");
        mInstance.insertCategory(3, 2, "Université des Sciences Appliquées");
        mInstance.insertCategory(3, 3, "Collège pédagogique");
        mInstance.insertCategory(3, 4, "Université d'État coopérative");

        mInstance.insertCategory(4, 0, "Forma de colegio");
        mInstance.insertCategory(4, 1, "Universidad");
        mInstance.insertCategory(4, 2, "Universidad de Ciencias Aplicadas");
        mInstance.insertCategory(4, 3, "Colegio pedagógico");
        mInstance.insertCategory(4, 4, "Universidad estatal cooperativa");

        mInstance.insertCategory(5, 0, "Forma di college");
        mInstance.insertCategory(5, 1, "Università");
        mInstance.insertCategory(5, 2, "Università delle scienze applicate");
        mInstance.insertCategory(5, 3, "Collegio pedagogico");
        mInstance.insertCategory(5, 4, "Università statale cooperativa");

        // Table tested
    }
}