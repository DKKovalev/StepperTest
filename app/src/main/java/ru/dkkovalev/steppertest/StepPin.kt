package ru.dkkovalev.steppertest

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

class StepPin @JvmOverloads constructor(
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

    var pinValue: String = "-1"
        set(value) {
            field = value
            invalidate()
        }

    private val rect = Rect()
    private val paint = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let(::drawPin)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(
                resources.getDimensionPixelSize(R.dimen.step_pin_size),
                MeasureSpec.EXACTLY
            ),
            MeasureSpec.makeMeasureSpec(
                resources.getDimensionPixelSize(R.dimen.step_pin_size),
                MeasureSpec.EXACTLY
            )
        )
    }

    private fun drawPin(canvas: Canvas) {
        drawSolid(canvas)
        drawText(
            canvas,
            pinValue,
            resources.getDimension(R.dimen.step_pin_size),
            R.color.colorPrimary
        )
    }

    private fun drawSolid(canvas: Canvas) {

        var radius = 0f

        when (stepperParams) {
            StepperStyle.DONE -> {
                paint.apply {
                    color = ContextCompat.getColor(context, R.color.green_primary_dc)
                    style = Paint.Style.FILL
                    isAntiAlias = true
                }

                radius = resources.getDimension(R.dimen.pin_solid_size)
            }

            StepperStyle.NEXT -> {
                paint.apply {
                    color = ContextCompat.getColor(context, R.color.grey_pebble_dc)
                    style = Paint.Style.FILL
                    isAntiAlias = true
                }

                radius = resources.getDimension(R.dimen.pin_solid_size)
            }

            StepperStyle.PROCESS -> {
                paint.apply {
                    color = ContextCompat.getColor(context, R.color.green_primary_dc)
                    style = Paint.Style.STROKE
                    strokeWidth = resources.getDimension(R.dimen.pin_stroke_width)
                    isAntiAlias = true
                }

                radius = resources.getDimension(R.dimen.pin_outline_size)
            }
        }

        canvas.drawCircle(
            width.toFloat() / 2,
            height.toFloat() / 2,
            radius,
            paint
        )
    }

    private fun drawOutline(canvas: Canvas) {

        val paint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.green_primary_dc)
            style = Paint.Style.STROKE
            strokeWidth = resources.getDimension(R.dimen.pin_stroke_width)
            isAntiAlias = true
        }

        val radius = resources.getDimensionPixelSize(R.dimen.pin_outline_size)
        val oval = RectF(x, y, x + radius, y + radius)
        canvas.drawOval(oval, paint)
    }

    private fun drawText(
        canvas: Canvas,
        text: String,
        pinRadius: Float,
        @ColorRes textColor: Int
    ) {
        canvas.getClipBounds(rect)

        paint.apply {
            color = ContextCompat.getColor(context, textColor)
            textSize = resources.getDimension(R.dimen.text_size)
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            getTextBounds(text, 0, text.length, rect)
        }

        val textX = pinRadius / 2
        val textY = pinRadius / 2 - (paint.descent() + paint.ascent()) / 2

        canvas.drawText(
            text,
            textX,
            textY,
            paint
        )
    }
}