package com.upnp.fakeCall

import android.content.Context
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.os.ConfigurationCompat
import java.util.Locale

object DelayFormatter {

    fun formatLong(context: Context, seconds: Int): String {
        return format(context, seconds, wide = true)
    }

    fun formatShort(context: Context, seconds: Int): String {
        return format(context, seconds, wide = false)
    }

    private fun format(context: Context, seconds: Int, wide: Boolean): String {
        val safeSeconds = seconds.coerceAtLeast(0)
        if (safeSeconds == 0) return context.getString(R.string.delay_now)

        val locale = ConfigurationCompat.getLocales(context.resources.configuration)[0]
            ?: Locale.getDefault()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            IcuDelayFormatter.format(locale, safeSeconds, wide)
        } else {
            formatFallback(safeSeconds)
        }
    }

    // Android 6 (API 23): no android.icu, so a simple, non-localized format.
    private fun formatFallback(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return when {
            seconds < 60 -> "$seconds s"
            remainingSeconds == 0 -> "$minutes min"
            else -> "$minutes min $remainingSeconds s"
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
private object IcuDelayFormatter {

    fun format(locale: Locale, safeSeconds: Int, wide: Boolean): String {
        val width = if (wide) MeasureFormat.FormatWidth.WIDE else MeasureFormat.FormatWidth.SHORT
        val formatter = MeasureFormat.getInstance(locale, width)
        val minutes = safeSeconds / 60
        val remainingSeconds = safeSeconds % 60

        return when {
            safeSeconds < 60 -> formatter.format(Measure(safeSeconds.toLong(), MeasureUnit.SECOND))
            remainingSeconds == 0 -> formatter.format(Measure(minutes.toLong(), MeasureUnit.MINUTE))
            else -> formatter.formatMeasures(
                Measure(minutes.toLong(), MeasureUnit.MINUTE),
                Measure(remainingSeconds.toLong(), MeasureUnit.SECOND)
            )
        }
    }
}
