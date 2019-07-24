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

        val pyramidWords = getReversedList(words)

        val matches = mutableMapOf<String, String>()
        for (pw in pyramidWords) {
            for (sug in suggestions) {
                if (sug.startsWith(pw, true)) {
                    matches[pw] = sug
                }
            }
        }

        if (matches.isNotEmpty()) {

            val finalMatch = matches[matches.keys.last()]

            if (finalMatch != null) {

                val finalMatchHalfLength = finalMatch.length * 0.25f
                val typedLength = kotlin.run {
                    val lastWordIndex = finalMatch.indexOf(lastWord, 0, true)
                    finalMatch.substring(0, lastWordIndex).length + lastWord.length
                }

                if (typedLength >= finalMatchHalfLength) {
                    val lastWordIndex = finalMatch.lastIndexOf(matches.keys.last())
                    return finalMatch.substring(lastWordIndex + matches.keys.last().length)
                }

            }
        }

        return null
    }


    private fun getReversedList(list: List<String>): MutableSet<String> {

        val reversed = mutableSetOf<String>()
        for (item in list.withIndex()) {
            if (item.index != 0) {
                val x = list.subList(list.size - item.index, list.size).joinToString(" ")
                reversed.add(x)
            }
        }

        reversed.add(list.joinToString(" "))
        return reversed
    }
}