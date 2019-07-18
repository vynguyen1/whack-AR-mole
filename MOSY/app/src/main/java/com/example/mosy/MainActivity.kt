package com.example.mosy

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.mosy.helpers.*
import com.example.mosy.objects.*
import com.google.ar.core.*
import com.google.ar.sceneform.FrameTime
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var arFragment : CustomArFragment
    private val dbHelper = GameScoreDatabaseHelper(this)
    private var gameLogic: GameLogic = GameLogic()
    private lateinit var bluetoothConnection: BluetoothConnection
    //private var imageDetected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as CustomArFragment
        setUpListeners()

        GameTimer.setTimer(this, timeText, messageText, arFragment, dbHelper)
        bluetoothConnection = BluetoothConnection(this, intent)
        bluetoothConnection.setConnection()

        ModelArInitializer.setUpModel(this)
        resetTextViews()
    }

    // ----------------------------- GAME LOGIC -----------------------------------

    private fun sendData(angle: Int, strength: Int){
        if (bluetoothConnection.getBluetoothSocket() != null) {
            try{
                // angle & strength von Polarkooridinaten in XY
                val xPos = strength * Math.cos(Math.toRadians(angle.toDouble())).toFloat()
                val yPos = strength * Math.sin(Math.toRadians(angle.toDouble())).toFloat()

                Log.d("PolarkoordinatenInXY", "$xPos $yPos")
                if(GameController.running && GameController.gameHasStarted) {
                    ARGameComponent.moveARLight(arFragment, xPos, yPos, scoreText, this)
                }

                val pair = GameCoordinatesCalculator.calculateValuesForPanTilt(xPos, yPos)
                val pan = pair.first
                val tilt = pair.second

                val json: String = JsonObjectCreator.toJSON(pan.toInt(), tilt.toInt()) + "\n"
                Log.d("jsonString", json)

                bluetoothConnection.getBluetoothSocket()!!.outputStream.write(json.toByteArray())
            } catch(e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun resetTextViews() {
        messageText.text = ""
        messageText.visibility = View.INVISIBLE
        timeText.text = resources.getString(R.string.timer)
        scoreText.text = Player.score.toString()
        playButton.text = resources.getString(R.string.buttonPlay)
    }

    private fun showHighScore() {
        val intent = Intent(this@MainActivity, HighScoreActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    // ----------------------------- SET UP LISTENERS -----------------------------------

    private fun setUpListeners() {
        arFragment.arSceneView.scene.addOnUpdateListener(this::onUpdateFrame)
        playButton.setOnClickListener {
            if (!GameController.running && !GameController.gameHasStarted) {
                gameLogic.startGame(arFragment, this, playButton)
            } else if (GameController.running && GameController.gameHasStarted &&
                !GameController.gameIsFinished || !GameController.running &&
                GameController.gameHasStarted && GameController.gameIsFinished
            ) {
                gameLogic.endGame(arFragment, this)
                resetTextViews()
            }
        }
        highScoreButton.setOnClickListener {
            if (!GameController.running || GameController.gameIsFinished) {
                showHighScore()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Can't look at high scores while playing", Toast.LENGTH_SHORT
                ).show()
            }
        }
        joystickView.setOnMoveListener({ angle, strength ->
            sendData(angle, strength)
        }, 100)
    }

    // ---------------------------------- DATABASE ---------------------------------------

    fun setupAugmentedImagesDb(config: Config, session: Session): Boolean {
        val bitmap = loadAugmentedImage() ?: return false
        val augmentedImageDatabase = AugmentedImageDatabase(session)
        // Imagegröße anpassen (Breite in Metern)
        //augmentedImageDatabase.addImage("test_image", bitmap, 1.60f)  // spielfeld.jpg
        //augmentedImageDatabase.addImage("test_image", bitmap, 0.79f) //  mandebrot.jpg
        //augmentedImageDatabase.addImage("test_image", bitmap, 0.30f) //  test_image_stove.jpg
        //augmentedImageDatabase.addImage("test_image", bitmap, 0.72f) //  test_image_stove.jpg groß
        //augmentedImageDatabase.addImage("test_image", bitmap, 0.25f)   // qr.jpg
        augmentedImageDatabase.addImage("test_image", bitmap)
        config.augmentedImageDatabase = augmentedImageDatabase
        return true
    }

    private fun loadAugmentedImage(): Bitmap? {
        try {
            // Image austauschen
            val inputStream: InputStream = assets.open("field.jpeg")
            return BitmapFactory.decodeStream(inputStream)
        }
        catch (e: IOException) {
            Log.e("ImageLoad", "IO Exception", e)
        }
        return null
    }

    private fun onUpdateFrame(frameTime: FrameTime) {
        if(!GameController.running) {
            val frame = arFragment.arSceneView.arFrame

            if (frame == null || frame.camera.trackingState != TrackingState.TRACKING) {
                return
            }
            val augmentedImages = frame.getUpdatedTrackables(AugmentedImage::class.java)
            val toast = Toast.makeText(
                this@MainActivity, "Detecting Image...",
                Toast.LENGTH_SHORT
            )
            for (augmImage: AugmentedImage in augmentedImages) {
                if (augmImage.trackingState == TrackingState.PAUSED) {
                    toast.show()
                    break
                }
                else if (augmImage.trackingState == TrackingState.TRACKING) {
                    if (augmImage.name == "test_image" && !GameController.gameHasStarted) {
                        toast.cancel()
                        /*if (!GameController.running && !GameController.gameHasStarted &&
                            GameController.gameIsFinished) {
                            if(checkRotationOfAugImage(augmImage)) {
                                Toast.makeText(
                                    this@MainActivity, "Image detected",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@MainActivity, "Rotation not correct",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }*/
                        ARValues.augmentedImage = augmImage
                        //val qw = augmentedImage!!.centerPose.rotationQuaternion[0]
                        //val poseRotationArray: FloatArray = floatArrayOf(1.0f / qw, 0.0f, 0.0f, 0.0f)
                        //val quaternionPose = Pose.makeRotation(poseRotationArray)Failed to resolve: com.google.ar.sceneform.ux:sceneform-ux:1.2.0
                        //augmentedImage!!.centerPose.compose(quaternionPose)
                        ARValues.planeXLength = ARValues.augmentedImage!!.extentX
                        ARValues.planeZLength = ARValues.augmentedImage!!.extentZ
                        ARValues.centerPoseX = ARValues.augmentedImage!!.centerPose.tx()
                        ARValues.centerPoseY = ARValues.augmentedImage!!.centerPose.ty()
                        ARValues.centerPoseZ = ARValues.augmentedImage!!.centerPose.tz()
                        //imageDetected = true
                    }
                }
                else if (augmImage.trackingState == TrackingState.STOPPED) {
                    ARValues.augmentedImage = null
                    //imageDetected = false
                }
            }
        }
    }

    /*private fun checkRotationOfAugImage(augImage: AugmentedImage): Boolean {
        val quaternion = augImage.centerPose.rotationQuaternion
        var counter = 0
        if(abs(1.0f - quaternion[0]) > 0.01f) {
            counter++
        }
        for(i in 1..3) {
            if(abs(0.0f - quaternion[i]) > 0.01f) {
                counter++
            }
        }
        return counter <= 2
    }*/
}
