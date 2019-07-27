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
        assertEquals("ogle.com", suggestionManager.getSuggestionFor("go"))
        assertEquals("book.com", suggestionManager.getSuggestionFor("face"))
        assertEquals(".168.354.45", suggestionManager.getSuggestionFor("192"))

        // no match
        assertNull(suggestionManager.getSuggestionFor("somesite"))

    }
}