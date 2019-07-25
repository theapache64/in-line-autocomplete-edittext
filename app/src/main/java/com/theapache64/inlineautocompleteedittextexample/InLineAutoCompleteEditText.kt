package com.theapache64.inlineautocompleteedittextexample

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.theapache64.inlineautocompleteedittextexample.utils.HtmlCompat
import com.theapache64.inlineautocompleteedittextexample.utils.SuggestionManager
import com.theapache64.twinkill.logger.info


class InLineAutoCompleteEditText(context: Context?, attrs: AttributeSet?) :
    EditText(context, attrs) {

    lateinit var dictionary: Array<String>
    private val suggestionMan by lazy {
        SuggestionManager(dictionary)
    }

    companion object {
        private const val TEXT_COLOR = "#000000"
        private const val HINT_COLOR = "#c2c2c2"
    }

    private val textWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            info("afterTextChanged invoked")
            suggestionMan.getSuggestionFor(s.toString()).let { remSug ->
                info("Suggestion is $remSug")
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
            "<font color=$TEXT_COLOR>$currentText</font><font color=$HINT_COLOR>$suggestion</font>"
        } else {
            "<font color=$TEXT_COLOR>$currentText</font>"
        }

        info("Setting :$fullText")

        setText(HtmlCompat.fromHtml(fullText))
        setSelection(currentText.length)

        info("Current length is ${text.length}")

        info("Adding text watcher")
        addTextChangedListener(textWatcher)
    }

}