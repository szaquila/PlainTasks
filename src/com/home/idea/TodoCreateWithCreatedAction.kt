package com.home.idea

import java.util.*
import java.text.SimpleDateFormat

class TodoCreateWithCreatedAction : TodoAction() {
    override fun parseSelectedText(initialValue: String) : String? {
        if (initialValue.indexOf("☐") > -1) return null
        val now = Date()
        val f = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val today = f.format(now)
        return " ☐ $initialValue @created($today)"
    }
}