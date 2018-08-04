package com.home.idea

class TodoCreateAction : TodoAction() {
    override fun parseSelectedText(initialValue: String) : String? {
        if (initialValue.indexOf("☐") > -1) return null
        return " ☐ $initialValue"
    }
}