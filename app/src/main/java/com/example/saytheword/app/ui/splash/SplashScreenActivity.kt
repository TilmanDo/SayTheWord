package com.example.saytheword.app.ui.splash

import android.content.Intent
import com.example.saytheword.app.ui.MainActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.saytheword.R
import com.example.saytheword.databinding.ActivitySplashScreenBinding

class SplashScreenActivity: AppCompatActivity() {

    val DELAY : Long = 1000

    lateinit var binding : ActivitySplashScreenBinding

    /**
     * Shows the Splash Screen and calls [navigateToMainActivity]
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        binding.activity = this

        supportActionBar?.hide()

        navigateToMainActivity(DELAY)

        super.onCreate(savedInstanceState)
    }

    /**
     * Launches [MainActivity]
     *
     * @param delay The time between showing the Splash Screen and launching [MainActivity]
     */
    private fun navigateToMainActivity(delay: Long){

        Handler(Looper.getMainLooper()).postDelayed({

            val mainActivityLaunchIntent = Intent(this, MainActivity::class.java)

            startActivity(mainActivityLaunchIntent)

        }, delay)

    }

}