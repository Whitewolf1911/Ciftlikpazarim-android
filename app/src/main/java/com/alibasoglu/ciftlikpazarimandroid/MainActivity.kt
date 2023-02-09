package com.alibasoglu.ciftlikpazarimandroid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.customviews.CustomToolbar
import com.alibasoglu.ciftlikpazarimandroid.databinding.ActivityMainBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.navigateSafe
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController =
            (supportFragmentManager.findFragmentById(binding.navigationHostFragment.id) as NavHostFragment).navController
        binding.bottomNavigationView.apply {
            setupWithNavController(navController)
            setOnItemReselectedListener {} // To prevent reselect item and resetting selected fragment
        }
    }

    fun navBack() {
        navController.navigateUp()
    }

    fun nav(directions: NavDirections, onError: (() -> Unit)? = null) {
        navController.navigateSafe(directions, onError)
    }

    fun configureToolbar(toolbarConfiguration: ToolbarConfiguration?) {
        binding.toolbar.configure(toolbarConfiguration)
    }

    fun hideNavBar() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun showNavBar() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    fun getToolbar(): CustomToolbar = binding.toolbar
}
