package com.example.liporace.pongfinalv3

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.os.Handler


class SplashActivity : Activity() {

    val DELAY : Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY)

    }
}
