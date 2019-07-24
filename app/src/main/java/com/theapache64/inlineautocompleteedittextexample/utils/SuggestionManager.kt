package com.theapache64.inlineautocompleteedittextexample.utils


class SuggestionManager(private val dictionary: Array<String>) {

    fun getSuggestionFor(text: String?): String? {

        // empty text
        if (text.isNullOrBlank()) {
            return null
        }

        // Splitting words by space
        val words = text.split(" ").filter { it.isNotBlank() }

        // Getting last word
        val lastWord = words.last()

        // Matching if last word exist in any dictionary
        val suggestions = mutableSetOf<String>()
        for (dicItem in dictionary) {
            if (dicItem.contains(lastWord, true)) {
                // Storing founded matches
                suggestions.add(dicItem)
            }
        }

        // Reverse ordering split-ed words
        val pyramidWords = getReversedList(words)
        val matchList = mutableListOf<String>()
        for (pw in pyramidWords) {
            for (sug in suggestions) {
                // Storing suggestions starts with reversed word
                if (sug.startsWith(pw, true)) {
                    matchList.add("$pw:$sug")
                }
            }
        }


        // Checking if second level match is not empty
        if (matchList.isNotEmpty()) {

            // Ordering by matched reversed word - (largest key first)
            matchList.sortBy { -it.split(":")[0].length }

            // Looping through in ascending order
            for (m in matchList) {

                val match = m.split(":")
                val selPw: String = match[0]
                var selSug: String = match[1]

                // trimming to
                selSug = selSug.replace(selPw, "", true)

                // Checking length
                val selSugLength = selSug.length
                val selSugQuarter = selSugLength * 0.25
                val selPwLength = selPw.length

                // Checking if typed word length is more than 25% of the suggestion
                if (selPwLength < selSugQuarter) {
                    return null
                }

                if (text.endsWith(" ")) {
                    selSug = selSug.trim()
                }

                return selSug
            }
        }



        return null
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