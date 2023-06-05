package com.vandrushko.ui.utils

private const val EMAIL_SECOND_PART_REPLACER = "^([^@\\s])+"
private const val NON_LETTERS_REPLACER = "[^a-zA-Z]"

class Parser {
    fun parseEmail(email: String?): String? {
        if (email != null && email.trim() != "") {
            return Regex(EMAIL_SECOND_PART_REPLACER)
                .find(email)?.value!!
                .split(Regex(NON_LETTERS_REPLACER))
                .joinToString(" ") {
                    it
                        .lowercase()
                        .replaceFirstChar(Char::uppercaseChar)
                }
        }
        return null
    }
}