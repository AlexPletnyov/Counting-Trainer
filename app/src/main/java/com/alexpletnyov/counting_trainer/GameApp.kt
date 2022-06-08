package com.alexpletnyov.counting_trainer

import android.app.Application
import com.alexpletnyov.counting_trainer.presentation.GameViewModelFactory

class GameApp: Application() {

	val factory by lazy {
		GameViewModelFactory(this)
	}
}