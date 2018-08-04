package com.home.idea

import java.text.SimpleDateFormat
import java.util.*

class TodoCancelAction : TodoAction() {
    override fun parseSelectedText(initialValue: String) : String? {
        if (initialValue.indexOf("☐") == -1) return null
        val now = Date()
        val f = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val today = f.format(now)
        val value = initialValue.replace("☐", "✘")
        return "$value @cancelled($today)"
    }
}