package com.example.dbmasterandroid

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dbmasterandroid.utils.PreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityApplication : AppCompatActivity() {

    companion object {
        lateinit var preferences: PreferenceUtil
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = PreferenceUtil(applicationContext)

        setSupportActionBar(main_toolbar)

        val host = fragment_container as NavHostFragment
        val navController = host.navController

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.loginFragment,
                R.id.tableSelectFragment,
                R.id.mainFragment))

        main_toolbar.navigationIcon?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "로그인"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.signUpIdFragment, R.id.signUpMainFragment,
                R.id.signUpPwFragment, R.id.signUpFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "회원가입"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.tableSelectFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "테이블 선택"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.mainFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = ""
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
                else->{
                    main_toolbar.visibility = View.GONE
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_container).navigateUp()
    }
}