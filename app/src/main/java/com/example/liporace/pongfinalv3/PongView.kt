package com.example.liporace.pongfinalv3

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceView
import android.view.SurfaceHolder
import android.util.Log
import android.view.MotionEvent


/**
 * Created by Liporace on 05/12/2017.
 */
open class PongView constructor(context: Context, x: Int, y: Int, pontMáxima: Int, modo: Int) : SurfaceView(context), Runnable {

    // The size of the screen in pixels
    var mScreenX: Int = 0
    var mScreenY: Int = 0

    var mPontMaxima: Int

    var mModo: Int

    // We need a SurfaceHolder object
    // We will see it in action in the draw method soon.
    var mOurHolder: SurfaceHolder? = null

    // The players mBat
    var mTaco: Taco
    var mTaco2: Taco


    var mPaint: Paint? = null

    // A mBall
    var mBola: Bola

    init {

        this.mPontMaxima = pontMáxima

        this.mModo = modo

        mScreenX = x
        mScreenY = y

        mOurHolder = holder
        mPaint = Paint()

        mTaco = Taco(mScreenX, mScreenY, 1)
        mTaco2 = Taco(mScreenX, mScreenY, 2)
        mBola = Bola(mScreenX, mScreenY)

        setupAndRestart()
    }


    fun setupAndRestart() {
        mBola.reset(mScreenX, mScreenY)
        mTaco.reset(mScreenX, mScreenY, 1)
        mTaco2.reset(mScreenX, mScreenY, 2)
//        mScorePlayer2 = 0
//        mScorePlayer1 = 0
    }

    override fun run() {
        while (mPlaying) {
            // Capture the current time in milliseconds in startFrameTime
            val startFrameTime = System.currentTimeMillis()

            // Update the frame
            // Update the frame
            if (!mPaused) {
                update()
            }

            // Draw the frame
            draw()

            /*
            Calculate the FPS this frame
            We can then use the result to
            time animations in the update methods.
        */
            val timeThisFrame = System.currentTimeMillis() - startFrameTime
            if (timeThisFrame >= 1) {
                mFPS = 1000 / timeThisFrame
            }
        }
    }

    fun update() {
        mBola.update(mFPS)

        mTaco.update(mFPS)
        mTaco2.update(mFPS)

        //Movimento do tacoCPU
        if (mModo == 1) {
            if (mBola.getRekt().bottom < mTaco2.getRect().bottom) {
                mTaco2.setMovementState(mTaco2.UP)
            } else {
                mTaco2.setMovementState(mTaco2.DOWN)
            }
        }


        // Checa se a bola bateu no primeiro Taco
        if ((mBola.getRekt().left < mTaco.getRect().right && mTaco.getRect().left < mBola.getRekt().right)
                &&
                mBola.getRekt().top > mTaco.getRect().bottom && mTaco.getRect().top > mBola.getRekt().bottom) {
            Log.e("PongActivity", "Bola atingiu o Taco1 : " + mBola.getRekt())
            mBola.setRandomXVelocity()
            mBola.reverseXVelocity()
            mBola.clearObstacleX(mTaco.getRect().left - 10)
            mBola.increaseVelocity()
        }

        //Checa se a bola bateu no segundo Taco
        if ((mBola.getRekt().left < mTaco2.getRect().right && mTaco2.getRect().left < mBola.getRekt().right)
                &&
                mBola.getRekt().top > mTaco2.getRect().bottom && mTaco2.getRect().top > mBola.getRekt().bottom) {
            Log.e("PongActivity", "Bola atingiu o Taco2 : " + mBola.getRekt())
            mBola.setRandomXVelocity()
            mBola.reverseXVelocity()
            mBola.clearObstacleX(mTaco2.getRect().right - 10)
            mBola.increaseVelocity()
        }


        // Bounce the mBall back when it hits the bottom of screen
        if (mBola.getRekt().top > mScreenY) {
            Log.e("PongActivity", "Bola atingiu o fundo : " + mBola.getRekt())
            mBola.reverseYVelocity()
            mBola.clearObstacleY((mScreenY - 5).toFloat())
        }

        // Bounce the mBall back when it hits the top of screen
        if (mBola.getRekt().bottom <= 5.toFloat()) {
            Log.e("PongActivity", "Bola atingiu o topo : " + mBola.getRekt())
            mBola.reverseYVelocity()
            mBola.clearObstacleY(30.toFloat())
        }
        // If the mBall hits left wall bounce
        if (mBola.getRekt().left <= 5) {
            Log.e("PongActivity", "Bola atingiu a esquerda : " + mBola.getRekt())
            mBola.reverseXVelocity()
//            mBola.clearObstacleX(5.toFloat());

            mScorePlayer2++
//            if (mScorePlayer2 == mPontMaxima){
//                fun goHome(){
//                    val intent : Intent
//                }
//            }
            mPaused = true
            setupAndRestart()
        }


        // If the mBall hits right wall bounce
        if (mBola.getRekt().right > mScreenX) {
            Log.e("PongActivity", "Bola atingiu a direita : " + mBola.getRekt())
            mBola.reverseXVelocity();

            mScorePlayer1++
            mPaused = true
            setupAndRestart()
//            mBola.clearObstacleX((mScreenX - 22).toFloat());
        }

    }

