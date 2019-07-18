package com.example.mosy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.mosy.helpers.GameScoreDatabaseHelper

class HighScoreActivity : AppCompatActivity() {

    private val dbHelper = GameScoreDatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)
        seeHighScores()
    }

    private fun seeHighScores() {
        val db = dbHelper.readableDatabase
        val cursor = dbHelper.getDbSortedByScore(db)

        val views: ArrayList<Pair<TextView, TextView>> = getViews()

        var index = 0
        with(cursor) {
            while(moveToNext()) {
                if(index > 4) {
                    break
                }
                val nameColumnId = getColumnIndexOrThrow("NAME")
                val name = getString(nameColumnId)
                val scoreColumnId = getColumnIndexOrThrow("SCORE")
                val score = getInt(scoreColumnId)

                val pair = views[index]
                pair.first.text = name
                pair.second.text = score.toString()

                index++
            }
        }
        cursor.close()
    }

    private fun getViews(): ArrayList<Pair<TextView, TextView>> {
        val views: ArrayList<Pair<TextView, TextView>> = arrayListOf()

        val nameTop1 = findViewById<TextView>(R.id.Top1Name)
        val scoreTop1 = findViewById<TextView>(R.id.Top1Score)
        views.add(Pair(nameTop1, scoreTop1))

        val nameTop2 = findViewById<TextView>(R.id.Top2Name)
        val scoreTop2 = findViewById<TextView>(R.id.Top2Score)
        views.add(Pair(nameTop2, scoreTop2))

        val nameTop3 = findViewById<TextView>(R.id.Top3Name)
        val scoreTop3 = findViewById<TextView>(R.id.Top3Score)
        views.add(Pair(nameTop3, scoreTop3))

        val nameTop4 = findViewById<TextView>(R.id.Top4Name)
        val scoreTop4 = findViewById<TextView>(R.id.Top4Score)
        views.add(Pair(nameTop4, scoreTop4))

        val nameTop5 = findViewById<TextView>(R.id.Top5Name)
        val scoreTop5 = findViewById<TextView>(R.id.Top5Score)
        views.add(Pair(nameTop5, scoreTop5))

        return views
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

}
