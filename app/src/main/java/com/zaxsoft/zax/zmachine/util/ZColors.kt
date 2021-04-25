package com.zaxsoft.zax.zmachine.util

import android.graphics.Color

object ZColors {
    fun get(color: Int): Int {
        return when(color) {
            2 -> Color.BLACK
            3 -> Color.RED
            4 -> Color.GREEN
            5 -> Color.YELLOW
            6 -> Color.BLUE
            7 -> Color.MAGENTA
            8 -> Color.CYAN
            else -> Color.WHITE
        }
    }
}