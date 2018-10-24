package com.example.admin.customcamerademo

import android.content.Context
import android.content.res.Configuration
import android.hardware.Camera
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.io.IOException

class ShowCamera(context: Context, camera: Camera) : SurfaceView(context), SurfaceHolder.Callback {
    private val camera = camera
    private var surfaceHolder: SurfaceHolder = holder
    init {
        holder.addCallback(this)
    }
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        camera.stopPreview()
        camera.release()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        var params = camera.parameters
        val sizes = params.supportedPictureSizes
        var mSize: Camera.Size? = null
        for (size in sizes) {
            mSize = size
        }
        //Change the orientation
        if (this.resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            params.set("orientation","portrait")
            camera.setDisplayOrientation(90)
            params.setRotation(90)
        } else {
            params.set("orientation","landscape")
            camera.setDisplayOrientation(0)
            params.setRotation(0)
        }

        if (mSize != null) {
            params.setPictureSize(mSize.width, mSize.height)
        }
        camera.parameters = params
        try {
            camera.setPreviewDisplay(holder)
            camera.startPreview()
        }catch (e: IOException) {

        }
    }

}