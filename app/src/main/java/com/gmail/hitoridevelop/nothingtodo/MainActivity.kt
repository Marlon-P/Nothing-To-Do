package com.gmail.hitoridevelop.nothingtodo


import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
    //[DONE] add night mode
    //[DONE] save night mode in shared preferences
    //TODO add more seamless transition to night mode, there's a bug where it takes two clicks to switch back to day mode
    //TODO deal with configuration changes
    private lateinit var binding: ActivityMainBinding

    private val key = "activities"
    private val preferences by lazy { getSharedPreferences(key, Context.MODE_PRIVATE) }
    private val mode = "day_night_mode"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNightMode()

        binding.navView.bringToFront()
        binding.navView.setNavigationItemSelectedListener(this)


        setUpToolBar()


    }

    private fun setupNightMode() {

        if (!preferences.contains(mode)) {
            with(preferences.edit()) {
                putString(mode, "Day")
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
                apply()
            }
        } else {
            when (preferences.getString(mode, "Day")) {
                "Day" -> {
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
                }
                else -> {
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
                }
            }
        }
    }

    private fun setUpToolBar() {
        //every time you change the title of the toolbar you have to setup the drawer toggle as well
        //otherwise you can't navigate back home
        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.action_bar_menu, menu)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.day_night_mode -> {
            when (item.title) {
                "Day Mode" -> {
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
                    item.title = "Night Mode"
                    with(preferences.edit()) {
                        putString(mode, "Day")//current mode is day
                        apply()
                    }
                    println("night mode off")
                }
                "Night Mode" -> {
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
                    item.title = "Day Mode"
                    with(preferences.edit()) {
                        putString(mode, "Night")
                        apply()
                    }
                    println("night mode on")
                }

            }
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
                setUpToolBar()
                binding.myNavHostFragment.findNavController().navigate(R.id.suggestActivityFragment)
                binding.drawerLayout.closeDrawers()
            }
            R.id.activities_to_do -> {
                binding.toolbar.setTitle(R.string.activities_to_do)
                setUpToolBar()
                binding.myNavHostFragment.findNavController()
                    .navigate(R.id.doLaterActivitiesFragment)
                binding.drawerLayout.closeDrawers()

            }
            R.id.completed_activities -> {
                binding.toolbar.setTitle(R.string.complete_activities)
                setUpToolBar()
                binding.myNavHostFragment.findNavController()
                    .navigate(R.id.completedActivitiesFragment)
                binding.drawerLayout.closeDrawers()
            }
        }
        return true
    }


}