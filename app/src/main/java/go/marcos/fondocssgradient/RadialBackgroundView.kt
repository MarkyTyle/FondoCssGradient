package go.marcos.fondocssgradient

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.hypot

class RadialBackgroundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()

        // Calculamos un radio lo suficientemente grande para que el gradiente
        // cubra toda la pantalla desde la esquina superior derecha.
        val radius = hypot(w, h)

        // Replicamos los mismos color-stops del radial-gradient(circle at top right, ...)
        // Fíjate que repetimos los colores cuando hay un cambio brusco (por ejemplo 48% -> 48%).
        val radialShader = RadialGradient(
            /* centerX = */ w,
            /* centerY = */ 0f,          // "top right" => esquina superior derecha
            /* radius   = */ radius,
            /* colores  = */ intArrayOf(
                Color.parseColor("#240977"), // 0%
                Color.parseColor("#240977"), // 48%
                Color.parseColor("#480795"), // 48%
                Color.parseColor("#480795"), // 53%
                Color.parseColor("#6D05B2"), // 53%
                Color.parseColor("#6D05B2"), // 56%
                Color.parseColor("#9102D0"), // 56%
                Color.parseColor("#9102D0"), // 69%
                Color.parseColor("#B500ED"), // 69%
                Color.parseColor("#B500ED")  // 100%
            ),
            /* stops    = */ floatArrayOf(
                0.0f,   // 0%
                0.48f,  // 48%
                0.48f,  // 48% (salto)
                0.53f,  // 53%
                0.53f,  // 53% (salto)
                0.56f,  // 56%
                0.56f,  // 56% (salto)
                0.69f,  // 69%
                0.69f,  // 69% (salto)
                1.0f    // 100%
            ),
            Shader.TileMode.CLAMP
        )

        // Dibujamos el gradiente en todo el lienzo
        paint.shader = radialShader
        canvas.drawRect(0f, 0f, w, h, paint)
    }
}
