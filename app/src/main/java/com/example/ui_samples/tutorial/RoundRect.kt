package com.example.ui_samples.tutorial

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.shapes.Shape
import android.widget.Space

class RoundRect(
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int,
    var radius: Float
): Shape() {

    // TODO: rename?
    override fun draw(canvas: Canvas?, paint: Paint?) {
        val c = canvas ?: Canvas()
        val p = paint ?: Paint()
        val left = x.toFloat()
        val top = y.toFloat()
        val right = x.toFloat() + width
        val bottom = y.toFloat() + height

        val rect = RectF(left, top, right, bottom)
        c.drawRoundRect(rect, radius, radius, p)
    }
}
