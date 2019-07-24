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
            "Hi, How are you?",
            "Hi, How old are you?",
            "Hi, How do you do?",
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
            "It's been a pleasure working with you",
            suggestionManager.getSuggestionFor("It's been a pl")
        )


        assertNull(
            suggestionManager.getSuggestionFor("It's be")
        )

        assertEquals(
            "StackOverflow is awesome",
            suggestionManager.getSuggestionFor("Stackoverflow is ")
        )

        assertNull(
            suggestionManager.getSuggestionFor("Stac")
        )


        assertEquals(
            "Hi, How are you?",
            suggestionManager.getSuggestionFor("Hi, How ar")
        )

        assertEquals(
            "Hi, How old are you?",
            suggestionManager.getSuggestionFor("Hi, How old")
        )

        assertEquals(
            "How old are you?",
            suggestionManager.getSuggestionFor("How old")
        )

        assertEquals(
            "GitHub is amazing",
            suggestionManager.getSuggestionFor("GitHub is")
        )

        assertEquals(
            "GitHub is amazing",
            suggestionManager.getSuggestionFor("Oodfgdsfg djsfghksdfg dsfg,s dgsdjfgkjhdsfg, GitHub is")
        )
    }
}