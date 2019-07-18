package com.example.mosy.objects

import android.content.Context
import com.example.mosy.helpers.GameScoreDatabaseHelper

object DatabaseComponent {

    fun checkDatabase(context: Context, dbHelper: GameScoreDatabaseHelper) {
        val cursor = dbHelper.getDbSortedByScore(dbHelper.readableDatabase)
        var counter = 0
        with(cursor) {
            while(cursor.moveToNext()) {
                if(counter < 5) {
                    if(Player.score >= getInt(getColumnIndex("SCORE"))) {
                        DialogBoxHelper.openDialogBox(context, dbHelper)
                        break
                    }
                    counter++
                }
            }
        }
        cursor.close()
    }
}