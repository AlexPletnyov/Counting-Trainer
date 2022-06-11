package com.alexpletnyov.counting_trainer.presentation

import android.app.Application
import com.alexpletnyov.counting_trainer.di.DaggerApplicationComponent

class GameApplication : Application() {

	val component by lazy {
		DaggerApplicationComponent.factory().create(this)
	}
}