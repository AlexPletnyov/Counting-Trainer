package com.alexpletnyov.counting_trainer.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alexpletnyov.counting_trainer.R

class MainActivity : AppCompatActivity() {

	var factory:GameViewModelFactory? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		factory = GameViewModelFactory(application)
	}
}