package pl.edu.pja.financemanager.monthlyBalanceActivity

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.roundToInt

class GraphView (
    context: Context,
    attributeSet: AttributeSet
    ): View(context, attributeSet){
    private val dataSet = mutableListOf<DataPoint>()
    private var xMin = 0
    private var xMax = 31
    private var yMin = 0
    private var yMax = 0


    //positive line
    private val positiveDataPointLinePaint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 7f
        isAntiAlias = true
    }

    //negative line
    private val negativeDataPointLinePaint = Paint().apply {
        color = Color.RED
        strokeWidth = 5f
        isAntiAlias = true
    }

    //axis line
    private val axisLinePaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 10f
    }

    //labels
    private val labelsPaint = Paint().apply {
        color = Color.BLACK
        textSize = 35F
    }



    fun setData(newDataSet: List<DataPoint>) {
        //set y axis min and max
        yMin = newDataSet.minByOrNull { it.yVal }?.yVal?:0
        if(yMin>0)yMin=0
        yMax = newDataSet.maxByOrNull { it.yVal }?.yVal?: 0
        if(yMax<0)yMax=0
        dataSet.clear()
        dataSet.addAll(newDataSet)
        //calls onDraw
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        dataSet.forEachIndexed { index, currentDataPoint ->
            val realX = currentDataPoint.xVal.toRealX()
            val realY = currentDataPoint.yVal.toRealY()

            if (index < dataSet.size - 1) {
                val nextDataPoint = dataSet[index + 1]
                val startX = currentDataPoint.xVal.toRealX()
                val startY = currentDataPoint.yVal.toRealY()
                val endX = nextDataPoint.xVal.toRealX()
                val endY = nextDataPoint.yVal.toRealY()
                if(currentDataPoint.yVal>0 && nextDataPoint.yVal<0) {
                    canvas.drawLine(startX, startY, (startX+endX)/2, 0.toRealY(), positiveDataPointLinePaint)
                    canvas.drawLine((startX+endX)/2, 0.toRealY(), endX, endY, negativeDataPointLinePaint)
                }else if(currentDataPoint.yVal<0 && nextDataPoint.yVal>=0) {
                    canvas.drawLine(startX, startY, (startX+endX)/2, 0.toRealY(), negativeDataPointLinePaint)
                    canvas.drawLine((startX+endX)/2, 0.toRealY(), endX, endY, positiveDataPointLinePaint)
                }else if(nextDataPoint.yVal>=0)
                    canvas.drawLine(startX, startY, endX, endY, positiveDataPointLinePaint)
                else
                    canvas.drawLine(startX, startY, endX, endY, negativeDataPointLinePaint)
            }
        }

        //draw axis
        //y axis
        canvas.drawLine(100f, 0f, 100f, height.toFloat(), axisLinePaint)
        //x axis
        val xAxisYStartStop = 0.toRealY()
        canvas.drawLine(100f, xAxisYStartStop, width.toFloat(), xAxisYStartStop, axisLinePaint)

        //draw y labels
        canvas.drawText(toLabel(yMin),0f,height.toFloat()-2f,labelsPaint)
        canvas.drawText(toLabel(yMin*3/4+yMax*1/4),0f,height.toFloat()*3/4,labelsPaint)
        canvas.drawText(toLabel(yMin*1/2+yMax*1/2),0f,height.toFloat()/2,labelsPaint)
        canvas.drawText(toLabel(yMin*1/4+yMax*3/4),0f,height.toFloat()*1/4,labelsPaint)
        canvas.drawText(toLabel(yMax),0f,30f,labelsPaint)

        //draw x labels
        val xLabelHeight = if(yMin>-100) xAxisYStartStop-30f else xAxisYStartStop+45f
        for(i in 5..25 step 5)
            canvas.drawText(i.toString(),i.toRealX()-10f,xLabelHeight,labelsPaint)
        canvas.drawText("30",30.toRealX()-15f,xLabelHeight,labelsPaint)


    }
    private fun Int.toRealX() = toFloat() / xMax * (width-100f)+100f
    private fun Int.toRealY() = height-((toFloat()-yMin) / (yMax-yMin) * height)
    private fun toLabel(number:Int):String{
        return if(number>9999)
            (number.toDouble()/1000).roundToInt().toString()+"k"
        else if(number<-9999)
            (number.toDouble()/1000).roundToInt().toString()+"k"
        else
            number.toString()
    }

}