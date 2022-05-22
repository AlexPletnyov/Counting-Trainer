package com.alexpletnyov.counting_trainer.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexpletnyov.counting_trainer.domain.entity.Level
import java.lang.RuntimeException

class GameViewModelFactory(
	private val level: Level,
	private val application: Application
) : ViewModelProvider.Factory {

	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
			return GameViewModel(level, application) as T
		}
		throw RuntimeException("Unknown view model class $modelClass")
	}
}