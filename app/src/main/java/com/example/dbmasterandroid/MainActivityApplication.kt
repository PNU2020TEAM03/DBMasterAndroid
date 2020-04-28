package com.example.dbmasterandroid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityApplication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)

        val host = fragment_container as NavHostFragment
        val navController = host.navController

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signupActivity -> {
                    main_toolbar.visibility = View.VISIBLE
                    main_toolbar.title = "회원가입"
                    supportActionBar?.show()
                }
            }
        }
    }
}