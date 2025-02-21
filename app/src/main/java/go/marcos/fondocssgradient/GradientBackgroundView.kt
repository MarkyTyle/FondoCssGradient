package go.marcos.fondocssgradient

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class GradientBackgroundView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()

        // -- 3er gradiente en CSS (base blanca): linear-gradient(90deg, #FFF, #FFF)
        //    => Lo pintamos primero como "fondo".
        val baseShader = LinearGradient(
            0f, 0f, /* x0,y0 */
            w, 0f,  /* x1,y1 (90° => de izq a der) */
            intArrayOf(
                Color.WHITE,
                Color.WHITE
            ),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        paint.shader = baseShader
        canvas.drawRect(0f, 0f, w, h, paint)

        // -- 2do gradiente en CSS: linear-gradient(45deg, transparent 0%, transparent 2%, #5792EA 2%, #5792EA 46%, #72B2EF 46%, #72B2EF 54%, transparent 54%, transparent 63%, #0730D8 63%, #0730D8 100%)
        //    => 45° => de (0,0) a (w,h)
        val secondShader = LinearGradient(
            0f, 0f,
            w, h,
            intArrayOf(
                /* 0%  */ Color.TRANSPARENT,
                /* 2%  */ Color.TRANSPARENT,
                /* 2%  */ Color.parseColor("#5792EA"),
                /* 46% */ Color.parseColor("#5792EA"),
                /* 46% */ Color.parseColor("#72B2EF"),
                /* 54% */ Color.parseColor("#72B2EF"),
                /* 54% */ Color.TRANSPARENT,
                /* 63% */ Color.TRANSPARENT,
                /* 63% */ Color.parseColor("#0730D8"),
                /*100%*/ Color.parseColor("#0730D8")
            ),
            floatArrayOf(
                0f,      // 0%
                0.02f,   // 2%
                0.02f,   // 2%
                0.46f,   // 46%
                0.46f,   // 46%
                0.54f,   // 54%
                0.54f,   // 54%
                0.63f,   // 63%
                0.63f,   // 63%
                1f       // 100%
            ),
            Shader.TileMode.CLAMP
        )
        paint.shader = secondShader
        canvas.drawRect(0f, 0f, w, h, paint)

        // -- 1er gradiente en CSS (el que va arriba):
        //    linear-gradient(135deg, transparent 0%, transparent 17%, rgba(87,146,234,0.6) 17%, rgba(87,146,234,0.6) 59%, transparent 59%, transparent 64%, rgba(34,81,222,0.6) 64%, rgba(34,81,222,0.6) 100%)
        //    => 135° => de top-right a bottom-left => (w,0) a (0,h)
        val firstShader = LinearGradient(
            w, 0f,
            0f, h,
            intArrayOf(
                /*  0% */ Color.TRANSPARENT,
                /* 17% */ Color.TRANSPARENT,
                /* 17% */ Color.argb((0.6 * 255).toInt(), 87, 146, 234), // RGBA(87,146,234,0.6)
                /* 59% */ Color.argb((0.6 * 255).toInt(), 87, 146, 234),
                /* 59% */ Color.TRANSPARENT,
                /* 64% */ Color.TRANSPARENT,
                /* 64% */ Color.argb((0.6 * 255).toInt(), 34, 81, 222),  // RGBA(34,81,222,0.6)
                /*100%*/ Color.argb((0.6 * 255).toInt(), 34, 81, 222)
            ),
            floatArrayOf(
                0f,
                0.17f,
                0.17f,
                0.59f,
                0.59f,
                0.64f,
                0.64f,
                1f
            ),
            Shader.TileMode.CLAMP
        )
        paint.shader = firstShader
        canvas.drawRect(0f, 0f, w, h, paint)
    }
}
