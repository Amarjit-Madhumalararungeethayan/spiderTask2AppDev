package com.example.minetime

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat


var xCoordinates = mutableListOf<Int>(10)
var yCoordinates = mutableListOf<Int>(10)

var abc = mutableListOf<Int>(10)
var def = mutableListOf<Int>(10)

var runCount = 0

var xClick = 0
var yClick = 0

var x1Red = 0f
var x2Red = 0f
var y1Red = 0f
var y2Red = 0f

var numMines = 0

var x1Blue = 0f
var x2Blue = 0f
var y1Blue = 0f
var y2Blue = 0f

private lateinit var extraCanvas: Canvas
private lateinit var extraBitmap: Bitmap

class Battleground(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    var bg = Paint()
    var line = Paint()
    var bomb = Paint()
    var disguise = Paint()

    var red = Paint()
    var blue = Paint()

    var number = Paint()

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

        red.color = Color.RED
        red.style = Paint.Style.FILL_AND_STROKE
        red.isAntiAlias = true

        blue.color = ContextCompat.getColor(context,R.color.spiderBlue)
        blue.style = Paint.Style.FILL_AND_STROKE
        blue.isAntiAlias = true

        number.color = Color.BLACK
        number.style = Paint.Style.FILL_AND_STROKE
        number.textSize = 60f
        number.typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)

    }

    @SuppressLint("ResourceAsColor")
    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int)
    {
        super.onSizeChanged(width, height, oldwidth, oldheight)
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(R.color.black)
    }

    var startX = 100f
    var startY = 100f

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bg)

        for (i in 1..9) {
            canvas?.drawLine(60f + startX * i, 160f, 60f + startX * i, 960f, line)
        }

        for (i in 1..9) {
            canvas?.drawLine(160f, 60f + startY * i, 960f, 60f + startY * i, line)
        }
        //grid starts at (160,160) and has a spacing of 100f
        // So the centre of a square would be of the format (210 + 100i),(210 + 100j) where i and j vary from 0 to 7

        for(i in 0..7){
            for(j in 0..7){
                canvas?.drawRect(170f + (100*i).toFloat(), 170f + (100*j).toFloat(),250f + (100*i).toFloat(),250f + (100*j).toFloat(),disguise)
            }
        }//l,t,r,b
            canvas?.drawRect(x1Blue, y1Blue, x2Blue, y2Blue, blue)

            canvas?.drawText("${numMines}", x1Blue + 25f, y1Blue + 60f, number)

            canvas?.drawRect(x1Red, y1Red, x2Red, y2Red, red)

        extraCanvas.drawRect(x1Blue, y1Blue, x2Blue, y2Blue, blue)
        extraCanvas.drawText("${numMines}", x1Blue + 25f, y1Blue + 60f, number)
        extraCanvas.drawRect(x1Red, y1Red, x2Red, y2Red, red)

        canvas?.drawBitmap(extraBitmap, 0f, 0f, null)
    }
    fun chooseCoors(){
        for (i in 0..runCount) {
                var a = (0..7).random()
                var b = (0..7).random()
            xCoordinates.add(a)
            yCoordinates.add(b)
        }
        abc.clear()
        def.clear()
        var j = 0
        while(j <= runCount){
            abc.add(xCoordinates[j] - 1)
            def.add(yCoordinates[j] - 1)
            abc.add(xCoordinates[j] - 1)
            def.add(yCoordinates[j])
            abc.add(xCoordinates[j] - 1)
            def.add(yCoordinates[j] + 1)
            abc.add(xCoordinates[j])
            def.add(yCoordinates[j] - 1)
            abc.add(xCoordinates[j])
            def.add(yCoordinates[j] + 1)
            abc.add(xCoordinates[j] + 1)
            def.add(yCoordinates[j] - 1)
            abc.add(xCoordinates[j] + 1)
            def.add(yCoordinates[j])
            abc.add(xCoordinates[j] + 1)
            def.add(yCoordinates[j] + 1)
            j++
        }
    }
    fun check(){
        for (i in 0..runCount){
            for (j in 0..runCount){
               if(i!=j){
                   if(xCoordinates[i] == xCoordinates[j] && yCoordinates[i] == yCoordinates[j]) {
                       escape()
                   }
               }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action)
        {
            MotionEvent.ACTION_DOWN ->{
                xClick = event.x.toInt()
                yClick = event.y.toInt()
            }
        }
        analyseTile()
        return true
    }

    private fun analyseTile() {
        numMines = 0
        Log.d("xCoor",xClick.toString())
        Log.d("yCoor",yClick.toString())

       if(xCoordinates[0] == 10){
           Log.d("abc","Game not started")
       }
        else if(xCoordinates[0] != 10){
           for (i in (0..runCount)) {
               if ((xClick in (160 + (100 * xCoordinates[i]))..(260 + (100 * xCoordinates[i]))) && (yClick in (160 + (100 * yCoordinates[i]))..(260 + (100 * yCoordinates[i])))) {
                   Log.d("def", "TARGET CONFIRMED")
                   x1Red = (170 + (100 * xCoordinates[i])).toFloat()
                   x2Red = x1Red + 80f
                   y1Red = (170 + (100 * yCoordinates[i])).toFloat()
                   y2Red = y1Red + 80f
                   invalidate()
               }
           }
                   Log.d("abc","Blank")
                   if((xClick in (160..960))&&(yClick in (160..960))){
                       when (xClick){
                           in (160..259) -> x1Blue = 170f
                           in (260..359) -> x1Blue = 270f
                           in (360..459) -> x1Blue = 370f
                           in (460..559) -> x1Blue = 470f
                           in (560..659) -> x1Blue = 570f
                           in (660..759) -> x1Blue = 670f
                           in (760..859) -> x1Blue = 770f
                           in (860..960) -> x1Blue = 870f
                       }
                       x2Blue = x1Blue + 80f
                   }
                   if((xClick in (160..960))&&(yClick in (160..960))){
                       when (yClick){
                           in (160..259) -> y1Blue = 170f
                           in (260..359) -> y1Blue = 270f
                           in (360..459) -> y1Blue = 370f
                           in (460..559) -> y1Blue = 470f
                           in (560..659) -> y1Blue = 570f
                           in (660..759) -> y1Blue = 670f
                           in (760..859) -> y1Blue = 770f
                           in (860..960) -> y1Blue = 870f
                       }
                       y2Blue = y1Blue + 80f
                   }
                   for (i in 0 until ((8*(runCount + 1)))){
                       var n1 = when (xClick){
                           in (160..259) -> 0
                           in (260..359) -> 1
                           in (360..459) -> 2
                           in (460..559) -> 3
                           in (560..659) -> 4
                           in (660..759) -> 5
                           in (760..859) -> 6
                           in (860..960) -> 7
                           else -> print("Error")
                       }
                       var n2 = when (yClick){
                           in (160..259) -> 0
                           in (260..359) -> 1
                           in (360..459) -> 2
                           in (460..559) -> 3
                           in (560..659) -> 4
                           in (660..759) -> 5
                           in (760..859) -> 6
                           in (860..960) -> 7
                           else -> print("Error")
                       }
                       if( n1 == abc[i] && n2 == def[i] ){
                           numMines++
                       }
                   }
                   postInvalidate()
               }
           }
    fun escape(){
        xCoordinates.clear()
        yCoordinates.clear()
        chooseCoors()
    }
    fun refresh1() {
        xCoordinates.clear()
        yCoordinates.clear()
        runCount = 6
    }
    fun refresh2() {
        xCoordinates.clear()
        yCoordinates.clear()
        runCount = 15
    }
}