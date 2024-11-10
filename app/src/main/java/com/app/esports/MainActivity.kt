package com.app.esports

import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
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
import com.app.esports.ui.home.HomeFragment
import com.app.esports.ui.schedule.ScheduleFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
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

        binding.appBarMain.main.viewPager.adapter = ViewPagerAdapter(this, fragments)

        binding.appBarMain.main.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.appBarMain.main.bottomNav.selectedItemId = binding.appBarMain.main.bottomNav.menu.getItem(position).itemId
            }
        })

        binding.appBarMain.main.bottomNav.setOnItemSelectedListener {
            binding.appBarMain.main.viewPager.currentItem = when(it.itemId){
                R.id.itemWhatWePlay -> 0
                R.id.itemOurSchedule -> 1
                R.id.itemWhoWeAre -> 2

                else -> 0
            }
            true
        }
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