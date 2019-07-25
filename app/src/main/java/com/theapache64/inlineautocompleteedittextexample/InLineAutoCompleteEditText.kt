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
            var newText = _newText.toString()
            mistake("afterTextChangedg invoked with $newText")
            if (hasActiveSuggestion) {
                val isBackspace = newText.length <= prevText!!.length
                newText = newText.substring(
                    0, if (isBackspace) {
                        prevText!!.length - 1
                    } else {
                        prevText!!.length + 1
                    }
                )
                info("Suggestion discarded, new text: $newText")
            }

            prevText = newText

            suggestionMan.getSuggestionFor(newText).let { remSug ->
                info("Suggestion :$remSug")
                setTextSilently(newText, remSug)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }

    init {
        addTextChangedListener(textWatcher)

        setOnClickListener {
            // Setting full text - suggestion accepted
            setTextSilently(text, null)
        }
    }

    private fun setTextSilently(
        currentText: CharSequence,
        suggestion: String?
    ) {

        info("Removing text watcher")
        removeTextChangedListener(textWatcher)


        val fullText = if (suggestion != null) {
            hasActiveSuggestion = true
            "<font color=$TEXT_COLOR>$currentText</font><font color=$HINT_COLOR>$suggestion</font>"
        } else {
            hasActiveSuggestion = false
            "<font color=$TEXT_COLOR>$currentText</font>"
        }.replace("\n", "<br/>")

        info("Setting :$fullText")

        setText(HtmlCompat.fromHtml(fullText))
        setSelection(currentText.length)

        info("Current length is ${text.length}")

        info("Adding text watcher")
        addTextChangedListener(textWatcher)
    }

}