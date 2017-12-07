package com.example.liporace.pongfinalv3

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.view.Display

class PongActivity : Activity() {

    var pongView : PongView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val display : Display = windowManager.defaultDisplay
        var size : Point = Point();
        display.getSize(size)

        // Initialize pongView and set it as the view
        pongView = PongView(this,size.x, size.y,3,1)
        setContentView(pongView)
    }

    override fun onResume() {
        super.onResume()
        pongView?.resume()
    }

    override fun onPause() {
        super.onPause()
        pongView?.pause()
    }
}
