package com.alexpletnyov.counting_trainer.domain.entity

data class Question(
	val sum: Int,
	val visibleNumber: Int,
	val options: List<Int>
) {
	private val rightAnswer: Int = sum - visibleNumber
	val rightAnswerPos get() = options.indexOfFirst { it == rightAnswer}
}