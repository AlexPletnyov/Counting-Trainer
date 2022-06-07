package com.alexpletnyov.counting_trainer.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexpletnyov.counting_trainer.data.GameRepositoryImpl
import com.alexpletnyov.counting_trainer.domain.usecases.GenerateQuestionUseCase
import com.alexpletnyov.counting_trainer.domain.usecases.GetGameSettingsUseCase

class GameViewModelFactory(
	private val application: Application
) : ViewModelProvider.Factory {

	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
			return GameViewModel(
				GetGameSettingsUseCase(GameRepositoryImpl),
				GenerateQuestionUseCase(GameRepositoryImpl),
				application) as T
		}
		throw RuntimeException("Unknown view model class $modelClass")
	}
}