package com.app.esports

import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.app.esports.databinding.ActivityMainBinding
import com.app.esports.ui.WhatWePlayFragment
import com.app.esports.ui.WhoWeAreFragment
import com.app.esports.ui.schedule.ScheduleFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    public lateinit var binding: ActivityMainBinding
    val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

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
                            title = "GAMES"
                        }
                        1 -> {
                            viewPager.setCurrentItem(1, true)
                            title = "SCHEDULE"
                        }
                        2 -> {
                            viewPager.setCurrentItem(2, true)
                            title = "TEAMS"
                        }
                    }
                }
            })

            bottomNav.setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.itemWhatWePlay -> {
                        viewPager.setCurrentItem(0, true)
                        title = "GAMES"
                    }
                    R.id.itemOurSchedule -> {
                        viewPager.setCurrentItem(1, true)
                        title = "SCHEDULE"
                    }
                    R.id.itemWhoWeAre -> {
                        viewPager.setCurrentItem(2, true)
                        title = "TEAMS"
                    }
                }
                toggleMainAndAdditionalFragments(true)
                true
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                var showMain = true
                when(destination.id){
                    R.id.nav_whatweplay -> viewPager.setCurrentItem(0, true)
                    R.id.nav_schedule -> viewPager.setCurrentItem(1, true)
                    R.id.nav_whoweare -> viewPager.setCurrentItem(2, true)

                    else -> {
                        showMain = false
                    }
                }
                toggleMainAndAdditionalFragments(showMain)
            }
        }
        toggleMainAndAdditionalFragments(true)
    }


    private fun toggleMainAndAdditionalFragments(showMain: Boolean) {
        binding.appBarMain.main.viewPager.visibility = if (showMain) View.VISIBLE else View.GONE
        binding.appBarMain.main.fragmentContainer.visibility = if (showMain) View.GONE else View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}