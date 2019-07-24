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
    private var hasActiveSuggestion = false
    private var tempText: String? = null

    init {
        addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                val suggestion = suggestionMan.getSuggestionFor(s.toString())
                if (suggestion != null) {
                    val newText =
                        "<font color=\"#cc0029\">${s.toString()}</font><font color=\"#ffcc00\">$suggestion</font>"
                    removeTextChangedListener(this)
                    tempText = s.toString()
                    setText(HtmlCompat.fromHtml(newText))
                    setSelection(s!!.length)
                    addTextChangedListener(this)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                info("beforeTextChanged $s")
                if (hasActiveSuggestion && tempText != null) {
                    removeTextChangedListener(this)
                    setText(tempText)
                    setSelection(tempText!!.length)
                    hasActiveSuggestion = false
                    tempText = null
                    addTextChangedListener(this)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                info("onTextChanged $s")

            }

        })
    }


}