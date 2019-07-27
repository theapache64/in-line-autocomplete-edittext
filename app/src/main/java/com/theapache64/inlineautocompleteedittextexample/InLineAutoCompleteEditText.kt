package com.theapache64.inlineautocompleteedittextexample

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.theapache64.inlineautocompleteedittextexample.utils.HtmlCompat
import com.theapache64.inlineautocompleteedittextexample.utils.SuggestionManager
import com.theapache64.twinkill.logger.info
import com.theapache64.twinkill.logger.mistake


class InLineAutoCompleteEditText(context: Context?, attrs: AttributeSet?) :
    EditText(context, attrs) {

    private var hasActiveSuggestion: Boolean = false
    lateinit var dictionary: Array<String>
    private val suggestionMan by lazy {
        SuggestionManager(dictionary)
    }

    companion object {
        private const val TEXT_COLOR = "#000000"
        private const val HINT_COLOR = "#c2c2c2"

        fun nbpize(string: String): String {
            info("Before : $string")
            val replace = string.replace(Regex("\\s"), "&nbsp;")
            info("After : $replace")
            return replace
        }
    }

    private val textWatcher = object : TextWatcher {

        override fun afterTextChanged(_newText: Editable?) {
            info("-----------------------------------")
            info("afterTextChanged: Current text is $text")
            info("afterTextChanged: New text is $_newText")

            mistake("Suggestion is:${suggestionMan.getSuggestionFor(_newText.toString())}")
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            info("-----------------------------------")
            info("beforeTextChanged: Current text is $text")
            info("beforeTextChanged: New text is $s")
        }

        override fun onTextChanged(_newText: CharSequence?, start: Int, before: Int, count: Int) {
            info("-----------------------------------")
            info("onTextChanged: Current text is $text")
            info("onTextChanged: New text is $_newText")
        }

    }

    init {
        addTextChangedListener(textWatcher)

        setOnClickListener {
            // Setting full text - suggestion accepted
            //prevText = text.toString()
            setTextSilently(text, null)
        }
    }

    private fun setTextSilently(
        currentText: CharSequence,
        suggestion: String?
    ) {

        removeTextChangedListener(textWatcher)

        val fullText = if (suggestion != null) {
            hasActiveSuggestion = true
            "<font color=$TEXT_COLOR>${nbpize(currentText.toString())}</font><font color=$HINT_COLOR>${nbpize(
                suggestion
            )}</font>"
        } else {
            hasActiveSuggestion = false
            "<font color=$TEXT_COLOR>${nbpize(currentText.toString())}</font>"
        }.replace("\n", "<br/>")

        info("Setting text to:$fullText")
        val prevPos = selectionEnd
        setText(HtmlCompat.fromHtml(fullText))
        setSelection(prevPos)

        addTextChangedListener(textWatcher)
    }

}

