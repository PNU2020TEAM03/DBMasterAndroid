package com.example.dbmasterandroid.utils

import java.util.regex.Pattern

object RegularExpressionUtil {
    fun validCheck(regex: Regex, value: String?): Boolean {
        val p = Pattern.compile(getRegex(regex))
        val m = p.matcher(value)
        return m.matches()
    }

    private fun getRegex(regex: Regex): String {
        return when (regex) {
            Regex.NAME -> "^[0-9a-zA-Z\$_]+$"
            Regex.CONFIRM_NUMBER -> "\\d{6}"
            Regex.PASSWORD -> "((?=.*\\d)(?=.*[a-z,A-Z])(?=.*[-~!@#\$%^&*()_+`|\"':;,<.>/?\\\\]).{8,16})"
            else -> ""
        }
    }

    enum class Regex {
        NAME, PHONE, CONFIRM_NUMBER, PASSWORD
    }
}