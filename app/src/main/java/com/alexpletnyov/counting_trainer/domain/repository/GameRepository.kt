package com.alexpletnyov.counting_trainer.domain.repository

import com.alexpletnyov.counting_trainer.domain.entity.GameSettings
import com.alexpletnyov.counting_trainer.domain.entity.Level
import com.alexpletnyov.counting_trainer.domain.entity.Question

interface GameRepository {

	fun generateQuestion(
		maxSumValue: Int,
		countOfOptions: Int
	): Question

	fun getGameSettings(level: Level): GameSettings
}