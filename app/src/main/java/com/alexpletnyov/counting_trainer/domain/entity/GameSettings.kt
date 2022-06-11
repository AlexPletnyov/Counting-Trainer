package com.alexpletnyov.counting_trainer.domain.entity

import javax.inject.Inject

data class GameSettings @Inject constructor(
	val maxSumValue: Int,
	val minCountOfRightAnswers: Int,
	val minPercentOfRightAnswers: Int,
	val gameTimeInSeconds: Int,
)