package com.example.dbmasterandroid

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.dbmasterandroid.utils.PreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_drawer_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

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
                R.id.signUpPwFragment, R.id.signUpFragment,
                R.id.signUpEmailFragment->{
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
                R.id.settingFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "환경 설정"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.settingPasswordFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "비밀번호 변경"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.settingNameFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "테이블 이름 변경"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.settingDropFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "테이블 삭제"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.tableDataFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "전체 보기"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
                R.id.dataExportFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "테이블 추출"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.dataInsertFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "데이터 추가"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.tableControlFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "테이블 관리"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.dataUpdateFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "데이터 갱신"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.tableJoinFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "테이블 조인"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.customQueryFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "쿼리문 실행"
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                else->{
                    main_toolbar.visibility = View.GONE
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        }

        drawer_nav_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_table_change->{
                    navController.navigate(R.id.action_mainFragment_to_tableSelectFragment)
                }
                R.id.navigation_table_control->{
                    navController.navigate(R.id.action_mainFragment_to_tableControlFragment)
                }
                R.id.navigation_setting->{
                    navController.navigate(R.id.action_mainFragment_to_settingFragment)
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
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
