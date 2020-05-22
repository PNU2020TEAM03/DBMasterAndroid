package com.example.dbmasterandroid

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_drawer_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivityApplication : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

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
                R.id.mainFragment), drawer_layout)

        main_toolbar.navigationIcon?.setTint(Color.WHITE)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            hideKeyboard()
            when (destination.id) {
                R.id.signUpIdFragment, R.id.signUpMainFragment,
                R.id.signUpPwFragment, R.id.signUpFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "회원가입"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.mainFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "테이블 관리"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
                R.id.tableCreateNameFragment, R.id.tableCreateInfoFragment,
                R.id.tableColumnNameFragment, R.id.tableColumnTypeFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "테이블 생성"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                else->{
                    main_toolbar.visibility = View.GONE
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        }

        drawer_nav_view.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.navigation_table_change->{
                        navController.navigate(R.id.action_mainFragment_to_tableSelectFragment)
                    }
                    R.id.navigation_logout->{
                        viewModel.logout()
                        navController.navigate(R.id.action_mainFragment_to_loginFragment)
                    }
                }
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_container).navigateUp(appBarConfiguration)
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if no view has focus
        val currentFocusedView = this.currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                    currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun setUserTableName(name: String, table: String) {
        drawer_id_tv.text = "$name 님."
        drawer_table_tv.text = table
    }

}
