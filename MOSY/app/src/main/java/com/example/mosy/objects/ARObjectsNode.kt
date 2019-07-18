package com.example.mosy.objects

import com.google.ar.core.AugmentedImage
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3

class ARObjectsNode : AnchorNode() {

    private var image: AugmentedImage? = null

    fun setObjects(image: AugmentedImage) {
        this.image = image
        anchor = image.createAnchor(image.centerPose)

        ARValues.numberOfObjects = RandomNumberGenerator.generateRandomNumberOfObjects()
        for (i in 0..ARValues.numberOfObjects) {
            val localPosition = Vector3()
            val xValue: Float = RandomNumberGenerator
                .generateRandomValuesForXY(-0.5f * image.extentX, 0.5f * image.extentX)
            val zValue: Float = RandomNumberGenerator
                .generateRandomValuesForXY(-0.5f * image.extentZ, 0.5f * image.extentZ)
            localPosition.set(xValue, 0.0f, zValue)
            createRenderable(localPosition)
        }
    }

    private fun createRenderable(localPosition: Vector3) {
        val objectsNode = Node()
        //objectsNode.localScale = Vector3(0.03f, 0.03f, 0.03f)
        objectsNode.localScale = Vector3(0.1f, 0.1f, 0.1f)
        objectsNode.setParent(this)
        objectsNode.localPosition = localPosition
        objectsNode.localRotation = Quaternion(0.0f, 1.0f, 0.0f, 0.0f)
        objectsNode.renderable = ARValues.objectsRenderable
    }


}