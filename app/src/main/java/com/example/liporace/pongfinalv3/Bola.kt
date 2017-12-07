package com.example.liporace.pongfinalv3

import android.graphics.RectF
import android.R.attr.left
import android.R.attr.right
import java.util.*
import android.R.attr.right
import android.R.attr.left




/**
 * Created by Liporace on 24/11/2017.
 */
open class Bola constructor(screenX : Int , screenY : Int){

    private var mRect: RectF
    private var mXVelocity: Float
    private var mYVelocity: Float
    private val mBallWidth: Float
    private val mBallHeight: Float

    private var screenAltura : Int
    private var screenLargura : Int

    init {

        screenLargura = screenX
        screenAltura = screenY
        //Tamanho da Bola
        mBallWidth = screenX / 100.toFloat()
        mBallHeight = mBallWidth

        //Velocidade inicial da bola
        mYVelocity = screenY / 4.toFloat()
        mXVelocity = mYVelocity;

        //Retangulo que é a estrutura da bola
        mRect = RectF()
    }

    //Conseguir uma referência ao objeto da bola
    fun getRekt() : RectF = mRect

    // Change the position each frame
    fun update(fps: Long) {
        mRect.left = mRect.left + mXVelocity / fps
        mRect.top = mRect.top + mYVelocity / fps
        mRect.right = mRect.left + mBallWidth
        mRect.bottom = mRect.top - mBallHeight
    }

    // Reverse the vertical heading
    fun reverseYVelocity() {
        mYVelocity = -mYVelocity
    }

    // Reverse the horizontal heading
    fun reverseXVelocity() {
        mXVelocity = -mXVelocity
    }

    fun setRandomXVelocity() {
        // Generate a random number either 0 or 1
        val generator = Random()
        val answer = generator.nextInt(2)
        if (answer == 0) {
            reverseXVelocity()
        }
    }

    // Speed up by 10%
    // A score of over 20 is quite difficult
    // Reduce or increase 10 to make this easier or harder
    fun increaseVelocity() {
        mXVelocity = mXVelocity + mXVelocity / 10
        mYVelocity = mYVelocity + mYVelocity / 10
    }

    fun clearObstacleY(y: Float) {
        mRect.bottom = y - mBallHeight
        mRect.top = y
    }

    fun clearObstacleX(x: Float) {
        mRect.left = x
        mRect.right = x + mBallWidth
    }

    fun reset(x: Int, y: Int) {
//        val randomizador = Random()
//        val resultBola : Int
//        if (randomizador.nextInt(5) != 0){
//            resultBola = randomizador.
//        }

        mRect.left = x / 2.toFloat()
        mRect.top = y / 2.toFloat()
        mRect.right = x / 2 + mBallWidth
        mRect.bottom = y /2 - mBallHeight
        mYVelocity = this.screenAltura / 4.toFloat()
        mXVelocity = mYVelocity
    }
}