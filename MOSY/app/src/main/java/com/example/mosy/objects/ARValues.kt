package com.example.mosy.objects

import com.google.ar.core.AugmentedImage
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable

object ARValues {

    var augmentedImage: AugmentedImage? = null
    var planeXLength: Float = 0.0f
    var planeZLength: Float = 0.0f
    var centerPoseX: Float = 0.0f
    var centerPoseY: Float = 0.0f
    var centerPoseZ: Float = 0.0f

    var anchorNodeLight: AnchorNode? = null
    //var corners : ArrayList<AnchorNode> = arrayListOf()
    var corners : ARCornerNode? = null
    //var anchorNodes : ArrayList<AnchorNode> = arrayListOf()
    var anchorNodes : ARObjectsNode? = null

    var cornersRenderable: ModelRenderable? = null
    var arLightRenderable: ModelRenderable? = null
    var objectsRenderable: ModelRenderable? = null

    var numberOfObjects: Int = 0
}