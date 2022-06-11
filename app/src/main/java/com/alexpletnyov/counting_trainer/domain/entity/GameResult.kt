package com.alexpletnyov.counting_trainer.domain.entity

import javax.inject.Inject

data class GameResult @Inject constructor(
	val winner: Boolean,
	val countOfRightAnswers: Int,
	val countOfQuestions: Int,
	val gameSettings: GameSettings
)