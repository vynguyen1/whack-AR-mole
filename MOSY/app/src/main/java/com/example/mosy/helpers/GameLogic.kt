package com.example.mosy.helpers

import android.content.Context
import android.widget.Button
import android.widget.Toast
import com.example.mosy.R
import com.example.mosy.objects.*

class GameLogic {

    fun startGame(arFragment: CustomArFragment, context: Context, playButton: Button) {
        if(ARValues.augmentedImage != null) {
            GameController.running = true
            GameController.gameHasStarted = true
            GameController.gameIsFinished = false
            playButton.text = context.resources.getString(R.string.buttonEndGame)
            ARGameComponent.createAnchors(arFragment)
            ARGameComponent.createARCorners(arFragment)
            ARGameComponent.createARLight(arFragment)
            GameTimer.countDownTimer.start()
        } else {
            Toast.makeText(context, "Detect image first", Toast.LENGTH_SHORT).show()
        }
    }

    fun endGame(arFragment: CustomArFragment, context: Context) {
        GameController.running = false
        GameController.gameHasStarted = false
        GameController.gameIsFinished = true
        Player.score = 0
        Player.name = ""
        GameTimer.countDownTimer.cancel()
        //gameTimer.getCountDownTimer().cancel()
        ARGameComponent.destroyAnchors(arFragment)
        Toast.makeText(context, "Spiel beendet", Toast.LENGTH_SHORT).show()
        //ARValues.augmentedImage = null
    }
}