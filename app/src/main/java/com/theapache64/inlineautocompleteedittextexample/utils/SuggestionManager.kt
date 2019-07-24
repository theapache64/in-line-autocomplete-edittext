package com.theapache64.inlineautocompleteedittextexample.utils


class SuggestionManager(private val dictionary: Array<String>) {

    fun getSuggestionFor(text: String?): String? {

        if (text.isNullOrBlank()) {
            return null
        }

        val words = text.split(" ").filter { it.isNotBlank() }

        // last word length must be greater than or equal to 2
        val lastWord = words.last()

        val suggestions = mutableSetOf<String>()
        for (dicItem in dictionary) {
            if (dicItem.contains(lastWord, true)) {
                suggestions.add(dicItem)
            }
        }

        val pyramidWords = mutableSetOf<String>()
        var wordBlock = ""
        for (word in words) {
            if (word.isNotBlank()) {
                wordBlock += "$word "
                pyramidWords.add(wordBlock.trim())
            }
        }

        val py2 = mutableSetOf<String>()
        for (w in words.reversed()) {
            py2.add(w)
        }

        val matches = mutableSetOf<String>()
        for (pw in pyramidWords) {
            for (sug in suggestions) {
                if (sug.startsWith(pw, true)) {
                    matches.add(sug)
                }
            }
        }

        val finalMatch = matches.firstOrNull()

        if (finalMatch != null) {

            val finalMatchHalfLength = finalMatch.length * 0.25f
            val typedLength = kotlin.run {
                val lastWordIndex = finalMatch.indexOf(lastWord)
                finalMatch.substring(0, lastWordIndex).length + lastWord.length
            }

            if (typedLength >= finalMatchHalfLength) {
                return finalMatch
            }

        }

        return null
    }
}