package com.alexpletnyov.counting_trainer.domain.entity

import javax.inject.Inject

data class Question @Inject constructor(
	val sum: Int,
	val visibleNumber: Int,
	val options: List<Int>
) {
	private val rightAnswer: Int = sum - visibleNumber
	val rightAnswerPos get() = options.indexOfFirst { it == rightAnswer}
}