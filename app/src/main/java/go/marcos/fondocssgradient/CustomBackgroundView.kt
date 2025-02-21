package go.marcos.fondocssgradient

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

class CustomBackgroundView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        val gradients = listOf(
            RadialGradient(width * 0.4f, height * 0.91f, width * 0.5f,
                intArrayOf(0x0AFBFBFB.toInt(), 0x04E5E5E5.toInt()), null, Shader.TileMode.CLAMP),
            RadialGradient(width * 0.66f, height * 0.97f, width * 0.5f,
                intArrayOf(0x04242424.toInt(), 0x042E2E2E.toInt()), null, Shader.TileMode.CLAMP),
            RadialGradient(width * 0.86f, height * 0.07f, width * 0.5f,
                intArrayOf(0x04282828.toInt(), 0x04C8C8C8.toInt()), null, Shader.TileMode.CLAMP)
        )

        for (gradient in gradients) {
            paint.shader = gradient
            canvas.drawRect(0f, 0f, width, height, paint)
        }
    }
}
