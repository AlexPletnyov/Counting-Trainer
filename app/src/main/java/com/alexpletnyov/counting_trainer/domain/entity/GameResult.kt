package com.alexpletnyov.counting_trainer.domain.entity

data class GameResult(
	val winner: Boolean,
	val countOfRightAnswers: Int,
	val countOfQuestions: Int,
	val gameSettings: GameSettings
)