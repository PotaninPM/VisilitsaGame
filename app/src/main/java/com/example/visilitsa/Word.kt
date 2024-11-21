package com.example.visilitsa

class Word {

    fun getRandomWordRu(): String {
        val words = listOf("кот", "собака", "дерево", "машина", "дом")
        return words.random()
    }

    fun getRandomWordEnd(): String {
        val words = listOf("cat", "dog", "tree", "car", "house")
        return words.random()
    }

    fun findLetter(word: String, letter: Char): Boolean {
        return word.contains(letter)
    }
}