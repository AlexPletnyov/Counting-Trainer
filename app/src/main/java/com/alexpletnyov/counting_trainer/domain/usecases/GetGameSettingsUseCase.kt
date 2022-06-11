package com.alexpletnyov.counting_trainer.domain.usecases

import com.alexpletnyov.counting_trainer.domain.entity.GameSettings
import com.alexpletnyov.counting_trainer.domain.entity.Level
import com.alexpletnyov.counting_trainer.domain.repository.GameRepository
import javax.inject.Inject

class GetGameSettingsUseCase @Inject constructor(
	private val repository: GameRepository
) {

	operator fun invoke(level: Level): GameSettings {
		return repository.getGameSettings(level)
	}
}