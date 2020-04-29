package com.example.dbmasterandroid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityApplication : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)

        val host = fragment_container as NavHostFragment
        val navController = host.navController

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.loginFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment->{
                    main_toolbar.visibility = View.INVISIBLE
                    supportActionBar?.hide()
                }
                R.id.loginFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "로그인"
                }
                R.id.signupFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "회원가입"
                }
                R.id.howToUseFirst->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "DB Master"
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_container).navigateUp()
    }
}