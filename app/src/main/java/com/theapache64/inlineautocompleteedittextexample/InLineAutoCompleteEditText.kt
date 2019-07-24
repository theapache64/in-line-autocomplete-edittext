package com.theapache64.inlineautocompleteedittextexample

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.theapache64.twinkill.logger.info


class InLineAutoCompleteEditText(context: Context?, attrs: AttributeSet?) :
    EditText(context, attrs) {
    lateinit var dictionary: Array<String>

    init {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                info("beforeTextChanged $s")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                info("onTextChanged $s")
            }

        })
    }



}