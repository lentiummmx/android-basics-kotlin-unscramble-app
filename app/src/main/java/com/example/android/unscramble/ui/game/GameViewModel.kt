package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "GameFragment"

/**
 * ViewModel containing the app data and methods to process the data
 */
class GameViewModel : ViewModel() {

    /**
     * Backing property implementation example
     */
    // Declare private mutable variable that can only be modified
    // within the class it is declared.
    private var _count = 0

    // Declare another public immutable field and override its getter method.
    // Return the private property's value in the getter method.
    // When count is accessed, the get() function is called and
    // the value of _count is returned.
    val count: Int
        get() = _count

    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    // List of word used in the game
    private var wordsLst: MutableList<String> = mutableListOf()

    private lateinit var currentWord: String

    /**
     * Updates currentWord and currentScrambledWord with the next word
     */
    private fun getNextWord() {
        currentWord = allWordsList.random()

        if (wordsLst.contains(currentWord)) {
            getNextWord()
        } else {
            var tmpWord = currentWord.toCharArray()
            tmpWord.shuffle()

            while (String(tmpWord).equals(currentWord, false)) {
                tmpWord.shuffle()
            }

            _currentScrambledWord = String(tmpWord)
            ++_currentWordCount
            wordsLst.add(currentWord)
        }
    }

    /**
     * Returns true if the current word count is less than MAX_NO_OF_WORDS.
     * Updates the next word.
     */
    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    /**
     * Increases the game score if the player's word is correct
     */
    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    /**
     * Returns true if the player word is correct
     * Increases the score accordingly
     */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord)) {
            increaseScore()
            return true
        }
        return false
    }

    /**
     * Re-initializes the game data to restart the game
     */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsLst.clear()
        getNextWord()
    }

    init {
        Log.d(TAG, "GameViewModel created!")
        getNextWord()
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     *
     *
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "GameViewModel destroyed!")
    }

}