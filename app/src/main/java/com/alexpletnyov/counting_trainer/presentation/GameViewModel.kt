package com.alexpletnyov.counting_trainer.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexpletnyov.counting_trainer.R
import com.alexpletnyov.counting_trainer.domain.entity.GameResult
import com.alexpletnyov.counting_trainer.domain.entity.GameSettings
import com.alexpletnyov.counting_trainer.domain.entity.Level
import com.alexpletnyov.counting_trainer.domain.entity.Question
import com.alexpletnyov.counting_trainer.domain.usecases.GenerateQuestionUseCase
import com.alexpletnyov.counting_trainer.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
	private val getGameSettingsUseCase: GetGameSettingsUseCase,
	private val generateQuestionUseCase: GenerateQuestionUseCase,
	private val application: Application
) : ViewModel() {

	private lateinit var gameSettings: GameSettings

	private var timer: CountDownTimer? = null

	private val _level = MutableLiveData<Level>()
	val level: LiveData<Level>
		get() = _level

	private val _formattedTime = MutableLiveData<String>()
	val formattedTime: LiveData<String>
		get() = _formattedTime

	private val _question = MutableLiveData<Question>()
	val question: LiveData<Question>
		get() = _question

	private val _percentOfRightAnswers = MutableLiveData<Int>()
	val percentOfRightAnswers: LiveData<Int>
		get() = _percentOfRightAnswers

	private val _progressAnswers = MutableLiveData<String>()
	val progressAnswers: LiveData<String>
		get() = _progressAnswers

	private val _enoughPercent = MutableLiveData<Boolean>()
	val enoughPercent: LiveData<Boolean>
		get() = _enoughPercent

	private val _timeRunningOut = MutableLiveData<Boolean>()
	val timeRunningOut: LiveData<Boolean>
		get() = _timeRunningOut

	private val _enoughCount = MutableLiveData<Boolean>()
	val enoughCount: LiveData<Boolean>
		get() = _enoughCount

	private val _minPercent = MutableLiveData<Int>()
	val minPercent: LiveData<Int>
		get() = _minPercent

	private val _gameResult = MutableLiveData<GameResult>()
	val gameResult: LiveData<GameResult>
		get() = _gameResult

	private var countOfRightAnswers = 0
	private var countOfQuestions = 0

	fun startGame(level: Level) {
		getGameSettings(level)
		startTimer()
		generateQuestion()
		updateProgress()
	}

	private fun finishGame() {
		_gameResult.value = GameResult(
			enoughCount.value == true && enoughPercent.value == true,
			countOfRightAnswers,
			countOfQuestions,
			gameSettings
		)
	}

	fun chooseAnswer(number: Int) {
		checkAnswer(number)
		updateProgress()
		generateQuestion()
	}

	private fun updateProgress() {
		val percent = calculatePercentOfRightAnswers()
		_percentOfRightAnswers.value = percent
		_progressAnswers.value = String.format(
			application.resources.getString(R.string.progress_answers),
			countOfRightAnswers,
			gameSettings.minCountOfRightAnswers
		)
		_enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
		_enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
	}

	private fun calculatePercentOfRightAnswers(): Int {
		if (countOfQuestions == 0) {
			return 0
		}
		return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
	}

	private fun checkAnswer(number: Int) {
		val rightAnswer = question.value?.rightAnswer
		if (number == rightAnswer) {
			countOfRightAnswers++
		}
		countOfQuestions++
	}

	private fun generateQuestion() {
		_question.value = generateQuestionUseCase(gameSettings.maxSumValue)
	}

	private fun getGameSettings(level: Level) {
		gameSettings = getGameSettingsUseCase(level)
		_minPercent.value = gameSettings.minPercentOfRightAnswers
	}

	private fun startTimer() {
		timer = object : CountDownTimer(
			gameSettings.gameTimeInSeconds * MILLIS_IN_SECOND,
			MILLIS_IN_SECOND
		) {
			override fun onTick(millsUntilFinished: Long) {
				_formattedTime.value = formatTime(millsUntilFinished)
				_timeRunningOut.value = millsUntilFinished < RUNNING_OUT_TIME
			}

			override fun onFinish() {
				finishGame()
			}
		}
		timer?.start()
	}

	private fun formatTime(millsUntilFinished: Long): String {
		val seconds = millsUntilFinished / MILLIS_IN_SECOND
		val minutes = seconds / SECONDS_IN_MINUTE
		val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTE)
		return String.format("%02d:%02d", minutes, leftSeconds)
	}

	fun setLevel(level: Level) {
		_level.value = level
	}

	//TODO Move timer cancellation to another method
	override fun onCleared() {
		super.onCleared()
		timer?.cancel()
	}

	companion object {

		private const val MILLIS_IN_SECOND = 1000L
		private const val SECONDS_IN_MINUTE = 60
		private const val RUNNING_OUT_TIME = 10_000
	}
}