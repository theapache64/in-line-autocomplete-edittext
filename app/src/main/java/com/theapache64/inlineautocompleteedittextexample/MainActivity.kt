package com.theapache64.inlineautocompleteedittextexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val etMessage = findViewById<InLineAutoCompleteEditText>(R.id.et_message)
        etMessage.dictionary = arrayOf(
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


    }

}
