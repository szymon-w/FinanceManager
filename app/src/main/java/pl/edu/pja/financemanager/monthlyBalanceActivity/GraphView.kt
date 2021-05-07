package pl.edu.pja.financemanager.monthlyBalanceActivity

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

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
        strokeWidth = 7f
        isAntiAlias = true
    }

    //axis line
    private val axisLinePaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 10f
    }


    fun setData(newDataSet: List<DataPoint>) {
        //set y axis min and max
        yMin = newDataSet.minByOrNull { it.yVal }?.yVal?:0
        yMax = newDataSet.maxByOrNull { it.yVal }?.yVal?: 0
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
                if(nextDataPoint.yVal>=0)
                    canvas.drawLine(startX, startY, endX, endY, positiveDataPointLinePaint)
                else
                    canvas.drawLine(startX, startY, endX, endY, negativeDataPointLinePaint)
            }
        }

        //draw axis
        //y axis
        canvas.drawLine(0f, 0f, 0f, height.toFloat(), axisLinePaint)
        //x axis
        val xAxisYstartStop = 0.toRealY()
        canvas.drawLine(0f, xAxisYstartStop, width.toFloat(), xAxisYstartStop, axisLinePaint)
    }
    private fun Int.toRealX() = toFloat() / xMax * width
    private fun Int.toRealY() = height-((toFloat()-yMin) / (yMax-yMin) * height)

}