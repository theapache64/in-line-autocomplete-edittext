package com.theapache64.inlineautocompleteedittextexample.utils

import com.theapache64.inlineautocompleteedittextexample.InLineAutoCompleteEditText
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
            "elopment is cool",
            suggestionManager.getSuggestionFor("android dev")
        )

        assertNull(suggestionManager.getSuggestionFor("and"))

        assertEquals(
            "ure working with you",
            suggestionManager.getSuggestionFor("This is some text.It's been a pleas")
        )

        assertEquals(
            "re you?",
            suggestionManager.getSuggestionFor("jhgkjdfg.dfghkfljh hfdgh kfdg .Hey how a")
        )

        assertEquals(
            " you?",
            suggestionManager.getSuggestionFor("Hey how are")
        )

        assertEquals(
            "you?",
            suggestionManager.getSuggestionFor("Hey how are ")
        )


        assertEquals(
            " is awesome",
            suggestionManager.getSuggestionFor("Stackoverflow")
        )

        assertEquals(" me!!", suggestionManager.getSuggestionFor("please help"))

        assertNull(suggestionManager.getSuggestionFor("Hey mannn gith "))
        assertEquals("ub is amazing", suggestionManager.getSuggestionFor("Hey mannn gith"))

        assertEquals(null, suggestionManager.getSuggestionFor("olakka"))

        assertEquals(
            null,
            suggestionManager.getSuggestionFor("This is some example and GitHub is \n\n")
        )
    }

    @Test
    fun specialTest() {
        assertEquals("hello&nbsp;hello", InLineAutoCompleteEditText.nbpize("hello hello"))
        assertEquals("&nbsp;", InLineAutoCompleteEditText.nbpize(" "))
        assertEquals("&nbsp;&nbsp;", InLineAutoCompleteEditText.nbpize("  "))
        assertEquals("&nbsp;&nbsp;b&nbsp;", InLineAutoCompleteEditText.nbpize("  b "))
    }
}