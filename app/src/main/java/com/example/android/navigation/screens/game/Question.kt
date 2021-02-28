package com.example.android.navigation.screens.game

data class Question (
        val questionId:Int,
        val answer:Boolean,
        var attempted:Boolean = false,
        var answered:Boolean = false
)
