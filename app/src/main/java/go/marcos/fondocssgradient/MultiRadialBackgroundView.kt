package go.marcos.fondocssgradient

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.hypot

class MultiRadialBackgroundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // hsla(190,0%,93%,0.05) se traduce aproximadamente a un gris muy claro con alpha 0.05
    // => alpha = 0.05 * 255 ~ 13, R=G=B ~ 93% de 255 => ~237
    // Color.argb(13, 237, 237, 237)
    private val subtleColor = Color.argb(13, 237, 237, 237)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()
        // Tomamos un radio grande para cubrir la pantalla al dibujar las burbujas
        val maxRadius = hypot(w, h)

        // 1) DIBUJAR FONDO (linear-gradient(135deg, #2538DE -> #60BDF4))
        //    135deg en CSS significa de top-left a bottom-right en Android
        val linearShader = LinearGradient(
            0f, 0f,  // top-left
            w, h,    // bottom-right
            intArrayOf(
                Color.parseColor("#1a2054"), // rgb(37,56,222)
                Color.parseColor("#325f78")  // rgb(96,189,244)
            ),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        paint.shader = linearShader
        canvas.drawRect(0f, 0f, w, h, paint)

        // 2) radial-gradient(circle at 55% 65%, color 0..52%, transparent 52..100%)
        drawRadial(
            canvas,
            centerX = 0.55f * w,
            centerY = 0.65f * h,
            radius = maxRadius,
            colorStopStart = 0.52f
        )

        // 3) radial-gradient(circle at 79% 7%, color 0..78%, transparent 78..100%)
        drawRadial(
            canvas,
            centerX = 0.79f * w,
            centerY = 0.07f * h,
            radius = maxRadius,
            colorStopStart = 0.78f
        )

        // 4) radial-gradient(circle at 60% 90%, color 0..20%, transparent 20..100%)
        drawRadial(
            canvas,
            centerX = 0.60f * w,
            centerY = 0.90f * h,
            radius = maxRadius,
            colorStopStart = 0.20f
        )

        // 5) radial-gradient(circle at 14% 15%, color 0..1%, transparent 1..100%)
        drawRadial(
            canvas,
            centerX = 0.14f * w,
            centerY = 0.15f * h,
            radius = maxRadius,
            colorStopStart = 0.01f
        )

        // 6) radial-gradient(circle at 85% 1%, color 0..96%, transparent 96..100%)
        drawRadial(
            canvas,
            centerX = 0.85f * w,
            centerY = 0.01f * h,
            radius = maxRadius,
            colorStopStart = 0.96f
        )
    }

    /**
     * Dibuja un "radial-gradient(circle at centerX, centerY, ...)"
     * donde el color (subtleColor) va de 0..colorStopStart y luego es transparente hasta 1.
     */
    private fun drawRadial(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        radius: Float,
        colorStopStart: Float
    ) {
        val radialShader = RadialGradient(
            centerX, centerY, radius,
            intArrayOf(
                // 0..colorStopStart => subtleColor
                // colorStopStart..1 => transparente
                subtleColor,        // 0%
                subtleColor,        // colorStopStart
                Color.TRANSPARENT,  // colorStopStart
                Color.TRANSPARENT   // 100%
            ),
            floatArrayOf(
                0f,
                colorStopStart,
                colorStopStart,
                1f
            ),
            Shader.TileMode.CLAMP
        )
        paint.shader = radialShader
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }
}
