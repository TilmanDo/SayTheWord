package com.example.saytheword.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.saytheword.R


class MainActivity : AppCompatActivity() {

    val viewModel: MainActivityViewModel by viewModels()

    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val navHostFragment : NavHostFragment= supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_frag) as NavHostFragment

        navController = navHostFragment.navController

        observeViewModel()
    }

    private fun observeViewModel(){

        val onNavigationEventObserver = Observer<Int>{

            if(it != -1) navigate(it)

        }

        viewModel.navigationEvent.observe(this, onNavigationEventObserver)

    }

    private fun navigate(action: Int){

        navController.navigate(action)

    }

    override fun onBackPressed() {

        viewModel.navigateBackwards()

    }



}