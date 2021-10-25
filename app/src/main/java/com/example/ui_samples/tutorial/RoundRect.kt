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
    var height: Int
): Shape() {

    // TODO: rename?
    override fun draw(canvas: Canvas?, paint: Paint?) {
        val c = canvas ?: Canvas()
        val p = paint ?: Paint()
        drawRect(c, x.toFloat(), y.toFloat(), x.toFloat() + width, y.toFloat() + height, p)
    }

    // TODO: ここにradius入れれば改善できる？
    private fun drawRect(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float, paint: Paint) {
        val radius = (bottom - top) / 2
        val rect = RectF(left, top, right, bottom)
        canvas.drawRoundRect(rect, radius, radius, paint)
    }
}
