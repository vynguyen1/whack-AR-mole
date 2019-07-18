package com.example.mosy.objects

import android.content.Context
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.example.mosy.R
import com.example.mosy.helpers.CustomArFragment
import com.example.mosy.helpers.GameScoreDatabaseHelper
import java.util.concurrent.TimeUnit

object GameTimer {

    lateinit var countDownTimer: CountDownTimer

    fun setTimer(context: Context, timeText: TextView, messageText: TextView, arFragment: CustomArFragment,
                         dbHelper: GameScoreDatabaseHelper) {
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes: Long = TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished)
                val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                if(seconds < 10) {
                    timeText.text = String.format("%d:0%d", minutes, seconds)
                } else {
                    timeText.text = String.format("%d:%d", minutes, seconds)
                }
                if(!GameController.running) {
                    this.cancel()
                }
            }
            override fun onFinish() {
                GameController.running = false
                GameController.gameIsFinished = true
                val message = context.resources.getString(R.string.messageFinished) + "\n" +
                        context.resources.getString(R.string.messageScore) + Player.score.toString()
                messageText.text = message
                messageText.visibility = View.VISIBLE
                ARGameComponent.destroyAnchors(arFragment)
                DatabaseComponent.checkDatabase(context, dbHelper)
            }
        }
    }
}