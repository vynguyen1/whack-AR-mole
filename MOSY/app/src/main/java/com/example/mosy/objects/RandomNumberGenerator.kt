package com.example.mosy.objects

import kotlin.random.Random

object RandomNumberGenerator {

    fun generateRandomNumberOfObjects(): Int {
        return Random.nextInt(5)
    }

    fun generateRandomValuesForXY(centerPoseValue: Float, augmentedImageValue: Float): Float {
        /*return Random.nextDouble(
            (centerPoseValue - augmentedImageValue / 2).toDouble(),
            (centerPoseValue + augmentedImageValue / 2).toDouble()).toFloat()*/
        return Random.nextDouble(centerPoseValue.toDouble(),
            augmentedImageValue.toDouble()).toFloat()
    }
}