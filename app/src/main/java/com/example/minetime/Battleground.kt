package com.example.minetime

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat

var xCoordinates = mutableListOf<Int>()
var yCoordinates = mutableListOf<Int>()

var numberOfMines = 0
var runCount = 0

var xClick = 0
var yClick = 0


class Battleground(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    var bg = Paint()
    var line = Paint()
    var bomb = Paint()
    var disguise = Paint()

    init{
        bg.color = Color.BLACK
        bg.style = Paint.Style.FILL_AND_STROKE

        line.color = Color.WHITE
        line.style = Paint.Style.FILL
        line.isAntiAlias = true
        line.strokeWidth = 5f

        bomb.color = Color.RED
        bomb.style = Paint.Style.FILL_AND_STROKE
        bomb.isAntiAlias = true

        disguise.color = ContextCompat.getColor(context, R.color.space_gray)
        disguise.style = Paint.Style.FILL_AND_STROKE
        disguise.isAntiAlias = true

    }

    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int)
    {
        super.onSizeChanged(width, height, oldwidth, oldheight)
    }

    var startX = 100f
    var startY = 100f


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(),bg)

        for(i in 1..9){
            canvas?.drawLine(60f + startX*i,160f,60f + startX*i ,960f,line)
        }

        for(i in 1..9){
            canvas?.drawLine(160f,60f + startY*i,960f ,60f + startY*i,line)
        }
            //grid starts at (160,160) and has a spacing of 100f
            // So the centre of a square would be of the format (210 + 100i),(210 + 100j) where i and j vary from 0 to 7

        for (i in 0..runCount){
            if(!(runCount==0)){
                var a = (0..7).random()
                var b = (0..7).random()
                if(xCoordinates.contains(a) && yCoordinates.contains(b))
                    refresh()
                else{
                    xCoordinates.add(a)
                    yCoordinates.add(b)
                    canvas?.drawCircle(210f + (100*a).toFloat(),210f + (100*b).toFloat(),25f,bomb)
                }
            }
        }
        for(i in 0..7){
            for(j in 0..7){
                canvas?.drawRect(170f + (100*i).toFloat(), 170f + (100*j).toFloat(),250f + (100*i).toFloat(),250f + (100*j).toFloat(),disguise)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action)
        {
            MotionEvent.ACTION_DOWN ->{
                xClick = event.getX().toInt()
                yClick = event.getY().toInt()
            }
        }

        return true
    }
    //xChange = event.x

    fun refresh(){
        xCoordinates.clear()
        yCoordinates.clear()
        runCount = 6
        postInvalidate()
    }
}