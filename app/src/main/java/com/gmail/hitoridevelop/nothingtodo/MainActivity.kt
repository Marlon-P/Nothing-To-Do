package com.gmail.hitoridevelop.nothingtodo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.gmail.hitoridevelop.nothingtodo.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    //[DONE] save data to room db
    //[DONE] add are you sure dialog for saving data
    //[DONE] add snack bar when finishing save and add undo function
    //[DONE] show list of saved activities when clicking the saved folder icon
    //[DONE] add loading screen when querying api
    //[DONE] add button to complete activity and SnackBar to undo complete
    //[DONE] add function to get completed activities and unfinished activities
    //[DONE] add fragment that shows list of completed activities
    //TODO add testing
    //[DONE] add swipe to complete and swipe to delete
    //TODO add swipe to delete for completed activities
    //TODO add night mode
    //TODO deal with configuration changes
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.navView.bringToFront()
        binding.navView.setNavigationItemSelectedListener(this)

        setUpToolBar(binding.toolbar)



    }

    private fun setUpToolBar(toolbar: androidx.appcompat.widget.Toolbar) {
        //every time you change the title of the toolbar you have to setup the drawer toggle as well
        //otherwise you can't navigate back home
        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean  = when (item.itemId) {
        R.id.day_night_mode -> {
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Toast.makeText(this, "menu item selected", Toast.LENGTH_SHORT).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                binding.toolbar.setTitle(R.string.app_name)
                setUpToolBar(binding.toolbar)
                binding.myNavHostFragment.findNavController().navigate(R.id.suggestActivityFragment)
                binding.drawerLayout.closeDrawers()
            }
            R.id.activities_to_do -> {
                binding.toolbar.setTitle(R.string.activities_to_do)
                setUpToolBar(binding.toolbar)
                binding.myNavHostFragment.findNavController().navigate(R.id.doLaterActivitiesFragment)
                binding.drawerLayout.closeDrawers()

            }
            R.id.completed_activities -> {
                binding.toolbar.setTitle(R.string.complete_activities)
                setUpToolBar(binding.toolbar)
                binding.myNavHostFragment.findNavController().navigate(R.id.completedActivitiesFragment)
                binding.drawerLayout.closeDrawers()
            }
        }
        return true
    }



}