package com.theapache64.inlineautocompleteedittextexample

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.theapache64.inlineautocompleteedittextexample.utils.HtmlCompat
import com.theapache64.inlineautocompleteedittextexample.utils.SuggestionManager


class InLineAutoCompleteEditText(context: Context?, attrs: AttributeSet?) :
    EditText(context, attrs) {
    lateinit var dictionary: Array<String>
    private val suggestionMan by lazy {
        SuggestionManager(dictionary)
    }

    init {
        addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                suggestionMan.getSuggestionFor(s.toString())?.let { remSug ->
                    setHintTextSilently(this, s.toString(), remSug)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        setOnClickListener {
            setText(HtmlCompat.fromHtml("<font color=#000000>$text</font>"))
            setSelection(text.toString().length)
        }
    }

    private fun setHintTextSilently(
        textWatcher: TextWatcher,
        text: CharSequence,
        suggestion: String
    ) {
        removeTextChangedListener(textWatcher)
        val fullText = "<font color=#000000>$text</font><font color=#c2c2c2>$suggestion</font>"
        setText(HtmlCompat.fromHtml(fullText))
        setSelection(text.length)
        addTextChangedListener(textWatcher)
    }

}