    fun draw() {
// Make sure our drawing surface is valid or we crash
        if (mOurHolder?.getSurface()!!.isValid()) {

            // Draw everything here

            // Lock the mCanvas ready to draw
            mCanvas = mOurHolder?.lockCanvas()

            // Clear the screen with my favorite color
            mCanvas?.drawColor(Color.argb(255, 56, 56, 56))

            // Choose the brush color for drawing
            mPaint?.setColor(Color.argb(255, 255, 255, 255))

            // Draw the mBat
            mCanvas?.drawRect(mTaco.getRect(), mPaint)

            mCanvas?.drawRect(mTaco2.getRect(), mPaint)

            // Draw the mBall
            mCanvas?.drawRect(mBola.getRekt(), mPaint)


            // Change the drawing color to white
            mPaint?.setColor(Color.argb(255, 255, 255, 255));

            // Draw the mScore
            mPaint?.setTextSize(40.toFloat());
            mCanvas?.drawText("Score: " + mScorePlayer1 + " X " + mScorePlayer2, 10.toFloat(), 50.toFloat(), mPaint);

            // Draw everything to the screen
            mOurHolder?.unlockCanvasAndPost(mCanvas);
        }
    }

    // If the Activity is paused/stopped
    // shutdown our thread.
    fun pause() {
        mPlaying = false
        try {
            mGameThread?.join()
        } catch (e: InterruptedException) {
            Log.e("Error:", "joining thread")
        }

    }

    // If the Activity starts/restarts
    // start our thread.
    fun resume() {
        mPlaying = true
        mGameThread = Thread(this)
        mGameThread?.start()
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {

        when (motionEvent.action and MotionEvent.ACTION_MASK) {

        // Player has touched the screen
            MotionEvent.ACTION_DOWN -> {

                mPaused = false
                Log.e("PongView", "Passou do pause")
                // Is the touch on the right or left?
                if (mModo == 1) {
                    Log.e("PongActivity", "Entrou no Modo01")
                    if (motionEvent.y < mScreenY / 2) {
                        Log.e("PongView", "Entrou no IF, posição : " + mTaco.getRect())
                        mTaco.setMovementState(mTaco.UP)
                    } else {
                        Log.e("PongView", "Entrou no ELSE, posição :" + mTaco.getRect())
                        mTaco.setMovementState(mTaco.DOWN)
                    }
                } else if (mModo == 2) {
                    Log.e("PongActivity", "Entrou no Modo02")
                    if (motionEvent.x < mScreenX / 2) {
                        if (motionEvent.y < mScreenY / 2) {
                            Log.e("PongView", "Entrou no IF, posição : " + mTaco.getRect())
                            mTaco.setMovementState(mTaco.UP)
                        } else {
                            Log.e("PongView", "Entrou no ELSE, posição :" + mTaco.getRect())
                            mTaco.setMovementState(mTaco.DOWN)
                        }
                    } else {
                        if (motionEvent.y < mScreenY / 2) {
                            Log.e("PongView", "Entrou no IF, posição : " + mTaco.getRect())
                            mTaco2.setMovementState(mTaco2.UP)
                        } else {
                            Log.e("PongView", "Entrou no ELSE, posição :" + mTaco.getRect())
                            mTaco2.setMovementState(mTaco2.DOWN)
                        }
                    }
                }
            }
        // Player has removed finger from screen
            MotionEvent.ACTION_UP ->
                mTaco.setMovementState(mTaco.STOPPED)
//            mTaco2.setMovementState(mTaco2.STOPPED)
        }
        return true
    }

    // This is our thread
    var mGameThread: Thread? = null

    // A boolean which we will set and unset
// when the game is running- or not
// It is volatile because it is accessed from inside and outside the thread
    @Volatile
    var mPlaying: Boolean = false

    // Game is mPaused at the start
    var mPaused = true

    // A Canvas and a Paint object
    var mCanvas: Canvas? = null

    // This variable tracks the game frame rate
    var mFPS: Long = 0

    // The mScore
    var mScorePlayer1 = 0
    var mScorePlayer2 = 0
}