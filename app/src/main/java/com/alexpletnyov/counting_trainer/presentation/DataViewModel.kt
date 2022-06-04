package com.alexpletnyov.counting_trainer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexpletnyov.counting_trainer.domain.entity.GameResult
import com.alexpletnyov.counting_trainer.domain.entity.Level

class DataViewModel : ViewModel() {

	private val _level = MutableLiveData<Level>()
	val level: LiveData<Level>
		get() = _level

	private val _gameResult = MutableLiveData<GameResult>()
	val gameResult: LiveData<GameResult>
		get() = _gameResult

	fun setLevel(level: Level) {
		_level.value = level
	}

	fun setGameResult(gameResult: GameResult) {
		_gameResult.value = gameResult
	}
}