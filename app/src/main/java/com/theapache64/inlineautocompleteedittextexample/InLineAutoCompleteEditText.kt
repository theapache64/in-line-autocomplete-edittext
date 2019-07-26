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
import com.theapache64.twinkill.logger.warning


class InLineAutoCompleteEditText(context: Context?, attrs: AttributeSet?) :
    EditText(context, attrs) {

    private var hasActiveSuggestion: Boolean = false
    private var prevText: String? = null
    lateinit var dictionary: Array<String>
    private val suggestionMan by lazy {
        SuggestionManager(dictionary)
    }

    companion object {
        private const val TEXT_COLOR = "#000000"
        private const val HINT_COLOR = "#c2c2c2"
    }

    private val textWatcher = object : TextWatcher {

        override fun afterTextChanged(_newText: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(_newText: CharSequence?, start: Int, before: Int, count: Int) {

            var newText = _newText.toString()

            mistake("afterTextChanged invoked with $newText")
            info("and prev text is: $prevText")
            if (hasActiveSuggestion) {
                val isBackSpace = count < before
                newText = if (isBackSpace) {
                    warning("Backspace detected on suggestion")
                    newText.substring(0, prevText!!.length - 1)
                } else {
                    newText.substring(0, prevText!!.length + 1)
                }

            }

            suggestionMan.getSuggestionFor(newText).let { remSug ->
                info("Suggestion :$remSug")
                prevText = newText
                info("new prev text is: $prevText")
                setTextSilently(newText, remSug)
            }
        }

    }

    init {
        addTextChangedListener(textWatcher)

        setOnClickListener {
            // Setting full text - suggestion accepted
            prevText = text.toString()
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
            "<font color=$TEXT_COLOR>$currentText</font><font color=$HINT_COLOR>$suggestion</font>"
        } else {
            hasActiveSuggestion = false
            "<font color=$TEXT_COLOR>$currentText</font>"
        }.replace("\n", "<br/>")

        setText(HtmlCompat.fromHtml(fullText))
        setSelection(HtmlCompat.fromHtml(currentText.toString()).length)

        addTextChangedListener(textWatcher)
    }

}