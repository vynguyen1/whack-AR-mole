package com.example.mosy.objects

import android.content.Context
import android.widget.Toast
import com.example.mosy.R
import com.google.ar.sceneform.rendering.ModelRenderable

object ModelArInitializer {

    fun setUpModel(context: Context) {
        ModelRenderable.builder()
            .setSource(context, R.raw.andy_dance)
            .build()
            .thenAccept { modelRenderable -> ARValues.cornersRenderable = modelRenderable }
            .exceptionally {
                Toast.makeText(context.applicationContext, "Unable to load model", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(context, R.raw.digda_one)
            .build()
            .thenAccept { modelRenderable -> ARValues.objectsRenderable = modelRenderable }
            .exceptionally {
                Toast.makeText(context.applicationContext, "Unable to load model", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(context, R.raw.osoted)
            .build()
            .thenAccept { modelRenderable -> ARValues.arLightRenderable = modelRenderable }
            .exceptionally {
                Toast.makeText(context.applicationContext, "Unable to load model", Toast.LENGTH_SHORT).show()
                null
            }
    }
}