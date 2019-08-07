package com.theapache64.inlineautocompleteedittextexample.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SuggestionManagerUrlTest {

    private val suggestionManager by lazy {
        val dictionary = arrayOf(
            "google.com",
            "facebook.com",
            "gmail.com",
            "yahoo.com",
            "192.168.354.45"
        )

        SuggestionManager(dictionary)
    }

    @Test
    fun test() {
        // null of empty and null input
        assertNull(suggestionManager.getSuggestionFor(null))
        assertNull(suggestionManager.getSuggestionFor(""))

        // matching
        assertEquals("le.com", suggestionManager.getSuggestionFor("goog"))
        assertEquals("ook.com", suggestionManager.getSuggestionFor("faceb"))
        assertEquals(".168.354.45", suggestionManager.getSuggestionFor("192"))

        // no match
        assertNull(suggestionManager.getSuggestionFor("somesite"))

    }
}