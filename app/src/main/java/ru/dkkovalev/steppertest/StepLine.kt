package ru.dkkovalev.steppertest

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class StepLine @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var stepperParams: StepperStyle = StepperStyle.PROCESS
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var lineLength: Float = resources.getDimension(R.dimen.pin_line_length)
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    private val paint = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let(::drawLine)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(
                lineLength.toInt(),
                MeasureSpec.EXACTLY
            ),
            MeasureSpec.makeMeasureSpec(
                resources.getDimensionPixelSize(R.dimen.step_pin_size),
                MeasureSpec.EXACTLY
            )
        )
    }

    private fun drawLine(canvas: Canvas) {

        if (stepperParams == StepperStyle.DONE) {
            paint.apply {
                color = ContextCompat.getColor(context, R.color.green_primary_dc)
                style = Paint.Style.FILL_AND_STROKE
                strokeWidth = resources.getDimension(R.dimen.pin_stroke_width)
            }
        } else {
            paint.apply {
                color = ContextCompat.getColor(context, R.color.grey_pebble_dc)
                style = Paint.Style.STROKE
                strokeCap = Paint.Cap.ROUND
                strokeWidth = resources.getDimension(R.dimen.pin_stroke_width)
                pathEffect = DashPathEffect(floatArrayOf(15f, 10f), 0f)
            }
        }

        canvas.drawLine(
            0f,
            height.toFloat() / 2,
            lineLength,
            height.toFloat() / 2,
            paint
        )
    }
}