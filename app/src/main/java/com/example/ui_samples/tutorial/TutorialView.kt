package com.example.ui_samples.tutorial

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.shapes.Shape
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.ui_samples.R

class TutorialView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var shapes = ArrayList<Shape>()
    private var paint = Paint()

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(ContextCompat.getColor(context, R.color.tutorial_background))
        for (shape in shapes) {
            shape.draw(canvas, paint)
        }
    }

    public fun addRoundRect(rect: RoundRect) {
        shapes.add(rect as Shape)
    }
}
