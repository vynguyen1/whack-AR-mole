package com.example.mosy.objects

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.example.mosy.helpers.CustomArFragment
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import kotlin.math.abs

object ARGameComponent {

    fun moveARLight(arFragment: CustomArFragment, xPos: Float, zPos: Float, scoreText: TextView, context: Context) {
        /*val xMin = ARValues.corners[0].anchor!!.pose.tx()
        val xMax = ARValues.corners[2].anchor!!.pose.tx()
        val yMin = ARValues.corners[0].anchor!!.pose.tz()
        val yMax = ARValues.corners[2].anchor!!.pose.tz()*/
        val xMin = ARValues.corners!!.children[0].localPosition.x
        val xMax = ARValues.corners!!.children[1].localPosition.x
        val yMin = ARValues.corners!!.children[2].localPosition.z
        val yMax = ARValues.corners!!.children[1].localPosition.z

        // 198 anpassen, damit Ecken auch erreicht werden, 150 oder 140?
        val xValue : Float = (xPos / 150 * abs(xMax - xMin))
        val yValue = 0.0f
        val zValue : Float = -(zPos / 150 * abs(yMax - yMin))


        Log.d("xPos,zPos", "$xPos $zPos")
        Log.d("centerPose", "$ARValues.centerPoseX $ARValues.centerPoseZ")
        for(corner in ARValues.corners!!.children) {
            Log.d("corners", corner.localPosition.x.toString() + " "
                    + corner.localPosition.z.toString())
        }
        Log.d("ARLight", "$xValue $zValue")

        /*val poseTranslationArray : FloatArray = floatArrayOf(xValue, yValue, zValue)
        val translationPose = Pose.makeTranslation(poseTranslationArray)
        val anchor = ARValues.augmentedImage!!.createAnchor(translationPose)
        ARValues.anchorNodeLight!!.anchor = anchor*/
        //val positionOfLight = calculatePositionInGameWorldOfAnchors(anchorNodeLight)
        val localPosition = Vector3()
        localPosition.set(xValue, yValue, zValue)
        ARValues.anchorNodeLight!!.children[0].localPosition = localPosition
        GameCoordinatesCalculator.checkPosition(arFragment, scoreText, context)
    }

    fun createAnchors(arFragment: CustomArFragment) {
        ARValues.anchorNodes = ARObjectsNode()
        ARValues.anchorNodes!!.setParent(arFragment.arSceneView.scene)
        ARValues.anchorNodes!!.setObjects(ARValues.augmentedImage!!)
        /*ARValues.numberOfObjects = RandomNumberGenerator.generateRandomNumberOfObjects()
        for (i in 0..ARValues.numberOfObjects) {
            val xValue: Float = RandomNumberGenerator
                .generateRandomValuesForXY(ARValues.centerPoseX, ARValues.planeXLength)
            val yValue: Float = ARValues.centerPoseY
            val zValue: Float = RandomNumberGenerator
                .generateRandomValuesForXY(ARValues.centerPoseZ, ARValues.planeZLength)

            val poseTranslationArray: FloatArray = floatArrayOf(xValue, yValue, zValue)
            val translationPose = Pose.makeTranslation(poseTranslationArray)
            val anchor = ARValues.augmentedImage!!.createAnchor(translationPose)
            Log.d("anchorsCreated", anchor.pose.tx().toString() + " " + anchor.pose.tz().toString())
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)
            ARValues.anchorNodes.add(anchorNode)
            createModel(anchorNode)
        }*/
        // createCornerAnchors(arFragment)
    }

    fun createARCorners(arFragment: CustomArFragment) {
        ARValues.corners = ARCornerNode()
        ARValues.corners!!.setParent(arFragment.arSceneView.scene)
        ARValues.corners!!.setCorners(ARValues.augmentedImage!!)
    }

    fun createARLight(arFragment: CustomArFragment) {
        val anchor = ARValues.augmentedImage!!.createAnchor(ARValues.augmentedImage!!.centerPose)
        ARValues.anchorNodeLight = AnchorNode(anchor)
        ARValues.anchorNodeLight!!.setParent(arFragment.arSceneView.scene)
        createModelForARLight()
    }

