package com.gmail.hitoridevelop.nothingtodo

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.hitoridevelop.nothingtodo.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    //TODO save data to room db
    //TODO add are you sure dialog for saving data
    //TODO add snack bar when finishing save and add undo function
    //TODO show list of saved activities when clicking the saved folder icon
    //TODO add a filter to the saved activities
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.navView.bringToFront()
        binding.navView.setNavigationItemSelectedListener(this)

        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)




    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.activities_to_do -> { Toast.makeText(this, "Do Activity", Toast.LENGTH_SHORT).show() }
            R.id.completed_activities -> {Toast.makeText(this, "Finished Activities", Toast.LENGTH_SHORT).show()}
        }
        return true
    }


}