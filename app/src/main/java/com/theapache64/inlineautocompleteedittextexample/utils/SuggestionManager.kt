package com.theapache64.inlineautocompleteedittextexample.utils


class SuggestionManager(private val dictionary: Array<String>) {

    fun getSuggestionFor(text: String?): String? {

        if (text.isNullOrBlank()) {
            return null
        }

        val words = text.split(" ").filter { it.isNotBlank() }
        val lastWord = words.last()

        val suggestions = mutableSetOf<String>()
        for (dicItem in dictionary) {
            if (dicItem.contains(lastWord, true)) {
                suggestions.add(dicItem)
            }
        }

        val pyramidWords = getReversedList(words)
        var selSug: String? = null
        var selPw: String? = null
        for (pw in pyramidWords) {
            if (pw.trim().length <= 1) {
                continue
            }

            for (sug in suggestions) {
                if (sug.startsWith(pw, true)) {
                    selSug = sug
                    selPw = pw
                    break
                }
            }

            if (selSug != null) {
                break
            }
        }

        if (selSug != null) {
            // trimming to
            selSug = selSug.replace(selPw!!, "", true)

            // Checking length
            val selSugLength = selSug.length
            val selSugQuarter = selSugLength * 0.25
            val selPwLength = selPw.length

            if (selPwLength < selSugQuarter) {
                selSug = null
            }

        }



        return selSug
    }


    private fun getReversedList(list: List<String>): MutableSet<String> {

        val reversed = mutableSetOf<String>()

        for (item in list.withIndex()) {
            if (item.index != 0) {
                val rev = list.subList(list.size - item.index, list.size).joinToString(" ")
                reversed.add(rev)
            }
        }

        // finally, add the full string
        reversed.add(list.joinToString(" "))
        return reversed
    }
}