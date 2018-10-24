package com.example.admin.customcamerademo


import android.hardware.Camera
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var camera: Camera
    lateinit var showCamera: ShowCamera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        camera = Camera.open()
        showCamera = ShowCamera(this, camera)
        frameLayout.addView(showCamera)
        capture.setOnClickListener(onClickListener)
    }

    private val mPictureCallback: Camera.PictureCallback = object:Camera.PictureCallback {
        override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
            var pictureFile: File? = getOutPutFile()
            println("Path is ${pictureFile?.path}")
            if (pictureFile == null) {
                return
            } else {
                try {
                    val fos = FileOutputStream(pictureFile)
                    fos.write(data)
                    fos.close()

                    camera?.startPreview()
                } catch (e : IOException) {
                    e.printStackTrace()
                }
            }
        }

    }

    private fun getOutPutFile(): File? {
        var state = Environment.getExternalStorageState()
        if (state != Environment.MEDIA_MOUNTED) {
            return null
        } else {
            var forder_gui = File(Environment.getExternalStorageDirectory(), "/GUI")

            if (!forder_gui.exists()) {
                forder_gui.mkdirs()
            }

            var outputFile = File(forder_gui,"temp.jpg")
            return outputFile
        }
    }

    private val onClickListener: View.OnClickListener = View.OnClickListener {
        when(it.id) {
            R.id.capture -> {
                if (camera != null) {
                    camera.takePicture(null, null, mPictureCallback)
                }
            }
        }
    }
}
