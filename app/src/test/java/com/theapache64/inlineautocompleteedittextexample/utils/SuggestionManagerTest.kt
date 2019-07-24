package com.theapache64.inlineautocompleteedittextexample.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class SuggestionManagerTest {


    private lateinit var suggestionManager: SuggestionManager

    @Before
    fun setUp() {
        val suggestions = arrayOf(
            "How are you?",
            "How old are you?",
            "How do you do?",
            "What is your name?",
            "How old are you?",
            "Android development is cool",
            "GitHub is amazing",
            "StackOverflow is awesome",
            "It's been a pleasure working with you",
            "Please help me!!"
        )
        suggestionManager = SuggestionManager(suggestions)
    }


    @Test
    fun getSuggestionFor() {
        assertNull(suggestionManager.getSuggestionFor(""))

        assertEquals(
            " pleasure working with you",
            suggestionManager.getSuggestionFor("It's been a")
        )

        assertEquals(
            "re you?",
            suggestionManager.getSuggestionFor("Hey how a")
        )


    }
}