package com.example.mosy.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GameScoreDatabaseHelper(val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "HIGH_SCORES"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        updateDatabase(db, 0, DATABASE_VERSION)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS HIGH_SCORES")
        updateDatabase(db, oldVersion, newVersion)
    }

    fun updateDatabase(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("CREATE TABLE HIGH_SCORES ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "SCORE INTEGER);")
        insertScore(db, "Default", 1)
    }

    fun insertScore(db: SQLiteDatabase, name: String, score: Int) {
        val contentValues = ContentValues()
        contentValues.put("NAME", name)
        contentValues.put("SCORE", score)
        db.insert("HIGH_SCORES", null, contentValues)
    }

    fun getDbSortedByScore(db: SQLiteDatabase): Cursor {
        //val projection = arrayOf("_id", "NAME", "SCORE")
        val sortOrder = "SCORE DESC"

        return db.query(
            "HIGH_SCORES",   // The table to query
            null,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )
    }
}