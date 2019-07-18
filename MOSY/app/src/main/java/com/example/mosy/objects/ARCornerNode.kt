package com.example.mosy.objects

import com.google.ar.core.AugmentedImage
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3

class ARCornerNode : AnchorNode() {

    private var image: AugmentedImage? = null

    fun setCorners(image: AugmentedImage) {
        this.image = image
        anchor = image.createAnchor(image.centerPose)

        val localPosition = Vector3()

        // Upper left corner.
        localPosition.set(-0.5f * image.extentX, 0.0f, -0.5f * image.extentZ)
        createRenderable(localPosition)

        // Upper right corner.
        localPosition.set(0.5f * image.extentX, 0.0f, -0.5f * image.extentZ)
        createRenderable(localPosition)

        // Lower right corner.
        localPosition.set(0.5f * image.extentX, 0.0f, 0.5f * image.extentZ)
        createRenderable(localPosition)

        // Lower left corner.
        localPosition.set(-0.5f * image.extentX, 0.0f, 0.5f * image.extentZ)
        createRenderable(localPosition)
    }

    private fun createRenderable(localPosition: Vector3) {
        val cornerNode = Node()
        //cornerNode.localScale = Vector3(0.05f, 0.05f, 0.05f)
        cornerNode.localScale = Vector3(0.15f, 0.15f, 0.15f)
        cornerNode.setParent(this)
        cornerNode.localPosition = localPosition
        cornerNode.renderable = ARValues.cornersRenderable
    }

}