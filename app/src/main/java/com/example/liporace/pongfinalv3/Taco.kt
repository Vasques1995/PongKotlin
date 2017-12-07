package com.example.liporace.pongfinalv3

import android.graphics.RectF
import android.util.Log


/**
 * Created by Liporace on 27/11/2017.
 */
open class Taco constructor(x: Int, y: Int, player : Int) {

    // RectF is an object that holds four coordinates - just what we need
    private val mRect: RectF

    // How long and high our mBat will be
    private val mLength: Float
    private val mHeight: Float

    // X is the far left of the rectangle which forms our mBat
    private var mXCoord: Float

    // Y is the top coordinate
    private var mYCoord: Float

    // This will hold the pixels per second speed that
    // the mBat will move
    private val mBatSpeed: Float

    // Which ways can the mBat move
    val STOPPED = 0
    val UP = 1
    val DOWN = 2

    // Is the mBat moving and in which direction
    private var mBatMoving = STOPPED

    // The screen length and width in pixels
    private var mScreenX: Int
    private var mScreenY: Int
    private var mPlayer : Int

    //Inicializando variáveis
    init {
        mPlayer = player
        mScreenX = x
        mScreenY = y

        mLength = (mScreenY / 25).toFloat()
        mHeight = (mScreenX / 8).toFloat()

        if(player == 1){
            mXCoord = (0).toFloat()
            mYCoord = (mScreenY / 2) + mHeight/2.toFloat()
        }
        else{
            mXCoord = (mScreenX  - mLength)
            mYCoord = (mScreenY / 2) + mHeight/2.toFloat()
        }
//        mXCoord = (mScreenX / 2).toFloat()
//        mYCoord = (mScreenY - 20).toFloat()

        mRect = RectF(mXCoord, mYCoord - mHeight, mXCoord + mLength, mYCoord)

        mBatSpeed = mScreenY.toFloat()
    }

    // This is a getter method to make the rectangle that
    // defines our bat available in PongView class
    fun getRect(): RectF = mRect

    // This method will be used to change/set if the mBat is going
    // left, right or nowhere
    fun setMovementState(state: Int?) {
        mBatMoving = state!!
    }

    // This update method will be called from update in PongView
    // It determines if the Bat needs to move and changes the coordinates
    // contained in mRect if necessary
    fun update(fps: Long) {

        if (mBatMoving == UP) {
            mYCoord = mYCoord - mBatSpeed / fps
        }

        if (mBatMoving == DOWN) {
            mYCoord = mYCoord + mBatSpeed / fps
        }

        // Make sure it's not leaving screen
        if (mRect.bottom > 840.toFloat()) {
            Log.e("PongView", "Chegou ao fundo da tela : " + mRect)
            mYCoord = mScreenY.toFloat()
        }
        if (mRect.top < 230.toFloat()) {
            Log.e("PongView", "Chegou ao topo da tela : " + mRect)
            mYCoord = 230.toFloat()
                    // The width of the Bat
                   // (mRect.top - mRect.bottom)
        }

        // Update the Bat graphics
        mRect.top = mYCoord
        mRect.bottom = mYCoord - mHeight
    }

    fun reset(x: Int, y: Int, player: Int) {
        if (player == 1){
            mXCoord = 0.toFloat()
            mYCoord = (mScreenY / 2) + mHeight/2.toFloat()
        }
        else if (player == 2){
            mXCoord = mScreenX - mLength
            mYCoord = (mScreenY / 2) + mHeight/2.toFloat()
        }
        mRect.top = mYCoord
        mRect.bottom = mYCoord - mHeight
    }
}