package com.example.android.navigation.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.navigation.R


class GameViewModel : ViewModel() {

    private var questionIndex = 0

    private lateinit var questionBank: MutableList<Question>

    private val _scoreString = MutableLiveData<String>()
    val score: LiveData<String>
        get() = _scoreString

    private val _question =
            MutableLiveData<Int>()
    val question: LiveData<Int>
        get() = _question



    private val _attempted = MutableLiveData<Boolean>()
    val attempted: LiveData<Boolean>
        get() = _attempted

    private val _checkTrue = MutableLiveData<Boolean>()
    val checkTrue: LiveData<Boolean>
        get() = _checkTrue

    private val _checkFalse = MutableLiveData<Boolean>()
    val checkFalse: LiveData<Boolean>
        get() = _checkFalse

    private val _isCorrect = MutableLiveData<Boolean>()
    val isCorrect: LiveData<Boolean>
        get() = _isCorrect

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished



    fun onGameFinish(){
        _eventGameFinished.value = true
    }

    fun onGameFinishComplete(){
        _eventGameFinished.value = false
    }

    fun questionsAttempted() = questionBank.count{it.attempted}

    fun newGame(){
        resetQuestionBank()
        questionIndex = 0
        _eventGameFinished.value = false
        updateQuestion()
    }

    fun updateQuestion() {

        _question.value = questionBank[questionIndex].questionId
        _attempted.value = questionBank[questionIndex].attempted

        _isCorrect.value = questionBank[questionIndex].answer == questionBank[questionIndex].answered
        _checkFalse.value = questionBank[questionIndex].attempted and (questionBank[questionIndex].answered == false)
        _checkTrue.value = questionBank[questionIndex].attempted and (questionBank[questionIndex].answered == true)

        _scoreString.value = " Your score: ${questionBank.count{it.attempted && (it.answer == it.answered)}}/${questionBank.size}"

        if(questionsAttempted() == questionBank.size){
            onGameFinish()
        }
    }



    private fun resetQuestionBank() {
        questionBank = mutableListOf(
                Question(R.string.question_1, false),
                Question(R.string.question_2, true),
                Question(R.string.question_3, true),
                Question(R.string.question_4, false),
                Question(R.string.question_5, false),
                Question(R.string.question_6, true),
                Question(R.string.question_7, false),
                Question(R.string.question_8, true),
                Question(R.string.question_9, false),
                Question(R.string.question_10, false),
                Question(R.string.question_11, false),
                Question(R.string.question_12, true),
                Question(R.string.question_13, false),
                Question(R.string.question_14, true),
                Question(R.string.question_15, false),
                Question(R.string.question_16, false),
                Question(R.string.question_17, true),
                Question(R.string.question_18, false),
                Question(R.string.question_19, false),
                Question(R.string.question_20, true)
        )
        questionBank.shuffle()


    }

    override fun onCleared() {
        super.onCleared()
        Log.i("qqq", "onCleared viewModel")
    }



    fun next(): Unit {
        questionIndex = (questionIndex + questionBank.size + 1) % questionBank.size
        updateQuestion()


    }

    fun prev(): Unit {
        questionIndex = (questionIndex + questionBank.size - 1) % questionBank.size
        updateQuestion()

    }


    fun onSelected(answer: Boolean) {
        questionBank[questionIndex].answered = answer
        questionBank[questionIndex].attempted = true
        updateQuestion()
  }

    init{
        newGame()
    }


}