package com.app.esports

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.app.esports.SignInFragment.Companion.REMEMBER_ME
import com.app.esports.SignInFragment.Companion.SAVED_PASSWORD
import com.app.esports.SignInFragment.Companion.SAVED_USERNAME
import com.app.esports.databinding.ActivityMainBinding
import com.app.esports.ui.ProposalFragment
import com.app.esports.ui.WhatWePlayFragment
import com.app.esports.ui.WhoWeAreFragment
import com.app.esports.ui.auth.AuthActivity
import com.app.esports.ui.schedule.ScheduleFragment

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    public lateinit var binding: ActivityMainBinding
    val fragments: ArrayList<Fragment> = ArrayList()
    public lateinit var active_user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        active_user = (intent.getParcelableExtra<User>(USER)?: finish()) as User

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_whatweplay, R.id.nav_whoweare, R.id.nav_schedule,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        fragments.add(WhatWePlayFragment())
        fragments.add(ScheduleFragment())
        fragments.add(WhoWeAreFragment())

        with(binding.appBarMain.main){
            viewPager.adapter = ViewPagerAdapter(this@MainActivity, fragments)
            viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    bottomNav.selectedItemId = bottomNav.menu.getItem(position).itemId

                    when(position){
                        0 -> {
                            viewPager.setCurrentItem(0, true)
                        }
                        1 -> {
                            viewPager.setCurrentItem(1, true)
                        }
                        2 -> {
                            viewPager.setCurrentItem(2, true)
                        }
                    }
                }
            })

            bottomNav.setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.itemWhatWePlay -> {
                        viewPager.setCurrentItem(0, true)
                        supportActionBar?.title = "GAMES"
                    }
                    R.id.itemOurSchedule -> {
                        viewPager.setCurrentItem(1, true)
                        supportActionBar?.title = "SCHEDULE"
                    }
                    R.id.itemWhoWeAre -> {
                        viewPager.setCurrentItem(2, true)
                        supportActionBar?.title = "TEAMS"
                    }
                }
                navController.navigateUp()

                toggleMainAndAdditionalFragments(true)
                true
            }

            navView.setNavigationItemSelectedListener {
                var showMain = true
                when(it.itemId){
                    R.id.nav_whatweplay -> viewPager.setCurrentItem(0, true)
                    R.id.nav_schedule -> viewPager.setCurrentItem(1, true)
                    R.id.nav_whoweare -> viewPager.setCurrentItem(2, true)


                    R.id.nav_signout ->{
                        val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences("SETTING", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean(SignInFragment.REMEMBER_ME, false)
                        editor.remove(SignInFragment.SAVED_USERNAME)
                        editor.remove(SignInFragment.SAVED_PASSWORD)
                        editor.apply()

                        val intent = Intent(applicationContext, AuthActivity::class.java)
                        startActivity(intent)

                        finish()
                    }
                    else -> {
                        navController.navigate(it.itemId)
                        showMain = false
                    }
                }
                toggleMainAndAdditionalFragments(showMain)

                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }
        toggleMainAndAdditionalFragments(true)
    }


    public fun toggleMainAndAdditionalFragments(showMain: Boolean) {
        binding.appBarMain.main.viewPager.visibility = if (showMain) View.VISIBLE else View.GONE
        binding.appBarMain.main.fragmentContainer.visibility = if (showMain) View.GONE else View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        val menuItem = menu?.findItem(R.id.username)

        menuItem?.title = "Hi, ${active_user.username}" // change to active user
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences("SETTING", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean(SignInFragment.REMEMBER_ME, false)
                editor.remove(SignInFragment.SAVED_USERNAME)
                editor.remove(SignInFragment.SAVED_PASSWORD)
                editor.apply()

                val intent = Intent(applicationContext, AuthActivity::class.java)
                startActivity(intent)

                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    companion object{
        val USER = "USER"
        //temporary array
//        var userData: ArrayList<User> = arrayListOf(
//            User(1, "user1", "user1@gmail.com", "password1"),
//            User(2, "user2", "user2@gmail.com", "password2"),
//            User(3, "admin", "admin@gmail.com", "admin123")
//        )
    }
}