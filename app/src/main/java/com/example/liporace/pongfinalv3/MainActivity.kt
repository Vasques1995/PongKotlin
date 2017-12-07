package com.example.liporace.pongfinalv3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : Activity() {

    private var buttonSingle : Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSingle = findViewById<Button>(R.id.single) as Button
    }


    /** Called when the user touches the button  */
    fun goPong(view: View) {
        val intent : Intent = Intent(this,PongActivity::class.java)
        startActivity(intent)
        // Do something in response to button click
    }

    fun goPong2(view : View){
        val intent : Intent = Intent(this,PongActivity2::class.java)
        startActivity(intent)
    }
}