    /*private fun createCornerAnchors(arFragment: CustomArFragment) {
        var xValue : Float = (ARValues.centerPoseX - ARValues.planeXLength / 2)
        val yValue : Float = ARValues.centerPoseY
        var zValue : Float = (ARValues.centerPoseZ - ARValues.planeZLength / 2)
        createAnchorNodeForCorners(arFragment, xValue, yValue, zValue)
        xValue = (ARValues.centerPoseX + ARValues.planeXLength / 2)
        createAnchorNodeForCorners(arFragment, xValue, yValue, zValue)
        zValue = (ARValues.centerPoseZ + ARValues.planeZLength / 2)
        createAnchorNodeForCorners(arFragment, xValue, yValue, zValue)
        xValue = (ARValues.centerPoseX - ARValues.planeXLength / 2)
        createAnchorNodeForCorners(arFragment, xValue, yValue, zValue)
    }

    private fun createAnchorNodeForCorners(arFragment: CustomArFragment, xValue: Float, yValue: Float, zValue: Float) {
        val poseTranslationArray : FloatArray = floatArrayOf(xValue, yValue, zValue)
        val translationPose = Pose.makeTranslation(poseTranslationArray)
        val anchor = ARValues.augmentedImage!!.createAnchor(translationPose)
        Log.d("anchorsCreatedCorner", anchor.pose.tx().toString() + " " + anchor.pose.tz().toString())
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)
        ARValues.corners.add(anchorNode)
        createModelForCorner(anchorNode)
    }

    private fun createModel(anchorNode: AnchorNode) {
        //val andy = TransformableNode(arFragment.transformationSystem)
        val andy = Node()    // -> nicht bewegbar
        //andy.scaleController.maxScale = 0.01f
        //andy.scaleController.minScale = 0.01f
        andy.localRotation = Quaternion(0.0f, 1.0f, 0.0f, 0.0f)
        andy.localScale = Vector3(0.03f, 0.03f, 0.03f)
        //andy.localScale = Vector3(0.1f, 0.1f, 0.1f)
        andy.setParent(anchorNode)
        andy.renderable = ARValues.objectsRenderable
        //andy.select()
    }*/

    /*private fun createModelForCorner(anchorNode: AnchorNode) {
        val food = Node()
        food.localScale = Vector3(0.05f, 0.05f, 0.05f)
        //food.localScale = Vector3(0.15f, 0.15f, 0.15f)
        food.setParent(anchorNode)
        food.renderable = ARValues.cornersRenderable
    }*/

    private fun createModelForARLight() {
        val osoted = Node()
        //osoted.localScale = Vector3(0.03f, 0.03f, 0.03f)
        osoted.localScale = Vector3(0.1f, 0.1f, 0.1f)
        osoted.setParent(ARValues.anchorNodeLight)
        osoted.renderable = ARValues.arLightRenderable
        osoted.isEnabled = false
    }

    fun destroyObjects(arFragment: CustomArFragment) {
        arFragment.arSceneView.scene.removeChild(ARValues.anchorNodes)
        ARValues.anchorNodes!!.anchor?.detach()
        ARValues.anchorNodes!!.setParent(null)
        ARValues.anchorNodes = null
    }

    fun destroyAnchors(arFragment: CustomArFragment) {

        // For Objects
        /*for(anchorNode in ARValues.anchorNodes) {
            arFragment.arSceneView.scene.removeChild(anchorNode)
            anchorNode.anchor?.detach()
            anchorNode.setParent(null)
        }
        ARValues.anchorNodes.clear()*/
        if(ARValues.anchorNodes != null) {
            arFragment.arSceneView.scene.removeChild(ARValues.anchorNodes)
            ARValues.anchorNodes!!.anchor?.detach()
            ARValues.anchorNodes!!.setParent(null)
            ARValues.anchorNodes = null
        }

        // For ARCorners
        /*for(anchorNode in ARValues.corners) {
            arFragment.arSceneView.scene.removeChild(anchorNode)
            anchorNode.anchor?.detach()
            anchorNode.setParent(null)
        }
        ARValues.corners.clear()*/
        if(ARValues.corners != null) {
            arFragment.arSceneView.scene.removeChild(ARValues.corners)
            ARValues.corners!!.anchor?.detach()
            ARValues.corners!!.setParent(null)
            ARValues.corners = null
        }

        // For ARLight
        if(ARValues.anchorNodeLight != null) {
            arFragment.arSceneView.scene.removeChild(ARValues.anchorNodeLight)
            ARValues.anchorNodeLight!!.anchor?.detach()
            ARValues.anchorNodeLight!!.setParent(null)
            ARValues.anchorNodeLight = null
        }
    }
}