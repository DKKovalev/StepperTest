package ru.dkkovalev.steppertest

data class StepperParams(val style: StepperStyle, val lineLength: Float)

enum class StepperStyle {
    DONE,
    NEXT,
    PROCESS
}