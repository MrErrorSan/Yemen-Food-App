package com.example.yemenfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Login.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
    public boolean insertData(String username, String password, String email) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, username);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PASSWORD, password);
            long result = db.insert(TABLE_USERS, null, values);
            return result != -1;
        } catch (SQLiteException e) {
            // Handle any exceptions here
            e.printStackTrace();
        }
        return false;
    }
    private boolean checkIfExists(String columnName, String columnValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = columnName + " = ?";
        String[] selectionArgs = { columnValue.trim() };
        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkUsername(String username) {
        return checkIfExists(COLUMN_USERNAME, username);
    }

    public boolean checkEmail(String email) {
        return checkIfExists(COLUMN_EMAIL, email);
    }

    public boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { username.trim(), password.trim() };
        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);
        boolean usernamePasswordMatch = cursor.getCount() > 0;
        cursor.close();
        return usernamePasswordMatch;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { email.trim(), password.trim() };
        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);
        boolean emailPasswordMatch = cursor.getCount() > 0;
        cursor.close();
        return emailPasswordMatch;
    }

    public boolean login(String username, String password) {
        return checkEmailPassword(username, password) || checkUsernamePassword(username, password);
    }

    public String getPasswordByUsernameOrEmail(String usernameOrEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ? OR " + COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { usernameOrEmail.trim(), usernameOrEmail.trim() };
        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);

        String password = null;
        if (cursor.moveToFirst()) {
            int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            if (passwordIndex >= 0) {
                password = cursor.getString(passwordIndex);
            }
        }

        cursor.close();
        return password;
    }
    public boolean deleteUserByUsernameOrEmail(String usernameOrEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USERNAME + " = ? OR " + COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { usernameOrEmail.trim(), usernameOrEmail.trim() };
        int rowsDeleted = db.delete(TABLE_USERS, selection, selectionArgs);
        return rowsDeleted > 0;
    }
    public boolean deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_USERS, null, null);
        return rowsDeleted > 0;
    }
    public void displayAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            Log.d("<<<User>>>", "################################");
            do {
                if (usernameIndex >= 0) {
                    String username = cursor.getString(usernameIndex);
                    Log.d("<<<User>>>", "Username >>>  " + username);
                }

                if (emailIndex >= 0) {
                    String email = cursor.getString(emailIndex);
                    Log.d("<<<User>>>", "Email   >>>   " + email);

                }
                if (passwordIndex >= 0) {
                    String password = cursor.getString(passwordIndex);
                    Log.d("<<<User>>>", "Password >>>  " + password);
                    Log.d("<<<User>>>", "################################");
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

}
