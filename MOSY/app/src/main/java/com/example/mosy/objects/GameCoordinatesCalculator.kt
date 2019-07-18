package com.example.mosy.objects

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.TextView
import com.example.mosy.helpers.CustomArFragment
import kotlin.math.abs

object GameCoordinatesCalculator {

    fun calculateValuesForPanTilt(x: Float, y: Float): Pair<Float, Float> {
        // 99 anpassen, damit Ecken auch erreicht werden, 75 oder 70?
        val xPos = x * ((GameFieldValues.fieldWidth/2) / 75)
        val yPos = y * ((GameFieldValues.fieldLength/2) / 75)
        Log.d("xPosyPos", "$xPos $yPos")
        val alpha = Math.asin(
            xPos / Math.sqrt(
                (xPos * xPos).toDouble()
                        + (yPos * yPos).toDouble()
                        + (GameFieldValues.height * GameFieldValues.height).toDouble()
            )
        ) * (180/Math.PI)
        val beta = Math.asin(
            yPos / Math.sqrt(
                (xPos * xPos).toDouble()
                        + (yPos * yPos).toDouble()
                        + (GameFieldValues.height * GameFieldValues.height).toDouble()
            )
        ) * (180/Math.PI)

        Log.d("alpha-beta", "$alpha $beta")
        val alphaMax = Math.atan(((GameFieldValues.fieldWidth/2.0f)/GameFieldValues.height).toDouble()) * (180/Math.PI)
        val betaMax = Math.atan(((GameFieldValues.fieldLength/2.0f)/GameFieldValues.height).toDouble())* (180/Math.PI)
        Log.d("alpha-betaMax", "$alphaMax $betaMax")

        val pan = (((GameFieldValues.xMax - GameFieldValues.xNull)/alphaMax * alpha) + GameFieldValues.xNull).toFloat()
        val tilt = (((GameFieldValues.yMax - GameFieldValues.yNull)/betaMax * beta) + GameFieldValues.yNull).toFloat()
        Log.d("pantilt", "$pan $tilt")

        return Pair(pan, tilt)
    }

    fun checkPosition(arFragment: CustomArFragment, scoreText: TextView, context: Context) {
        if(GameController.check) {
            GameController.check = false
            /*val list: ArrayList<Int> = arrayListOf()

            for ((index, anchorNode) in ARValues.anchorNodes.withIndex()) {
                if (abs(anchorNode.anchor!!.pose.tx() - ARValues.anchorNodeLight!!.anchor!!.pose.tx()) <= 0.06f &&
                    abs(anchorNode.anchor!!.pose.tz() - ARValues.anchorNodeLight!!.anchor!!.pose.tz()) <= 0.06f
                ) {
                    list.add(index)
                }
            }
            for (index in list) {
                Thread.sleep(100)
                arFragment.arSceneView.scene.removeChild(ARValues.anchorNodes[index])
                ARValues.anchorNodes[index].anchor?.detach()
                ARValues.anchorNodes[index].setParent(null)
                Player.score += 1
                scoreText.text = Player.score.toString()
                ARValues.numberOfObjects--
                ARValues.anchorNodes.remove(ARValues.anchorNodes[index])
                if (ARValues.numberOfObjects < 0) {
                    ARGameComponent.destroyAnchors(arFragment)
                    ARGameComponent.createAnchors(arFragment)
                }
            }
            list.clear()
            */
            for (node in ARValues.anchorNodes!!.children) {
                if (abs(node.localPosition.x - ARValues.anchorNodeLight!!.children[0].localPosition.x) <= 0.06f &&
                    abs(node.localPosition.z - ARValues.anchorNodeLight!!.children[0].localPosition.z) <= 0.06f
                ) {
                    if(node.isEnabled) {
                        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                        Player.score += 1
                        scoreText.text = Player.score.toString()
                        node.isEnabled = false
                    }
                }
            }
            var catchedAll = true
            for(node in ARValues.anchorNodes!!.children) {
                if(node.isEnabled) {
                    catchedAll = false
                    break
                }
            }
            if(catchedAll) {
                ARGameComponent.destroyObjects(arFragment)
                ARGameComponent.createAnchors(arFragment)
            }
        }
        GameController.check = true
    }
}