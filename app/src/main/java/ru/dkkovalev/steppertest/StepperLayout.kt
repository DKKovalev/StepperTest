package ru.dkkovalev.steppertest

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class StepperLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val padding = resources.getDimensionPixelOffset(R.dimen.margin16)

    init {
        setPadding(padding, 0, padding, 0)
        gravity = Gravity.CENTER_HORIZONTAL
    }

    var steps: List<Step> = emptyList()
        set(value) {
            field = value
            drawSteps()
            drawLine()
        }
    private val metrics = DisplayMetrics()

    private fun drawSteps() {
        removeAllViews()

        steps.forEachIndexed { index, step ->
            addView(StepPin(context).apply {
                pinValue = index.toString()
                id = index
            })
        }

        (context as AppCompatActivity).windowManager.defaultDisplay.getMetrics(metrics)
    }

    private fun drawLine() {
        var i = 1

        do {
            val lastView = getChildAt(childCount - 1) as StepPin
            val firstView = getChildAt(0) as StepPin
            addView(StepLine(context).apply {
                stepperParams = StepperStyle.DONE
                lineLength = calculateStepLineWidth(firstView, lastView)
            }, i)

            i += 2

        } while (i < childCount)
    }

    private fun calculateStepLineWidth(first: StepPin, last: StepPin): Float {


        return 48f
    }
}